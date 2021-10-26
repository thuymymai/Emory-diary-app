package com.example.emory;

import java.util.ArrayList;

public class DiaryList {
    private String date;
    private ArrayList<Diary> diaries;

    public DiaryList(String date, ArrayList<Diary> diaries) {
        this.date = date;
        this.diaries = diaries;
    }

    public String getDate() {
        return this.date;
    }

    public ArrayList<Diary> getDiaryData() {
        return this.diaries;
    }

    public String toString() {
        return this.date + this.diaries;
    }
}
