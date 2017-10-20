package com.example.x550vx_dm066t.myapplication.controller.DB_controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by x550vx-dm066t on 15/10/2017.
 */
public class DatabaseController extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "HackerNewsDB";

    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NEWS_TABLE = "CREATE TABLE NEWS_LIST( NEWS_ID VARCHAR PRIMARY KEY, TITLE VARCHAR, BY VARCHAR, TYPE VARCHAR, KIDS VARCHAR, SCORE VARCHAR)";
        db.execSQL(CREATE_NEWS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        onCreate(db);
    }
    }
