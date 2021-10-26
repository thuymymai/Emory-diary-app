package com.example.emory;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//the class take the calendar into use, so it needs to implement DatePickerDiaLog.OnDateSetListener
public class AddMoodActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView chosenDate;
    private DayMonthYear fullDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);

        chosenDate = findViewById(R.id.calendar);
        ImageButton btnGood = findViewById(R.id.good);
        ImageButton btnHappy = findViewById(R.id.happy);
        ImageButton btnExcited = findViewById(R.id.excited);
        ImageButton btnSad = findViewById(R.id.sad);
        ImageButton btnAwful = findViewById(R.id.awful);
        ImageButton btnTerrible = findViewById(R.id.terrible);
        ImageButton btnClose = findViewById(R.id.close);

        fullDate = new DayMonthYear();
        chosenDate.setText(fullDate.getCurrentFullDate());

        btnGood.setOnClickListener((View v) -> {
            startNote("good");
        });

        btnHappy.setOnClickListener((View v) -> {
            startNote("happy");
        });

        btnExcited.setOnClickListener((View v) -> {
            startNote("excited");
        });

        btnSad.setOnClickListener((View v) -> {
            startNote("sad");
        });

        btnAwful.setOnClickListener((View v) -> {
            startNote("awful");
        });

        btnTerrible.setOnClickListener((View v) -> {
            startNote("terrible");
        });

        btnClose.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, EntriesActivity.class);
            startActivity(intent);
        });
    }

    //start the note
    public void startNote(String drawable) {
        Intent intent = new Intent(this, WriteNoteActivity.class);
        //send icon with name to next activity
        intent.putExtra("icon", drawable);
        //send date to next activity
        intent.putExtra("date", chosenDate.getText().toString());
        startActivity(intent);
    }

    //the method to open calendar dialog
    public void onCalendarClick(View v) {
        fullDate.show(getSupportFragmentManager(), "date picker");
    }

    //the method to set full date to current text view, for example 4. March, 2021
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        chosenDate.setText(fullDate.setFullDate(year, month, dayOfMonth));
    }
}