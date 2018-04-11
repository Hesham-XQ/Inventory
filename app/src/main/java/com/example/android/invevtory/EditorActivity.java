/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.invevtory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.invevtory.data.InventoryContract.InventoryEntry;
import com.example.android.invevtory.data.InventoryDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText productName;
    private EditText price;
    private EditText quantity;
    private EditText supplierName;
    private EditText supplierNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        productName = (EditText) findViewById(R.id.product_name);
        price = (EditText) findViewById(R.id.price);
        quantity = (EditText) findViewById(R.id.quantity);
        supplierName = (EditText) findViewById(R.id.supplier_name);
        supplierNumber = (EditText) findViewById(R.id.supplier_number);
    }

    private void insertProduct() {
        String nameString = productName.getText().toString().trim();
        String priceString = price.getText().toString().trim();
        String quantityString = quantity.getText().toString().trim();
        String supplierNameString = supplierName.getText().toString().trim();
        String supplierNumberString = supplierNumber.getText().toString().trim();

        // Create database helper
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(InventoryEntry.COLUMN_PRODUCT_PRICE, priceString);
        values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, quantityString);
        values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierNameString);
        values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER, supplierNumberString);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);
        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Pet saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertProduct();
                finish();
                return true;

            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}