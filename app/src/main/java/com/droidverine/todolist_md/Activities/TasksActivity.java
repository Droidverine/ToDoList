package com.droidverine.todolist_md.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.droidverine.todolist_md.Adapters.ToDoListAdapter;
import com.droidverine.todolist_md.Models.TodoList;
import com.droidverine.todolist_md.R;
import com.droidverine.todolist_md.Utils.SQLiteDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.util.List;

public class TasksActivity extends AppCompatActivity implements View.OnClickListener {
    SQLiteDb sqLiteDb;
    RecyclerView recyclerView;
    ToDoListAdapter toDoListAdapter;
    List<TodoList> todoLists;
    Intent intent, intent1;


    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("Activity resumed", "resumed");
        sqLiteDb = new SQLiteDb(getApplicationContext());
        sqLiteDb.getReadableDatabase();
        intent1 = getIntent();

        Log.d("category ali", "" + intent1.getStringExtra("Category"));
        todoLists = sqLiteDb.gettodolist(intent1.getStringExtra("Category"));
        toDoListAdapter = new ToDoListAdapter(getApplicationContext(), todoLists, "taks");
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(toDoListAdapter);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclertodolist);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                //TO add new task
                Intent intent = new Intent(TasksActivity.this, AddtodoitemActivity.class);
                intent.putExtra("TasksActivity", intent1.getStringExtra("Category"));
                this.startActivity(intent);
                break;

        }
    }
}
