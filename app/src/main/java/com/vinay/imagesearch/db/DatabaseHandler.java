package com.vinay.imagesearch.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * DatabaseHandler.
 *
 * Sqlite database handler helper
 *
 * @author vinay
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, "CommentsDet.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tb_comment(id INTEGER PRIMARY KEY AUTOINCREMENT, image_id TEXT, comment_text TEXT, time_stamp INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_comment");
        onCreate(db);
    }

    public long addComment(String img_id, String comment_text) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("image_id", img_id);
        values.put("comment_text", comment_text);
        values.put("time_stamp", System.currentTimeMillis());

        // Inserting Row
        long a = db.insert("tb_comment", null, values);
        db.close(); // Closing database connection
        return a;
    }

    public ArrayList<HashMap<String, String>> getComments(String img_id) {

        ArrayList<HashMap<String, String>> alCommentList = new ArrayList<HashMap<String, String>>();
        // Select Query filterinf g based on image id and soting in desc order on time added
        String selectQuery = "SELECT  * FROM tb_comment WHERE image_id='" + img_id + "' ORDER BY time_stamp DESC;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        HashMap<String, String> hm;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                hm = new HashMap<String, String>();
                hm.put("id", "" + cursor.getInt(0));
                hm.put("image_id", "" + cursor.getString(1));
                hm.put("comment_text", "" + cursor.getString(2));
                hm.put("time_stamp", "" + cursor.getLong(3));
                // Adding to list
                alCommentList.add(hm);
            } while (cursor.moveToNext());
        }

        // return comment list
        return alCommentList;
    }
}
