package com.example.turtletalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.turtletalk.database.AppDatabase;
import com.example.turtletalk.models.Profile;
import com.example.turtletalk.viewmodels.ProfileViewModel;

public class MainActivity extends AppCompatActivity {
    ProfileViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        EditText usernameLogin = findViewById(R.id.login_username);
        EditText passwordLogin = findViewById(R.id.login_password);
        Button loginButton = findViewById(R.id.login_loginButton);
        Button createAccountButton = findViewById(R.id.login_createAccount);



        viewModel.getLoginState().observe(this, (currentState) ->{
            passwordLogin.setText("");
            passwordLogin.setHint(currentState);
        });

        loginButton.setOnClickListener((view) -> {
            viewModel.loginAccount(usernameLogin.getText().toString(), passwordLogin.getText().toString());
        });

        createAccountButton.setOnClickListener((view) -> {
            Intent i = new Intent(MainActivity.this, CreateAccount.class);
            MainActivity.this.startActivity(i);
        });





    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getCurrentLogin().observe(this, (login) ->{
            if (viewModel.getCurrentProfile() != null){
                System.out.println("USER IS NON NULL");
                Intent i = new Intent(this, HomeScreen.class);
                i.putExtra("username", viewModel.getCurrentLogin().getValue());
                startActivity(i);
                finish();
            }
        });

    }
}