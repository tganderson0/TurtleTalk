package com.example.turtletalk.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.turtletalk.models.Profile;

@Database(entities = {Profile.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase{
    public abstract ProfileDao getProfileDao();
}
