package com.example.lotus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
//    private ActivityMainBinding binding;


//    private static final String MAIN_ACTIVITY_KEY="MAIN_ACT";
    public static final String TAG = "Lotus";
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onStart(){
        super.onStart();
        EdgeToEdge.enable(this);
        if (isUserLoggedIn(getApplicationContext())) {
            Intent intent = intentFactory.createIntent(getApplicationContext(), LandingPage.class);
            intent.putExtra(Constants.LOGIN_ACTIVITY_KEY, username);
            startActivity(intent);
            return;
        }
        Intent intent = intentFactory.createIntent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
    public boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        username = prefs.getString("username", "");
        return prefs.getBoolean("isLoggedIn", false);  // Default to false if the flag isn't found
    }
}