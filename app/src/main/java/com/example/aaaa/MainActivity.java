package com.example.aaaa;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    SharedPreferences sp;
//    EditText regno,pwd;
    TextInputLayout regNoTextInputLayout;
    TextInputLayout passwordTextInputLayout;
    TextInputEditText regNoEditText;
    TextInputEditText passwordEditText;
    Button enter;
    private StudentViewModel mStudentViewModel;
    List<Student> allStudents;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backup_activity_main);
        getSupportActionBar().setTitle("Login");
        sp=getSharedPreferences("login",MODE_PRIVATE);
        if(sp.getBoolean("logged",false))
            goToHomePage();
        regNoTextInputLayout = findViewById(R.id.regNoTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        regNoEditText = findViewById(R.id.regNoEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        enter=findViewById(R.id.enter);
        mStudentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        allStudents=mStudentViewModel.getAllStudents();
        int len=allStudents.size();
        enter.setOnClickListener(new View.OnClickListener()
        {
            int position;
            @Override
            public void onClick(View view)
            {
                if(validateInputs())
                {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("Students").document(regNoEditText.getText().toString());
//                    FirebaseApp.initializeApp(MainActivity.this);
//                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//
//                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
//                    {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot)
//                        {
//                            if (documentSnapshot.exists())
//                            {
//                                if(passwordEditText.getText().toString().equals(documentSnapshot.getString("Password")))
//                                {
//                                    sp.edit().putBoolean("logged", true).apply();
//                                    sp.edit().putString("regno", regNoEditText.getText().toString()).apply();
//                                    goToHomePage();
//                                }
//                                else
//                                    Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
//                            }
//                            else
//                            {
//                                Toast.makeText(MainActivity.this, "Registration number not found", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });

                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot)
                        {
                            if (documentSnapshot.exists())
                            {
                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                String reg = regNoEditText.getText().toString() + "@vitap.ac.in";
                                String pass = passwordEditText.getText().toString();
                                firebaseAuth.signInWithEmailAndPassword(reg, pass)
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>()
                                        {
                                            @Override
                                            public void onSuccess(AuthResult authResult)
                                            {
                                                sp.edit().putBoolean("logged", true).apply();
                                                sp.edit().putString("regno", regNoEditText.getText().toString()).apply();
                                                goToHomePage();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener()
                                        {
                                            @Override
                                            public void onFailure(@NonNull Exception e)
                                            {
                                                Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            else
                                Toast.makeText(MainActivity.this, "Registration number not found", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
    private boolean validateInputs()
    {
        boolean isValid = true;
        String regNoInput = regNoEditText.getText().toString().trim();
        if (TextUtils.isEmpty(regNoInput))
        {
            regNoTextInputLayout.setError("Registration number is required");
            isValid = false;
        }
        else if (!isValidRegNo(regNoInput))
        {
            regNoTextInputLayout.setError("Invalid registration number format");
            isValid = false;
        }
        else
            regNoTextInputLayout.setError(null);
        String passwordInput = passwordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(passwordInput))
        {
            passwordTextInputLayout.setError("Password is required");
            isValid = false;
        }
        else if (!isValidPassword(passwordInput))
        {
            passwordTextInputLayout.setError("Password must be at least 6 characters long");
            isValid = false;
        }
        else
            passwordTextInputLayout.setError(null);
        return isValid;
    }
    private boolean isValidRegNo(String regNo)
    {
        if (regNo.length()!=9)
            return false;
        if(!(Character.isDigit(regNo.charAt(0)) && Character.isDigit(regNo.charAt(1))))
            return false;
        if(!(Character.isLetter(regNo.charAt(2)) && Character.isLetter(regNo.charAt(3)) && Character.isLetter(regNo.charAt(4))))
            return false;
        if(!(Character.isDigit(regNo.charAt(5)) && Character.isDigit(regNo.charAt(6)) && Character.isDigit(regNo.charAt(7)) && Character.isDigit(regNo.charAt(8))))
            return false;
        return true;
    }
    private boolean isValidPassword(String password)
    {
        return true;
    }
    public void goToHomePage()
    {
        Intent i=new Intent(getApplicationContext(),HomePage.class);
        startActivity(i);
    }
}