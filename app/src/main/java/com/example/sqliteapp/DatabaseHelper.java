package com.example.sqliteapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.material.tabs.TabLayout;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;

/**
     * Created by ProgrammingKnowledge on 4/3/2015.
     */

/**
 * Nothing to say
 */
    public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Hotel.db";
    public static final String TABLE_NAME = "hotel_table";
    public static final String KEY_ID = "ID";
    public static final String KEY_ROOM = "ROOM";
    public static final String KEY_STATUS = "STATUS";
    public static final String KEY_DATE = "DATE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ROOM TEXT,STATUS TEXT,DATE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String room, String status, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_ROOM, room);
        contentValues.put(KEY_STATUS, status);
        contentValues.put(KEY_DATE, date);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String id, String room, String status, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, id);
        contentValues.put(KEY_ROOM,room);
        contentValues.put(KEY_STATUS, status);
        contentValues.put(KEY_DATE, date);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userlist = new ArrayList<>();
        String query = "SELECT name,status,date FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("room", cursor.getString(cursor.getColumnIndex(KEY_ROOM)));
            user.put("date", cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("status", cursor.getString(cursor.getColumnIndex(KEY_STATUS)));
            userlist.add(user);
        }
        return userlist;
    }
}

