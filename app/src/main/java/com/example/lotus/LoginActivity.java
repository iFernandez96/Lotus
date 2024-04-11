package com.example.lotus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String LOGIN_ACTIVITY_KEY = "LOGIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
    }
    public static Intent LoginActivityIntentFactory(Context context, boolean receiviedValue){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(LOGIN_ACTIVITY_KEY, receiviedValue);
        return intent;
    }
}