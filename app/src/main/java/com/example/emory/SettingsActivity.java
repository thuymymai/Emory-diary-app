package com.example.emory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.entries:
                    startActivity(new Intent(this, EntriesActivity.class));
                    return true;

                case R.id.addMood:
                    startActivity(new Intent(this, AddMoodActivity.class));
                    return true;

                case R.id.toDoList:
                    startActivity(new Intent(this, TodoListActivity.class));
                    return true;

                case R.id.settings:
                    return true;

                case R.id.moodGraph:
                    startActivity(new Intent(this, MoodAnalyticsActivity.class));
                    return true;
            }
            return false;
        });

        //get user nickname from sign up activity
        SharedPreferences sharedPreferences = getSharedPreferences("SignUp", MODE_PRIVATE);
        TextView nickname = findViewById(R.id.nickname);
        String saveNickname = sharedPreferences.getString("Name", "Nickname");
        nickname.setText(saveNickname);

        //Button link to Reminder activity to set time for notification
        Button alarmBtn = findViewById(R.id.setAlarm);
        alarmBtn.setOnClickListener((View v) -> {
            Intent intent = new Intent(SettingsActivity.this, ReminderActivity.class);
            startActivity(intent);
        });

        //go to change nickname activity
        Button btnNickName=findViewById(R.id.changeNickname);
        btnNickName.setOnClickListener((View v) -> {
            Intent intent = new Intent(SettingsActivity.this, ChangeNicknameActivity.class);
            startActivity(intent);
        });

        //got to change password activity
        Button btnPassword=findViewById(R.id.changePassword);
        btnPassword.setOnClickListener((View v) -> {
            Intent intent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });
    }

}
