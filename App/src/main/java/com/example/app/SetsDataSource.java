package com.example.app;

/**
 * Created by dan on 12/10/13.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 12/7/13.
 */

public class SetsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SetsSQLiteHelper dbHelper;
    private String[] allColumns = { SetsSQLiteHelper.COLUMN_ID,
            SetsSQLiteHelper.COLUMN_NAME };

    public SetsDataSource(Context context) {
        dbHelper = new SetsSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Set createSet(String name) {
        ContentValues values = new ContentValues();
        values.put(SetsSQLiteHelper.COLUMN_NAME, name);
        long insertId = database.insert(SetsSQLiteHelper.TABLE_SETS, null,
                values);
        Cursor cursor = database.query(SetsSQLiteHelper.TABLE_SETS,
                allColumns, SetsSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Set newSet = cursorToSet(cursor);
        cursor.close();
        return newSet;
    }

    public void deleteSet(Set set) {
        long id = set.getId();
        System.out.println("Set deleted with id: " + id);
        database.delete(SetsSQLiteHelper.TABLE_SETS, SetsSQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public void editSet(Set set) {
        long id = set.getId();
        ContentValues args = new ContentValues();
        args.put(SetsSQLiteHelper.COLUMN_NAME, set.getName());
        database.update(SetsSQLiteHelper.TABLE_SETS, args, SetsSQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Set> getAllSets() {
        List<Set> sets = new ArrayList<Set>();

        Cursor cursor = database.query(SetsSQLiteHelper.TABLE_SETS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Set set = cursorToSet(cursor);
            sets.add(set);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return sets;
    }

    private Set cursorToSet(Cursor cursor) {
        Set set = new Set();
        set.setId((int) cursor.getLong(0));
        set.setName(cursor.getString(1));
        return set;
    }
}