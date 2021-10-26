package com.example.emory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.emory.SharedPref.TODOLIST;

public class TodoListDetails extends AppCompatActivity {
    ArrayList<Todo> todoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_details);
        //call shared prefs singleton and take array list of to do item
        SharedPref.init(getApplicationContext());
        String dataReceived = SharedPref.read(TODOLIST, String.valueOf(new ArrayList<Todo>()));
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Todo>>() {
        }.getType();
        todoList = gson.fromJson(dataReceived, type);

        //get index from intent put extra
        Bundle b = getIntent().getExtras();
        int indexOfTodo = b.getInt("indexOfTodo");

        //set info to textView by function in To do class
        ((TextView) findViewById(R.id.nameTextView)).setText(String.valueOf(todoList.get(indexOfTodo).getName()));
        ((TextView) findViewById(R.id.deadlineTextView)).setText(String.valueOf(todoList.get(indexOfTodo).getDeadline()));
        ((TextView) findViewById(R.id.noteTextView)).setText(String.valueOf(todoList.get(indexOfTodo).getNote()));
    }
}