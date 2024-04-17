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
    private static LoginRepo repository;

    private LoginRepo(Application app) {
        LoginDatabase db = LoginDatabase.getDatabase(app);
        this.loginDAO = db.loginDao();
        this.allLogins = (ArrayList<Login>) this.loginDAO.getAllRecords();
    }

    //abstraction to get all records on a thread
    public ArrayList<Login> getAllLogins() {
        //uses future
        Future<ArrayList<Login>> future = LoginDatabase.databaseWriteExecutor.submit( //want to submit this our thread
                new Callable<ArrayList<Login>>() {
                    @Override
                    public ArrayList<Login> call() throws Exception {
                        return (ArrayList<Login>) loginDAO.getAllRecords();
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

    public static LoginRepo getRepo(Application app){
        if (repository != null){
            return repository;
        }
        Future<LoginRepo> future = LoginDatabase.databaseWriteExecutor.submit(
                new Callable<LoginRepo>() {
                    @Override
                    public LoginRepo call() throws Exception {
                        return new LoginRepo(app);
                    }
                }
        );
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "problem getting log repo, thread error");
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
