package com.example.turtletalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.turtletalk.database.AppDatabase;
import com.example.turtletalk.models.Profile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "profile-db").build();

        EditText usernameLogin = findViewById(R.id.login_username);
        EditText passwordLogin = findViewById(R.id.login_password);
        Button loginButton = findViewById(R.id.login_loginButton);
        Button createAccountButton = findViewById(R.id.login_createAccount);

        loginButton.setOnClickListener((view) -> {
            new Thread(() ->{
                Profile profile = database.getProfileDao().findByUsername(usernameLogin.getText().toString());

                if (profile == null){
                    passwordLogin.setText("");
                    passwordLogin.setHint("Incorrect Login");
                    return;
                }

                if (profile.correctPassword(passwordLogin.getText().toString())){
                    System.out.println("Successful login!");
                    Intent i = new Intent(MainActivity.this, HomeScreen.class);
                    i.putExtra("loggedInUser", profile.username);
                    MainActivity.this.startActivity(i);
                }else{
                    passwordLogin.setText("");
                    passwordLogin.setHint("Incorrect Login");
                }
            }).start();
        });

        createAccountButton.setOnClickListener((view) -> {
            Intent i = new Intent(MainActivity.this, CreateAccount.class);
            MainActivity.this.startActivity(i);
        });


    }
}