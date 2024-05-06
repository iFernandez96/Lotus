package com.example.lotus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class TrackingToggleReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences("TrackingPrefs", Context.MODE_PRIVATE);
        boolean isCurrentlyTracking = prefs.getBoolean("isTracking", false);
        if (isCurrentlyTracking) {
            stopTracking(context);
        } else {
            startTracking(context);
        }
        // Update tracking state
        prefs.edit().putBoolean("isTracking", !isCurrentlyTracking).apply();
    }

    private void startTracking(Context context) {
        // Start tracking and handle any setup needed
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.autoon);
        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        }
        // Initialize and start tracking
    }

    private void stopTracking(Context context) {
        // Stop tracking and handle any cleanup
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.autooff);
        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        }
        // Cleanup tracking resources
    }
}
