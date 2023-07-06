package com.example.aaaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NightCanteen extends AppCompatActivity
{
    List<NightCanteenItems> nightCanteenItems;
    RecyclerView recyclerView2;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_canteen);
        getSupportActionBar().setTitle("Night Canteen");
        textView=findViewById(R.id.textView2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("nightCanteen");
         nightCanteenItems= new ArrayList<>();

         myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                nightCanteenItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    NightCanteenItems nightCanteenItems1 = snapshot.getValue(NightCanteenItems.class);
                    nightCanteenItems.add(nightCanteenItems1);
                }
                textView.setVisibility(View.INVISIBLE);
                recyclerView2 = findViewById(R.id.recyclerView2);
                NightCanteen.MyAdapter2 myAdapter2 = new MyAdapter2(NightCanteen.this, nightCanteenItems);
                recyclerView2.setLayoutManager(new LinearLayoutManager(NightCanteen.this, LinearLayoutManager.VERTICAL, false));
                recyclerView2.setAdapter(myAdapter2);
            }
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(NightCanteen.this, "Error bro", Toast.LENGTH_SHORT).show();
            }
        });
}

    class MyAdapter2 extends RecyclerView.Adapter<NightCanteen.MyAdapter2.Myholder2>
    {
        Context myContext;
        List<NightCanteenItems> nightCanteenItems;

        MyAdapter2(Context context,List<NightCanteenItems> nightCanteenItems)//, String[] text, Menu menu)
        {
            myContext = context;
            this.nightCanteenItems=nightCanteenItems;
        }

        @NonNull
        @Override
        public NightCanteen.MyAdapter2.Myholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(myContext);
            View view = layoutInflater.inflate(R.layout.inside_night_canteen_card, parent, false);
            return new NightCanteen.MyAdapter2.Myholder2(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NightCanteen.MyAdapter2.Myholder2 myholder2, int position)
        {
            myholder2.name.setText(nightCanteenItems.get(position).getName());
            myholder2.desc.setText(nightCanteenItems.get(position).getDescription());
            myholder2.price.setText("₹"+nightCanteenItems.get(position).getPrice());
        }

        @Override
        public int getItemCount()
        {
            return nightCanteenItems.size();
        }

        public class Myholder2 extends RecyclerView.ViewHolder
        {
            TextView name;
            TextView desc;
            TextView price;

            public Myholder2(@NonNull View itemView)
            {
                super(itemView);
                name = itemView.findViewById(R.id.foodnamenight);
                desc = itemView.findViewById(R.id.fooddescriptionnight);
                price = itemView.findViewById(R.id.price);
            }
        }
    }
}