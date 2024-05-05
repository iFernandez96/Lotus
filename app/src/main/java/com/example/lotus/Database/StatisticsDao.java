package com.example.lotus.Database;

import androidx.room.Dao;
//import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.lotus.Database.entities.Statistics;

import java.time.LocalDateTime;
//import java.util.List;

@Dao
public interface StatisticsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Statistics... statistics);

//    @Delete
//    void delete(Statistics statistics);
//
//    @Query("SELECT * FROM " + LoginDatabase.STATISTICS_TABLE + " ORDER BY userID")
//    List<Statistics> getAllStatistics();
//
//    @Query("DELETE from " + LoginDatabase.STATISTICS_TABLE)
//    void deleteAll();

    @Query("SELECT * FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE userID = :userID")
    Statistics getStatisticsByUserID(int userID);

//    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE + " SET rangeHeadMovement = :rangeHeadMovement WHERE userID = :userID")
//    void updateHeadMovementRange(float[] rangeHeadMovement, int userID);
//
//    // get the total number of logins
//    @Query("SELECT SUM(totalLogins) FROM " + LoginDatabase.STATISTICS_TABLE)
//    int getTotalLogins();
//
//    // get the total number of head triggers
//    @Query("SELECT SUM(totalHeadTriggers) FROM " + LoginDatabase.STATISTICS_TABLE)
//    int getTotalHeadTriggers();
//
//    // get the total number of times the tracker was used
//    @Query("SELECT SUM(totalTimesUsedTracker) FROM " + LoginDatabase.STATISTICS_TABLE)
//    int getTotalTimesUsedTracker();
//
//    // get the last userID that logged in
//    @Query("SELECT userID FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE lastLogin = (SELECT MAX(lastLogin) FROM " + LoginDatabase.STATISTICS_TABLE + ")")
//    int getLastUserID();
//
//    // get the last userID that logged out
//    @Query("SELECT userID FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE lastLogout = (SELECT MAX(lastLogout) FROM " + LoginDatabase.STATISTICS_TABLE + ")")
//    int getLastLogoutUserID();
//
//    // get the last userID that used the tracker
//    @Query("SELECT userID FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE lastTrackerUse = (SELECT MAX(lastTrackerUse) FROM " + LoginDatabase.STATISTICS_TABLE + ")")
//    int getLastTrackerUseUserID();
//
//    // get the total number of logins for a specific user
//    @Query("SELECT totalLogins FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE userID = :userID")
//    int getTotalLoginsByUserID(int userID);
//
//    // get the total number of head triggers for a specific user
//    @Query("SELECT totalHeadTriggers FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE userID = :userID")
//    int getTotalHeadTriggersByUserID(int userID);
//
//    // get the total number of times the tracker was used for a specific user
//    @Query("SELECT totalTimesUsedTracker FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE userID = :userID")
//    int getTotalTimesUsedTrackerByUserID(int userID);
//
//    // get the average use time for a specific user
//    @Query("SELECT averageUseTime FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE userID = :userID")
//    int getAverageUseTimeByUserID(int userID);
//
//    // get the last tracker use for a specific user
//    @Query("SELECT lastTrackerUse FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE userID = :userID")
//    LocalDateTime getLastTrackerUseByUserID(int userID);
//
//    // get the last login for a specific user
//    @Query("SELECT lastLogin FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE userID = :userID")
//    String getLastLoginByUserID(int userID);
//
    // get the range of head movement for a specific user
    @Query("SELECT rangeHeadMovementBottom FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE userID = :userID")
    float getRangeHeadMovementBottom(int userID);

    // get the range of head movement for a specific user
    @Query("SELECT rangeHeadMovementTop FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE userID = :userID")
    float getRangeHeadMovementTop(int userID);
    // update the total logins for a specific user
    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE + " SET totalLogins = :totalLogins WHERE userID = :userID")
    void updateTotalLogins(int totalLogins, int userID);

    // update the total head triggers for a specific user
    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE + " SET totalHeadTriggers = :totalHeadTriggers WHERE userID = :userID")
    void updateTotalHeadTriggers(int totalHeadTriggers, int userID);

    // update the total times the tracker was used for a specific user
    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE + " SET totalTimesUsedTracker = :totalTimesUsedTracker WHERE userID = :userID")
    void updateTotalTimesUsedTracker(int totalTimesUsedTracker, int userID);

    // update the average use time for a specific user
    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE + " SET averageUseTime = :averageUseTime WHERE userID = :userID")
    void updateAverageUseTime(int averageUseTime, int userID);

    // update the last tracker use for a specific user
    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE + " SET lastTrackerUse = :lastTrackerUse WHERE userID = :userID")
    void updateLastTrackerUse(LocalDateTime lastTrackerUse, int userID);

    // update the last login for a specific user
    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE + " SET lastLogin = :lastLogin WHERE userID = :userID")
    void updateLastLogin(LocalDateTime lastLogin, int userID);

    // update the range of head movement for a specific user
    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE +
            " SET rangeHeadMovementBottom = :rangeHeadMovementBottom, rangeHeadMovementTop = :rangeHeadMovementTop WHERE userID = :userID")
    void updateRangeHeadMovement(float rangeHeadMovementBottom, float rangeHeadMovementTop, int userID);

    // update the last logout for a specific user
    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE + " SET lastLogout = :lastLogout WHERE userID = :userID")
    void updateLastLogout(LocalDateTime lastLogout, int userID);

