package com.example.domofonmasterapp.Service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "favouritesKeyDB";
    public static final String TABLE_KEYS_FAV = "fav_key";

    public static final String KEY_ID = "_id";
    public static final String KEY_CODE = "code";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DATA_START = "data_start";
    public static final String KEY_DATA_END = "data_end";
    public static final String KEY_PORCH = "porch";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_KEYS_FAV + "(" + KEY_ID
                + " integer primary key," + KEY_CODE + " integer," + KEY_TYPE + " integer," + KEY_DATA_START + " text,"
                + KEY_DATA_END + " text," + KEY_PORCH + " integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + TABLE_KEYS_FAV);

        onCreate(db);
    }
}
