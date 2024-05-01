package com.example.lotus;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceFragmentCompat;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import android.provider.Settings;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.User;

public class SettingsActivity extends AppCompatActivity {
    private LoginRepo repository;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        repository = LoginRepo.getRepo(getApplication());
        username = getIntent().getStringExtra(Constants.LOGIN_ACTIVITY_KEY);

        Button logoutButton = findViewById(R.id.Logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialogue();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Switch switchEnableNotifications = findViewById(R.id.switch_enable_notifications);
        switchEnableNotifications.setChecked(NotificationManagerCompat.from(this).areNotificationsEnabled());

        switchEnableNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!NotificationManagerCompat.from(SettingsActivity.this).areNotificationsEnabled()) {
                    // Show dialog to explain why notifications need to be enabled
                    showExplanationDialog();
                }
            }
        });
    }

    private void requestNotificationPermission() {
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(intent);
    }

    private void logout() {
        exitLoginSession(getApplicationContext());
        startActivity(intentFactory.createIntent(getApplicationContext(), LoginActivity.class));
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    private void showExplanationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Notification Permission Needed")
                .setMessage("This app needs notification permissions to send you important alerts. Please enable them in the settings.")
                .setPositiveButton("Settings", (dialog, which) -> requestNotificationPermission())
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                    // Reset the switch to reflect the current state without triggering the listener
                    Switch switchEnableNotifications = findViewById(R.id.switch_enable_notifications);
                    switchEnableNotifications.setOnCheckedChangeListener(null);  // Temporarily remove listener
                    switchEnableNotifications.setChecked(NotificationManagerCompat.from(SettingsActivity.this).areNotificationsEnabled());
                    // Reattach the listener
                    switchEnableNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked && !NotificationManagerCompat.from(SettingsActivity.this).areNotificationsEnabled()) {
                                showExplanationDialog();  // Only show dialog if enabled again
                            }
                        }
                    });
                })
                .create()
                .show();
    }

    private void showLogoutDialogue() {
        AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
        alert.setTitle("Logout?");
        alert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        alert.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alert.create().show();
    }

    public void exitLoginSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();
    }
}
