package com.example.lotus.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.lotus.Database.entities.Login;
import com.example.lotus.Database.entities.User;
import com.example.lotus.Database.typeConverter.LocalDateTypeConverter;
import com.example.lotus.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//Deleting the 1 will erase the entire database!!!!
@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {Login.class, User.class},version = 2, exportSchema = false)
public abstract class LoginDatabase extends RoomDatabase {

    public static final String DATABASE_NAME= "loginDatabase";
    public static final String USER_TABLE = "UserTable";
    public static final String LOGIN_TABLE = "LoginTable";

    private static volatile LoginDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS =4;
    // way to call new threads.
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS); // want you grab a thread, and have it ready to use
    //Creates our database
    //Want a a thread
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
            databaseWriteExecutor.execute(() -> {
                // Your initialization code here, e.g., inserting default users
                Log.i(MainActivity.TAG, "Database created");
                databaseWriteExecutor.execute(() ->{
                    UserDao dao = INSTANCE.userDao();
                    dao.deleteAll();
                    User admin = new User("admin1", "admin1@admin.com", "admin1");
                    admin.setAdmin(true);
                    dao.insert(admin);

                    User testUser1= new User("testuser1", "testuser1@testuser1.com", "testuser1");
                    dao.insert(testUser1);
                    });
            });
        }
    };

    public abstract LoginDAO loginDao();

    public abstract UserDao userDao();
}
