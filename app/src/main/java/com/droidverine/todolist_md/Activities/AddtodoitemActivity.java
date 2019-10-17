package com.droidverine.todolist_md.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.droidverine.todolist_md.R;
import com.droidverine.todolist_md.Utils.SQLiteDb;

public class AddtodoitemActivity extends AppCompatActivity implements View.OnClickListener {
    SQLiteDb sqLiteDb;
    EditText Edtname, Edtdate;
    Button btnaddtodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodoitem);
        Edtname = findViewById(R.id.EdtName);
        Edtdate = findViewById(R.id.EdtDate);
        btnaddtodo=findViewById(R.id.btnaddtolist);
        btnaddtodo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name, date;
        name = Edtname.getText().toString();
        date = Edtdate.getText().toString();
        switch (view.getId()) {
            case R.id.btnaddtolist:

                sqLiteDb = new SQLiteDb(getApplicationContext());
                sqLiteDb.getWritableDatabase();
                sqLiteDb.insertdb(name,date);
                break;

        }


    }
}
