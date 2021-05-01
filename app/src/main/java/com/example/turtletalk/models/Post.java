package com.example.turtletalk.models;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Post {
    private final String username;
    private String caption;
    private final ArrayList<String> comments;
    private int likes;
    private final Bitmap photo;

    public Post(String poster, String caption, Bitmap photo){
        this.username = poster;
        this.caption = caption;
        comments = new ArrayList<>();
        likes = 0;
        this.photo = photo;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void addLike(){
        likes += 1;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public int getLikes() {
        return likes;
    }

    public String getUsername() {
        return username;
    }

    public String getCaption() {
        return caption;
    }

    public Bitmap getPhotoBitmap() {
        return photo;
    }
}
