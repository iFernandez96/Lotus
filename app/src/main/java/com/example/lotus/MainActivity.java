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
//    private ActivityMainBinding binding;


//    private static final String MAIN_ACTIVITY_KEY="MAIN_ACT";
    public static final String TAG = "Lotus";
    private String username;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private LoginRepo repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onStart(){
        super.onStart();
        repository = LoginRepo.getRepo(getApplication());
        assert repository != null;
        EdgeToEdge.enable(this);
        if (isUserLoggedIn(getApplicationContext())) {
            Intent intent = intentFactory.createIntent(getApplicationContext(), LandingPage.class);
            intent.putExtra(Constants.LOGIN_ACTIVITY_KEY, username);
            startActivity(intent);
            return;
        }
        Intent intent = intentFactory.createIntent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        // Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
        } else {
            // Permission has already been granted, proceed with getting the data
            getPhoneDetails();
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
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, proceed with the sensitive data operation
                getPhoneDetails();
            } else {
                // Permission denied, handle the feature or disable functionality
                Toast.makeText(this, "Permission denied to read your Phone state", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getPhoneDetails() {
        // Create an instance of Phone with the retrieved data
        Phone phone = new Phone();
        phone.setModel_name(android.os.Build.MODEL);
        phone.setBrand(android.os.Build.BRAND);
        phone.setAndroid_version(android.os.Build.VERSION.RELEASE);
        phone.setSecurity_patch(Build.VERSION.SECURITY_PATCH);
        phone.setBuild_number(Build.ID);
        phone.setKernel_version(System.getProperty("os.version"));
        phone.setBaseband_version(Build.getRadioVersion());


        // Assume userID is retrieved or set somewhere in your app
        phone.setUserID(repository.getUserByUsername(username).getUserID());

        // Log or display the information
        Log.d(TAG, "Device Information:");
        Log.d(TAG, "Model: " + phone.getModel_name() + ", Android Version: " + phone.getAndroid_version());

        // Now, call the method to update the database
        repository.updateDatabaseWithPhoneDetails(phone);
    }

}