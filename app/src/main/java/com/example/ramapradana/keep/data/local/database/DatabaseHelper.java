package com.example.ramapradana.keep.data.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "keep.db";
    private static final String TABLE_NAME_1 = "event";
    private static final String TABLE_1_COL = "id";
    private static final String TABLE_2_COL = "event_name";
    private static final String TABLE_3_COL = "created_at";
    private static final String TABLE_4_COL = "file_count";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table event(id integer primary key, event_name text, created_at text, file_count integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists event");
        this.onCreate(db);
    }

    public boolean insertEvent(int eventId, String eventName, String createdAt, int fileCount){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_1_COL, eventId);
        contentValues.put(TABLE_2_COL, eventName);
        contentValues.put(TABLE_3_COL, createdAt);
        contentValues.put(TABLE_4_COL, fileCount);
        long result = db.insert(TABLE_NAME_1, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllEvent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME_1 + " order by id desc", null);
        return result;
    }

    public void deleteAllEventAndDetail(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
    }
}
