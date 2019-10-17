package com.droidverine.todolist_md.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.droidverine.todolist_md.Models.TodoList;

import java.util.ArrayList;
import java.util.List;


public class SQLiteDb extends SQLiteOpenHelper {
    Constants constants = new Constants();
    public static final String DB_NAME = "ToDOList.db";
    String createquery = "CREATE TABLE " + constants.DBNAME + " ( " + constants.TASK_DATE + " VARCHAR , "
            + constants.TASK_NAME + " VARCHAR PRIMARY KEY , " + constants.TASK_STATUS + " VARCHAR );";

    public SQLiteDb(Context context) {
        super(context, DB_NAME, null, 1);
        Log.d("ALa", "databse");
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //for creating table by executing query written as string above "createquery".
        sqLiteDatabase.execSQL(createquery);
        Log.d("ALa", "databse22");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void insertdb(String Name, String Date) {
        //To Insert Data Into table
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(constants.TASK_NAME, Name);
        contentValues.put(constants.TASK_DATE, Date);
        contentValues.put(constants.TASK_STATUS, "PENDING");
        sqLiteDatabase.insert(constants.DBNAME, null, contentValues);
        Log.d("inserted", "data");

    }
    public List<TodoList> gettodolist( )
    {

        List<TodoList> todoListArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        //  Cursor cursor = db.query(PARTICIPANTS_TABLE, new String[]{"*"},null, null, null, null, null);
        String cursorQuery = "SELECT * FROM " +constants.DBNAME+" ORDER BY TASKDATE;";
        Cursor cursor = db.rawQuery(cursorQuery, null);
        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious())
        {
            TodoList todoList_data = new TodoList();
            todoList_data.setTaskname(cursor.getString(cursor.getColumnIndex(constants.TASK_NAME)));
            todoList_data.setTaskdate(cursor.getString(cursor.getColumnIndex(constants.TASK_DATE)));
            todoList_data.setTaskstatus(cursor.getString(cursor.getColumnIndex(constants.TASK_DATE)));
            todoListArrayList.add(todoList_data);
            Log.d("getdata",cursor.getString(cursor.getColumnIndex(constants.TASK_NAME)));
        }
        cursor.close();
        db.close();
        return todoListArrayList;
    }
}
