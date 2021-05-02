package com.example.turtletalk;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.turtletalk.database.AppDatabase;
import com.example.turtletalk.models.Profile;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "profile-db").build();

        EditText usernameEdit = findViewById(R.id.createAccount_usernameEdit);
        EditText nameEdit = findViewById(R.id.createAccount_nameEdit);
        EditText passwordOne = findViewById(R.id.createAccount_password_one);
        EditText passwordTwo = findViewById(R.id.createAccount_password_two);
        TextView errorText = findViewById(R.id.createAccount_errorText);

        findViewById(R.id.createAccountScreen_createAccount).setOnClickListener((view) -> {

            //////////////////////////////////////////////////
            // ProfileViewModel.createAccount(username, name, password, secondPassword);
            //////////////////////////////////////////////////

            new Thread(() ->{


                if (usernameEdit.getText().toString().equals("")){
                    runOnUiThread(() ->{
                        errorText.setText("Please type a username!");
                    });

                    return;
                }else if(nameEdit.getText().toString().equals("")){
                    runOnUiThread(() ->{
                        errorText.setText("Please type your name!");
                    });
                    return;
                }else if(passwordOne.getText().toString().equals("")){
                    runOnUiThread(() ->{
                        errorText.setText("Please enter a password!");
                    });
                    return;
                }else if(!passwordOne.getText().toString().equals(passwordTwo.getText().toString())){
                    runOnUiThread(() ->{
                        errorText.setText("Passwords need to match!");
                    });
                    return;
                }else if (database.getProfileDao().findByUsername(usernameEdit.getText().toString()) != null){
                    runOnUiThread(() ->{
                        errorText.setText("That username is already taken!");
                    });
                    return;
                }
                else{
                    runOnUiThread(() ->{
                        errorText.setText("");
                    });
                }

                Profile profile = new Profile(usernameEdit.getText().toString(), passwordOne.getText().toString(), nameEdit.getText().toString());

                database.getProfileDao().insert(profile);

            }).start();
        });
    }
}