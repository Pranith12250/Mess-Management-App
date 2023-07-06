package com.example.aaaa;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "feedback_data")
public class FeedbackData
{
    @PrimaryKey
    @NonNull
    private Date date;
    @NonNull
    @ColumnInfo(name = "rating")
    private float starCount;
    @ColumnInfo(name = "suggestion")
    private String suggestion;
    public FeedbackData(Date date, float starCount, String suggestion)
    {
        this.date=date;
        this.starCount=starCount;
        this.suggestion=suggestion;
    }
    public Date getDate()
    {
        return this.date;
    }
    public float getStarCount()
    {
        return this.starCount;
    }
    public String getSuggestion()
    {
        return this.suggestion;
    }
    public void setDate(@NonNull Date date)
    {
        this.date = date;
    }
    public void setStarCount(@NonNull float starCount)
    {
        this.starCount=starCount;
    }
    public void setSuggestion(String suggestion)
    {
        this.suggestion=suggestion;
    }
}
