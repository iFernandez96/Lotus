package com.example.lotus.Database;

import android.app.Application;
import android.util.Log;

import com.example.lotus.Database.entities.Login;
import com.example.lotus.Database.entities.User;
import com.example.lotus.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class LoginRepo {
    private final LoginDAO loginDAO;
    private ArrayList<User> allLogins;
    private static LoginRepo repository;

    private final UserDao userDao;

    private LoginRepo(Application app) {
        LoginDatabase db = LoginDatabase.getDatabase(app);
        this.loginDAO = db.loginDao();
        this.userDao = db.userDao();
        this.allLogins = (ArrayList<User>) this.userDao.getAllUsers();
    }

    //abstraction to get all records on a thread
    public ArrayList<User> getAllLogins() {
        //uses future
        Future<ArrayList<User>> future = LoginDatabase.databaseWriteExecutor.submit( //want to submit this our thread
                new Callable<ArrayList<User>>() {
                    @Override
                    public ArrayList<User> call() throws Exception {
                        return (ArrayList<User>) userDao.getAllUsers();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
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
    public interface OnUsernameCheckListener {
        void onUsernameChecked(boolean exists);
    }

    public void insertUser2Database(User... user){
        LoginDatabase.databaseWriteExecutor.execute(() ->
        {
            userDao.insert(user);
        });
    }

    public int countUsernames(String username){
        Future<Integer> future = LoginDatabase.databaseWriteExecutor.submit(
                new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return loginDAO.countUsernames(username);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem with getting all User from loginDao in the repo");
        }
        return 0;
    }
}
