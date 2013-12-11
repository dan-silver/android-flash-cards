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

public class CardsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private CardsSQLiteHelper dbHelper;
    private String[] allColumns = { CardsSQLiteHelper.COLUMN_ID,
            CardsSQLiteHelper.COLUMN_FRONT, CardsSQLiteHelper.COLUMN_BACK };

    public CardsDataSource(Context context) {
        dbHelper = new CardsSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Card createCard(String front, String back) {
        ContentValues values = new ContentValues();
        values.put(CardsSQLiteHelper.COLUMN_FRONT, front);
        values.put(CardsSQLiteHelper.COLUMN_BACK, back);
        long insertId = database.insert(CardsSQLiteHelper.TABLE_CARDS, null,
                values);
        Cursor cursor = database.query(CardsSQLiteHelper.TABLE_CARDS,
                allColumns, CardsSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Card newCard = cursorToCard(cursor);
        cursor.close();
        return newCard;
    }

    public void deleteCard(Card card) {
        long id = card.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(CardsSQLiteHelper.TABLE_CARDS, CardsSQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public void editCard(Card card) {
        long id = card.getId();
        ContentValues args = new ContentValues();
        args.put(CardsSQLiteHelper.COLUMN_FRONT, card.getFront());
        args.put(CardsSQLiteHelper.COLUMN_BACK, card.getBack());
        database.update(CardsSQLiteHelper.TABLE_CARDS, args, CardsSQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Card> getAllComments() {
        List<Card> comments = new ArrayList<Card>();

        Cursor cursor = database.query(CardsSQLiteHelper.TABLE_CARDS,
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