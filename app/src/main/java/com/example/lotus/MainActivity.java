package com.example.lotus;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
//    private ActivityMainBinding binding;


//    private static final String MAIN_ACTIVITY_KEY="MAIN_ACT";
    public static final String TAG = "Lotus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onStart(){
        super.onStart();
        EdgeToEdge.enable(this);
        Intent intent = intentFactory.createIntent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}