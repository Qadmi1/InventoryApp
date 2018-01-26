package com.example.appty.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appty.inventoryapp.data.Contract.BookEntry;

/**
 * Created by appty on 25/01/18.
 */

public class BookDbHelper extends SQLiteOpenHelper {

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "store.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link BookDbHelper}.
     *
     * @param context of the app
     */
    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the table
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME + " ("
                + BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookEntry.COLUMN_BOOK_NAME + " TEXT , "
                + BookEntry.COLUMN_BOOK_PRICE + " INTEGER , "
                + BookEntry.COLUMN_BOOK_QUANTITY + " INTEGER , "
                + BookEntry.COLUMN_BOOK_IMAGE + " TEXT , "
                + BookEntry.COLUMN_BOOK_SUPPLIER_NAME + " TEXT , "
                + BookEntry.COLUMN_BOOK_SUPPLIER_EMAIL + " TEXT , "
                + BookEntry.COLUMN_BOOK_SUPPLIER_NUMBER + " INTEGER  );";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
