package com.example.aaaa;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HomePage extends AppCompatActivity
{
    static final String[] foodTimes = {"Breakfast", "Lunch", "Snacks", "Dinner"};
    RecyclerView recyclerView;

    SharedPreferences sp;
    String studentRegno;

    Button messCard;
    Button feedback;
    Button nightCanteen;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().setTitle("Home");
        messCard = findViewById(R.id.messcard);
        feedback=findViewById(R.id.feedback);
        nightCanteen=findViewById(R.id.nightcanteen);

        sp = getSharedPreferences("login", MODE_PRIVATE);
        studentRegno = sp.getString("regno", "");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MyAdapter myAdapter = new MyAdapter(this, foodTimes);
        recyclerView.setAdapter(myAdapter);

        messCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i2=new Intent(getApplicationContext(),MessCard.class);
                startActivity(i2);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent toFeedback=new Intent(getApplicationContext(),Feedback.class);
                startActivity(toFeedback);
            }
        });

        nightCanteen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent nc=new Intent(getApplicationContext(),NightCanteen.class);
                startActivity(nc);
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Myholder>
    {
        Context myContext;
        String myText[];
        Menu myMenu;

        Map<String, Object> mainMap;
        Map<String, Object> breakfastMap;
        Map<String, Object> lunchMap;
        Map<String, Object> snacksMap;
        Map<String, Object> dinnerMap;

        String itemName;
        String itemDescription="";
        String others="";

        MyAdapter(Context context, String[] text)//, Menu menu)
        {
            myContext = context;
            myText = text;
            itemName="Unable to fetch";
            itemDescription="Unable to fetch";
            others="Unable to fetch";
        }

        @NonNull
        @Override
        public MyAdapter.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(myContext);
            View view = layoutInflater.inflate(R.layout.inside_recycler, parent, false);
            return new Myholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Myholder myholder, int position)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yy");
            Date date = new Date(System.currentTimeMillis());
            String currentDate = dateFormat.format(date);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("Menu7").document(currentDate);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
            {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot)
                {
                    if (documentSnapshot.exists())
                    {
                        DocumentReference student=db.collection("Students").document(studentRegno);
                        student.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnap)
                            {
                                if (documentSnap.get("Mess category").equals("NV"))
                                    mainMap = (Map<String, Object>) documentSnapshot.getData().get("NonVeg");
                                else
                                    mainMap = (Map<String, Object>) documentSnapshot.getData().get("Veg");
                                breakfastMap = (Map<String, Object>) mainMap.get("Breakfast");
                                lunchMap = (Map<String, Object>) mainMap.get("Lunch");
                                snacksMap = (Map<String, Object>) mainMap.get("Snacks");
                                dinnerMap = (Map<String, Object>) mainMap.get("Dinner");
                                switch (myholder.getAdapterPosition())
                                {
                                    case 0:
                                        itemName = (String) breakfastMap.get("BreakfastItem");
                                        itemDescription = (String) breakfastMap.get("BreakfastItemDescription");
                                        others = (String) breakfastMap.get("BreakfastOthers");
                                        break;
                                    case 1:
                                        itemName = (String) lunchMap.get("LunchItem");
                                        itemDescription = (String) lunchMap.get("LunchItemDescription");
                                        others = (String) lunchMap.get("LunchOthers");
                                        break;
                                    case 2:
                                        itemName = (String) snacksMap.get("SnacksItem");
                                        itemDescription = (String) snacksMap.get("SnacksItemDescription");
                                        others = (String) snacksMap.get("SnacksOthers");
                                        break;
                                    case 3:
                                        itemName = (String) dinnerMap.get("DinnerItem");
                                        itemDescription = (String) dinnerMap.get("DinnerItemDescription");
                                        others = (String) dinnerMap.get("DinnerOthers");
                                        break;
                                }
                                myholder.textView1.setText(myText[myholder.getAdapterPosition()]);
                                myholder.textView2.setText(itemName);
                                myholder.textView3.setText(itemDescription);
                                myholder.textView4.setText(others);
                            }
                        });
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return myText.length;
        }

        public class Myholder extends RecyclerView.ViewHolder
        {
            TextView textView1;
            TextView textView2;
            TextView textView3;
            TextView textView4;

            public Myholder(@NonNull View itemView)
            {
                super(itemView);
                textView1 = itemView.findViewById(R.id.foodtime);
                textView2 = itemView.findViewById(R.id.foodname);
                textView3 = itemView.findViewById(R.id.fooddescription);
                textView4 = itemView.findViewById(R.id.others);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu)
    {
        getMenuInflater().inflate(R.menu.menuitems, (android.view.Menu) menu);
        return super.onCreateOptionsMenu((android.view.Menu) menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logoutButton:
              sp = getSharedPreferences("login", MODE_PRIVATE);
              sp.edit().putBoolean("logged",false).apply();
              Toast.makeText(this, "Successfully logged out", Toast.LENGTH_LONG).show();
              finish();
              break;
        }
        return super.onOptionsItemSelected((android.view.MenuItem) item);
    }

    @Override
    public void onBackPressed()
    {
      return;
    }
}