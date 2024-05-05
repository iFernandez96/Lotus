package com.example.lotus;

import android.app.Activity;
import android.content.Context;

public class LotusHeadTracking {

    private final Tracker tracker;
    private float bottomRange = 180;
    private float topRange = -180;
    private int numHeadDips;

    public LotusHeadTracking(Context context, Activity activity) { // Constructor to receive a Context
        // Added to hold the Context
        tracker = new Tracker(context, activity);
        tracker.initializeSensors();
    }

    public void startTracking() {
        tracker.startTracking();
    }

    public void stopTracking() {
        bottomRange = tracker.getBottomRange();
        topRange = tracker.getTopRange();
        numHeadDips = tracker.getNumHeadDips();
        tracker.stopTracking();
    }

    public float getBottomRange() {
        return bottomRange;
    }

    public float getTopRange() {
        return topRange;
    }

    public int getNumHeadDips() {
        return numHeadDips;
    }
}
