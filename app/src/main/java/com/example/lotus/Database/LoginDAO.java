package com.example.lotus.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.lotus.Database.entities.Login;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface LoginDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Login login);

//    @Query("Select * from " + LoginDatabase.LOGIN_TABLE)
//    List<Login> getAllRecords();

    @Query("SELECT COUNT(*) FROM " + LoginDatabase.LOGIN_TABLE + " WHERE username = :username")
    Integer countUsernames(String username);


}
