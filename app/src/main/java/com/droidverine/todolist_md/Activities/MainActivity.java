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

import android.view.View;


import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    SQLiteDb sqLiteDb;
    RecyclerView recyclerView;
    ToDoListAdapter toDoListAdapter;
    List<TodoList> todoLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclertodolist);
        setSupportActionBar(toolbar);
        sqLiteDb = new SQLiteDb(getApplicationContext());
        sqLiteDb.getReadableDatabase();
        todoLists = sqLiteDb.gettodolist();
        toDoListAdapter = new ToDoListAdapter(getApplicationContext(), todoLists);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(toDoListAdapter);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                this.startActivity(new Intent(MainActivity.this, AddtodoitemActivity.class));
                break;

        }
    }
}
