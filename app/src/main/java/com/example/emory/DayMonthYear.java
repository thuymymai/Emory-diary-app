package com.example.emory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

//the class extends dialog fragment to create a calendar dialog and take it into use.
//please find the reference "Date picker and Calendar Dialog" on References box on Planner.
public class DayMonthYear extends DialogFragment {
    private ArrayList<String> months;
    private Calendar calendar;

    //the method to create calendar dialog
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        return new DatePickerDialog(getActivity(), R.style.date_picker_style,
                (DatePickerDialog.OnDateSetListener) getActivity(),
                year, month, day);
    }

    public DayMonthYear() {
        this.months = new ArrayList<>();
        this.calendar = Calendar.getInstance();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
    }

    public int getCurrentDay() {
        return calendar.get(Calendar.DATE);
    }

    //get full current date, for example, 4. March, 2021
    public String getCurrentFullDate() {
        return calendar.get(Calendar.DATE) + ". " +
                this.months.get(calendar.get(Calendar.MONTH)) + ", " +
                calendar.get(Calendar.YEAR);
    }

    //get current month and year, for example March, 2021
    public String getCurrentMonthYear() {
        return this.months.get(calendar.get(Calendar.MONTH)) + ", " + calendar.get(Calendar.YEAR);
    }

    public String getPrevMonthYear(String date) {
        String[] parts = date.split(", ");
        Integer year = Integer.parseInt(parts[1]);
        String prevMonth = "";
        int curYear = year;

        /*if the month is January, set the previous month to be December,
        and the year will be minus 1 */
        if (parts[0].equals("January")) {
            prevMonth = "December";
            curYear -= 1;
            return prevMonth + ", " + curYear;
        }

        //Otherwise, loop through months arraylist to find the previous index in months
        for (int i = 0; i < months.size(); i++) {
            if (months.get(i).equals(parts[0])) {
                prevMonth = months.get(i - 1);
            }
        }
        return prevMonth + ", " + curYear;
    }

    public String getNextMonthYear(String date) {
        String[] parts = date.split(", ");
        Integer year = Integer.parseInt(parts[1]);
        String nextMonth = "";
        int curYear = year;

        /*if the month is December, set the next month to be January,
        and the year will be added 1 */
        if (parts[0].equals("December")) {
            nextMonth = "January";
            curYear += 1;
            return nextMonth + ", " + curYear;
        }

        //otherwise, loop through months arraylist to find the next index in months
        for (int i = 0; i < months.size(); i++) {
            if (months.get(i).equals(parts[0])) {
                nextMonth = months.get(i + 1);
            }
        }
        return nextMonth + ", " + curYear;
    }

    //set a random date in a month, for example 28. February, 2021
    public String setFullDate(int year, int month, int date) {
        return date + ". " + this.months.get((month)) + ", " + year;
    }

    //get total days in a month provided in parameter
    public int getDaysInMonth(String monthYear) {
        String[] parts = monthYear.split(", ");
        calendar.set(Calendar.MONTH, months.indexOf(parts[0]));
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return daysInMonth;
    }
}
