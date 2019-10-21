package com.droidverine.todolist_md.Utils;

import android.content.ComponentName;
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
    String createtabletasks = "CREATE TABLE " + constants.DBNAME + " ( " + constants.TASK_DATE + " VARCHAR , "
            + constants.TASK_NAME + " VARCHAR PRIMARY KEY , " + constants.TASK_CATEGORY + " VARCHAR , " + constants.TASK_STATUS + " VARCHAR );";
    String createtablecategories = "CREATE TABLE " + constants.TABLE_CATEGORIES + " ( " + constants.CATEGORY_INDEX + " VARCHAR , "
            + constants.CATEGORY_NAME + " VARCHAR PRIMARY KEY ); ";

    public SQLiteDb(Context context) {
        super(context, DB_NAME, null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //for creating table by executing query written as string above "createquery".
        sqLiteDatabase.execSQL(createtabletasks);
        sqLiteDatabase.execSQL(createtablecategories);

        Log.d("DATABASE", "CREATED SUCCESSFULLY");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public String insertdb(String Category, String TaskName, String Date) {
        //To Insert Data Into table
        try {


            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(constants.TASK_CATEGORY, Category);
            contentValues.put(constants.TASK_NAME, TaskName);
            contentValues.put(constants.TASK_DATE, Date);
            contentValues.put(constants.TASK_STATUS, "0");
            sqLiteDatabase.insertOrThrow(constants.DBNAME, null, contentValues);
            Log.d("DATABASE", "ENTERED SUCCESSFULLY");
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            Log.e("DATABASE ERROR", e.toString());
            return "Exist";

        }
        return "Succesfull";
    }

    public String edititem(String oldTaskName, String TaskName, String taskdate) {
        //To Edit item
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String cursorQuery = "UPDATE " + constants.DBNAME + " SET " + constants.TASK_NAME + " = '" + TaskName + "' , " +
                    "" + constants.TASK_DATE + " = '" +
                    " " + taskdate + " ' WHERE " + constants.TASK_NAME + " = '" + oldTaskName +
                    "' ;";
            db.execSQL(cursorQuery);
            Log.d("DATABASE", "ENTERED SUCCESSFULLY");
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            Log.e("DATABASE ERROR", e.toString());
            return "Exist";

        }
        return "Succesfull";
    }

    public String editcategory(String oldCategoryname, String newCategoryname) {
        try {
            //To Edit category

            SQLiteDatabase db = this.getWritableDatabase();
            String cursorQueryfortaskstable = "UPDATE " + constants.DBNAME + " SET " + constants.TASK_CATEGORY + " = '" + newCategoryname + "'" + " WHERE " + constants.TASK_CATEGORY + " = '" + oldCategoryname +
                    "' ;";
            db.execSQL(cursorQueryfortaskstable);
            String cursorQueryforcategoriestable = "UPDATE " + constants.TABLE_CATEGORIES + " SET " + constants.CATEGORY_NAME + " = '" + newCategoryname + "'" + " WHERE " + constants.CATEGORY_NAME + " = '" + oldCategoryname +
                    "' ;";
            db.execSQL(cursorQueryforcategoriestable);
            Log.d("DATABASE", "ENTERED SUCCESSFULLY");
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            Log.e("DATABASE ERROR", e.toString());
            return "Exist";

        }
        return "Succesfull";
    }

    public List<TodoList> gettodolist(String Category) {

        List<TodoList> todoListArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String cursorQuery = "SELECT * FROM " + constants.DBNAME + " WHERE " + constants.TASK_CATEGORY + " = '" + Category + "' ORDER BY TASKSTATYS DESC, TASKDATE ASC;";
        Cursor cursor = db.rawQuery(cursorQuery, null);
        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            TodoList todoList_data = new TodoList();
            todoList_data.setTaskCategory(cursor.getString(cursor.getColumnIndex(constants.TASK_CATEGORY)));
            todoList_data.setTaskname(cursor.getString(cursor.getColumnIndex(constants.TASK_NAME)));
            todoList_data.setTaskdate(cursor.getString(cursor.getColumnIndex(constants.TASK_DATE)));
            todoList_data.setTaskstatus(cursor.getString(cursor.getColumnIndex(constants.TASK_STATUS)));
            todoListArrayList.add(todoList_data);
            Log.d("DATABASE", todoListArrayList.toString());
        }


        cursor.close();
        db.close();
        return todoListArrayList;
    }

    public String insertdb(String Category) {
        try {


            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(constants.CATEGORY_NAME, Category);
            sqLiteDatabase.insertOrThrow(constants.TABLE_CATEGORIES, null, contentValues);
            Log.d("DATABASE DATA INSERTED", "CATEGORIES");
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            Log.e("already there", "CATEGORIES");
            return "Exist";
        }
        return "Succesfull";
    }

    public List<TodoList> getcategories(String Category) {

        List<TodoList> todoLists = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String cursorQuery = "SELECT DISTINCT " + constants.CATEGORY_NAME + " FROM " + constants.TABLE_CATEGORIES + " ;";
        Cursor cursor = db.rawQuery(cursorQuery, null);
        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            TodoList todoList_data = new TodoList();
            todoList_data.setTaskCategory(cursor.getString(cursor.getColumnIndex(constants.CATEGORY_NAME)));

            todoLists.add(todoList_data);
        }
        cursor.close();
        db.close();
        return todoLists;
    }

    public void checkboxset(String category, String taskname, String status) {

        SQLiteDatabase db = this.getWritableDatabase();
        String cursorQuery = "UPDATE " + constants.DBNAME + " SET " + constants.TASK_STATUS + " = '" + status + "'" + " WHERE " + constants.TASK_NAME + " = '" + taskname + "' AND "
                + constants.TASK_CATEGORY + " = '" + category + "' ;";
        db.execSQL(cursorQuery);

    }

    public void deletetodoitem(String category, String taskname) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (taskname != null) {
            String cursorQuery = "DELETE FROM " + constants.DBNAME + " WHERE " + constants.TASK_NAME + " = '" + taskname + "' AND "
                    + constants.TASK_CATEGORY + " = '" + category + "' ;";
            db.execSQL(cursorQuery);

        } else {
            //String  cursorQueryforcategoriestable = "DELETE " + constants.TABLE_CATEGORIES + " " + constants.CATEGORY_NAME + " = '" + category + "'" + " WHERE " + constants.CATEGORY_NAME + " = '" + category + "' AND "
            //         + constants.TASK_CATEGORY + " = '" + category + "' ;";
            //     String  cursorQueryfortodoitemstable = "DELETE " + constants.DBNAME + " " + constants.C + " = '" + category + "'" + " WHERE " + constants.CATEGORY_NAME + " = '" + category + "' AND "
            //           + constants.TASK_CATEGORY + " = '" + category + "' ;";
        }


    }

    public int countitems(String category) {
        SQLiteDatabase db = this.getReadableDatabase();

        String cursorQueryforcategoriestable = "SELECT * FROM " + constants.DBNAME + " WHERE TASKCATEGORY = '" + category + "';";
        // db.execSQL(cursorQueryforcategoriestable);
        Cursor cursor = db.rawQuery(cursorQueryforcategoriestable, null);
        int count = cursor.getCount();
        cursor.close();
        Log.d("counter", "" + count);

        return count;
    }

    public int getcompletedcount(String category) {
        SQLiteDatabase db = this.getReadableDatabase();

        String cursorQueryforcategoriestable = "SELECT * FROM " + constants.DBNAME + " WHERE TASKCATEGORY = '" + category + "' AND TASKSTATYS = '1' ;";
        // db.execSQL(cursorQueryforcategoriestable);
        Cursor cursor = db.rawQuery(cursorQueryforcategoriestable, null);
        int count = cursor.getCount();
        cursor.close();
        Log.d("completed count", "" + count);

        return count;
    }

    public String moveitem(String Category, String TaskName) {
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String cursorQuery = "UPDATE " + constants.DBNAME + " SET " + constants.TASK_CATEGORY + " = '" + Category + "'  " +
                    " WHERE " + constants.TASK_NAME + " = '" + TaskName +
                    "' ;";
            db.execSQL(cursorQuery);
            Log.d("DATABASE", "Moved SUCCESSFULLY");
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            Log.e("DATABASE ERROR", e.toString());
            return "Exist";

        }
        return "Succesfull";
    }


}
