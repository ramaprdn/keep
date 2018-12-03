package com.example.ramapradana.keep.data.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.ramapradana.keep.FriendFragment;
import com.example.ramapradana.keep.MainActivity;
import com.example.ramapradana.keep.data.remote.model.User;
import com.example.ramapradana.keep.data.remote.model.UserItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "keep.db";
    private Context context;

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

    //friends table
    private static final String TABLE_FRIEND = "friend";
    private static final String COL_FRIEND_ID = "id";
    private static final String COL_FRIEND_USERNAME  = "username";
    private static final String COL_FRIEND_NAME = "name";
    private static final String COL_FRIEND_EMAIL = "email";
    private static final String COL_FRIEND_CREATED_AT = "created_at";
    private static final String COL_FRIEND_UPDATED_AT = "updated_at";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 5);
        this.context = context;
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

        db.execSQL(
                "CREATE TABLE " + TABLE_FRIEND + "(" +
                        COL_FRIEND_ID + " integer PRIMARY KEY, "+
                        COL_FRIEND_USERNAME + " TEXT,"+
                        COL_FRIEND_NAME + " TEXT, " +
                        COL_FRIEND_EMAIL + " TEXT, "+
                        COL_FRIEND_CREATED_AT + " TEXT, "+
                        COL_FRIEND_UPDATED_AT + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_EVENT);
        db.execSQL("drop table if exists " + TABLE_EVENTFILE);
        db.execSQL("drop table if exists " + TABLE_FRIEND);
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

    public void deleteEvent(String[] id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENT, COL_EVENT_ID + " = ?", id);
        db.delete(TABLE_EVENTFILE, COL_EVENTFILE_EVENTID + " = ?", id);
    }

    public boolean updateNote(String title, String content, String[] id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EVENTFILE_TITLE, title);
        contentValues.put(COL_EVENTFILE_CONTENT, content);
        db.update(TABLE_EVENTFILE, contentValues, "id = ?", id);
        return true;
    }

    public void deleteFileOrNote(String[] fileId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTFILE,  COL_EVENTFILE_ID + " = ?", fileId);
    }

    public void deleteAllFileOfEvent(String[] id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTFILE,  COL_EVENTFILE_EVENTID + " = ?", id);
    }

    public boolean insertFriend(int friendId, String username, String name, String email){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FRIEND_ID, friendId);
        contentValues.put(COL_FRIEND_USERNAME, username);
        contentValues.put(COL_FRIEND_NAME, name);
        contentValues.put(COL_FRIEND_EMAIL, email);

        long result = db.insert(TABLE_FRIEND, null, contentValues);
        if(result == -1){
           return false;
        }else{
            return true;
        }
    }

    public Cursor getAllFriend(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_FRIEND + " order by "+ COL_FRIEND_ID +" desc", null);
        return result;
    }

    public void synchronizeFriendData(List<UserItem> friends){
        List<UserItem> tempFriends = friends;
        Log.d("JUMLAH FRIEND", tempFriends.size() + "");

        SQLiteDatabase db = getWritableDatabase();
        Cursor friendList = this.getAllFriend();

        int start = 0;
        int offlineFriendId = 0;
        int onlineFriendId = 0;

        // data from online and offline is sorted desc by id
        // so it easy to know new record or deleted record
        Log.d("JUMLAH FRIEND OFFLINE", friendList.getCount() + "");
        if(friendList.getCount() == 0){
            Log.d("ACTION", "Karena kosong, insert dari online");
            for(int i = 0; i < tempFriends.size(); i++){
                UserItem userItem = tempFriends.get(i);

                boolean inserted = this.insertFriend(
                        userItem.getUserId(),
                        userItem.getUserUsername(),
                        userItem.getUserName(),
                        userItem.getUserEmail()
                );

                if (!inserted){
                    Toast.makeText(context, "Error inserted", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Log.d("ACTION", "Sync");
            while(friendList.moveToNext()){
                for(int i = start; i < tempFriends.size(); i++){
                    offlineFriendId = friendList.getInt(0);
                    onlineFriendId = tempFriends.get(i).getUserId();

                    //there is no problem. the record is match between online and offline
                    if(offlineFriendId == onlineFriendId) {
                        tempFriends.remove(i);
                        break;
                    }

                    //this mean the local record is not exist in online.
                    //the local record must be delete
                    else if(offlineFriendId > onlineFriendId){
                        db.delete(TABLE_FRIEND, COL_FRIEND_ID + " = ?", new String[] {String.valueOf(offlineFriendId)});
                        break;
                    }

                    //this is new record in online and have to insert in sqlite
                    else if(offlineFriendId < onlineFriendId){
                        this.insertFriend(
                                tempFriends.get(i).getUserId(),
                                tempFriends.get(i).getUserUsername(),
                                tempFriends.get(i).getUserName(),
                                tempFriends.get(i).getUserEmail()
                        );

                        tempFriends.remove(i);
                        continue;
                    }
                }
            }
        }

    }
}
