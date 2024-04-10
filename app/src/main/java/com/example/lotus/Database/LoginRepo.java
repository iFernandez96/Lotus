package com.example.lotus.Database;

import android.app.Application;
import android.util.Log;

import com.example.lotus.Database.entities.Login;
import com.example.lotus.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class LoginRepo {
    private LoginDAO loginDAO;
    private ArrayList<Login> allLogins;

    public LoginRepo(Application app) {
        LoginDatabase db = LoginDatabase.getDatabase(app);
        this.loginDAO = db.loginDao();
        this.allLogins = this.loginDAO.getAllRecords();
    }

    public ArrayList<Login> getAllLogins() {
        Future<ArrayList<Login>> future = LoginDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Login>>() {
                    @Override
                    public ArrayList<Login> call() throws Exception {
                        return loginDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem with getting all Login Logs in the repo");
        }
        return null;
    }

    public void insertLoginLog(Login login){
        LoginDatabase.databaseWriteExecutor.execute(() ->
        {
            loginDAO.insert(login);
        });
    }
}
