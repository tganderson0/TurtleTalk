package com.example.turtletalk;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.turtletalk.viewmodels.ProfileViewModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


public class SearchFragment extends Fragment {


    ProfileViewModel viewModel;

    public SearchFragment() {
        super(R.layout.fragment_search);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        EditText usernameEditText = view.findViewById(R.id.search_usernameEditText);
        TextView errorTextView = view.findViewById(R.id.search_error_text);
        Button searchButton = view.findViewById(R.id.search_searchButton);

        viewModel.getSearchState().observe(getViewLifecycleOwner(), (searchState) ->{
            errorTextView.setText(searchState);
        });

        searchButton.setOnClickListener((buttonView) ->{
            viewModel.addFriend(usernameEditText.getText().toString());
            System.out.printf("Searching for friend: %s", usernameEditText.getText().toString());
        });



    }
}