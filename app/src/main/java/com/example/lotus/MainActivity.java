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
//        ActivityLoginBinding loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
//        View view2 = loginBinding.getRoot();
        Intent intent = LoginActivity.LoginActivityIntentFactory(getApplicationContext(), false);
        startActivity(intent);
    }
}