package com.example.appty.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.appty.inventoryapp.data.Contract.ProductEntry;

import com.example.appty.inventoryapp.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity {
    // Database helper object that will provide access to the Database
    private ProductDbHelper productDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate the helper object
        productDbHelper = new ProductDbHelper(this);
        // This method insert dummy data to the table
        insertData();
        // This method displays the inserted dummy data in a Log message
        displayDatabaseInfo();
    }

    // This method insert dummy data to the table
    private void insertData() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = productDbHelper.getWritableDatabase();

        // Create a ContentValues object to insert the data to the table
        ContentValues values = new ContentValues();

        // Insert the data to the columns where the column name is a key
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, "book1");
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, 7);

        // Insert a new row to the table
        long newDummyRow = db.insert(ProductEntry.TABLE_NAME, null, values);
    }

    // This method returns a cursor with the desired columns
    private Cursor queryData(){
        SQLiteDatabase db = productDbHelper.getWritableDatabase();

        // In this projection we will define the desired columns for the cursor
        String[] projection = {
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE
        };

        // Query a cursor with the previous projection
        Cursor cursor = db.query(
                ProductEntry.TABLE_NAME,
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
        int nameIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);

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
