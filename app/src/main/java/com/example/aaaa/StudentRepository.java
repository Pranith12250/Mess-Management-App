package com.example.aaaa;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentRepository
{
    private StudentDao mStudentDao;
    public List<Student> mAllStudents;
    public StudentRepository(Application application)
    {
        StudentRoomDatabase db=StudentRoomDatabase.getDatabase(application);
        mStudentDao= db.studentDao();
        mAllStudents=mStudentDao.getSortedRegNos();
    }
    List<Student> getAllStudents()
    {
        return mAllStudents;
    }
    void insert(Student student)
    {
        StudentRoomDatabase.databaseWriteExecutor.execute(()->{mStudentDao.insert(student);});
    }
}
