package com.kinire.proyectointegrador.db.db_access.impls;

import com.kinire.proyectointegrador.components.Category;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.db.db_access.DAOs.CategoryDAO;
import com.kinire.proyectointegrador.db.db_access.DAOs.ProductDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductImpl implements ProductDAO {

    private final Logger logger;

    private final CategoryDAO categoryDAO;

    public ProductImpl() {
        this.logger = Logger.getLogger(CategoryImpl.class.getName());
        this.categoryDAO = new CategoryImpl();
    }


    @Override
    public boolean insertProduct(Product product) {
        boolean success = false;
        String query = "INSERT INTO products (name, price, image_path, category_id, last_modification) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setFloat(2, product.getPrice());
            statement.setString(3, product.getImagePath());
            statement.setLong(4, product.getCategory().getId());
            statement.setDate(5, Date.valueOf(product.getLastModified() == null ? LocalDate.now() : product.getLastModified()));
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
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            set.next();
            product = new Product(
                    set.getLong("p.id"),
                    set.getString("p.name"),
                    set.getFloat("price"),
                    set.getString("image_path"),
                    set.getDate("last_modification").toLocalDate(),
                    new Category(
                            set.getLong("c.id"),
                            set.getString("c.name")
                    ),
                    null
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
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
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
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
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
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            getProductsWithStatement(products, statement);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return products;
    }

    @Override
    public List<Product> selectMissingProducts(List<Product> productsAlreadyFetched) {
        if(productsAlreadyFetched.isEmpty())
            throw new IllegalArgumentException("Product list must have some product");
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products INNER JOIN tpv_test.categories c on products.category_id = c.id" +
                " WHERE products.id NOT IN (?" + ", ?".repeat(productsAlreadyFetched.size() - 1) + ")";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < productsAlreadyFetched.size(); i++) {
                statement.setLong(i + 1, productsAlreadyFetched.get(i).getId());
            }
            getProductsWithStatement(products, statement);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return products;
    }

    @Override
    public List<Product> selectUpdatedProducts(List<Product> productsToCheck) {
        if(productsToCheck.isEmpty())
            throw new IllegalArgumentException("Product list must have some product");
        List<Product> products = new ArrayList<>();
        String query = "SELECT id, last_modification FROM products";
        final HashMap<Long, LocalDate> lastUpdatedById = new HashMap<>();
        try (Connection connection = DataSource.getConnection();
               PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet set = statement.executeQuery();
            while(set.next())
                lastUpdatedById.put(set.getLong("id"), set.getDate("last_modification").toLocalDate());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        for (Product product : productsToCheck) {
            LocalDate modifiedDate = lastUpdatedById.get(product.getId());
            if(modifiedDate.isAfter(product.getLastModified()))
                products.add(product);
        }
        return products;
    }
    /**
     *
     * @param id
     * @param product
     * @return
     */
    @Override
    public boolean updateProduct(long id, Product product) {
        if(categoryDAO.selectCategory(product.getCategory().getId()) == null)
            throw new IllegalArgumentException("The category must exist in order to be able to update it");
        boolean success = false;
        String query = "UPDATE products SET name = ?, price = ?, image_path = ?, category_id = ?, last_modification = ? WHERE id = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setFloat(2, product.getPrice());
            statement.setString(3, product.getImagePath());
            statement.setLong(4, product.getCategory().getId());
            statement.setDate(5, Date.valueOf(LocalDate.now()));
            statement.setLong(6, id);
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
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
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
                            set.getDate("last_modification").toLocalDate(),
                            new Category(
                                    set.getLong("c.id"),
                                    set.getString("c.name")
                            ),
                            null
                    )
            );
        }
        set.close();
    }
}
