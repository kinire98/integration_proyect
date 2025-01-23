package com.kinire.proyectointegrador.android.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kinire.proyectointegrador.components.User;

public class UserDAO {

    private AdminSqlite sqlite;

    public UserDAO(Context context) {
        this.sqlite = new AdminSqlite(context);
    }

    public void setUser(User user) {
        SQLiteDatabase db = sqlite.getWritableDatabase();
        String queryDeleteUser = "DELETE FROM " + AdminSqlite.USER_TABLE_NAME;
        db.execSQL(queryDeleteUser);
        ContentValues values = new ContentValues();
        values.put(AdminSqlite.USERNAME_COLUMN_NAME, user.getUser());
        try {
            values.put(AdminSqlite.PASSWORD_COLUMN_NAME, EncriptionKeyStore.encrypt(user.getPassword()));
        } catch (Exception e) {}
        db.insert(AdminSqlite.USER_TABLE_NAME, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public User getUser() {
        User user = new User();
        SQLiteDatabase db = sqlite.getReadableDatabase();
        final String query = "SELECT * FROM " + AdminSqlite.USER_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            user.setUser(cursor.getString(cursor.getColumnIndex(AdminSqlite.USERNAME_COLUMN_NAME)));
            try {
                user.setPassword(
                        EncriptionKeyStore.decrypt(
                                cursor.getString(cursor.getColumnIndex(AdminSqlite.PASSWORD_COLUMN_NAME))
                        )
                );
            } catch (Exception e) {}
        }
        cursor.close();
        db.close();
        return user;
    }
}