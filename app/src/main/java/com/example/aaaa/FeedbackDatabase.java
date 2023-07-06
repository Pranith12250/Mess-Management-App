package com.example.aaaa;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {FeedbackData.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class FeedbackDatabase extends RoomDatabase
{
    public abstract FeedbackDataDao feedbackDataDao();

    private static FeedbackDatabase INSTANCE;

    public static synchronized FeedbackDatabase getInstance(Context context)
    {
        if (INSTANCE == null)
        {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FeedbackDatabase.class, "feedback-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
