package com.kinire.proyectointegrador.client;

import com.kinire.proyectointegrador.models.Category;
import com.kinire.proyectointegrador.models.Product;

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

        /*Class.forName("org.mariadb.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mariadb://localhost/tpv_test", "admin", "");*/
    }

    public ArrayList<Product> getProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        products.add(
                new Product(
                        1L,
                        "Producto prueba",
                        15.0f,
                        "testimage.png",
                        new Category(
                                1L,
                                "Prueba"
                        )
                )
        );
        return products;
    }

    /*public ArrayList<Product> getProducts() throws SQLException {
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
    }*/
    public InputStream getImage(String path) {
        return null;
    }
    public boolean isAdminPasswordCorrect(String password) {
        return true;
    }
}
