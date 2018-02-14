package com.example.appty.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appty.inventoryapp.data.Contract.ProductEntry;


public class ProductDetails extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, Button.OnClickListener {

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

    private int quantity = 0;


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


        Button addButton = findViewById(R.id.add_quantity_button_detail);
        addButton.setOnClickListener(this);

        Button reduceButton = findViewById(R.id.reduce_quantity_button_detail);
        reduceButton.setOnClickListener(this);

        getLoaderManager().initLoader(PRODUCT_LOADER_DETAILS, null, this);

    }

    private void editProduct() {
        Intent intent = new Intent(ProductDetails.this, EditorActivity.class);
        Uri currentProductURIEdit = currentProductUri;

        intent.setData(currentProductURIEdit);

        startActivity(intent);
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the product in the database.
     */
    private void deleteProduct() {
        // Only perform the delete if this is an existing pet.
        if (currentProductUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(currentProductUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.delete_error),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.delete_success),
                        Toast.LENGTH_SHORT).show();
            }
        }
        // Close the activity
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_item:
                editProduct();
                break;
            case R.id.action_delete_item:
                showDeleteConfirmationDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onClick(View view) {
        ContentValues values = new ContentValues();
        switch (view.getId()) {
            case R.id.add_quantity_button_detail:
                quantity++;
                values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
                getContentResolver().update(currentProductUri, values, null, null);
                break;

            case R.id.reduce_quantity_button_detail:
                if (quantity > 0) {
                    quantity--;
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
                    getContentResolver().update(currentProductUri, values, null, null);
                }
                else {
                    Toast.makeText(this, getString(R.string.sold_out),
                            Toast.LENGTH_SHORT).show();
                }
                    break;
        }


    }
}
