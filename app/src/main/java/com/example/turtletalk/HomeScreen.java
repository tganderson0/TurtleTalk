package com.example.turtletalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.turtletalk.database.AppDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {

    private String loggedInUser;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        database = Room.databaseBuilder(this, AppDatabase.class, "profile-db").build();

        loggedInUser = getIntent().getStringExtra("loggedInUser");

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

    public String getLoggedInUser(){
        return loggedInUser;
    }

    public AppDatabase getDatabase(){
        return database;
    }

    public void changeErrorText(TextView errorText, String msg){
        runOnUiThread(() -> errorText.setText(msg));
    }
}