package com.example.turtletalk;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.turtletalk.database.AppDatabase;
import com.example.turtletalk.models.Post;
import com.example.turtletalk.models.Profile;
import com.example.turtletalk.viewmodels.ProfileViewModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


public class HomeFragment extends Fragment {

    ArrayList<Post> posts;
    ProfileViewModel viewModel;
    CountDownLatch latch = new CountDownLatch(1);

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        posts = new ArrayList<>();

        if (viewModel.getFriendList().size() == 0){
            viewModel.getFriendProfiles();
        }

        InputStream inputStream = getContext().getResources().openRawResource(R.raw.beach);


        Post defaultPost = new Post("Foggeydoughtnut", "It's great weather here!", BitmapFactory.decodeStream(inputStream));

        posts.add(defaultPost);
        posts.add(defaultPost);
        posts.add(defaultPost);

        viewModel.getFriendList().forEach((friend) ->{
            if (friend != null){
                posts.addAll(friend.userPosts);
            }
        });

        RecyclerView postList = view.findViewById(R.id.posts_recyclerView);

        postList.setLayoutManager(new LinearLayoutManager(getContext()));

        postList.setAdapter(new PostAdapter(posts));


    }
}