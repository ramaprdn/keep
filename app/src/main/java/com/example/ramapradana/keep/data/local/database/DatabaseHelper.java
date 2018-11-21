package com.example.ramapradana.keep.data.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "keep.db";

    // event table
    private static final String TABLE_EVENT = "event";
    private static final String COL_EVENT_ID = "id";
    private static final String COL_EVENT_NAME = "event_name";
    private static final String COL_EVENT_CREATEAT = "created_at";
    private static final String COL_EVENT_FILECOUNT = "file_count";

    // event file table
    private static final String TABLE_EVENTFILE = "eventfile";
    private static final String COL_EVENTFILE_ID = "id";
    private static final String COL_EVENTFILE_EVENTID = "event_id";
    private static final String COL_EVENTFILE_TITLE = "title";
    private static final String COL_EVENTFILE_CONTENT = "content";
    private static final String COL_EVENTFILE_FORMAT = "format";
    private static final String COL_EVENTFILE_UPLOADBY = "upload_by";
    private static final String COL_EVENTFILE_CREATEAT = "created_at";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table event(id integer primary key, event_name text, created_at text, file_count integer)");
        db.execSQL(
                "CREATE TABLE " + TABLE_EVENTFILE + "("+
                        COL_EVENTFILE_ID + " integer PRIMARY KEY, "+
                        COL_EVENTFILE_EVENTID + " integer,"+
                        COL_EVENTFILE_TITLE + " TEXT,"+
                        COL_EVENTFILE_CONTENT + " TEXT, "+
                        COL_EVENTFILE_FORMAT + " TEXT, "+
                        COL_EVENTFILE_UPLOADBY +" INTEGER, "+
                        COL_EVENTFILE_CREATEAT +" TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_EVENT);
        db.execSQL("drop table if exists " + TABLE_EVENTFILE);
        this.onCreate(db);
    }

    public boolean insertEvent(int eventId, String eventName, String createdAt, int fileCount){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EVENT_ID, eventId);
        contentValues.put(COL_EVENT_NAME, eventName);
        contentValues.put(COL_EVENT_CREATEAT, createdAt);
        contentValues.put(COL_EVENT_FILECOUNT, fileCount);
        long result = db.insert(TABLE_EVENT, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //insert to event file table. return false is error, true is success
    public boolean insertEventFile(int id, int eventId, String title, String content, String format, int uploadBy, String createdAt){
        SQLiteDatabase db =  getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EVENTFILE_ID, id);
        contentValues.put(COL_EVENTFILE_EVENTID, eventId);
        contentValues.put(COL_EVENTFILE_TITLE, title);
        contentValues.put(COL_EVENTFILE_CONTENT, content);
        contentValues.put(COL_EVENTFILE_FORMAT, format);
        contentValues.put(COL_EVENTFILE_UPLOADBY, uploadBy);
        contentValues.put(COL_EVENTFILE_CREATEAT, createdAt);
        long result = db.insert(TABLE_EVENTFILE, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllEvent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_EVENT + " order by id desc", null);
        return result;
    }

    public Cursor getEventFile(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_EVENTFILE + " where event_id = " + id + " order by id desc", null);
        return result;
    }

    public void deleteAllEventAndDetail(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
    }

    public void deleteEventFile(String[] id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTFILE, "event_id = ?", id);
    }
}
