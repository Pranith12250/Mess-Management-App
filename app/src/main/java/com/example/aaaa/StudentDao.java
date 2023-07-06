package com.example.aaaa;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao
{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Student student);
    @Query("DELETE FROM Student_List")
    void deleteAll();
    @Query("SELECT * FROM Student_List")
    List<Student> getSortedRegNos();
//    LiveData<List<Student>> getSortedRegNos(); // use when you want to make live changes to data displayed on ui
}
