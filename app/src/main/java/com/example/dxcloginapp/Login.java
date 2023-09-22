package com.example.dxcloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button login_btn;
    TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        registerLink = findViewById(R.id.registerLink);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {

                } else {
                    checkUser();
                }
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

    public Boolean validateUsername() {
        String val = username.getText().toString();
        if (val.isEmpty()) {
            username.setError("Username cannot be empty");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = password.getText().toString();
        if (val.isEmpty()) {
            password.setError("Password cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }


    public void checkUser(){
        String userUsername = username.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    username.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userPassword)) {
                        username.setError(null);

                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);

                        Intent intent = new Intent(Login.this, MainActivity.class);

                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    } else {
                        password.setError("Invalid userid or password");
                        password.requestFocus();
                    }
                } else {
                    username.setError("Invalid userid or password");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}