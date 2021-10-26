package com.example.emory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import static com.example.emory.SharedPref.TODOLIST;

public class AddTodoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ArrayList<Todo> todoList = new ArrayList<>();
    private DayMonthYear fullDate;
    EditText nameEditText, noteEditText;
    TextView deadlineEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        //Call shared prefs singleton
        SharedPref.init(getApplicationContext());
        //call a new DayMonthYear constructor
        fullDate = new DayMonthYear();

        deadlineEditText = findViewById(R.id.deadlineEditText);
        deadlineEditText.setText(fullDate.getCurrentFullDate());
        saveTodoList();
    }

    public void saveTodoList() {
        //First update the todoList in Shared prefs
        String dataReceived = SharedPref.read(TODOLIST, String.valueOf(new ArrayList<Todo>()));
        Gson gson1 = new Gson();
        Type type = new TypeToken<ArrayList<Todo>>() {
        }.getType();
        todoList = gson1.fromJson(dataReceived, type);

        //add more item to the list in Shared Prefs
        Button addBtn = findViewById(R.id.addTodo);
        addBtn.setOnClickListener(v -> {
            //take data from userinput
            nameEditText = findViewById(R.id.nameTodoEditText);
            deadlineEditText = findViewById(R.id.deadlineEditText);
            noteEditText = findViewById(R.id.noteEditText);
            //check if user input name or not
            if (nameEditText.getText().toString().matches("")) {
                Toast.makeText(this, "You did not enter Name", Toast.LENGTH_SHORT).show();
            } else {
                Todo todo = new Todo(nameEditText.getText().toString(),
                        deadlineEditText.getText().toString(), noteEditText.getText().toString());
                todoList.add(todo);
                //save to shared again
                Gson gson2 = new Gson();
                SharedPref.write(TODOLIST, gson2.toJson(todoList));
                /*call this new intent to start TodoListActivity again
                after finish() destroys the onCreate of that activity*/
                Intent intent = new Intent(AddTodoActivity.this, TodoListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //get date from calendar and set to text
    public void onCalendarClick(View v) {
        fullDate.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        deadlineEditText.setText(fullDate.setFullDate(year, month, dayOfMonth));
    }
}

