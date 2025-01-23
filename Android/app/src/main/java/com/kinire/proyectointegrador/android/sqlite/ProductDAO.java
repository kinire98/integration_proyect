package com.kinire.proyectointegrador.android.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kinire.proyectointegrador.components.Category;
import com.kinire.proyectointegrador.components.Product;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private final AdminSqlite sqlite;

    private final DateTimeFormatter formatter;

    public ProductDAO(Context context) {
        this.sqlite = new AdminSqlite(context);
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public void insertProduct(Product product) {
        SQLiteDatabase db = sqlite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AdminSqlite.ID_COLUMN_NAME, product.getId());
        values.put(AdminSqlite.NAME_COLUMN_NAME, product.getName());
        values.put(AdminSqlite.PRICE_COLUMN_NAME, product.getPrice());
        values.put(AdminSqlite.DATE_COLUMN_NAME, product.getLastModified().format(formatter));
        values.put(AdminSqlite.CATEGORY_ID_COLUMN_NAME, product.getCategory().getId());
        values.put(AdminSqlite.CATEGORY_NAME_COLUMN_NAME, product.getCategory().getName());
        values.put(AdminSqlite.IMAGE_PATH_COLUMN_NAME, product.getImagePath());
        values.put(AdminSqlite.IMAGE_COLUMN_NAME, product.getImage());
        db.insert(AdminSqlite.PRODUCTS_TABLE_NAME, null,  values);
        db.close();
    }

    @SuppressLint("Range")
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM " + AdminSqlite.PRODUCTS_TABLE_NAME;
        SQLiteDatabase db = sqlite.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            products.add(new Product(
                    cursor.getLong(cursor.getColumnIndex(AdminSqlite.ID_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(AdminSqlite.NAME_COLUMN_NAME)),
                    cursor.getFloat(cursor.getColumnIndex(AdminSqlite.PRICE_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(AdminSqlite.IMAGE_PATH_COLUMN_NAME)),
                    LocalDate.parse(cursor.getString(cursor.getColumnIndex(AdminSqlite.DATE_COLUMN_NAME)), formatter),
                    new Category(
                            cursor.getLong(cursor.getColumnIndex(AdminSqlite.CATEGORY_ID_COLUMN_NAME)),
                            cursor.getString(cursor.getColumnIndex(AdminSqlite.CATEGORY_NAME_COLUMN_NAME))
                    ),
                    cursor.getBlob(cursor.getColumnIndex(AdminSqlite.IMAGE_COLUMN_NAME))
                    ));
        }
        cursor.close();
        db.close();
        return products;
    }
    public boolean areThereProducts() {
        boolean thereAreProducts;
        String query = "SELECT * FROM " + AdminSqlite.PRODUCTS_TABLE_NAME;
        SQLiteDatabase db = sqlite.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        thereAreProducts = cursor.moveToNext();
        cursor.close();
        db.close();
        return thereAreProducts;
    }
}
