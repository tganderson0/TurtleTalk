package com.example.turtletalk.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.turtletalk.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

@Entity
public class Profile {
    @PrimaryKey @NonNull
    public String username;

    @ColumnInfo(name="password")
    public String password;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="user_posts")
    public ArrayList<Post> userPosts;

    @ColumnInfo(name="friend_usernames")
    public ArrayList<String> friendUsernames;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] profilePhoto;


    public Profile(@NonNull String username, String password, String name){
        this.username = username;
        this.password = password;
        this.name = name;
        this.userPosts = new ArrayList<>();
        friendUsernames = new ArrayList<>();


    }


    public boolean correctPassword(String attempt) {
        return attempt.equals(password);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void addPost(Post post){
        userPosts.add(post);
    }

    public void setProfilePhoto(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        profilePhoto = stream.toByteArray();
    }

    public void addFriend(String username){
        friendUsernames.add(username);
    }

    public Bitmap getProfileBitmap(){
        return BitmapFactory.decodeByteArray(profilePhoto,0,profilePhoto.length);
    }
}
