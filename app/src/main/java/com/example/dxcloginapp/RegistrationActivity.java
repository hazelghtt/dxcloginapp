package com.example.dxcloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    EditText signupUsername, signupEmail, signupPassword;
    Button regButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView loginLink ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        signupEmail = findViewById(R.id.email);
        signupUsername = findViewById(R.id.userName);
        signupPassword = findViewById(R.id.password);
        loginLink  = findViewById(R.id.loginLink);
        regButton = findViewById(R.id.register_btn);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String username = signupUsername.getText().toString();
                String email = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter User Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                HelperClass helperClass = new HelperClass(email, username, password);
                reference.child(username).setValue(helperClass);
                Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistrationActivity.this, Login.class);
                startActivity(intent);
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }

}