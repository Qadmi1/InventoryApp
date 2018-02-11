package com.example.appty.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
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


        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, 8);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, "ahmad");
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL, "email");
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER, 111111);

        // Insert a new row to the table
        Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);
    }

    // This method returns a cursor with the desired columns
    private Cursor queryData() {
        // In this projection we will define the desired columns for the cursor
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER
        };


        Cursor cursor = getContentResolver().query(ProductEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
        return cursor;
    }

    // This method displays the inserted dummy data in a Log message
    private void displayDatabaseInfo() {

        // Create a cursor and assign to it the returned cursor from the queryData() method
        Cursor cursor = queryData();

        // Find the ListView which will be populated with the pet data
        ListView petListView = (ListView) findViewById(R.id.list);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        ProductCursorAdapter adapter = new ProductCursorAdapter(this, cursor);

        // Attach the adapter to the ListView.
        petListView.setAdapter(adapter);

    }
}
