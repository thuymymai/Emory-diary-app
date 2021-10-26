package com.example.emory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EntriesActivity extends AppCompatActivity {
    private TextView month;
    private DayMonthYear monthYear;
    private ArrayList<DiaryList> diaryList = new ArrayList<>();
    private ListView listView;
    private static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);

        setDate();
        navigateBottom();
        createListView();
    }

    //the method is used to set the current month and year on the top
    public void setDate() {
        month = findViewById(R.id.month);
        monthYear = new DayMonthYear();
        month.setText(monthYear.getCurrentMonthYear());
    }

    //the navigation bar on the bottom is used to navigate to each page
    public void navigateBottom() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.entries);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.entries:
                    return true;

                case R.id.addMood:
                    startActivity(new Intent(this, AddMoodActivity.class));
                    return true;

                case R.id.toDoList:
                    startActivity(new Intent(this, TodoListActivity.class));
                    return true;

                case R.id.settings:
                    startActivity(new Intent(this, SettingsActivity.class));
                    return true;

                case R.id.moodGraph:
                    startActivity(new Intent(this, MoodAnalyticsActivity.class));
                    return true;
            }
            return false;
        });
    }

    /*for every click on the back button, it will load the previous month and year,
     remove previous month data and set new data to the view
     */
    public void onBack(View v) {
        month = findViewById(R.id.month);
        String prevMonthYear = monthYear.getPrevMonthYear(month.getText().toString());
        month.setText(prevMonthYear);
        removeExistingListView();
        createListView();
    }

    /*for every click on the next button, it will load the next month and year,
     remove previous month data and set new data to the view
     */
    public void onNext(View v) {
        month = findViewById(R.id.month);
        String nextMonthYear = monthYear.getNextMonthYear(month.getText().toString());
        month.setText(nextMonthYear);
        removeExistingListView();
        createListView();
    }

    //the method is used to delete all the current data in list view to apply new data into it
    public void removeExistingListView() {
        DiaryListAdapter diaryListAdapter = (DiaryListAdapter) listView.getAdapter();
        diaryListAdapter.clearData();
    }

    //the method is to load list view into an array adapter
    public void createListView() {
        Gson gson = new Gson();
        ArrayList<Diary> diaries;
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        String date = month.getText().toString();

        //get the total days of the current month year on the top nav bar
        Integer daysOfMonths = monthYear.getDaysInMonth(date);

        //loop in shared preferences to get the diary in a single day
        for (int i = daysOfMonths; i >= 1; i--) {
            String data = sharedPreferences.getString(i + ". " + date, String.valueOf(new ArrayList<Diary>()));
            Type diaryType = new TypeToken<ArrayList<Diary>>() {
            }.getType();
            diaries = gson.fromJson(data, diaryType);
            //if it has data, save to a diary list
            if (!diaries.isEmpty()) {
                diaryList.add(new DiaryList(i + ". " + date, diaries));
            }
        }

        //load the diary list into list view
        listView = findViewById(R.id.listView);
        listView.setAdapter(new DiaryListAdapter(this, diaryList));
    }

}