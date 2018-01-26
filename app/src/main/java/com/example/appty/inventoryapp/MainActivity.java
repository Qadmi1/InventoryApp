package com.example.appty.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.appty.inventoryapp.data.Contract.BookEntry;

import com.example.appty.inventoryapp.data.BookDbHelper;

public class MainActivity extends AppCompatActivity {
    // Database helper object that will provide access to the Database
    private BookDbHelper bookDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate the helper object
        bookDbHelper = new BookDbHelper(this);
        // This method insert dummy data to the table
        insertData();
        // This method displays the inserted dummy data in a Log message
        displayDatabaseInfo();
    }

    // This method insert dummy data to the table
    private void insertData() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = bookDbHelper.getWritableDatabase();

        // Create a ContentValues object to insert the data to the table
        ContentValues values = new ContentValues();

        // Insert the data to the columns where the column name is a key
        values.put(BookEntry.COLUMN_BOOK_NAME, "book1");
        values.put(BookEntry.COLUMN_BOOK_PRICE, 7);

        // Insert a new row to the table
        long newDummyRow = db.insert(BookEntry.TABLE_NAME, null, values);
    }

    // This method returns a cursor with the desired columns
    private Cursor queryData(){
        SQLiteDatabase db = bookDbHelper.getWritableDatabase();

        // In this projection we will define the desired columns for the cursor
        String[] projection = {
                BookEntry.COLUMN_BOOK_NAME,
                BookEntry.COLUMN_BOOK_PRICE
        };

        // Query a cursor with the previous projection
        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    // This method displays the inserted dummy data in a Log message
    private void displayDatabaseInfo() {

        // Create a cursor and assign to it the returned cursor from the queryData() method
        Cursor cursor = queryData();

        // Get the index number for the desired columns
        int nameIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
        int priceIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);

        try {

            // Loop through the rows in the cursor and print the desired columns in a Log
            while (cursor.moveToNext())
            {
                String currentName = cursor.getString(nameIndex);
                int currentPrice = cursor.getInt(priceIndex);
                Log.v("MAIN Activity",currentName + " - " + currentPrice );
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();

        }
    }
}
