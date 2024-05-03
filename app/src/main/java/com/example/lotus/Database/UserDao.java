package com.example.lotus.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.lotus.Database.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + LoginDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsers();

    @Query("DELETE from " + LoginDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + LoginDatabase.USER_TABLE + " WHERE username = :username")
    User getUserByUsername(String username);

    @Query("UPDATE " + LoginDatabase.USER_TABLE + " SET username = :username, email = :email, password = :password WHERE username = :username")
    void updateUser(String username, String email, String password);

}
