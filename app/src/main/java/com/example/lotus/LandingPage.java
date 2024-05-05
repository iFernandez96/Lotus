package com.example.lotus;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.Statistics;
import com.example.lotus.Database.entities.User;
import com.example.lotus.databinding.ActivityLandingPageBinding;

import java.time.LocalDateTime;

public class LandingPage extends AppCompatActivity {
    private LoginRepo repository;
    private boolean isTracking = false;
    // private static final String TAG = "LandingPage";
    private Statistics statistics;
    private long totalTrackerUseTime = 0;
    private LandingPage activity;
    private LotusHeadTracking lotusHeadTracking;
    private ActivityLandingPageBinding binding;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
    }



    /**
     * Check if the user exists in the database
     * @return true if the user exists, false otherwise
     */
    private boolean checkUserExists(String username){
        return repository.getUserByUsername(username) != null;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        EdgeToEdge.enable(this);
//
//        ActivityLandingPageBinding landingPageBinding = ActivityLandingPageBinding.inflate(getLayoutInflater());
//        setContentView(landingPageBinding.getRoot());
//
//        repository = LoginRepo.getRepo(getApplication());
//        assert repository != null;
//
//        String username = getIntent().getStringExtra(Constants.LOGIN_ACTIVITY_KEY);
//        if (!checkUserExists(username)){
//            Toast.makeText(this, "Please Refresh cache and delete App data", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//
//
//        landingPageBinding.usernameView.setText(username);
//        User user = repository.getUserByUsername(username);
//        if (user != null) {
//            // Update the statistics for the user
//            if (repository.getStatisticsByUserID(user.getId()) == null) {
//                statistics = new Statistics(user.getId());
//                repository.insertStatistics(statistics);
//            } else {
//                statistics = repository.getStatisticsByUserID(user.getId());
//            }
//            statistics.setUserID(user.getId());
//            statistics.setLastLogin(LocalDateTime.now());
//            statistics.setTotalLogins(statistics.getTotalLogins() + 1);
//
//            landingPageBinding.usernameView.setText(username);
//            landingPageBinding.isAdmin.setVisibility(user.isAdmin() ? View.VISIBLE : View.GONE);
//        }
//
//        ImageButton lotusHeadTrackingbutton = findViewById(R.id.imageButton);
//        lotusHeadTracking = new LotusHeadTracking(getApplicationContext(), this);
//        lotusHeadTrackingbutton.setOnClickListener(v -> {
//            isTracking = !isTracking;
//            new LotusHeadTrackingHelper(isTracking, statistics, totalTrackerUseTime, lotusHeadTracking, activity).execute();
//        });
//
//        Button settingsButton = landingPageBinding.settingsButton;
//
//        settingsButton.setOnClickListener(v -> {
//            Intent intent = new Intent(LandingPage.this, SettingsActivity.class);
//            startActivity(intent);
//        });
//
//        // Done with basic startup tasks
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer = new MediaPlayer();
        initializeRepository();
        setupUserDetails();
        setupTrackingButton();
        setupSettingsButton();
    }

    private void setupSettingsButton() {
        Button settingsButton = binding.settingsButton;
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPage.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void initializeRepository() {
        repository = LoginRepo.getRepo(getApplication());
        if (repository == null) {
            throw new RuntimeException("Failed to initialize repository");
        }
    }

    private void setupTrackingButton() {
        ImageButton lotusHeadTrackingButton = binding.imageButton;
        lotusHeadTracking = new LotusHeadTracking(getApplicationContext(), this);
        lotusHeadTrackingButton.setOnClickListener(v -> {
            isTracking = !isTracking;
            if (isTracking) {
                playSound(R.raw.autoon);
            } else {
                playSound(R.raw.autooff);
            }
            new LotusHeadTrackingHelper(isTracking, statistics, totalTrackerUseTime, lotusHeadTracking, this).execute();
        });
    }




    private void setupUserDetails() {
        String username = getIntent().getStringExtra(Constants.LOGIN_ACTIVITY_KEY);

        new Thread(() -> {
            if (!checkUserExists(username)) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Please Refresh cache and delete App data", Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;
            }

            runOnUiThread(() -> binding.usernameView.setText(username));

            User user = repository.getUserByUsername(username);
            if (user != null) {
                runOnUiThread(() -> {
                    binding.usernameView.setText(username);
                    binding.isAdmin.setVisibility(user.isAdmin() ? View.VISIBLE : View.GONE);
                });
                setupStatisticsForUser(user);
            }
        }).start();
    }

    private void setupStatisticsForUser(User user) {
        Statistics stats = repository.getStatisticsByUserID(user.getId());
        if (stats == null) {
            stats = new Statistics(user.getId());
            repository.insertStatistics(stats);
        } else {
            stats.setTotalLogins(stats.getTotalLogins() + 1);
            stats.setLastLogin(LocalDateTime.now());
            repository.updateAllUserStatistics(stats);
        }
        statistics = stats;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        cleanUp();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        cleanUp();
    }

    private void cleanUp() {
        if (isTracking) {
            playSound(R.raw.autooff);
            lotusHeadTracking.stopTracking();
        }
        if (statistics != null) {
            statistics.setLastLogout(LocalDateTime.now());
            statistics.setLastTrackerUse(LocalDateTime.now());
            repository.updateAllUserStatistics(statistics);
        }
    }

//    void playSound(int soundResourceId) {
//        MediaPlayer mediaPlayer = MediaPlayer.create(this, soundResourceId);
//        if (mediaPlayer != null) {
//            mediaPlayer.start();
//            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
//        } else {
//            // Handle error: MediaPlayer creation failed
//            Log.e("MediaPlayer", "Could not create MediaPlayer for resource ID: " + soundResourceId);
//        }
//    }

    void playSound(int soundResourceId) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(this, soundResourceId);
            } else {
                mediaPlayer = MediaPlayer.create(this, soundResourceId);
            }
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        } catch (Exception e) {
            Log.e("MediaPlayer", "Error playing sound: ", e);
        }
    }

}