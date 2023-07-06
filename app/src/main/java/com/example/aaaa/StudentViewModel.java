package com.example.aaaa;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentViewModel extends AndroidViewModel
{
    private StudentRepository mRepository;
    public final List<Student> mAllStudents;
    public StudentViewModel(Application application)
    {
        super(application);
        mRepository=new StudentRepository(application);
        mAllStudents=mRepository.getAllStudents();
    }
    List<Student> getAllStudents()
    {
        return mAllStudents;
    }
    public void insert(Student student)
    {
        mRepository.insert(student);
    }
}
