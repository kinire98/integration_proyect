package com.kinire.proyectointegrador.client;

import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Category;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.User;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Connection {

    private static Connection self;

    private User user;


    public static Connection getInstance() {
        if(self == null) {
            try {
                self = new Connection();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return self;
    }
    Connection() {}

    public ArrayList<Product> getProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            products.add(
                    new Product(
                            1L,
                            "Producto prueba",
                            15.0f,
                            "testimage.png",
                            LocalDate.now(),
                            new Category(
                                    1L,
                                    "Prueba"
                            )
                    )
            );
        }
        return products;
    }
    public InputStream getImage(String path) {
        return null;
    }
    public boolean isAdminPasswordCorrect(String password) {
        return true;
    }
    public void uploadPurchase(Purchase purchase) {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
