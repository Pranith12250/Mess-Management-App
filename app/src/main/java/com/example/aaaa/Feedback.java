package com.example.aaaa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

public class Feedback extends AppCompatActivity
{
    RatingBar ratingBar;
    TextView improvementText;
    TextInputLayout feedbacktil;
    EditText feedbackText;
    Button feedbackyes;
    Button feedbackno;
    Button done;

    private FeedbackDatabase mFeedbackDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setTitle("Feedback");
        ratingBar=findViewById(R.id.ratingBar);
        improvementText=findViewById(R.id.mainfeedback);
        feedbackText=findViewById(R.id.feedbackText);
        feedbackyes=findViewById(R.id.feedbackyes);
        feedbackno=findViewById(R.id.feedbackno);
        done=findViewById(R.id.done);
        feedbacktil=findViewById(R.id.feedbacktil);

        mFeedbackDatabase = FeedbackDatabase.getInstance(getApplicationContext());

        Date currentDate=new Date();
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
            {
                float rating=ratingBar.getRating();
                if (rating<5)
                {
                    feedbackyes.setVisibility(View.INVISIBLE);
                    feedbackno.setVisibility(View.INVISIBLE);
                    done.setVisibility(View.VISIBLE);
                    improvementText.setText("What do you feel was lacking today?");
                    improvementText.setVisibility(View.VISIBLE);
                    feedbacktil.setVisibility(View.VISIBLE);
                    done.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            if(feedbackText.getText().toString().isEmpty())
                            {
                                FeedbackData feedbackData=new FeedbackData(currentDate,rating,null);
                                adder(feedbackData);
                            }
                            else
                            {
                                FeedbackData feedbackData=new FeedbackData(currentDate,rating,feedbackText.getText().toString());
                                adder(feedbackData);
                            }
                            Toast.makeText(Feedback.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                else
                {
                    improvementText.setText("Thank you for your rating! Do you want to give any extra Feedback?");
                    improvementText.setVisibility(View.VISIBLE);
                    feedbacktil.setVisibility(View.INVISIBLE);
                    feedbackyes.setVisibility(View.VISIBLE);
                    feedbackno.setVisibility(View.VISIBLE);
                    done.setVisibility(View.INVISIBLE);
                    feedbackyes.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            feedbacktil.setVisibility(View.VISIBLE);
                            done.setVisibility(View.VISIBLE);
                            done.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    FeedbackData feedbackData=new FeedbackData(currentDate,rating,feedbackText.getText().toString());
                                    adder(feedbackData);
                                    Toast.makeText(Feedback.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    });
                    feedbackno.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            FeedbackData feedbackData=new FeedbackData(currentDate,rating,null);
                            adder(feedbackData);
                            Toast.makeText(Feedback.this, "Thank you for your rating!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });
    }
    public void adder(FeedbackData feedbackData)
    {
        FeedbackDatabase.getInstance(this).feedbackDataDao().insert(feedbackData);
    }
}