//    // get the last logout for a specific user
//    @Query("SELECT lastLogout FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE userID = :userID")
//    String getLastLogoutByUserID(int userID);
//
//    // Delete all statistics for a specific user
//    @Query("DELETE FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE userID = :userID")
//    void deleteStatisticsByUserID(int userID);
//
//    // update the total logins for a specific user
//    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE + " SET totalLogins = :totalLogins WHERE userID = :userID")
//    void updateTotalLoginsByUserID(int totalLogins, int userID);
//
//    // update the total head triggers for a specific user
//    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE + " SET totalHeadTriggers = :totalHeadTriggers WHERE userID = :userID")
//    void updateTotalHeadTriggersByUserID(int totalHeadTriggers, int userID);
//
//
//    @Query("DELETE FROM " + LoginDatabase.STATISTICS_TABLE + " WHERE userID = :userID")
//    void deleteAllStatisticsByUserID(int userID);
//
//
//    // update the total times the tracker was used for a specific user
//    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE + " SET totalTimesUsedTracker = :totalTimesUsedTracker WHERE userID = :userID")
//    void updateTotalTimesUsedTrackerByUserID(int totalTimesUsedTracker, int userID);

    @Transaction
    default void updateAllUserStatistics(Statistics statistics) {
        updateRangeHeadMovement(statistics.getRangeHeadMovementBottom(), statistics.getRangeHeadMovementTop(), statistics.getUserID());
        updateTotalLogins(statistics.getTotalLogins(), statistics.getUserID());
        updateTotalHeadTriggers(statistics.getTotalHeadTriggers(), statistics.getUserID());
        updateTotalTimesUsedTracker(statistics.getTotalTimesUsedTracker(), statistics.getUserID());
        updateAverageUseTime(statistics.getAverageUseTime(), statistics.getUserID());
        updateLastLogin(statistics.getLastLogin(), statistics.getUserID());
        updateLastLogout(statistics.getLastLogout(), statistics.getUserID());
        updateLastTrackerUse(statistics.getLastTrackerUse(), statistics.getUserID());
    }

    @Query("UPDATE " + LoginDatabase.STATISTICS_TABLE +
            " SET rangeHeadMovementBottom = :rangeHeadMovementBottom, rangeHeadMovementTop = :rangeHeadMovementTop, " +
            "totalLogins = :totalLogins, totalHeadTriggers = :totalHeadTriggers, " +
            "totalTimesUsedTracker = :totalTimesUsedTracker, averageUseTime = :averageUseTime, " +
            "lastLogin = :lastLogin, lastLogout = :lastLogout, lastTrackerUse = :lastTrackerUse " +
            "WHERE userID = :userID")
    void updateAllUserStatistics(float rangeHeadMovementBottom, float rangeHeadMovementTop, int totalLogins, int totalHeadTriggers,
                                 int totalTimesUsedTracker, int averageUseTime, LocalDateTime lastLogin, LocalDateTime lastLogout,
                                 LocalDateTime lastTrackerUse, int userID);



}
