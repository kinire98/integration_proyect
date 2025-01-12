package com.kinire.client;

import com.kinire.models.Category;
import com.kinire.models.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public class Connection {

    private java.sql.Connection connection;

    private static Connection self;

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
    Connection() throws ClassNotFoundException, SQLException {

        Class.forName("org.mariadb.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mariadb://localhost/tpv_test", "admin", "");
    }

    public ArrayList<Product> getProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM products p INNER JOIN tpv_test.categories c on p.category_id = c.id;";
        ResultSet set = statement.executeQuery(query);
        while(set.next()) {
            products.add(
                    new Product(
                            set.getLong("p.id"),
                            set.getString("p.name"),
                            set.getFloat("price"),
                            set.getString("image_path"),
                            new Category(
                                    set.getLong("c.id"),
                                    set.getString("c.name")
                            )
                    )
            );
        }
        return products;
    }
    public InputStream getImage(String path) {
        return null;
    }
}
