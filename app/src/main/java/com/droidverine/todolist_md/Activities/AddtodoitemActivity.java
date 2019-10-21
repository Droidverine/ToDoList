package com.droidverine.todolist_md.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.droidverine.todolist_md.Models.TodoList;
import com.droidverine.todolist_md.R;
import com.droidverine.todolist_md.Utils.SQLiteDb;

import java.util.Calendar;
import java.util.List;

public class AddtodoitemActivity extends AppCompatActivity implements View.OnClickListener {
    Calendar calendar;
    SQLiteDb sqLiteDb;
    EditText Edtname, Edtdate;
    AutoCompleteTextView ACTVcategory;
    Button btnaddtodo, btndatepicker;
    List<TodoList> todoLists;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dateofMonth;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodoitem);
        Edtname = findViewById(R.id.EdtName);
        Edtdate = findViewById(R.id.EdtDate);
        btnaddtodo = findViewById(R.id.btnaddtolist);
        btndatepicker = findViewById(R.id.btndatepciker);
        btndatepicker.setOnClickListener(this);
        btnaddtodo.setOnClickListener(this);
        ACTVcategory = findViewById(R.id.Edtcategory);
        intent = getIntent();
        if (intent.getStringExtra("Activity") != null) {
            ACTVcategory.setVisibility(View.VISIBLE);
            Edtname.setVisibility(View.GONE);
            Edtdate.setVisibility(View.GONE);
            btndatepicker.setVisibility(View.GONE);

        } else if (intent.getStringExtra("EditCategory") != null) {

            ACTVcategory.setVisibility(View.VISIBLE);
            Edtname.setVisibility(View.GONE);
            Edtdate.setVisibility(View.GONE);
            btndatepicker.setVisibility(View.GONE);
        } else if (intent.getStringExtra("Edititem") != null) {
            ACTVcategory.setVisibility(View.GONE);
            Edtname.setVisibility(View.VISIBLE);
            Edtdate.setVisibility(View.VISIBLE);
            btndatepicker.setVisibility(View.VISIBLE);

        } else if (intent.getStringExtra("Category") != null) {
            Edtname.setVisibility(View.VISIBLE);
            Edtdate.setVisibility(View.VISIBLE);
            btndatepicker.setVisibility(View.VISIBLE);
        } else if (intent.getStringExtra("TasksActivity") != null) {
            ACTVcategory.setVisibility(View.GONE);
            Edtname.setVisibility(View.VISIBLE);
            Edtdate.setVisibility(View.VISIBLE);
            btndatepicker.setVisibility(View.VISIBLE);
        } else if (intent.getStringExtra("MovetoCat") != null) {
            ACTVcategory.setVisibility(View.VISIBLE);
            Edtname.setVisibility(View.GONE);
            Edtdate.setVisibility(View.GONE);
            btndatepicker.setVisibility(View.GONE);
        }
        SQLiteDb sqLiteDb = new SQLiteDb(getApplicationContext());
        todoLists = sqLiteDb.getcategories("abc");
        if (todoLists != null) {
            String[] array = new String[todoLists.size()];
            for (int i = 0; i < todoLists.size(); i++) {
                array[i] = todoLists.get(i).getTaskCategory();
                Log.d("mc", "onCreate: " + array[i]);

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, array);
            ACTVcategory.setAdapter(adapter);
        }

    }

    @Override
    public void onClick(View view) {
        String name, date, category;

        name = Edtname.getText().toString();

        date = Edtdate.getText().toString();
        category = ACTVcategory.getText().toString();
        switch (view.getId()) {
            case R.id.btnaddtolist:

                sqLiteDb = new SQLiteDb(getApplicationContext());
                sqLiteDb.getWritableDatabase();
                if (intent.getStringExtra("Activity") != null) {
                    String op = sqLiteDb.insertdb(category);

                } else if (intent.getStringExtra("TasksActivity") != null) {
                    name = Edtname.getText().toString();
                    date = Edtdate.getText().toString();
                    intent = getIntent();
                    Log.d("ghemc", "" + intent.getStringExtra("TasksActivity"));
                    sqLiteDb.insertdb(intent.getStringExtra("TasksActivity"), name, date);

                } else if (intent.getStringExtra("Edititem") != null) {
                    String edit = intent.getStringExtra("Edititem");
                    name = Edtname.getText().toString();
                    date = Edtdate.getText().toString();
                    sqLiteDb.edititem(edit, name, date);


                } else if (intent.getStringExtra("EditCategory") != null) {

                    String op = sqLiteDb.editcategory(intent.getStringExtra("EditCategory").toString(), ACTVcategory.getText().toString());
                    if (op.equals("Exist")) {
                        Toast.makeText(getApplicationContext(), "Already Exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Entered Successufully", Toast.LENGTH_SHORT).show();

                    }
                } else if (intent.getStringExtra("Category") != null) {
                    String op = sqLiteDb.insertdb(category, name, date);
                    if (op.equals("Exist")) {
                        Toast.makeText(getApplicationContext(), "Already Exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Entered Successufully", Toast.LENGTH_SHORT).show();

                    }
                } else if (intent.getStringExtra("MovetoCat") != null) {
                    String op = sqLiteDb.moveitem(category, intent.getStringExtra("MovetoCat"));
                    if (op.equals("Exist")) {
                        Toast.makeText(getApplicationContext(), "Already Exists in this category", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Moved Successufully", Toast.LENGTH_SHORT).show();

                    }
                }
                break;
            case R.id.btndatepciker:
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dateofMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddtodoitemActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Edtdate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dateofMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

        }

    }


}

