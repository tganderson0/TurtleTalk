package com.example.turtletalk;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.turtletalk.database.AppDatabase;
import com.example.turtletalk.models.Post;
import com.example.turtletalk.models.Profile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


public class SearchFragment extends Fragment {

    AppDatabase database;
    Profile currentProfile;
    CountDownLatch latch = new CountDownLatch(1);
    HomeScreen homeScreen;

    public SearchFragment() {
        super(R.layout.fragment_search);
    }

    public void setUser(){
        homeScreen = (HomeScreen) getActivity();
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
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        EditText usernameEditText = view.findViewById(R.id.search_usernameEditText);
        TextView errorTextView = view.findViewById(R.id.search_error_text);
        Button searchButton = view.findViewById(R.id.search_searchButton);

        searchButton.setOnClickListener((buttonView) ->{
            System.out.printf("Searching for friend: %s", usernameEditText.getText().toString());
            new Thread(() ->{
                Profile friend = (database.getProfileDao().findByUsername(usernameEditText.getText().toString()));
                if (friend != null){
                    System.out.println("The friend exists in the database");
                    if (currentProfile.friendUsernames.contains(friend.username)){
                        homeScreen.changeErrorText(errorTextView, "Already a friend!");
                    }else{
                        currentProfile.addFriend(friend.username);
                        homeScreen.changeErrorText(errorTextView, "Friend added!");
                    }
                }else{
                    System.out.printf("Searching for friend: %s", usernameEditText.getText().toString());
                    homeScreen.changeErrorText(errorTextView, "The user was not found");
                }
            }).start();
        });



    }
}