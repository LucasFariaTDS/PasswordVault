package com.lucas.passwordvault.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.lucas.passwordvault.DB.DBPassword;

public class DBController_Password {
    private SQLiteDatabase db;
    private DBPassword bank;

    public DBController_Password(Context context) {
        bank = new DBPassword(context);
    }

    public String insertData(String password) {
        ContentValues data;
        long result;
        db = bank.getWritableDatabase();
        data = new ContentValues();
        data.put(DBPassword.Password, password);
        try {
            result = db.insert(DBPassword.Table, null, data);
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
