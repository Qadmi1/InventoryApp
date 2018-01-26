package com.example.appty.inventoryapp.data;

import android.provider.BaseColumns;

/**
 * Created by appty on 25/01/18.
 */

public class Contract {

    public class BookEntry implements BaseColumns {
        /**
         * Name of the table
         */
        public static final String TABLE_NAME = "books";
        /**
         * Name of the columns
         */
        public final static String _ID = BaseColumns._ID;
        public static final String COLUMN_BOOK_NAME = "name";
        public static final String COLUMN_BOOK_PRICE = "price";
        public static final String COLUMN_BOOK_QUANTITY = "quantity";
        public static final String COLUMN_BOOK_IMAGE = "image";
        public static final String COLUMN_BOOK_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_BOOK_SUPPLIER_EMAIL = "supplier_email";
        public static final String COLUMN_BOOK_SUPPLIER_NUMBER = "supplier_phone_number";
    }
}
