package com.example.appty.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appty.inventoryapp.data.Contract.ProductEntry;

/**
 * Created by appty on 25/01/18.
 */

public class ProductDbHelper extends SQLiteOpenHelper {

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "store.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link ProductDbHelper}.
     *
     * @param context of the app
     */
    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the table
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + ProductEntry.TABLE_NAME + " ("
                + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductEntry.COLUMN_PRODUCT_NAME + " TEXT , "
                + ProductEntry.COLUMN_PRODUCT_PRICE + " INTEGER , "
                + ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER , "
                + ProductEntry.COLUMN_PRODUCT_IMAGE + " TEXT , "
                + ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " TEXT , "
                + ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL + " TEXT , "
                + ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER + " INTEGER  );";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
