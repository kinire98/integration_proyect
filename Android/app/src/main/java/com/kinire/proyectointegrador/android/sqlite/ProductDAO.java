package com.kinire.proyectointegrador.android.sqlite;

import android.content.Context;

import com.kinire.proyectointegrador.components.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private AdminSqlite sqlite;

    public ProductDAO(Context context) {
        this.sqlite = new AdminSqlite(context);
    }

    public void insertProducts(List<Product> products) {

    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        return products;
    }
}
