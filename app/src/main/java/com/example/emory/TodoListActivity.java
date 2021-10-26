package com.example.emory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static com.example.emory.SharedPref.GET_CHECKED;
import static com.example.emory.SharedPref.TODOLIST;

public class TodoListActivity extends AppCompatActivity {
    private ArrayList<Todo> todoList = new ArrayList<>();
    private ArrayAdapter<Todo> arrayAdapter;
    private int position = 0;
    private String itemSelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);
        //Call shared preferences singleton
        SharedPref.init(getApplicationContext());

        //button to next activity to add item to list
        FloatingActionButton floatBtn = findViewById(R.id.addTodoBtn);
        floatBtn.setOnClickListener((View v) -> getDetails());

        //Bottom navigation link
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.toDoList);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.entries:
                    startActivity(new Intent(this, EntriesActivity.class));
                    return true;
                case R.id.addMood:
                    startActivity(new Intent(this, AddMoodActivity.class));
                    return true;

                case R.id.toDoList:
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

        //Call float context menu
        ListView lv = findViewById(R.id.listView);
        registerForContextMenu(lv);

        //call function of list view
        reload();

        //save checked state of checkbox in list view
        getSavedItem();

    }

    private void getDetails() {
        Intent intent = new Intent(this, AddTodoActivity.class);
        startActivityForResult(intent, 1);
    }

    private void reload() {
        //Shared Preferences singleton to take the array list sent from AddTodoActivity
        String dataReceived = SharedPref.read(TODOLIST, String.valueOf(new ArrayList<Todo>()));
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Todo>>() {
        }.getType();
        todoList = gson.fromJson(dataReceived, type);

        //cast list view to array adapter
        ListView lv = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                todoList);
        lv.setAdapter(arrayAdapter);

        //on Item click will save the state of checkbox clicked
        lv.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            int count = lv.getCount();
            SparseBooleanArray sparseBooleanArray = lv.getCheckedItemPositions();
            for (int h = 0; h < count; h++) {
                if (sparseBooleanArray.get(h)) {
                    itemSelected += lv.getItemAtPosition(h).toString() + " ";
                    SharedPref.write(GET_CHECKED, itemSelected);
                }
            }
        });
        //user can choose multiple item
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //on long click will pop-up a float context menu
        lv.setOnItemLongClickListener((adapterView, view, i, l) -> {
            position = i;
            openContextMenu(lv);
            return true;
        });

    }

    //Create context menu and set function to options
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.todolist_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //option 1 to TdoDetails activity, check name, note and deadline of to do
            case R.id.option_1:
                Intent nextActivity = new Intent(TodoListActivity.this, TodoListDetails.class);
                nextActivity.putExtra("indexOfTodo", position);
                Log.d("mi", String.valueOf(position));
                startActivity(nextActivity);
                return true;
            //option 2 delete item
            case R.id.option_2:
                removeItem(todoList.get(position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //function to remove item from list and update checked state
    private void removeItem(Todo todo) {
        arrayAdapter.remove(todo);
        todoList.remove(todo);
        saveData();
        getSavedItem();
        arrayAdapter.notifyDataSetChanged();
    }

    //save array list to singleton shared prefs
    private void saveData() {
        Gson gson = new Gson();
        SharedPref.write(TODOLIST, gson.toJson(todoList));
    }

    //get the checked item from shared prefs after activity is destroyed
    private void getSavedItem() {
        String savedItem = SharedPref.read(GET_CHECKED, "");
        ListView lv = findViewById(R.id.listView);
        int count = lv.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            String currentItem = lv.getAdapter().getItem(i).toString();
            if (savedItem.contains(currentItem)) {
                lv.setItemChecked(i, true);
            } else {
                lv.setItemChecked(i, false);
            }
        }
    }
}

