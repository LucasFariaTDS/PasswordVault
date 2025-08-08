package com.lucas.passwordvault.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lucas.passwordvault.DB.DBUsername;

public class DBController_Username {
    private SQLiteDatabase db;
    private DBUsername bank;

    public DBController_Username(Context context) {
        bank = new DBUsername(context);
    }

    public String insertData(String username) {
        ContentValues data;
        long result;
        db = bank.getWritableDatabase();
        data = new ContentValues();
        data.put(DBUsername.Username, username);
        try {
            result = db.insert(DBUsername.Table, null, data);
            if (result == -1) {
                return "Error inserting record";
            } else {
                return "Record inserted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        }
    }
}


