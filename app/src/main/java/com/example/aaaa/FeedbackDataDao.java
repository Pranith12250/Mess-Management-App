package com.example.aaaa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface FeedbackDataDao
{
    @Query("SELECT * FROM feedback_data WHERE date = :date ")
    FeedbackData getByDate(Date date);
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(FeedbackData feedbackData);
    @Delete
    void delete(FeedbackData feedbackData);
    @Query("SELECT * FROM feedback_data ORDER BY date DESC LIMIT 1")
    FeedbackData getLatestFeedback();

}
