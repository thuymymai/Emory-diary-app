package com.example.emory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/*
This singleton class have some purposes:
- save and retrieve data in a organized way since we can have different KEY for different type of data
-  save time by not writing 20 times of the same code
- easier to understand the shared pref and also singleton
 */
public class SharedPref {
    private static SharedPreferences mySharedPref;
    //key to save todolist
    public static final String TODOLIST = "TODOLIST";
    //key to save checked state of check box in list view
    public static final String GET_CHECKED = "BOOLEAN";

    //constructor
    private SharedPref() {

    }

    //call instance of singleton with context
    public static void init(Context context) {
        if (mySharedPref == null)
            mySharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    //save data to singleton shared pref
    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mySharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mySharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    //retrieve data from singleton shared pref
    public static String read(String key, String defValue) {
        return mySharedPref.getString(key, defValue);
    }

    public static boolean read(String key, boolean defValue) {
        return mySharedPref.getBoolean(key, defValue);
    }
}