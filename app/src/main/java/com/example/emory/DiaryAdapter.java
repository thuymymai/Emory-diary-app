package com.example.emory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

//this adapter is to load the data of single diary to one card view on Entries view
public class DiaryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Diary> diaries;
    private ArrayList<Action> actions;

    public DiaryAdapter(Context context, ArrayList<Diary> diaries) {
        this.context = context;
        this.diaries = diaries;
        this.actions = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return diaries.size();
    }

    @Override
    public Object getItem(int position) {
        return diaries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get each diary from diaries based on each position
        Diary diary = diaries.get(position);
        actions = diary.getActions();

        if (convertView == null) {
            //then, inflate the specific custom view
            //and then, apply to list view
            convertView = View.inflate(this.context, R.layout.single_diary_view, null);
        }

        //find element in the convert view
        ImageView itemMood = convertView.findViewById(R.id.itemMood);
        ImageView itemPic = convertView.findViewById(R.id.itemPic);
        TextView itemNote = convertView.findViewById(R.id.itemNote);

        //retrieve drawable from Id
        //please find the reference from "Get Drawable from resource Id" from References 2 box on Planner
        Drawable mood = ContextCompat.getDrawable(context, diary.retrieveMoodIdFromName());

        //set drawable to Image view
        itemMood.setImageDrawable(mood);

        //this is the grid view to display all the actions in a single diary
        //it links with the Action Adapter
        GridView activityList = convertView.findViewById(R.id.activityList);
        //the adapter is only triggered when the action list is not empty
        if (!actions.isEmpty()) {
            activityList.setAdapter(new ActionAdapter(this.context, this.actions));
        }

        //set text to text view
        itemNote.setText("Note: " + diary.getNote());

        Bitmap pic = diary.decodePic();
        //this is just a work-around
        //instead of looking for a method to scale bitmap, just set the View to Gone by default
        //if the pic is not null, then set the view to Visible to display the pic
        if (pic != null) {
            itemPic.setVisibility(View.VISIBLE);
            itemPic.setImageBitmap(pic);
        }

        return convertView;
    }
}
