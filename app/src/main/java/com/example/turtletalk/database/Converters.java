package com.example.turtletalk.database;


import androidx.room.TypeConverter;

import com.example.turtletalk.models.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class Converters {

    @TypeConverter
    public static ArrayList<String> fromStringToArrList(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromStringArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static ArrayList<Post> fromStringToArrPost(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromPostArrayList(ArrayList<Post> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
