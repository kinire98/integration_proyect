package com.kinire.proyectointegrador.db.db_access.impls;

import com.kinire.proyectointegrador.components.Category;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.User;
import com.kinire.proyectointegrador.db.db_access.DAOs.CategoryDAO;
import com.kinire.proyectointegrador.db.db_access.DAOs.ProductDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductImpl implements ProductDAO {

    private final Connection connection;

    private final Logger logger;

    private final CategoryDAO categoryDAO;

    public ProductImpl() throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mariadb://localhost/tpv_test", "admin", "");
        this.logger = Logger.getLogger(CategoryImpl.class.getName());
        this.categoryDAO = new CategoryImpl();
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    @Override
    public boolean insertProduct(Product product) {
        boolean success = false;
        String query = "INSERT INTO products VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, product.getId());
            statement.setString(2, product.getName());
            statement.setFloat(3, product.getPrice());
            statement.setString(4, product.getImagePath());
            statement.setLong(5, product.getCategory().getId());
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

    @Override
    public Product selectProductById(long id) {
        Product product = null;
        String query = "SELECT * FROM products p INNER JOIN tpv_test.categories c on p.category_id = c.id WHERE p.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            set.next();
            product = new Product(
                    set.getLong("p.id"),
                    set.getString("p.name"),
                    set.getFloat("price"),
                    set.getString("image_path"),
                    new Category(
                            set.getLong("c.id"),
                            set.getString("c.name")
                    )
            );
            set.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return product;
    }

    @Override
    public List<Product> selectProductByCategory(long categoryId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products p INNER JOIN tpv_test.categories c on p.category_id = c.id WHERE c.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, categoryId);
            getProductsWithStatement(products, statement);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return products;
    }

    @Override
    public List<Product> selectProductsByIds(long[] ids) {
        if(ids.length < 2)
            throw new IllegalArgumentException("If you are going to use just one id use the getOne method");
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products p INNER JOIN tpv_test.categories c on p.category_id = c.id WHERE p.id IN (?" + ", ?".repeat(ids.length - 1) +
                ")";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < ids.length; i++) {
                statement.setLong(i + 1, ids[i]);
            }
            getProductsWithStatement(products, statement);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return products;
    }


    @Override
    public List<Product> selectAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products p INNER JOIN tpv_test.categories c on p.category_id = c.id";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            getProductsWithStatement(products, statement);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return products;
    }

    @Override
    public boolean updateProduct(long id, Product product) {
        if(categoryDAO.selectCategory(product.getCategory().getId()) == null)
            throw new IllegalArgumentException("The category must exist in order to be able to update it");
        boolean success = false;
        String query = "UPDATE products SET name = ?, price = ?, image_path = ?, category_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setFloat(2, product.getPrice());
            statement.setString(3, product.getImagePath());
            statement.setLong(4, product.getCategory().getId());
            statement.setLong(5, id);
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

    @Override
    public boolean deleteProduct(long id) {
        boolean success = false;
        String query = "DELETE FROM products WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

    private void getProductsWithStatement(List<Product> products, PreparedStatement statement) throws SQLException {
        ResultSet set = statement.executeQuery();
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
        set.close();
    }
}
