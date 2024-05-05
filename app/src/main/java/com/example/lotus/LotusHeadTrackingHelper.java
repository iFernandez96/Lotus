package com.example.lotus;

import android.os.Handler;
import android.os.Looper;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.Statistics;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LotusHeadTrackingHelper {
    private boolean isTracking;
    private final Statistics statistics;
    private long totalTrackerUseTime;
    private final LotusHeadTracking lotusHeadTracking;
    private final LandingPage activity;
    private LoginRepo repository;

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
            try {
                repository = LoginRepo.getRepo(activity.getApplication());
                if (repository == null) {
                    throw new IllegalStateException("Repository is not initialized!");
                }

                if (isTracking) {
                    activity.playSound(R.raw.autoon);
                    statistics.setLastTrackerUse(LocalDateTime.now());
                    statistics.setTotalTimesUsedTracker(statistics.getTotalTimesUsedTracker() + 1);
                    // Start timing for average use time in seconds
                    totalTrackerUseTime = (long) (System.currentTimeMillis() / 1000.0);
                    lotusHeadTracking.startTracking();
                } else {
                    activity.playSound(R.raw.autooff);
                    lotusHeadTracking.stopTracking();
                    // Stop timing for average use time in seconds
                    totalTrackerUseTime = (long) (System.currentTimeMillis() / 1000.0) - totalTrackerUseTime;
                    statistics.setAverageUseTime((int) ((statistics.getAverageUseTime() * (statistics.getTotalTimesUsedTracker() - 1) + totalTrackerUseTime) / statistics.getTotalTimesUsedTracker()));
                    statistics.setRangeHeadMovementBottom(lotusHeadTracking.getBottomRange());
                    statistics.setRangeHeadMovementTop(lotusHeadTracking.getTopRange());
                    statistics.setTotalHeadTriggers(statistics.getTotalHeadTriggers() + lotusHeadTracking.getNumHeadDips());
                    statistics.setLastTrackerUse(LocalDateTime.now());
                    repository.updateAllUserStatistics(statistics);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }

            handler.post(() -> {
                // UI Thread work here
            });
        });
    }

}