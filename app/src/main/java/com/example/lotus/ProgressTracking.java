package com.example.lotus;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.Statistics;

public class ProgressTracking extends AppCompatActivity {
    private StatisticsViewModel statisticsViewModel;

    private TextView tvTotalLogins;
    private TextView tvAverageUseTime;
    private TextView tvHeadMovementRange;
    private TextView tvTotalHeadTriggers;
    private TextView tvTotalTimesUsedTracker;
    private TextView tvLastTrackerUse;
    private TextView tvLastLogin;
    private TextView tvLastLogout;

    private LoginRepo repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracking);

        initViews();
        statisticsViewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        repository = LoginRepo.getRepo(getApplication());
        String username = getIntent().getStringExtra(Constants.LOGIN_ACTIVITY_KEY);
        int userId = repository.getUserByUsername(username).getId();
        statisticsViewModel.getStatisticsByUserId(userId).observe(this, this::updateUI);
    }

    private void initViews() {
        tvTotalLogins = findViewById(R.id.tvTotalLogins);
        tvAverageUseTime = findViewById(R.id.tvAverageUseTime);
        tvHeadMovementRange = findViewById(R.id.tvHeadMovementRange);
        tvTotalHeadTriggers = findViewById(R.id.tvTotalHeadTriggers);
        tvTotalTimesUsedTracker = findViewById(R.id.tvTotalTimesUsedTracker);
        tvLastTrackerUse = findViewById(R.id.tvLastTrackerUse);
        tvLastLogin = findViewById(R.id.tvLastLogin);
        tvLastLogout = findViewById(R.id.tvLastLogout);
    }

    private void updateUI(Statistics statistics) {
        if (statistics != null) {
            tvTotalLogins.setText("Total Logins: " + statistics.getTotalLogins());
            tvAverageUseTime.setText("Average Use Time: " + statistics.getAverageUseTime());
            tvHeadMovementRange.setText("Head Movement Range: " + statistics.getRangeHeadMovementBottom() + " to " + statistics.getRangeHeadMovementTop());
            tvTotalHeadTriggers.setText("Total Head Triggers: " + statistics.getTotalHeadTriggers());
            tvTotalTimesUsedTracker.setText("Total Times Used Tracker: " + statistics.getTotalTimesUsedTracker());
            tvLastTrackerUse.setText("Last Tracker Use: " + statistics.getLastTrackerUse().toString());
            tvLastLogin.setText("Last Login: " + statistics.getLastLogin().toString());
            tvLastLogout.setText("Last Logout: " + statistics.getLastLogout().toString());
        }
    }
}
