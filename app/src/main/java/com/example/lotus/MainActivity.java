package com.example.lotus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.util.Log;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.Phone;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Lotus";
    private String username;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private LoginRepo repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        repository = LoginRepo.getRepo(getApplication());
        assert repository != null;

        boolean isLoggedIn = isUserLoggedIn(getApplicationContext());
        EdgeToEdge.enable(this);

        if (isLoggedIn) {
            // Check for permissions and get phone details if logged in
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
                getPhoneDetails();
            } else {
                getPhoneDetails();
            }

            Intent intent = intentFactory.createIntent(getApplicationContext(), LandingPage.class);
            intent.putExtra(Constants.LOGIN_ACTIVITY_KEY, username);
            startActivity(intent);
        } else {
            username = ""; // Set username to empty if not logged in
            Intent intent = intentFactory.createIntent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    public boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        username = prefs.getString("username", "");
        return prefs.getBoolean("isLoggedIn", false);  // Default to false if the flag isn't found
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getPhoneDetails();
        } else {
            Toast.makeText(this, "Permission denied to read your Phone state", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPhoneDetails() {
        Phone phone = new Phone();
        phone.setModel_name(Build.MODEL);
        phone.setBrand(Build.BRAND);
        phone.setAndroid_version(Build.VERSION.RELEASE);
        phone.setSecurity_patch(Build.VERSION.SECURITY_PATCH);
        phone.setBuild_number(Build.ID);
        phone.setKernel_version(System.getProperty("os.version"));
        phone.setBaseband_version(Build.getRadioVersion());

        Toast.makeText(this, "username: " + username, Toast.LENGTH_SHORT).show();
        // Retrieve user ID from repository using username
        phone.setUserID(repository.getUserByUsername(username).getId());

        Log.d(TAG, "Device Information:");
        Log.d(TAG, "Model: " + phone.getModel_name() + ", Android Version: " + phone.getAndroid_version());

        repository.insertPhone(phone);
        repository.updateDatabaseWithPhoneDetails(phone);
    }
}
