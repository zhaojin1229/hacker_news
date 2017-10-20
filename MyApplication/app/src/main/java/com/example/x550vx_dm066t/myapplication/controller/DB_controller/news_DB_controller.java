package com.example.x550vx_dm066t.myapplication.controller.DB_controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.x550vx_dm066t.myapplication.property.story;

/**
 * Created by x550vx-dm066t on 15/10/2017.
 */

public class news_DB_controller {

    private String newsTable;
   private Context context;
    private DatabaseController mDbHelper;

    public news_DB_controller(Context ctx, String s){
        context = ctx;
        newsTable =s;
        mDbHelper = new DatabaseController(context);
    }

    public void addStory(story news) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NEWS_ID", news.getID());
        values.put("TITLE",  news.getTitle());
        values.put("BY",  news.getBy());
        values.put("TYPE",  news.getType());
        values.put("KIDS",  news.getKids());
        values.put("SCORE",  news.get_score());
        // Inserting Row
        long s = db.insert("NEWS_LIST", null, values);
        db.close(); // Closing database connection
    }

    public void deleteStory() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete("NEWS_LIST",null,null);
        db.close(); // Closing database connection
    }

    public Cursor getStory() {
        String countQuery = "SELECT NEWS_ID as _id, * FROM NEWS_LIST";
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor!=null){
            if(cursor.getCount()>0){
                cursor.moveToFirst();
            }
        }
//        cursor.close();
        return cursor;
    }
}
