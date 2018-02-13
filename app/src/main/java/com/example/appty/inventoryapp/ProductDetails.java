package com.example.appty.inventoryapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.appty.inventoryapp.data.Contract.ProductEntry;


public class ProductDetails extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Content URI for the existing product
     */
    private Uri currentProductUri;

    /**
     * TextView field to show the product's name
     */
    private TextView nameTextView;

    /**
     * TextView field to show the product's price
     */
    private TextView priceTextView;

    /**
     * TextView field to show the product's quantity
     */
    private TextView quantityTextView;

    /**
     * TextView field to show the product's supplier name
     */
    private TextView supplierNameTextView;

    /**
     * TextView field to show the product's supplier email
     */
    private TextView supplierEmailTextView;

    /**
     * TextView field to show the product's supplier number
     */
    private TextView supplierNumberTextView;

    private int PRODUCT_LOADER_DETAILS = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.details_product);
        super.onCreate(savedInstanceState);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new pet or editing an existing one.
        Intent intent = getIntent();
        currentProductUri = intent.getData();

        nameTextView = findViewById(R.id.text_name);
        priceTextView = findViewById(R.id.text_price);
        quantityTextView = findViewById(R.id.text_quantity);
        supplierNameTextView = findViewById(R.id.text_supplier_name);
        supplierEmailTextView = findViewById(R.id.text_supplier_email);
        supplierNumberTextView = findViewById(R.id.text_supplier_number);

        getLoaderManager().initLoader(PRODUCT_LOADER_DETAILS, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the details shows all product attributes, define a projection that contains
        // all columns from the product table
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER
        };

        return new CursorLoader(
                this,
                currentProductUri,
                projection,
                null,
                null,
                null
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int supplierEmailColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL);
            int supplierNumberColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String priceString = cursor.getString(priceColumnIndex);
            int price = 0;
            if (!TextUtils.isEmpty(priceString)) {
                price = Integer.parseInt(priceString);
            }
            String quantityString = cursor.getString(quantityColumnIndex);
            int quantity = 0;
            if (!TextUtils.isEmpty(quantityString)) {
                quantity = Integer.parseInt(quantityString);
            }
            String supplierName = cursor.getString(supplierNameColumnIndex);
            String supplierEmail = cursor.getString(supplierEmailColumnIndex);
            String supplierNumberString = cursor.getString(supplierNumberColumnIndex);
            int supplierNumber = 0;
            if (!TextUtils.isEmpty(supplierNumberString)) {
                supplierNumber = Integer.parseInt(supplierNumberString);
            }

            // Update the views on the screen with the values from the database
            nameTextView.setText(name);
            priceTextView.setText(String.valueOf(price));
            quantityTextView.setText(String.valueOf(quantity));
            supplierNameTextView.setText(supplierName);
            supplierEmailTextView.setText(supplierEmail);
            supplierNumberTextView.setText(String.valueOf(supplierNumber));


        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        nameTextView.setText("");
        priceTextView.setText("");
        quantityTextView.setText("");
        supplierNameTextView.setText("");
        supplierEmailTextView.setText("");
        supplierNumberTextView.setText("");
    }
}
