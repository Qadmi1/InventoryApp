package com.example.appty.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.appty.inventoryapp.data.Contract.ProductEntry;
/**
 * Created by appty on 10/02/18.
 */

public class ProductCursorAdapter extends CursorAdapter {
    public ProductCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Create references to the Views in the product_item layout
        TextView productName = view.findViewById(R.id.product_item_name);
        TextView productPrice = view.findViewById(R.id.product_item_price);
        TextView productQuantity = view.findViewById(R.id.product_item_quantity);
//        Button saleButton = view.findViewById(R.id.sale_button);

        // Find the columns of product attributes that we're interested in
        int productColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);

        // Read the product attributes from the Cursor for the current product
        String product = cursor.getString(productColumnIndex);
        String priceString = cursor.getString(priceColumnIndex);
        int price = Integer.parseInt(priceString);
        String quantityString = cursor.getString(quantityColumnIndex);
        int quantity = Integer.parseInt(quantityString);

        // Update the TextViews with the attributes for the current product
        productName.setText(product);
        productPrice.setText(String.valueOf(price));
        productQuantity.setText(String.valueOf(quantity));

    }
}