package com.lucas.passwordvault.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lucas.passwordvault.DB.DB;
import com.lucas.passwordvault.DB.DBEmail;
import com.lucas.passwordvault.DB.DBPassword;

public class DBController_Email {
    private SQLiteDatabase db;
    private DBEmail bank;

    public DBController_Email(Context context) {
        bank = new DBEmail(context);
    }

    public String insertData(String email) {
        ContentValues data;
        long result;
        db = bank.getWritableDatabase();
        data = new ContentValues();
        data.put(DBEmail.Email, email);
        try {
            result = db.insert(DBEmail.Table, null, data);
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

