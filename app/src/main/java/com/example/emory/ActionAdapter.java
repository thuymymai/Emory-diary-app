package com.example.emory;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

//this adapter is to load the arraylist of actions into entries card view
public class ActionAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Action> actions;

    public ActionAdapter(Context context, ArrayList<Action> actions) {
        this.context = context;
        this.actions = actions;
    }

    @Override
    public int getCount() {
        return actions.size();
    }

    @Override
    public Object getItem(int position) {
        return actions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get each action from action array list based on each position
        Action action = actions.get(position);

        if (convertView == null) {
            //then, inflate the specific custom view
            //and then, apply to list view
            convertView = View.inflate(this.context, R.layout.single_action_view, null);
        }

        //find element in the convert view
        ImageView itemActionIcon = convertView.findViewById(R.id.itemActionIcon);
        TextView itemActionText = convertView.findViewById(R.id.itemActionText);

        //get drawable from id, please find reference from "Get drawable from resourceId " from Reference 2 box on Planner
        Drawable actionIcon = ContextCompat.getDrawable(context, action.retrieveId());

        //set drawable to image view
        itemActionIcon.setImageDrawable(actionIcon);

        //set text to text view
        itemActionText.setText(action.getName());

        return convertView;
    }
}
