package com.example.lotus.Database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.lotus.Database.LoginDatabase;
import com.example.lotus.Database.typeConverter.FloatStringTypeConverter;

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
    private float rangeHeadMovementBottom;
    private float rangeHeadMovementTop;
    private int totalHeadTriggers;
    private int totalTimesUsedTracker;
    private LocalDateTime lastTrackerUse;

    private LocalDateTime lastLogin;
    private LocalDateTime lastLogout;

    public Statistics(int userID) {
        this.userID = userID;
        this.totalLogins = 0;
        this.averageUseTime = 0;
        this.rangeHeadMovementBottom = 0;
        this.rangeHeadMovementTop = 0;
        this.totalHeadTriggers = 0;
        this.totalTimesUsedTracker = 0;
        this.lastTrackerUse = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
        this.lastLogout = LocalDateTime.now();

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

    public float getRangeHeadMovementBottom() {
        return rangeHeadMovementBottom;
    }

    public void setRangeHeadMovementBottom(float rangeHeadMovementBottom) {
        this.rangeHeadMovementBottom = rangeHeadMovementBottom;
    }

    public float getRangeHeadMovementTop() {
        return rangeHeadMovementTop;
    }

    public void setRangeHeadMovementTop(float rangeHeadMovementTop) {
        this.rangeHeadMovementTop = rangeHeadMovementTop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return id == that.id && userID == that.userID && totalLogins == that.totalLogins && averageUseTime == that.averageUseTime && Float.compare(rangeHeadMovementBottom, that.rangeHeadMovementBottom) == 0 && Float.compare(rangeHeadMovementTop, that.rangeHeadMovementTop) == 0 && totalHeadTriggers == that.totalHeadTriggers && totalTimesUsedTracker == that.totalTimesUsedTracker && Objects.equals(lastTrackerUse, that.lastTrackerUse) && Objects.equals(lastLogin, that.lastLogin) && Objects.equals(lastLogout, that.lastLogout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, totalLogins, averageUseTime, rangeHeadMovementBottom, rangeHeadMovementTop, totalHeadTriggers, totalTimesUsedTracker, lastTrackerUse, lastLogin, lastLogout);
    }
}
