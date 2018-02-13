package com.example.appty.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.design.widget.FloatingActionButton;

import com.example.appty.inventoryapp.data.Contract.ProductEntry;
import com.example.appty.inventoryapp.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    // Database helper object that will provide access to the Database
    private ProductDbHelper productDbHelper;
    private ProductCursorAdapter adapter;
    private int PRODUCT_LOADER = 1;
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

        // Find the ListView which will be populated with the pet data
        ListView productListView = findViewById(R.id.list);

        // Find and set the empty view
        View emptyView = findViewById(R.id.empty_text);
        productListView.setEmptyView(emptyView);

        productListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProductDetails.class);

                Uri currentProductURI = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);

                intent.setData(currentProductURI);

                startActivity(intent);
            }
        });
        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        adapter = new ProductCursorAdapter(this, null);
        // Attach the adapter to the ListView.
        productListView.setAdapter(adapter);

        // Kick off the loader
        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY
        };

        return new CursorLoader(this,
                ProductEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
