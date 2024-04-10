package com.example.lotus.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.lotus.Database.entities.Login;
import com.example.lotus.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Deleting the 1 will erase the entire database!!!!
@Database(entities = {Login.class},version = 1, exportSchema = false)
public abstract class LoginDatabase extends RoomDatabase {

    public static final String DATABASE_NAME= "login_database";
    public static final String LOGIN_TABLE = "LoginTable";

    private static volatile LoginDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS =4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static LoginDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (LoginDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LoginDatabase.class,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
    return INSTANCE;
    }
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            //Log.i(MainActivity.TAG, DAtABASE CREATED)
            //TODO: add databaseWriteExecutor.execute(() -> {...}
        }
    };

    public abstract LoginDAO loginDao();
}
