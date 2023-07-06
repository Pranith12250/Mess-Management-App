package com.example.aaaa;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Student.class}, version=1, exportSchema = false)
public abstract class StudentRoomDatabase extends RoomDatabase
{
    public abstract StudentDao studentDao();
    private static volatile StudentRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static StudentRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE==null)
        {
            synchronized(StudentRoomDatabase.class)
            {
                if (INSTANCE==null)
                {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),StudentRoomDatabase.class,"Student_List").addCallback(sRoomDatabaseCallback).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
    public static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onCreate(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                StudentDao dao = INSTANCE.studentDao();
                dao.deleteAll();
                Student student = new Student("20BCE7388","S Pranith","admin1@pran", "NV","MH4",R.drawable.photo__1_);
                dao.insert(student);
                student = new Student("20BCD7262","Prem Sai","admin2@prem", "NV","MH4",R.drawable.prem_pic);
                dao.insert(student);
                student = new Student("20BCE7107","Divya Sai","admin3@divya", "NV","LH1",R.drawable.divya_pic);
                dao.insert(student);
                student = new Student("20BCN7025","Ashfaaq","123456","V","MH1",R.drawable.ashfaaq_pic);
                dao.insert(student);
                student = new Student("20BCN7020","Ganesh","123456","V","MH2",R.drawable.ganesh_pic);
                dao.insert(student);
                student = new Student("20BCE7648","Dhyan","123456","NV","MH4",R.drawable.dhyan_pic);
                dao.insert(student);
                student = new Student("20BCE7362","B Sigma Preetham","sigma@123","V","MH3",R.drawable.preetham_pic);
                dao.insert(student);
            });
        }
    };
}
