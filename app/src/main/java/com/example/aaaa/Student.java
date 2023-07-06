package com.example.aaaa;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Student_List")
public class Student
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Registration number")
    public String mRegno;
    @NonNull
    @ColumnInfo(name = "Name")
    public String mName;
    @NonNull
    @ColumnInfo(name = "pass")
    private String mPassword;
    @NonNull
    @ColumnInfo(name = "vnv")
    private String mVegNonVeg;
    @NonNull
    @ColumnInfo(name = "hostelblock")
    private String mHostelBlock;
    @NonNull
    @ColumnInfo(name = "picture")
    private int mPicture;
    public Student(@NonNull String regno, @NonNull String name, @NonNull String password,@NonNull String vegNonVeg,String mHostelBlock, @NonNull int mPicture)
    {
        this.mRegno=regno;
        this.mName=name;
        this.mPassword=password;
        this.mVegNonVeg=vegNonVeg;
        this.mHostelBlock=mHostelBlock;
        this.mPicture=mPicture;
    }
    public String getRegNo()
    {
        return this.mRegno;
    }
    public String getName()
    {
        return this.mName;
    }
    public String getPassword()
    {
        return this.mPassword;
    }
    public String getVegNonVeg()
    {
        return mVegNonVeg;
    }
    public String getHostelBlock()
    {
        return mHostelBlock;
    }
    public int getPicture()
    {
        return mPicture;
    }
}
