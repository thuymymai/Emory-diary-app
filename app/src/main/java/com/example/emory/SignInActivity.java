package com.example.emory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    Button btnSignUp,btnLogin;
    String email,password;
    EditText edEmail,edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignUp = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edEmail = findViewById(R.id.edLoginEmail);
        edPassword = findViewById(R.id.edLoginPassword);

        //take user data from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("SignUp", MODE_PRIVATE);
        email = sharedPreferences.getString("Email","DEFAULT_EMAIL");
        password = sharedPreferences.getString("Password","DEFAULT_PASSWORD");

        signUp();
        login();
    }

    //move to sign up
    private void signUp() {
        btnSignUp.setOnClickListener((View v) -> {
            Intent intent = new Intent(SignInActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    //activate login action
    private void login() {
        btnLogin.setOnClickListener((View v) -> {
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();

            validateLogin(email.toLowerCase(),password);
        });
    }

    /*if user provides correct email and password in shared preferences
    they are validated to log in
     */
    private void validateLogin(String userEmail, String userPassword) {
        if (userEmail.equals(email) && userPassword.equals(password)) {
            Toast.makeText(SignInActivity.this,"Login SuccessFul",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignInActivity.this, AddMoodActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(SignInActivity.this,"Username or password is incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
