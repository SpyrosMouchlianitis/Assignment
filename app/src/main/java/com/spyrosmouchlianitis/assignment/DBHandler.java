/* Get current location source: https://javapapers.com/android/get-current-location-in-android/
* */

package com.spyrosmouchlianitis.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "android_assignment";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "timestamps";
    private static final String ID_COL = "id";
    private static final String LAT_COL = "latitude";
    private static final String LON_COL = "longitude";
    private static final String ACTION_COL = "action_";
    private static final String TIME_COL = "timestamp";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LAT_COL + " DOUBLE,"
                + LON_COL + " DOUBLE,"
                + ACTION_COL + " TEXT,"
                + TIME_COL + " TEXT)";

        db.execSQL(query);
    }

    public void addNewRecord(double lat, double lon, String action, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(LAT_COL, lat);
        contentValues.put(LON_COL, lon);
        contentValues.put(ACTION_COL, action);
        contentValues.put(TIME_COL, timestamp);

        db.insert(TABLE_NAME, null, contentValues);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
