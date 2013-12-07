package com.example.app;

/**
 * Created by dan on 12/7/13.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CommentsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_FRONT, MySQLiteHelper.COLUMN_BACK };

    public CommentsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Card createCard(String front, String back) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_FRONT, front);
        values.put(MySQLiteHelper.COLUMN_BACK, back);
        long insertId = database.insert(MySQLiteHelper.TABLE_CARDS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CARDS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Card newCard = cursorToCard(cursor);
        cursor.close();
        return newCard;
    }

    public void deleteCard(Card comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_CARDS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Card> getAllComments() {
        List<Card> comments = new ArrayList<Card>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CARDS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Card comment = cursorToCard(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Card cursorToCard(Cursor cursor) {
        Card comment = new Card();
        comment.setId(cursor.getLong(0));
        comment.setFront(cursor.getString(1));
        comment.setBack(cursor.getString(2));
        return comment;
    }
}