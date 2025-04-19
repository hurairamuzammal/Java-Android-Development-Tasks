package com.example.task_manager;



import android.content.ContentValues;

import android.content.Context;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;



import androidx.annotation.Nullable;





public class db_class extends SQLiteOpenHelper {



    private  static final String NAME="MYDATABASE";

    private  static final String TABLE_NAME="taskinfo";

    private static final int VERSION=1;



    private static final String COLUMN_ID = "id";

    private static final String COLUMN_TITLE = "title";

    private static final String COLUMN_DESCRIPTION = "description";

    private static final String COLUMN_DATE = "date";

    private static final String COLUMN_TIME = "time";

    private static final String COLUMN_PRIORITY = "priority";



    public db_class(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);

    }



    @Override

    public void onCreate(SQLiteDatabase db) {



        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +

                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                COLUMN_TITLE + " TEXT, " +

                COLUMN_DESCRIPTION + " TEXT, " +

                COLUMN_DATE + " TEXT, " +

                COLUMN_TIME + " TEXT, " +

                COLUMN_PRIORITY + " INTEGER)";

        db.execSQL(CREATE_TABLE);



        // Insert sample tasks if not already present

        insertSampleTasks(db);


    }

    private void insertSampleTasks(SQLiteDatabase db) {

        // Check if tasks already exist

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();



        // If no tasks exist, insert sample tasks

        if (count == 0) {

            ContentValues values = new ContentValues();



            values.put(COLUMN_TITLE, "Buy Groceries");

            values.put(COLUMN_DESCRIPTION, "Milk, Eggs, Bread");

            values.put(COLUMN_DATE, "2025-04-05");

            values.put(COLUMN_TIME, "8:22 PM");

            values.put(COLUMN_PRIORITY, 1);

            db.insert(TABLE_NAME, null, values);



            values.put(COLUMN_TITLE, "Complete Assignment");

            values.put(COLUMN_DESCRIPTION, "Submit project by Monday");

            values.put(COLUMN_DATE, "2025-04-05");

            values.put(COLUMN_TIME, "8:30 PM");

            values.put(COLUMN_PRIORITY, 2);

            db.insert(TABLE_NAME, null, values);



            values.put(COLUMN_TITLE, "Gym Workout");

            values.put(COLUMN_DESCRIPTION, "Leg day training");

            values.put(COLUMN_DATE, "2025-03-31");

            values.put(COLUMN_TIME, "07:00 AM");

            values.put(COLUMN_PRIORITY, 3);

            db.insert(TABLE_NAME, null, values);



            Log.d("DB_INSERT", "Sample tasks inserted successfully!");

        } else {

            Log.d("DB_INSERT", "Sample tasks already exist. Skipping insertion.");

        }


    }



    private boolean hasExistingTasks(SQLiteDatabase db) {

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        boolean hasTasks = false;

        if (cursor.moveToFirst()) {

            hasTasks = cursor.getInt(0) > 0; // If count > 0, tasks exist

        }

        cursor.close();

        return hasTasks;

    }



    public long addTask(String title, String description, String date, String time, int priority) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, title);

        values.put(COLUMN_DESCRIPTION, description);

        values.put(COLUMN_DATE, date);

        values.put(COLUMN_TIME, time);

        values.put(COLUMN_PRIORITY, priority);



        long id = db.insert(TABLE_NAME, null, values);

        db.close();

        return id;

    }

    public Cursor getAllTasks() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

    }



    public void updateTask(int id, String title, String description, String date, String time, int priority) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, title);

        values.put(COLUMN_DESCRIPTION, description);

        values.put(COLUMN_DATE, date);

        values.put(COLUMN_TIME, time);

        values.put(COLUMN_PRIORITY, priority);



        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});

        db.close();

    }



    public void deleteTask(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});

        db.close();

    }


    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);

    }

}

