package com.example.emory;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

/*
this class is to call intent to trigger Alarm service
please see reference on planner
 */
public class ReminderActivity extends AppCompatActivity {
    private int notificationId = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        //set handler for Float button, when it clicked it generate an alarm at the user picked time
        FloatingActionButton floatBtn = findViewById(R.id.doneChooseTime);
        floatBtn.setOnClickListener(view -> {
            TimePicker timePicker = findViewById(R.id.timePicker);
            //this intent link to Alarm manager class
            Intent intent = new Intent(ReminderActivity.this, AlarmReceiver.class);
            intent.putExtra("notification", notificationId);

            PendingIntent alarmIntent = PendingIntent.getBroadcast(ReminderActivity.this, 0,
                    intent, PendingIntent.FLAG_CANCEL_CURRENT);

            AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
            //hour and minute picked by user
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();

            //call Calendar singleton
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, hour);
            startTime.set(Calendar.MINUTE, minute);
            startTime.set(Calendar.SECOND, 0);
            long alarmStartTime = startTime.getTimeInMillis();

            //repeat alarm everyday at the time set
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime, AlarmManager.INTERVAL_DAY, alarmIntent);
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

