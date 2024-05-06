package com.example.lotus;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lotus.Database.LoginRepo;

public class UserViewModelFactory implements ViewModelProvider.Factory {
    private final LoginRepo repository;

    public UserViewModelFactory(LoginRepo repo) {
        this.repository = repo;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(repository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
