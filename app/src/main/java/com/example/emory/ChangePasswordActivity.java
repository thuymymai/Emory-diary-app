package com.example.emory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//activity for change password
public class ChangePasswordActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Button btnSubmit;
    EditText edPassword,edConfirmPassword;
    String password,confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        btnSubmit=findViewById(R.id.btnChange);
        edPassword=findViewById(R.id.edNewPw);
        edConfirmPassword=findViewById(R.id.edConfirmPw);
        sharedPreferences = getApplicationContext().getSharedPreferences("SignUp", 0);
        editor = sharedPreferences.edit();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password=edPassword.getText().toString();
                confirmPassword=edConfirmPassword.getText().toString();
                //check field empty or not
                if(TextUtils.isEmpty(password)){
                    edPassword.setError(getResources().getString(R.string.error_message));
                }else if (TextUtils.isEmpty(confirmPassword)){
                    edConfirmPassword.setError(getResources().getString(R.string.error_message));
                }  else if(!password.equals(confirmPassword)){
                    //show message both password not matched
                    Toast.makeText(ChangePasswordActivity.this,"Password doesn't match",Toast.LENGTH_SHORT).show();
                }else {
                    editor.putString("Password",password);
                    editor.commit();
                    //notify to user password has been changed
                    Toast.makeText(ChangePasswordActivity.this,"Password has been change successfully",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(ChangePasswordActivity.this,EntriesActivity.class);
                    startActivity(intent);
                }


            }
        });
    }
}