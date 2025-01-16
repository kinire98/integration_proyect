package com.kinire.proyectointegrador.db.db_access.impls;

import com.kinire.proyectointegrador.components.Category;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.ShoppingCartItem;
import com.kinire.proyectointegrador.db.db_access.DAOs.PurchasedProductDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchaseProductImpl implements PurchasedProductDAO {

    private final Logger logger;

    public PurchaseProductImpl() {
        this.logger = Logger.getLogger(CategoryImpl.class.getName());
    }


    @Override
    public boolean insertPurchasedProduct(ShoppingCartItem shoppingCartItem, long purchaseId) {
        boolean success = false;
        String query = "INSERT INTO purchased_products VALUES (?, ?, ?)";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, purchaseId);
            statement.setLong(2, shoppingCartItem.getProduct().getId());
            statement.setInt(3, shoppingCartItem.getAmount());
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

    @Override
    public ShoppingCartItem selectPurchasedProduct(long purchaseId, long productId) {
        ShoppingCartItem shoppingCartItem = null;
        String query = "SELECT amount FROM purchased_products INNER JOIN tpv_test.products p INNER JOIN tpv_test.categories c ON p.category_id = c.id AND purchased_products.product_id = p.id  WHERE product_id = ? AND purchase_id = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, productId);
            statement.setLong(2, purchaseId);
            ResultSet set = statement.executeQuery();
            set.next();
            shoppingCartItem = new ShoppingCartItem(
                   new Product(
                           set.getLong("p.id"),
                           set.getString("p.name"),
                           set.getFloat("price"),
                           set.getString("image_path"),
                           set.getDate("last_modification").toLocalDate(),
                           new Category(
                                   set.getLong("c.id"),
                                   set.getString("c.name")
                           )
                   ),
                    set.getInt("amount")
            );
            set.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return shoppingCartItem;
    }

    @Override
    public List<ShoppingCartItem> selectByPurchase(long purchaseId) {
        List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
        String query = "SELECT amount FROM purchased_products INNER JOIN tpv_test.products p INNER JOIN tpv_test.categories c ON p.category_id = c.id AND purchased_products.product_id = p.id  WHERE purchase_id = ?";
        return getShoppingCartItems(purchaseId, shoppingCartItems, query);
    }

    @Override
    public List<ShoppingCartItem> selectByProduct(long productId) {
        List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
        String query = "SELECT amount FROM purchased_products INNER JOIN tpv_test.products p INNER JOIN tpv_test.categories c ON p.category_id = c.id AND purchased_products.product_id = p.id  WHERE product_id = ?";
        return getShoppingCartItems(productId, shoppingCartItems, query);
    }

    private List<ShoppingCartItem> getShoppingCartItems(long id, List<ShoppingCartItem> shoppingCartItems, String query) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                shoppingCartItems.add(new ShoppingCartItem(
                        new Product(
                                set.getLong("p.id"),
                                set.getString("p.name"),
                                set.getFloat("price"),
                                set.getString("image_path"),
                                set.getDate("last_modification").toLocalDate(),
                                new Category(
                                        set.getLong("c.id"),
                                        set.getString("c.name")
                                )
                        ),
                        set.getInt("amount")
                ));
            }
            set.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return shoppingCartItems;
    }

    @Override
    public boolean updatePurchasedProduct(long purchaseId, long productId, ShoppingCartItem item) {
        boolean success = false;
        String query = "UPDATE purchased_products SET amount = ? WHERE product_id = ? AND purchase_id = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, item.getAmount());
            statement.setLong(2, productId);
            statement.setLong(3, purchaseId);
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

    @Override
    public boolean deletePurchasedProduct(long purchaseId, long productId) {
        boolean success = false;
        String query = "DELETE FROM purchased_products WHERE product_id = ? AND purchase_id = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, productId);
            statement.setLong(2, purchaseId);
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }
}
