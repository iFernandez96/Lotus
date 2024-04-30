package com.example.lotus;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.User;

public class SettingsActivity extends AppCompatActivity {
    private LoginRepo repository;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        // Initialize repository and username
        repository = LoginRepo.getRepo(getApplication());
        username = getIntent().getStringExtra(Constants.LOGIN_ACTIVITY_KEY);

        // Set up logout button click listener
        Button logoutButton = findViewById(R.id.Logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialogue();
            }
        });
        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.my_container, new SettingsFragment())
                    .commit();
        }
        */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    private void sendUserToLogIn(User user) {
        exitLoginSession(getApplicationContext());
        Intent intent = intentFactory.createIntent(getApplicationContext(), LoginActivity.class);
        intent.putExtra(Constants.LOGIN_ACTIVITY_KEY, user.getUsername());
        startActivity(intent);
    }

    public void exitLoginSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();
    }

    private void showLogoutDialogue() {
        AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
        final AlertDialog alertTalk = alert.create();

        alert.setTitle("Logout?");

        alert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertTalk.dismiss();
            }
        });
        alert.create().show();
    }

    private boolean checkUserExists(String username) {
        return repository.getUserByUsername(username) != null;
    }
}