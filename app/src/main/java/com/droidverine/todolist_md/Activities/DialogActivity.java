package com.droidverine.todolist_md.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.droidverine.todolist_md.R;
import com.droidverine.todolist_md.Utils.SQLiteDb;

import androidx.annotation.Nullable;

public class DialogActivity extends Activity implements View.OnClickListener {
    Button btnyes, btnno;
    String category, task;
    SQLiteDb sqLiteDb;
    Boolean checkboxstat;
    String operationtype;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_popup);
        bundle = getIntent().getExtras();
        btnyes = findViewById(R.id.btnyes);
        btnno = findViewById(R.id.btnno);
        operationtype = bundle.getString("OperationType");
        task = bundle.getString("TaskName");
        category = bundle.getString("TaskCat");
        checkboxstat = bundle.getBoolean("Checkboxstat");
        sqLiteDb = new SQLiteDb(getApplicationContext());
        btnyes.setOnClickListener(this);
        btnno.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnyes:
                if (operationtype.equals("Delete")) {
                    sqLiteDb.deletetodoitem(category, task);
                    finish();

                } else if (operationtype.equals("Checkbox")) {
                    if (checkboxstat) {

                        sqLiteDb.checkboxset(category, task, "1");

                    } else {
                        sqLiteDb.checkboxset(category, task, "0");

                    }
                } else if (operationtype.equals("DeleteCategory")) {
                    sqLiteDb.deletecategory(category);

                }
                finish();

                break;
            case R.id.btnno:
                finish();
                break;


        }
    }
}
