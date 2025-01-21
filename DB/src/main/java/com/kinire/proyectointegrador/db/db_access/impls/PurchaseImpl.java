package com.kinire.proyectointegrador.db.db_access.impls;

import com.kinire.proyectointegrador.components.*;
import com.kinire.proyectointegrador.db.db_access.DAOs.PurchaseDAO;
import com.kinire.proyectointegrador.db.db_access.DAOs.PurchasedProductDAO;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchaseImpl implements PurchaseDAO {


    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PurchaseImpl.class);
    private final Logger logger;

    private final PurchasedProductDAO purchasedProductDAO;

    public PurchaseImpl() {
        this.logger = Logger.getLogger(CategoryImpl.class.getName());
        this.purchasedProductDAO = new PurchaseProductImpl();
    }


    @Override
    public boolean insertPurchase(Purchase purchase) {
        int insertedRows = 0;
        String query = "INSERT INTO purchases (username, purchase_date) VALUES (?, ?)";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, purchase.getUser().getUser());
            statement.setDate(2, Date.valueOf(purchase.getPurchaseDate()));
            insertedRows += statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        insertedRows += purchasedProductDAO.bulkInsertPurchasedProduct(purchase.getShoppingCartItems(), getPurchaseId(purchase));
        return insertedRows == purchase.getShoppingCartItems().size() + 1;
    }
    private int getPurchaseId(Purchase purchase) {
        int id = 0;
        String query = "SELECT id FROM purchases WHERE username = ? AND purchase_date = ? ORDER BY id DESC LIMIT 1;";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, purchase.getUser().getUser());
            statement.setDate(2, Date.valueOf(purchase.getPurchaseDate()));
            ResultSet set = statement.executeQuery();
            set.next();
            id = set.getInt("id");
            set.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return id;
    }

    @Override
    public Purchase selectPurchase(long id) {
        Purchase purchase = null;
        String query = "SELECT * FROM purchases INNER JOIN tpv_test.purchased_products pp on purchases.id = pp.purchase_id " +
                "INNER JOIN tpv_test.products p on p.id = pp.product_id " +
                "INNER JOIN tpv_test.categories c on c.id = p.category_id " +
                "INNER JOIN tpv_test.users u on u.username = purchases.username " +
                "WHERE purchases.id = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            boolean userAdded = false;
            purchase = new Purchase();
            purchase.setId(id);
            purchase.setShoppingCartItems(new ArrayList<>());
            while(set.next()) {
                if(!userAdded) {
                    purchase.setUser(
                            new User(
                                    set.getString("u.username"),
                                    set.getString("password")
                            )
                    );
                    purchase.setPurchaseDate(set.getDate("purchase_date").toLocalDate());
                    userAdded = true;
                }
                purchase.getShoppingCartItems().add(
                        new ShoppingCartItem(
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
                        )
                );
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
            purchase = null;
        }
        return purchase;
    }

    @Override
    public List<Purchase> selectPurchaseByClient(User user) {
        List<Purchase> purchases = new ArrayList<>();
        String query = "SELECT * FROM purchases INNER JOIN tpv_test.purchased_products pp on purchases.id = pp.purchase_id " +
                "INNER JOIN tpv_test.products p on p.id = pp.product_id " +
                "INNER JOIN tpv_test.categories c on c.id = p.category_id " +
                "INNER JOIN tpv_test.users u on u.username = purchases.username " +
                "WHERE u.username = ? " +
                "ORDER BY purchases.id";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUser());
            selectPurchases(purchases, statement);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
            purchases = null;
        }
        return purchases;
    }

    @Override
    public List<Purchase> selectPurchaseByMonth(LocalDate date) {
        List<Purchase> purchases = new ArrayList<>();
        String query = "SELECT * FROM purchases INNER JOIN tpv_test.purchased_products pp on purchases.id = pp.purchase_id " +
                "INNER JOIN tpv_test.products p on p.id = pp.product_id " +
                "INNER JOIN tpv_test.categories c on c.id = p.category_id " +
                "INNER JOIN tpv_test.users u on u.username = purchases.username " +
                "WHERE MONTH(purchase_date) = MONTH(?) AND " +
                "YEAR(purchase_date) = YEAR(?) " +
                "ORDER BY purchases.id";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(date));
            statement.setDate(2, Date.valueOf(date));
            selectPurchases(purchases, statement);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
            purchases = null;
        }
        return purchases;
    }

    private void selectPurchases(List<Purchase> purchases, PreparedStatement statement) throws SQLException {
        ResultSet set = statement.executeQuery();
        long previousPurchaseId = -1L;
        Purchase currentPurchase = null;
        while(set.next()) {
            long currentPurchaseId = set.getLong("purchases.id");

            if(previousPurchaseId != currentPurchaseId) {
                logger.log(Level.INFO, "New purchase");
                currentPurchase = new Purchase();
                currentPurchase.setPurchaseDate(set.getDate("purchase_date").toLocalDate());
                currentPurchase.setId(currentPurchaseId);
                currentPurchase.setUser(
                        new User(
                                set.getString("u.username"),
                                set.getString("password")
                        )
                );
                if(previousPurchaseId == -1L) {
                    purchases.add(currentPurchase);
                }
                previousPurchaseId = currentPurchaseId;
            }

            assert currentPurchase != null;
            logger.log(Level.INFO, "New product");
            currentPurchase.getShoppingCartItems().add(
                    new ShoppingCartItem(
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
                    )
            );
        }
        purchases.add(currentPurchase);
        set.close();
    }

    @Override
    public boolean updatePurchase(long id, Purchase purchase) {
        boolean success = false;
        String query = "UPDATE purchases SET purchase_date = ? WHERE id = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(purchase.getPurchaseDate()));
            statement.setLong(2, id);
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

    @Override
    public boolean deletePurchase(long id) {
        boolean success = false;
        String query = "DELETE FROM purchases WHERE id = ?";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

}
