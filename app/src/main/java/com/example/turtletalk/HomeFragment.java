package com.example.turtletalk;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.turtletalk.database.AppDatabase;
import com.example.turtletalk.models.Post;
import com.example.turtletalk.models.Profile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


public class HomeFragment extends Fragment {

    AppDatabase database;
    Profile currentProfile;
    CountDownLatch latch = new CountDownLatch(1);

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    public void setUser(){
        HomeScreen homeScreen = (HomeScreen) getActivity();
        assert homeScreen != null;

        database = homeScreen.getDatabase();

        String username = homeScreen.getLoggedInUser();
        new Thread(() ->{
            currentProfile = database.getProfileDao().findByUsername(username);
            latch.countDown();
        }).start();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        setUser();
        ArrayList<Post> posts = new ArrayList<>();

        InputStream inputStream = getContext().getResources().openRawResource(R.raw.beach);


        Post defaultPost = new Post("Foggeydoughtnut", "It's great weather here!", BitmapFactory.decodeStream(inputStream));

        posts.add(defaultPost);
        posts.add(defaultPost);
        posts.add(defaultPost);

        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (currentProfile.friendUsernames != null){
                for (String friend : currentProfile.friendUsernames) {
                    Profile friendProfile = database.getProfileDao().findByUsername(friend);
                    if (friendProfile != null){
                        posts.addAll(friendProfile.userPosts);
                    }
                }
            }
        }).start();



        RecyclerView postList = view.findViewById(R.id.posts_recyclerView);

        postList.setLayoutManager(new LinearLayoutManager(getContext()));

        postList.setAdapter(new PostAdapter(posts));


    }
}