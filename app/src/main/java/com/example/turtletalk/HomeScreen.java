package com.example.turtletalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.turtletalk.database.AppDatabase;
import com.example.turtletalk.viewmodels.ProfileViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {
    ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        Intent intent = getIntent();
        System.out.println(intent.getStringExtra("username"));
        viewModel.setCurrentLogin(intent.getStringExtra("username"));

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, HomeFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        }

        BottomNavigationView menu = findViewById(R.id.bottom_navigation);

        menu.setOnNavigationItemSelectedListener((menuItem) ->{
            if (menuItem.getItemId() == R.id.main_home){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }else if (menuItem.getItemId() == R.id.profile_search){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, SearchFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }else if (menuItem.getItemId() == R.id.menu_post){
                System.out.println("Post selected!");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, PostFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }else{
                System.out.println("Profile selected!");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }
            return true;
        });

    }
}