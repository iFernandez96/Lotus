package com.example.lotus;

import com.example.lotus.Database.LoginRepo;

public class VerifyLogin {
    public static boolean checkUserExists(LoginRepo repository, String username){
        return repository.getUserByUsername(username);
    }
}
