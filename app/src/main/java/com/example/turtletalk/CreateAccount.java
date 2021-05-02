package com.example.turtletalk;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.turtletalk.database.AppDatabase;
import com.example.turtletalk.models.Profile;
import com.example.turtletalk.viewmodels.ProfileViewModel;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProfileViewModel viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        setContentView(R.layout.activity_create_account);

        TextView errorText = findViewById(R.id.createAccount_errorText);
        viewModel.getCreateAccountState().observe(this, errorText::setText);

        EditText usernameEdit = findViewById(R.id.createAccount_usernameEdit);
        EditText nameEdit = findViewById(R.id.createAccount_nameEdit);
        EditText passwordOne = findViewById(R.id.createAccount_password_one);
        EditText passwordTwo = findViewById(R.id.createAccount_password_two);

        findViewById(R.id.createAccountScreen_createAccount).setOnClickListener((view) -> {

            viewModel.createAccount(usernameEdit.getText().toString(), nameEdit.getText().toString(), passwordOne.getText().toString(), passwordTwo.getText().toString());
        });
    }
}