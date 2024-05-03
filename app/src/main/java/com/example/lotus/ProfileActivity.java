package com.example.lotus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lotus.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    private TextView profileName;
    private CheckBox emailNotifications;
    private CheckBox pushNotifications;
    private CheckBox newsletterSub;
    private RadioGroup visibilityGroup;
    private RadioButton publicButton;
    private RadioButton privateButton;
    private Button updatePreferences;
    private Button logoutButton;
    private Button deleteUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}