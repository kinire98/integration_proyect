package com.kinire.proyectointegrador.android.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Clase encargada de ser el Ayudador para SQL
 * Contiene todos los nombres de columnas y tablas para poder acceder a ellos desde los DAOs correspondientes
 */
class AdminSqlite extends SQLiteOpenHelper {

    private static final int VERSION = 4;

    static final String DATABASE_NAME = "tpv_products";
    static final String USER_TABLE_NAME = "user";
    static final String PRODUCTS_TABLE_NAME = "products";
    static final String CATEGORIES_TABLE_NAME = "categories";

    static final String USERNAME_COLUMN_NAME = "username";
    static final String PASSWORD_COLUMN_NAME = "password";

    static final String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME + " (" +
            USERNAME_COLUMN_NAME + " TEXT PRIMARY KEY," +
            PASSWORD_COLUMN_NAME + " TEXT);";

    static final String ID_COLUMN_NAME = "id";
    static final String NAME_COLUMN_NAME = "name";


    static final String PRICE_COLUMN_NAME = "price";
    static final String DATE_COLUMN_NAME = "date";
    static final String CATEGORY_ID_COLUMN_NAME = "category_id";
    static final String CATEGORY_NAME_COLUMN_NAME = "category_name";
    static final String IMAGE_PATH_COLUMN_NAME = "image_path";
    static final String IMAGE_COLUMN_NAME = "image";

    static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + PRODUCTS_TABLE_NAME + " (" +
            ID_COLUMN_NAME + " INTEGER PRIMARY KEY, " +
            NAME_COLUMN_NAME + " TEXT, " +
            PRICE_COLUMN_NAME + " REAL, " +
            DATE_COLUMN_NAME + " TEXT, " +
            CATEGORY_ID_COLUMN_NAME + " NUMBER, " +
            CATEGORY_NAME_COLUMN_NAME + " TEXT, " +
            IMAGE_PATH_COLUMN_NAME + " TEXT, " +
            IMAGE_COLUMN_NAME + " BLOB);";



    public AdminSqlite(@Nullable Context context) {
            super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
    }
}
