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

//the function of this adapter is to load all activities to grid view displayed on write note xml
public class ActionList extends BaseAdapter {
    private Context context;
    private ArrayList<Action> actionList;

    //constructor to create the adapter, and add action to actionList
    public ActionList(Context context) {
        this.context = context;
        this.actionList = new ArrayList<>();
        this.actionList.add(new Action("family", R.drawable.action_ic_family));
        this.actionList.add(new Action("friends", R.drawable.action_ic_friends));
        this.actionList.add(new Action("favourite", R.drawable.action_ic_favourite));
        this.actionList.add(new Action("fitness", R.drawable.action_ic_fitness));
        this.actionList.add(new Action("food", R.drawable.action_ic_food));
        this.actionList.add(new Action("movie", R.drawable.action_ic_movie));
        this.actionList.add(new Action("sleep", R.drawable.action_ic_sleep));
        this.actionList.add(new Action("travel", R.drawable.action_ic_travel));
        this.actionList.add(new Action("study", R.drawable.action_ic_study));
        this.actionList.add(new Action("weather", R.drawable.action_ic_weather));
        this.actionList.add(new Action("work", R.drawable.action_ic_work));
        this.actionList.add(new Action("shopping", R.drawable.action_ic_shopping));
        this.actionList.add(new Action("games", R.drawable.action_ic_games));
        this.actionList.add(new Action("celebration", R.drawable.action_ic_celebration));
        this.actionList.add(new Action("relax", R.drawable.action_ic_relax));
        this.actionList.add(new Action("sick", R.drawable.action_ic_sick));
    }

    public ActionList(Context context, ArrayList<Action> allActions) {
        this.context = context;
        this.actionList = allActions;
    }

    public int getSize() {
        return this.actionList.size();
    }

    public Action getAction(int position) {
        return this.actionList.get(position);
    }

    public ArrayList<Action> getAllActions() {
        return this.actionList;
    }

    @Override
    public int getCount() {
        return this.getSize();
    }

    @Override
    public Object getItem(int position) {
        return this.getAction(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get each action from action list based on each position
        Action action = this.actionList.get(position);

        if (convertView == null) {
            //then, inflate the specific custom view
            //and then, apply to grid view
            convertView = View.inflate(this.context, R.layout.write_note_single_action, null);
        }

        //find element in the convert view
        ImageView itemView = convertView.findViewById(R.id.imageView);
        TextView itemActionText = convertView.findViewById(R.id.itemActionText);

        //get drawable from id, please find reference from "Get drawable from resourceId " from Reference 2 box on Planner
        Drawable actionIcon = ContextCompat.getDrawable(context, action.retrieveId());

        //set drawable to image view
        itemView.setImageDrawable(actionIcon);

        //set text to text view
        itemActionText.setText(action.getName());

        return convertView;
    }
}
