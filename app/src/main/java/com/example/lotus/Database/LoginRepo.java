package com.example.lotus.Database;

import android.app.Application;
import android.util.Log;

import com.example.lotus.Database.entities.Statistics;
import com.example.lotus.Database.entities.Phone;
import com.example.lotus.Database.entities.User;
import com.example.lotus.MainActivity;

import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.concurrent.Callable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class LoginRepo {
//    private ArrayList<User> allLogins;
    private static LoginRepo repository;
    private final PhoneDAO phoneDAO;

    private final UserDao userDao;
    private final StatisticsDao StatisticsDao;

    private LoginRepo(Application app) {
        LoginDatabase db = LoginDatabase.getDatabase(app);
        this.userDao = db.userDao();
        this.StatisticsDao = db.statisticsDao();
//        this.allLogins = (ArrayList<User>) this.userDao.getAllUsers();
        repository = this;
        this.phoneDAO = db.phoneDao();
//        this.allLogins = (ArrayList<User>) this.userDao.getAllUsers();
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
//    public ArrayList<User> getAllLogins() {
//        //uses future
//        Future<ArrayList<User>> future = LoginDatabase.databaseWriteExecutor.submit( //want to submit this our thread
//                () -> (ArrayList<User>) userDao.getAllUsers()
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting all Login Logs in the repo");
//        }
//        return null;
//    }

    public static LoginRepo getRepo(Application app){
        if (repository != null){
            return repository;
        }
        Future<LoginRepo> future = LoginDatabase.databaseWriteExecutor.submit(
                () -> new LoginRepo(app)
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
                userDao.insert(user));
    }

    public User getUserByUsername(String username){
        Future<User> future = LoginDatabase.databaseWriteExecutor.submit(
                () -> userDao.getUserByUsername(username)
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem with getting all User from loginDao in the repo");
        }
        return null;
    }

    public void updateUser(String name, String email, String password) {
        LoginDatabase.databaseWriteExecutor.execute(() -> userDao.updateUser(name, email, password));
    }

    public void deleteUser(User user) {
        LoginDatabase.databaseWriteExecutor.execute(() -> {
            userDao.delete(user);
        });
    }

//    public void deleteAll() {
//        LoginDatabase.databaseWriteExecutor.execute(userDao::deleteAll);
//    }

    public void insertStatistics(Statistics statistics) {
        LoginDatabase.databaseWriteExecutor.execute(() -> {
            StatisticsDao.insert(statistics);
        });
    }

    public Statistics getStatisticsByUserID(int userID) {
        Future<Statistics> future = LoginDatabase.databaseWriteExecutor.submit(
                () -> StatisticsDao.getStatisticsByUserID(userID)
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem with getting all Statistics from statisticsDao in the repo");
        }
        return null;
    }

//    public void updateHeadMovementRange(float[] rangeHeadMovement, int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.updateHeadMovementRange(rangeHeadMovement, userID);
//        });
//    }

//    public void deleteAllStatistics() {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.deleteAll();
//        });
//    }
//
//    public int getTotalLogins() {
//        Future<Integer> future = LoginDatabase.databaseWriteExecutor.submit(
//                () -> StatisticsDao.getTotalLogins()
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting total logins from statisticsDao in the repo");
//        }
//        return 0;
//    }
//
//    public int getTotalHeadTriggers() {
//        Future<Integer> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return StatisticsDao.getTotalHeadTriggers();
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting total head triggers from statisticsDao in the repo");
//        }
//        return 0;
//    }
//
//    public int getTotalTimesUsedTracker() {
//        Future<Integer> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return StatisticsDao.getTotalTimesUsedTracker();
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting total times used tracker from statisticsDao in the repo");
//        }
//        return 0;
//    }
//
//    public int getLastUserID() {
//        Future<Integer> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return StatisticsDao.getLastUserID();
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting last user ID from statisticsDao in the repo");
//        }
//        return 0;
//    }
//
//    public int getLastLogoutUserID() {
//        Future<Integer> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return StatisticsDao.getLastLogoutUserID();
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting last logout user ID from statisticsDao in the repo");
//        }
//        return 0;
//    }
//
//    public int getLastTrackerUseUserID() {
//        Future<Integer> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return StatisticsDao.getLastTrackerUseUserID();
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting last tracker use user ID from statisticsDao in the repo");
//        }
//        return 0;
//    }
//
//    public int getTotalLoginsByUserID(int userID) {
//        Future<Integer> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return StatisticsDao.getTotalLoginsByUserID(userID);
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting total logins by user ID from statisticsDao in the repo");
//        }
//        return 0;
//    }
//
//    public int getTotalHeadTriggersByUserID(int userID) {
//        Future<Integer> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return StatisticsDao.getTotalHeadTriggersByUserID(userID);
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting total head triggers by user ID from statisticsDao in the repo");
//        }
//        return 0;
//    }
//
//    public int getTotalTimesUsedTrackerByUserID(int userID) {
//        Future<Integer> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return StatisticsDao.getTotalTimesUsedTrackerByUserID(userID);
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting total times used tracker by user ID from statisticsDao in the repo");
//        }
//        return 0;
//    }
//
//    public int getAverageUseTimeByUserID(int userID) {
//        Future<Integer> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<Integer>() {
//                    @Override
//                    public Integer call() throws Exception {
//                        return StatisticsDao.getAverageUseTimeByUserID(userID);
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting average use time by user ID from statisticsDao in the repo");
//        }
//        return 0;
//    }
//
//    public LocalDateTime getLastTrackerUseByUserID(int userID) {
//        Future<LocalDateTime> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<LocalDateTime>() {
//                    @Override
//                    public LocalDateTime call() throws Exception {
//                        return StatisticsDao.getLastTrackerUseByUserID(userID);
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting last tracker use by user ID from statisticsDao in the repo");
//        }
//        return null;
//    }
//
//    public String getLastLoginByUserID(int userID) {
//        Future<String> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<String>() {
//                    @Override
//                    public String call() throws Exception {
//                        return StatisticsDao.getLastLoginByUserID(userID);
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting last login by user ID from statisticsDao in the repo");
//        }
//        return null;
//    }
//
//    public float[] getRangeHeadMovementByUserID(int userID) {
//        Future<float[]> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<float[]>() {
//                    @Override
//                    public float[] call() throws Exception {
//                        return StatisticsDao.getRangeHeadMovementByUserID(userID);
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting range head movement by user ID from statisticsDao in the repo");
//        }
//        return null;
//    }
//
//    public void updateTotalLogins(int totalLogins, int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.updateTotalLogins(totalLogins, userID);
//        });
//    }
//
//    public void updateTotalHeadTriggers(int totalHeadTriggers, int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.updateTotalHeadTriggers(totalHeadTriggers, userID);
//        });
//    }
//
//    public void updateTotalTimesUsedTracker(int totalTimesUsedTracker, int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.updateTotalTimesUsedTracker(totalTimesUsedTracker, userID);
//        });
//    }
//
//    public void updateAverageUseTime(int averageUseTime, int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.updateAverageUseTime(averageUseTime, userID);
//        });
//    }
//
//    public void updateLastTrackerUse(LocalDateTime lastTrackerUse, int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.updateLastTrackerUse(lastTrackerUse, userID);
//        });
//    }
//
//    public void updateLastLogin(LocalDateTime lastLogin, int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.updateLastLogin(lastLogin, userID);
//        });
//    }
//
//    public void updateRangeHeadMovement(float[] rangeHeadMovement, int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.updateRangeHeadMovement(rangeHeadMovement, userID);
//        });
//    }
//
//    public void updateLastLogout(LocalDateTime lastLogout, int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.updateLastLogout(lastLogout, userID);
//        });
//    }
//
//    public void updateTotalLoginsByUserID(int totalLogins, int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.updateTotalLoginsByUserID(totalLogins, userID);
//        });
//    }
//
//    public void updateTotalHeadTriggersByUserID(int totalHeadTriggers, int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.updateTotalHeadTriggersByUserID(totalHeadTriggers, userID);
//        });
//    }
//
//    public void deleteStatisticsByUserID(int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.deleteStatisticsByUserID(userID);
//        });
//    }
//
//    public void deleteAllStatisticsByUserID(int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.deleteAllStatisticsByUserID(userID);
//        });
//    }
//
//    public void updateTotalTimesUsedTrackerByUserID(int totalTimesUsedTracker, int userID) {
//        LoginDatabase.databaseWriteExecutor.execute(() -> {
//            StatisticsDao.updateTotalTimesUsedTrackerByUserID(totalTimesUsedTracker, userID);
//        });
//    }
//
//    public String getLastLogoutByUserID(int userID) {
//        Future<String> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<String>() {
//                    @Override
//                    public String call() throws Exception {
//                        return StatisticsDao.getLastLogoutByUserID(userID);
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting last logout by user ID from statisticsDao in the repo");
//        }
//        return null;
//    }
//
//    public ArrayList<Statistics> getAllStatistics() {
//        Future<ArrayList<Statistics>> future = LoginDatabase.databaseWriteExecutor.submit(
//                new Callable<ArrayList<Statistics>>() {
//                    @Override
//                    public ArrayList<Statistics> call() throws Exception {
//                        return (ArrayList<Statistics>) StatisticsDao.getAllStatistics();
//                    }
//                }
//        );
//        try {
//            return future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            Log.i(MainActivity.TAG, "Problem with getting all Statistics from statisticsDao in the repo");
//        }
//        return null;
//    }

    public void updateAllUserStatistics(Statistics statistics) {
        LoginDatabase.databaseWriteExecutor.execute(() -> StatisticsDao.updateAllUserStatistics(statistics));
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

    // Delete User from the database
    public void deleteUser(){
        LoginDatabase.databaseWriteExecutor.execute(userDao::deleteAll);
    }


}
