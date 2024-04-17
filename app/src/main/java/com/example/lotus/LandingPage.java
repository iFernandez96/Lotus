package com.example.lotus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.databinding.ActivityLandingPageBinding;

public class LandingPage extends AppCompatActivity {

    private ActivityLandingPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

}