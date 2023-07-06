package com.example.aaaa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MessCard extends AppCompatActivity
{

    SharedPreferences sp;
    String studentRegno;

    TextView name, hostelblock, vegnonveg,registrationo;
    ImageView pic;

    String nameFire, block, category, imageUrl;

    ConstraintLayout back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_card);
        getSupportActionBar().setTitle("My Mess Card");

        sp = getSharedPreferences("login", MODE_PRIVATE);
        studentRegno = sp.getString("regno", "");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Students").document(studentRegno);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                nameFire=documentSnapshot.getString("Name");
                block=documentSnapshot.getString("Hostel block");
                imageUrl=documentSnapshot.getString("Picture");
                if(documentSnapshot.get("Mess category").equals("NV"))
                    category="NON-VEG";
                else
                    category="VEG";
                setImageUrl(imageUrl);
                name=findViewById(R.id.name);
                hostelblock=findViewById(R.id.hostelblock);
                registrationo=findViewById(R.id.registrationo);
                name.setText(nameFire);
                registrationo.setText(studentRegno);
                hostelblock.setText(block);
                back=findViewById(R.id.back);
                vegnonveg=findViewById(R.id.vegnonveg);
                if(category.equals("VEG"))
                {
                    back.setBackgroundColor(Color.parseColor("#8FDB82"));
                    vegnonveg.setText("VEG");
                    getSupportActionBar().hide();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(Color.parseColor("#8FDB82"));
                    }
                }
                else
                {
                    back.setBackgroundColor(Color.parseColor("#EC7269"));
                    vegnonveg.setText("NON-VEG");
                    getSupportActionBar().hide();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(Color.parseColor("#EC7269"));
                    }
                }
            }
        });
    }
    private void setImageUrl(String imageUrl)
    {
        pic = findViewById(R.id.pic); // Replace "imageView" with the ID of your ImageView
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .into(pic);
    }
}