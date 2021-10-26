package com.example.emory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SignupActivity extends AppCompatActivity {
    EditText edName, edAge, edEmail, edPassword;
    Button btnSignUp;
    String name, age, email, password;
    private int error = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edName = findViewById(R.id.edName);
        edAge = findViewById(R.id.edAge);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        /*when user clicks button sign up
        set error code to 0 and validate sign up again
        */
        btnSignUp.setOnClickListener((View v) -> {
            error = 0;
            name = edName.getText().toString();
            age = edAge.getText().toString();
            email = edEmail.getText().toString();
            password = edPassword.getText().toString();
            validateSignup();
        });
    }

    private void validateSignup() {
        //set an error code for each error
        if (name.isEmpty()) error = 1;
        if (age.isEmpty()) error = 2;
        if (email.isEmpty() || (!checkErrorEmail(email.toLowerCase()))) error = 3;

        //check each case and provide specific error notification
        switch (error) {
            case 1:
                edName.setError(getResources().getString(R.string.error_name));
                break;
            case 2:
                edAge.setError(getResources().getString(R.string.error_age));
                break;
            case 3:
                edEmail.setError(getResources().getString(R.string.error_email));
                break;
            case 0:
                //if none of error cases, save user data to shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences("SignUp", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Name", name);
                editor.putString("Age", age);
                editor.putString("Email", email.toLowerCase());
                editor.putString("Password", password);
                editor.apply();
                Toast.makeText(SignupActivity.this,"User has been register successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignupActivity.this, SignInActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean checkErrorEmail(String emailInput) {
        //email should have "@"
        if (!(emailInput.contains("@"))) return false;
        String mailDomain = emailInput.substring(emailInput.indexOf('@') + 1);
        //email domain should have "." and there is text after ".
        if (!mailDomain.contains(".")) return false;
        if (mailDomain.indexOf('.') == 0) return false;
        if (mailDomain.indexOf('.') == (mailDomain.length() - 1)) return false;
        return true;
    }
}