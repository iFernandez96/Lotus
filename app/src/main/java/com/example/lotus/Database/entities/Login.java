package com.example.lotus.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity(tableName = "LoginTable") //wil be sotred in a database
public class Login {
    private String username;
    private String password;
    private LocalDateTime date;
    @PrimaryKey(autoGenerate = true)
    private int ID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return ID == login.ID && Objects.equals(username, login.username) && Objects.equals(password, login.password) && Objects.equals(date, login.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, date, ID);
    }

    public Login() {
        date = LocalDateTime.now();
    }
}

