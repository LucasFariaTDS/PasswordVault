package com.lucas.passwordvault.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBPassword extends SQLiteOpenHelper implements BaseColumns {
    public static final String Password_Bank = "password.db";
    public static final String Table = "passwordGenerate";
    public static final String ID = "id";
    public static final String Password = "password";
    public static final int Version = 3;

    public DBPassword(Context context) {
        super(context, Password_Bank, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String criarDBpw = "CREATE TABLE " + DBPassword.Table +
                "( " + DBPassword.ID + " INTEGER PRIMARY KEY,"
                + DBPassword.Password + " text)";
        sqLiteDatabase.execSQL(criarDBpw);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
