package com.example.lotus.Database;

import android.app.Application;
import android.util.Log;

import com.example.lotus.Database.entities.Phone;
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
    private final PhoneDAO phoneDAO;

    private final UserDao userDao;

    private LoginRepo(Application app) {
        LoginDatabase db = LoginDatabase.getDatabase(app);
        this.loginDAO = db.loginDao();
        this.userDao = db.userDao();
        this.phoneDAO = db.phoneDao();
        this.allLogins = (ArrayList<User>) this.userDao.getAllUsers();
    }
    /*
    public void addUserWithPhone(User user, Phone phone) {
        LoginDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
            phoneDAO.insert(phone);
        });
    }
     */
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

    public void deleteUser(User user){
        LoginDatabase.databaseWriteExecutor.execute(() -> {
            userDao.delete(user);
        });
    }
    public User getUserByUsername(String username){
        Future<User> future = LoginDatabase.databaseWriteExecutor.submit(
                new Callable<User>() {
                    @Override
                    public User call() throws Exception {
                        return userDao.getUserByUsername(username);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem with getting all User from loginDao in the repo");
        }
        return null;
    }

    public void updateUser(String name, String email, String password) {
        LoginDatabase.databaseWriteExecutor.execute(() -> {
            userDao.updateUser(name, email, password);
        });
    }

    // get a phone by the user id
    public Phone getPhoneByUserID(int userID){
        Future<Phone> future = LoginDatabase.databaseWriteExecutor.submit(
                new Callable<Phone>() {
                    @Override
                    public Phone call() throws Exception {
                        return phoneDAO.getPhoneByUserID(userID);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem with getting all User from loginDao in the repo");
        }
        return null;
    }

    // insert the phone into the database
    public void insertPhone(Phone... phone){
        LoginDatabase.databaseWriteExecutor.execute(() ->
        {
            phoneDAO.insert(phone);
        });
    }

    // insert a model name into the database
    public void insertModelName(String model_name, int userID){
        LoginDatabase.databaseWriteExecutor.execute(() ->
        {
            phoneDAO.insertModelName(model_name, userID);
        });
    }
    // insert a brand into the database
    public void insertBrand(String brand, int userID){
        LoginDatabase.databaseWriteExecutor.execute(() ->
        {
            phoneDAO.insertBrand(brand, userID);
        });
    }
    // insert a firmware version into the database
    public void insertFirmwareVersion(String firmware_version, int userID){
        LoginDatabase.databaseWriteExecutor.execute(() ->
        {
            phoneDAO.insertFirmwareVersion(firmware_version, userID);
        });
    }
    // insert an android version into the database
    public void insertAndroidVersion(String android_version, int userID){
        LoginDatabase.databaseWriteExecutor.execute(() ->
        {
            phoneDAO.insertAndroidVersion(android_version, userID);
        });
    }
    // insert a security patch into the database
    public void insertSecurityPatch(String security_patch, int userID){
        LoginDatabase.databaseWriteExecutor.execute(() ->
        {
            phoneDAO.insertSecurityPatch(security_patch, userID);
        });
    }
    // insert a build number into the database
    public void insertBuildNumber(String build_number, int userID){
        LoginDatabase.databaseWriteExecutor.execute(() ->
        {
            phoneDAO.insertBuildNumber(build_number, userID);
        });
    }
    // insert a kernel version into the database
    public void insertKernelVersion(String kernel_version, int userID){
        LoginDatabase.databaseWriteExecutor.execute(() ->
        {
            phoneDAO.insertKernelVersion(kernel_version, userID);
        });
    }
    // insert a baseband version into the database
    public void insertBasebandVersion(String baseband_version, int userID){
        LoginDatabase.databaseWriteExecutor.execute(() ->
        {
            phoneDAO.insertBasebandVersion(baseband_version, userID);
        });
    }
    //update the database with the phone details
    public void updateDatabaseWithPhoneDetails(Phone phone){
        LoginDatabase.databaseWriteExecutor.execute(() -> {
            phoneDAO.updatePhoneDetails(phone);
        });
    }
    // delete all the data of the user's phone
    public void deletePhone(Phone phone){
        LoginDatabase.databaseWriteExecutor.execute(() -> {
            phoneDAO.delete(phone);
        });
    }

}
