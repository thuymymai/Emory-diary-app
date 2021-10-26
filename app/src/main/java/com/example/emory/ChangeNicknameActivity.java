package com.example.emory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//This activity for change nickname
public class ChangeNicknameActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    Button btnSubmit;
    EditText edNickName;
    TextView txtNickname;
    String nickName,updatedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nickname);
        btnSubmit=findViewById(R.id.btnChange);
        txtNickname=findViewById(R.id.txtNickName);
        edNickName=findViewById(R.id.edNickName);
        //get name from SharedPreferences
        mSharedPreferences = getApplicationContext().getSharedPreferences("SignUp", 0);
        editor = mSharedPreferences.edit();
        nickName=mSharedPreferences.getString("Name","DEFAULT_NAME");
        if(nickName!=null){
            //set nickname from SharedPreferences to textview
            txtNickname.setText(nickName);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedName=edNickName.getText().toString();
                if(TextUtils.isEmpty(updatedName)){
                    edNickName.setError(getResources().getString(R.string.error_message));
                }else {
                    editor.putString("Name", updatedName);
                    editor.commit();
                    //Toast use for notify user new name is changed
                    Toast.makeText(ChangeNicknameActivity.this,"Nickname changed successfully",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(ChangeNicknameActivity.this,EntriesActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}