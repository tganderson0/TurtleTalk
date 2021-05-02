package com.example.turtletalk.viewmodels;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.turtletalk.HomeScreen;
import com.example.turtletalk.MainActivity;
import com.example.turtletalk.database.AppDatabase;
import com.example.turtletalk.models.Profile;

import java.util.ArrayList;

public class ProfileViewModel extends AndroidViewModel {
    private AppDatabase database;

    private MutableLiveData<String> createAccountState = new MutableLiveData<>();

    private MutableLiveData<String> loginState = new MutableLiveData<>();

    private MutableLiveData<String> currentLogin = new MutableLiveData<>();

    private Profile currentProfile;

    private MutableLiveData<Profile> lastSearchedProfile = new MutableLiveData<>();

    private ObservableArrayList<Profile> friendList = new ObservableArrayList<>();

    private MutableLiveData<String> searchState = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        database = Room.databaseBuilder(application, AppDatabase.class, "all_profiles").build();
        createAccountState.setValue("");
        loginState.setValue("Password");
        currentLogin.setValue("");
        searchState.setValue("");
    }

    public MutableLiveData<String> getCreateAccountState() {
        return createAccountState;
    }

    public MutableLiveData<String> getCurrentLogin() {
        return currentLogin;
    }

    public MutableLiveData<String> getLoginState() {
        return loginState;
    }

    public MutableLiveData<String> getSearchState() {
        return searchState;
    }

    public String currentUser(){
        return currentLogin.toString();
    }

    public ObservableArrayList<Profile> getFriendList() {
        return friendList;
    }

    public MutableLiveData<Profile> getLastSearchedProfile() {
        return lastSearchedProfile;
    }

    public void searchForProfile(String username){
        new Thread(() ->{
            lastSearchedProfile.postValue(database.getProfileDao().findByUsername(username));
        }).start();
    }

    public void setCurrentLogin(String currentLogin) {
        this.currentLogin.setValue(currentLogin);
        new Thread(() ->{
            currentProfile = database.getProfileDao().findByUsername(currentLogin);
        }).start();
    }

    public Profile getCurrentProfile() {
        return currentProfile;
    }

    public void createAccount(String username, String name, String password, String retypedPassword){

        new Thread(() ->{

            if (username == null || username.equals("")){
                createAccountState.postValue("Please type a username!");
            }else if(name == null || name.equals("")){
                createAccountState.postValue("Please type your name!");
            }else if(password == null || password.equals("")){
                createAccountState.postValue("Please enter a password!");
            }else if(!password.equals(retypedPassword)){
                createAccountState.postValue("Passwords need to match!");
            }else if (database.getProfileDao().findByUsername(username) != null){
                createAccountState.postValue("That username is already taken!");
            }else{
                Profile profile = new Profile(username, password, name);
                database.getProfileDao().insert(profile);
                createAccountState.postValue("Account Created");
            }



        }).start();
    }

    private void updateAccount(){
        database.getProfileDao().update(currentProfile);
    }

    public void loginAccount(String username, String password){
        new Thread(() ->{
            Profile profile = database.getProfileDao().findByUsername(username);
            if (profile == null){
                loginState.postValue("Incorrect Login");
            }
            else if (profile.correctPassword(password)){
                System.out.println("Successful login!");
                loginState.postValue("Logging in!");
                currentProfile = profile;
                currentLogin.postValue(username);
            }else{
                loginState.postValue("Incorrect Login");
            }
        }).start();
    }

    public void getFriendProfiles(){
        if (currentProfile == null){
            return;
        }
        new Thread(() ->{

            currentProfile.friendUsernames.forEach((friend) ->{
                friendList.add(database.getProfileDao().findByUsername(friend));
            });
        }).start();
    }

    public void addFriend(String username){
        new Thread(() ->{
            if(currentProfile.friendUsernames.contains(username)){
                searchState.postValue("Already a friend!");
            }else if (database.getProfileDao().findByUsername(username) == null){
                searchState.postValue("The requested user does not exist");
            }else{
                searchState.postValue("Friend added!");
                currentProfile.friendUsernames.add(username);
                updateAccount();
            }
        }).start();
    }

    public void storeData() {


    }





}
