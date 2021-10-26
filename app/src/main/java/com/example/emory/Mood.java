package com.example.emory;

import java.util.ArrayList;

public class Mood {
    private String mood;
    private ArrayList<Action> actions;

    public Mood(String mood, ArrayList<Action> actions) {
        this.mood = mood;
        this.actions = actions;
    }

    public String getMood() {
        return mood;
    }

    public ArrayList<Action> getActions() {
        return this.actions;
    }

    public String toString() {
        return this.mood + " " + String.valueOf(this.actions);
    }
}
