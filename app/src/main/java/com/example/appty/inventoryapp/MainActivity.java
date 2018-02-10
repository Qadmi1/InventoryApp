package com.example.appty.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private Cursor queryData(){
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

        // Get the index number for the desired columns
        int _id = cursor.getColumnIndex(ProductEntry._ID);
        int nameIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int _quantity = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int _sup_name = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
        int _sup_email = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL);
        int _sup_num = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER);


        TextView textViewID = findViewById(R.id._id_);
        TextView textViewName = findViewById(R.id.name);
        TextView textViewPrice = findViewById(R.id.price);
        TextView textViewQuantity = findViewById(R.id.quantity);
        TextView textViewSupName = findViewById(R.id.sup_name);
        TextView textViewSupEmail = findViewById(R.id.sup_email);
        TextView textViewSupNum = findViewById(R.id.sup_number);

        try {

            // Loop through the rows in the cursor and print the desired columns in a Log
            while (cursor.moveToNext())
            {
                int id = cursor.getInt(_id);
                String currentName = cursor.getString(nameIndex);
                int currentPrice = cursor.getInt(priceIndex);
                int quantity = cursor.getInt(_quantity);
                String sup_name = cursor.getString(_sup_name);
                String sup_email = cursor.getString(_sup_email);
                int sup_num = cursor.getInt(_sup_num);

                textViewID.setText(String.valueOf(id));
                textViewName.setText(currentName);
                textViewPrice.setText(String.valueOf(currentPrice));
                textViewQuantity.setText(String.valueOf(quantity));
                textViewSupName.setText(sup_name);
                textViewSupEmail.setText(sup_email);
                textViewSupNum.setText(String.valueOf(sup_num));
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();

        }
    }
}
