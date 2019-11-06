package com.droidverine.todolist_md.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.droidverine.todolist_md.Models.TodoList;
import com.droidverine.todolist_md.R;
import com.droidverine.todolist_md.Utils.SQLiteDb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddtodoitemActivity extends AppCompatActivity implements View.OnClickListener {
    Calendar calendar;
    SQLiteDb sqLiteDb;
    EditText Edtname, Edtdate, Edtcat;
    Button btnaddtodo, btndatepicker;
    List<TodoList> todoLists;
    DatePickerDialog datePickerDialog;
    TextView TxtTasktype;
    int year;
    int month;
    int dateofMonth;
    Intent intent;
    Spinner spinnercategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodoitem);
        spinnercategories = (Spinner) findViewById(R.id.spiner_categories);

        Edtcat = findViewById(R.id.Edtcategory);
        Edtname = findViewById(R.id.EdtName);
        Edtdate = findViewById(R.id.EdtDate);
        btnaddtodo = findViewById(R.id.btnaddtolist);
        btndatepicker = findViewById(R.id.btndatepciker);
        TxtTasktype = findViewById(R.id.Txttasktype);
        btndatepicker.setOnClickListener(this);
        btnaddtodo.setOnClickListener(this);
        intent = getIntent();
        if (intent.getStringExtra("Activity") != null) {
            Edtcat.setVisibility(View.VISIBLE);
            Edtname.setVisibility(View.GONE);
            Edtdate.setVisibility(View.GONE);
            btndatepicker.setVisibility(View.GONE);
            TxtTasktype.setText("Create Category");
            btnaddtodo.setText("Create");


        } else if (intent.getStringExtra("EditCategory") != null) {

            Edtcat.setVisibility(View.VISIBLE);
            Edtname.setVisibility(View.GONE);
            Edtdate.setVisibility(View.GONE);
            btndatepicker.setVisibility(View.GONE);
            TxtTasktype.setText("Edit category");
            btnaddtodo.setText("Edit");

        } else if (intent.getStringExtra("Edititem") != null) {
            Edtcat.setVisibility(View.GONE);

            Edtname.setVisibility(View.VISIBLE);
            Edtdate.setVisibility(View.VISIBLE);
            btndatepicker.setVisibility(View.VISIBLE);
            TxtTasktype.setText("Edit Item");
            btnaddtodo.setText("Edit");



        } else if (intent.getStringExtra("Category") != null) {
            Edtname.setVisibility(View.VISIBLE);
            Edtdate.setVisibility(View.VISIBLE);
            btndatepicker.setVisibility(View.VISIBLE);
        } else if (intent.getStringExtra("TasksActivity") != null) {
            Edtcat.setVisibility(View.GONE);
            TxtTasktype.setText("Create task");
            btnaddtodo.setText("Create");



            Edtname.setVisibility(View.VISIBLE);
            Edtdate.setVisibility(View.VISIBLE);
            btndatepicker.setVisibility(View.VISIBLE);
        } else if (intent.getStringExtra("MovetoCat") != null) {
            spinnercategories.setVisibility(View.VISIBLE);
            btnaddtodo.setText("Move");

            Edtcat.setVisibility(View.GONE);
            Edtname.setVisibility(View.GONE);
            Edtdate.setVisibility(View.GONE);
            btndatepicker.setVisibility(View.GONE);
            SQLiteDb sqLiteDb = new SQLiteDb(getApplicationContext());
            todoLists = sqLiteDb.getcategories("abc");
            List<String> abc = new ArrayList<>();
            todoLists = sqLiteDb.getcategories("abc");
            for (int i = 0; i < todoLists.size(); i++) {
                abc.add(todoLists.get(i).getTaskCategory());

            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, abc);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnercategories.setAdapter(dataAdapter);
        }
        {

        }

    }

    @Override
    public void onClick(View view) {
        String name, date, category;
        String response;
        name = Edtname.getText().toString();

        date = Edtdate.getText().toString();
        category = Edtcat.getText().toString();
        switch (view.getId()) {
            case R.id.btnaddtolist:

                sqLiteDb = new SQLiteDb(getApplicationContext());
                sqLiteDb.getWritableDatabase();
                if (intent.getStringExtra("Activity") != null) {
                    if(category.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Category can't be empty",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String op = sqLiteDb.insertdb(category);
                        if(op.equals("Succesfull"))
                        {
                            Toast.makeText(getApplicationContext(),"Whoaaa",Toast.LENGTH_SHORT).show();
                            finish();

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Already Exists",Toast.LENGTH_SHORT).show();

                        }

                    }


                } else if (intent.getStringExtra("TasksActivity") != null) {
                    name = Edtname.getText().toString();

                    date = Edtdate.getText().toString();
                    intent = getIntent();
                    String ed_text = Edtname.getText().toString().trim();

                    String msg;
                    if(ed_text.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Task name can't be empty", Toast.LENGTH_SHORT).show();

                    }else{
                        if (date.equals("")) {
                            response=  sqLiteDb.insertdb(intent.getStringExtra("TasksActivity"), name, "Infinity");
                            if(response.equals("Exist"))
                            {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                finish();

                            }

                        } else {
                            response= sqLiteDb.insertdb(intent.getStringExtra("TasksActivity"), name, date);
                            if(response.equals("Exist"))
                            {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }
                    }



                } else if (intent.getStringExtra("Edititem") != null) {
                    String edit = intent.getStringExtra("Edititem");
                    name = Edtname.getText().toString();
                    date = Edtdate.getText().toString();
                    String ed_text = Edtname.getText().toString().trim();

                    String msg;
                    if(ed_text.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Name cant be empty", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        if (date.equals("")) {
                            msg = sqLiteDb.edititem(edit, name, "Infinity");
                            finish();

                        } else {
                            msg = sqLiteDb.edititem(edit, name, date);
                            finish();

                        }
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

}

                } else if (intent.getStringExtra("EditCategory") != null) {
                   category= Edtcat.getText().toString();
                    if(category.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"category cant be null", Toast.LENGTH_SHORT).show();

                    }else {
                    String op = sqLiteDb.editcategory(intent.getStringExtra("EditCategory"), category);
                    if (op.equals("Exists")) {
                        Toast.makeText(getApplicationContext(), op, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), op, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    }
                } else if (intent.getStringExtra("Category") != null) {

                } else if (intent.getStringExtra("MovetoCat") != null) {

                    String op = sqLiteDb.moveitem(String.valueOf(spinnercategories.getSelectedItem()), intent.getStringExtra("MovetoCat"));
                    if (op.equals("Exist")) {
                        Toast.makeText(getApplicationContext(), "Already Exists in this category", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Moved Successufully", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
                break;
            case R.id.btndatepciker:
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dateofMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddtodoitemActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                if (day > 9) {
                                    Edtdate.setText(year + "/" + (month + 1) + "/" + day);

                                } else if (day < 10) {
                                    Edtdate.setText(year + "/" + (month + 1) + "/0" + day);

                                }
                            }
                        }, year, month, dateofMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

                datePickerDialog.show();

        }

    }


}

