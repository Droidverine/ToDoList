package com.droidverine.todolist_md.Activities;

import android.content.Intent;
import android.os.Bundle;
import com.droidverine.todolist_md.Adapters.ToDoListAdapter;
import com.droidverine.todolist_md.Models.TodoList;
import com.droidverine.todolist_md.Utils.SQLiteDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.droidverine.todolist_md.R;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    SQLiteDb sqLiteDb;
    RecyclerView recyclerView;
    ToDoListAdapter toDoListAdapter;
    List<TodoList> todoLists;
    Intent intent;

    @Override
    protected void onStart() {
        super.onStart();
        intent = getIntent();
        sqLiteDb = new SQLiteDb(getApplicationContext());
        sqLiteDb.getReadableDatabase();
        todoLists = sqLiteDb.getcategories("");
        toDoListAdapter = new ToDoListAdapter(getApplicationContext(), todoLists, "Home");
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(toDoListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclercategories);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddtodoitemActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Activity","HomeActivity");
                getApplicationContext().startActivity(intent);

            }
        });
    }

}
