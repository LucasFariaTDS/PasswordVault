package com.lucas.passwordvault.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBEmail extends SQLiteOpenHelper implements BaseColumns {
    public static final String Email_bank = "email.db";
    public static final String Table = "emailGenerator";
    public static final String ID = "id";
    public static final String Email = "email";
    public static final int Version = 3;

    public DBEmail(Context context) {
        super(context, Email_bank, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String criarDBem = "CREATE TABLE " + DBEmail.Table +
                "( " + DBEmail.ID + " INTEGER PRIMARY KEY,"
                + DBEmail.Email + " text)";
        sqLiteDatabase.execSQL(criarDBem);
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
