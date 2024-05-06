package com.example.lotus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.lotus.Database.entities.User;

public class AdminActivity extends AppCompatActivity {
    private UserViewModel viewModel;
    private LinearLayout userDetailsLayout;
    private EditText editTextName, editTextEmail, editTextPassword;
    private Button buttonUpdate, buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userDetailsLayout = findViewById(R.id.userDetailsLayout);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setupSearch();
        setupButtons();
    }

    private void setupSearch() {
        SearchView searchUser = findViewById(R.id.searchUser);
        searchUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.fetchUserByUsername(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;  // Setup trie?
            }
        });

        viewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                userDetailsLayout.setVisibility(View.VISIBLE);
                editTextName.setText(user.getName());
                editTextEmail.setText(user.getEmail());
                editTextPassword.setText(user.getPassword());
            } else {
                Toast.makeText(AdminActivity.this, "No user found", Toast.LENGTH_SHORT).show();
                userDetailsLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setupButtons() {
        buttonUpdate.setOnClickListener(v -> {
            User user = new User(
                    editTextName.getText().toString(),
                    editTextEmail.getText().toString(),
                    editTextPassword.getText().toString()  // TODO: Handle password updates with security in mind.
            );
            viewModel.updateUser(user);
            Toast.makeText(AdminActivity.this, "User updated successfully", Toast.LENGTH_SHORT).show();
        });

        buttonDelete.setOnClickListener(v -> {
            viewModel.deleteUser();
            Toast.makeText(AdminActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
            userDetailsLayout.setVisibility(View.GONE);  // Hide user details layout after deletion.
        });
    }
}
