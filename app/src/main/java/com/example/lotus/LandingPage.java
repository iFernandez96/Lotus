package com.example.lotus;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.Statistics;
import com.example.lotus.Database.entities.User;
import com.example.lotus.databinding.ActivityLandingPageBinding;

import java.time.LocalDateTime;

public class LandingPage extends AppCompatActivity {
    private LoginRepo repository;
    private String username;
    private boolean isTracking = false;
    private static final String TAG = "LandingPage";
    private Statistics statistics;
    private long totalTrackerUseTime = 0;
    private LotusHeadTracking lotusHeadTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing_page);
    }


    /**
     * Check if the user exists in the database
     * @param username
     * @return true if the user exists, false otherwise
     */
    private boolean checkUserExists(String username){
        return repository.getUserByUsername(username) != null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EdgeToEdge.enable(this);

        ActivityLandingPageBinding landingPageBinding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(landingPageBinding.getRoot());

        repository = LoginRepo.getRepo(getApplication());
        assert repository != null;

        username = getIntent().getStringExtra(Constants.LOGIN_ACTIVITY_KEY);
        if (!checkUserExists(username)){
            finish();
        }


        landingPageBinding.usernameView.setText(username);
        User user = repository.getUserByUsername(username);
        if (user != null) {
            // Update the statistics for the user
            statistics = repository.getStatisticsByUserID(user.getId());
            statistics.setLastLogin(LocalDateTime.now());
            statistics.setTotalLogins(statistics.getTotalLogins() + 1);

            landingPageBinding.usernameView.setText(username);
            landingPageBinding.isAdmin.setVisibility(user.isAdmin() ? View.VISIBLE : View.GONE);
        }

        ImageButton lotusHeadTrackingbutton = findViewById(R.id.imageButton);
        lotusHeadTracking = new LotusHeadTracking(getApplicationContext(), this);
        lotusHeadTrackingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTracking = !isTracking;
                if (isTracking) {
                    playSound(R.raw.autoon);
                    statistics.setLastTrackerUse(LocalDateTime.now());
                    statistics.setTotalTimesUsedTracker(statistics.getTotalTimesUsedTracker() + 1);
                    // start timing for average use time in seconds
                    totalTrackerUseTime = (long) (System.currentTimeMillis() / 1000.0);
                    lotusHeadTracking.startTracking();
                } else {
                    playSound(R.raw.autooff);
                    // stop timing for average use time in seconds
                    totalTrackerUseTime = (long) (System.currentTimeMillis() / 1000.0) - totalTrackerUseTime;
                    statistics.setAverageUseTime((int) ((statistics.getAverageUseTime() * statistics.getTotalTimesUsedTracker() + totalTrackerUseTime) /2));
                    lotusHeadTracking.stopTracking();
                }
            }
        });

        Button settingsButton = landingPageBinding.settingsButton;

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Done with basic startup tasks

    }

    @Override
    protected void onStop() {
        super.onStop();
        cleanUp();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanUp();
    }

    private void cleanUp() {
        if (isTracking)
        {
            playSound(R.raw.autooff);
            lotusHeadTracking.stopTracking();
            statistics.setAverageUseTime((int) ((statistics.getAverageUseTime() * statistics.getTotalTimesUsedTracker() + totalTrackerUseTime) /2));
            statistics.setLastLogout(LocalDateTime.now());
            statistics.setLastTrackerUse(LocalDateTime.now());
            // statistics.setTotalHeadTriggers();
        }
    }

    private void playSound(int soundResourceId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, soundResourceId);
        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        } else {
            // Handle error: MediaPlayer creation failed
            Log.e("MediaPlayer", "Could not create MediaPlayer for resource ID: " + soundResourceId);
        }
    }

}