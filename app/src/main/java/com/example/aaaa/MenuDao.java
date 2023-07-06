package com.example.aaaa;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
@Dao
public interface MenuDao
{
    @Query("SELECT * FROM MENU WHERE date = :date ")
    Menu getMenuForDate(String date);
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Menu menu);
    @Query("DELETE FROM Menu")
    void deleteAll();
}
