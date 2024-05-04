package com.example.lotus.Database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.lotus.Database.LoginDatabase;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity(tableName = LoginDatabase.STATISTICS_TABLE,
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "userID",
                onDelete = CASCADE))
public class Statistics {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userID;
    private int totalLogins;

    private int averageUseTime;
    private float[] rangeHeadMovement;
    private int totalHeadTriggers;
    private int totalTimesUsedTracker;
    private LocalDateTime lastTrackerUse;

    private LocalDateTime lastLogin;
    private LocalDateTime lastLogout;

    public Statistics(int userID) {
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTotalLogins() {
        return totalLogins;
    }

    public void setTotalLogins(int totalLogins) {
        this.totalLogins = totalLogins;
    }

    public int getAverageUseTime() {
        return averageUseTime;
    }

    public void setAverageUseTime(int averageUseTime) {
        this.averageUseTime = averageUseTime;
    }

    public float[] getRangeHeadMovement() {
        return rangeHeadMovement;
    }

    public int getTotalHeadTriggers() {
        return totalHeadTriggers;
    }

    public void setTotalHeadTriggers(int totalHeadTriggers) {
        this.totalHeadTriggers = totalHeadTriggers;
    }

    public int getTotalTimesUsedTracker() {
        return totalTimesUsedTracker;
    }

    public void setTotalTimesUsedTracker(int totalTimesUsedTracker) {
        this.totalTimesUsedTracker = totalTimesUsedTracker;
    }

    public LocalDateTime getLastTrackerUse() {
        return lastTrackerUse;
    }

    public void setLastTrackerUse(LocalDateTime lastTrackerUse) {
        this.lastTrackerUse = lastTrackerUse;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getLastLogout() {
        return lastLogout;
    }

    public void setLastLogout(LocalDateTime lastLogout) {
        this.lastLogout = lastLogout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return id == that.id && userID == that.userID && totalLogins == that.totalLogins && averageUseTime == that.averageUseTime && totalHeadTriggers == that.totalHeadTriggers && totalTimesUsedTracker == that.totalTimesUsedTracker && Arrays.equals(rangeHeadMovement, that.rangeHeadMovement) && Objects.equals(lastTrackerUse, that.lastTrackerUse) && Objects.equals(lastLogin, that.lastLogin) && Objects.equals(lastLogout, that.lastLogout);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, userID, totalLogins, averageUseTime, totalHeadTriggers, totalTimesUsedTracker, lastTrackerUse, lastLogin, lastLogout);
        result = 31 * result + Arrays.hashCode(rangeHeadMovement);
        return result;
    }

    public void setRangeHeadMovement(float[] rangeHeadMovement) {
        this.rangeHeadMovement = rangeHeadMovement;
    }
}
