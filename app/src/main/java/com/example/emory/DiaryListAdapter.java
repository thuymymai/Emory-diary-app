package com.example.emory;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

//this adapter is to load the data of a diary list of a day into a list view on Entries view
public class DiaryListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DiaryList> diaryLists;

    public DiaryListAdapter(Context context, ArrayList<DiaryList> diaryLists) {
        this.context = context;
        this.diaryLists = diaryLists;
    }

    @Override
    public int getCount() {
        return diaryLists.size();
    }

    @Override
    public Object getItem(int position) {
        return diaryLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearData() {
        diaryLists.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get each day diary from the diary list based on each position
        DiaryList diaryList = diaryLists.get(position);

        if (convertView == null) {
            //then, inflate the specific custom view
            //and then, apply to list view
            convertView = View.inflate(context, R.layout.card_view, null);
        }

        //find element in the convert view
        TextView date = convertView.findViewById(R.id.date);
        ListView itemView = convertView.findViewById(R.id.itemView);

        //set month and year to the top
        date.setText(String.valueOf(diaryList.getDate()));

        //reverse the list to put the newest one on the top and the oldest one below
        ArrayList<Diary> reversedList = new ArrayList<>(diaryList.getDiaryData());
        Collections.reverse(reversedList);

        //take the reversed list into a new adapter to iterate
        itemView.setAdapter(new DiaryAdapter(this.context, reversedList));

        return convertView;
    }

}
