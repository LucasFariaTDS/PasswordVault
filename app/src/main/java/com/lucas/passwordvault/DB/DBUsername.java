package com.lucas.passwordvault.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBUsername extends SQLiteOpenHelper implements BaseColumns {
    public static final String Username_bank = "username.db";
    public static final String Table = "usernameGenerator";
    public static final String ID = "id";
    public static final String Username = "username";
    public static final int Version = 1;

    public DBUsername(Context context) {
        super(context, Username_bank, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String criarDBun = "CREATE TABLE " + DBUsername.Table +
                "( " + DBUsername.ID + " INTEGER PRIMARY KEY,"
                + DBUsername.Username + " text)";
        sqLiteDatabase.execSQL(criarDBun);
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
