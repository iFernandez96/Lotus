package com.example.lotus;

import android.os.Handler;
import android.os.Looper;

import com.example.lotus.Database.entities.Statistics;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LotusHeadTrackingHelper {
    private final boolean isTracking;
    private final Statistics statistics;
    private long totalTrackerUseTime;
    private final LotusHeadTracking lotusHeadTracking;
    private final LandingPage activity;

    public LotusHeadTrackingHelper(boolean isTracking, Statistics statistics, long totalTrackerUseTime, LotusHeadTracking lotusHeadTracking, LandingPage activity) {
        this.isTracking = isTracking;
        this.statistics = statistics;
        this.totalTrackerUseTime = totalTrackerUseTime;
        this.lotusHeadTracking = lotusHeadTracking;
        this.activity = activity;
    }

    public void execute() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            //Background work here
            if (isTracking) {
                activity.playSound(R.raw.autoon);
                statistics.setLastTrackerUse(LocalDateTime.now());
                statistics.setTotalTimesUsedTracker(statistics.getTotalTimesUsedTracker() + 1);
                // start timing for average use time in seconds
                totalTrackerUseTime = (long) (System.currentTimeMillis() / 1000.0);
                lotusHeadTracking.startTracking();
            } else {
                activity.playSound(R.raw.autooff);
                // stop timing for average use time in seconds
                totalTrackerUseTime = (long) (System.currentTimeMillis() / 1000.0) - totalTrackerUseTime;
                statistics.setAverageUseTime((int) ((statistics.getAverageUseTime() * (statistics.getTotalTimesUsedTracker() - 1) + totalTrackerUseTime) / statistics.getTotalTimesUsedTracker()));
                // Set the statistics float[] rangeHeadMovement to the range of head movement
                statistics.setRangeHeadMovement(new float[]{lotusHeadTracking.bottomRange, lotusHeadTracking.topRange});
                lotusHeadTracking.stopTracking();
            }

            handler.post(() -> {
                //UI Thread work here
            });
        });
    }
}