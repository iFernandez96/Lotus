package com.example.lotus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lotus.Database.LoginRepo;
import com.example.lotus.Database.entities.Login;
import com.example.lotus.databinding.ActivityLoginBinding;
import com.example.lotus.databinding.ActivityMainBinding;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActivityLoginBinding loginBinding;


    private static final String MAIN_ACTIVITY_KEY="MAIN_ACT";
    public static final String TAG = "Lotus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        //TODO if not logged in set the view to log in activity
//        View view = binding.getRoot();
//        setContentView(view);
    }
    @Override
    protected void onStart(){
        super.onStart();
        //EdgeToEdge.enable(this); //Unsure what this does...
        //setContentView(R.layout.activity_main); //Not needed
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view2 = loginBinding.getRoot();
        Intent intent = LoginActivity.LoginActivityIntentFactory(getApplicationContext(), false);
        startActivity(intent);
    }
    public static Intent MainActivity(Context context, boolean receiviedValue){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_KEY, receiviedValue);
        return intent;
    }
}