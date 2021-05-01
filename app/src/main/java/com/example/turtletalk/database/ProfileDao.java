package com.example.turtletalk.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.turtletalk.models.Profile;

@Dao
public interface ProfileDao {

    @Query("SELECT * FROM profile WHERE username = :username LIMIT 1")
    public Profile findByUsername(String username);

    @Insert
    public void insert(Profile profile);

    @Update
    public void update(Profile profile);

    @Delete
    public void delete(Profile profile);
}
