package com.example.turtletalk.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

import com.example.turtletalk.database.AppDatabase;
import com.example.turtletalk.models.Profile;

public class ProfileViewModel extends AndroidViewModel {
    private AppDatabase database;
    public ProfileViewModel(@NonNull Application application) {
        super(application);
        database = Room.databaseBuilder(application, AppDatabase.class, "all_profiles").build();
    }


    public String createAccount(String username, String name, String password, String retypedPassword){
        String[] resultCode = new String[1];

        new Thread(() ->{

            if (username == null || username.equals("")){
                resultCode[0] = ("Please type a username!");
            }else if(name == null || name.equals("")){
                resultCode[0] = ("Please type your name!");
            }else if(password == null || password.equals("")){
                resultCode[0] = ("Please enter a password!");
            }else if(!password.equals(retypedPassword)){
                resultCode[0] = ("Passwords need to match!");
            }else if (database.getProfileDao().findByUsername(username) != null){
                resultCode[0] = ("That username is already taken!");
            }else{
                resultCode[0] = ("Account created!");
                Profile profile = new Profile(username, password, name);
                database.getProfileDao().insert(profile);
            }



        }).start();

        return resultCode[0];
    }
}
