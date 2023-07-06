package com.example.aaaa;

import static com.example.aaaa.StudentRoomDatabase.databaseWriteExecutor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Menu.class}, version=1)
public abstract class MenuDatabase extends RoomDatabase
{
    public abstract MenuDao menuDao();
    private static final String DATABASE_NAME = "menu_database";
    private static volatile MenuDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static synchronized MenuDatabase getInstance(Context context)
    {
        if (INSTANCE == null)
        {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MenuDatabase.class, DATABASE_NAME)
                    .addCallback(mRoomDatabaseCallback).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
    public static RoomDatabase.Callback mRoomDatabaseCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                MenuDao dao = INSTANCE.menuDao();
                dao.deleteAll();
                BreakfastItem bi1=new BreakfastItem(1,"Chole Bhature","Chole Bhature is a combination of chana masala and bhatura/puri, a deep-fried bread made from maida."," Boiled eggs, Poha, Chutney, Milk and coffee");
                LunchItem li1=new LunchItem(2,"Chicken 65","Chicken 65 is a spicy, deep-fried chicken dish","Rice, appalam, Rasam, Sambar, Mixed vegetable");
                SnacksItem si1=new SnacksItem(3,"Samosa","A samosa or singara is a fried South Asian pastry with a savoury filling, including ingredients such as spiced potatoes, onions, and peas.","Tea, coffee");
                DinnerItem di1=new DinnerItem(4,"Uttapam","An uttapam is a type of dosa from South India. Unlike a typical dosa, which is crisp and crepe-like, an uttapam is thicker, with toppings.","Rice, sambar, chutney, watermelons, Milk and coffee");
                Menu menu = new Menu("01/03/2023",bi1,li1,si1,di1);
                dao.insert(menu);
           });
        }
    };
}
