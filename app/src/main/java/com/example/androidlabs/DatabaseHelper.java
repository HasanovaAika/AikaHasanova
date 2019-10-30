package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MessagesDatabase";
    public static final String TABLE_NAME = "Messages";
    public static final int VERSION_NUMBER = 2;

    public static final String COL_MESSAGE = "Message";
    public static final String COL_ISSENT = "isSent";
    public static final String COL_MESSAGEID = "MessageId";


    public DatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUMBER);
    }


    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( " +
                COL_MESSAGEID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_MESSAGE  + " TEXT, " + COL_ISSENT + " BOOLEAN) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        Log.i("Database upgrade", "Old Version:" + oldVer + " New Version: " + newVer);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer){
        Log.i("Database downgrade", "Old Version: " + oldVer + " New Version: " + newVer);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
