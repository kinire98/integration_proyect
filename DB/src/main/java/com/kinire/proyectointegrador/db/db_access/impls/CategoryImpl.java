package com.kinire.proyectointegrador.db.db_access.impls;

import com.kinire.proyectointegrador.components.Category;
import com.kinire.proyectointegrador.db.db_access.DAOs.CategoryDAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CategoryImpl implements CategoryDAO {

    private final Connection connection;

    private final Logger logger;

    public CategoryImpl() throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mariadb://localhost/tpv_test", "admin", "");
        this.logger = Logger.getLogger(CategoryImpl.class.getName());
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    @Override
    public boolean insertCategory(Category category) {
        boolean success = false;
        String query = "INSERT INTO categories VALUES (? , ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, category.getId());
            statement.setString(2, category.getName());
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

    @Override
    public Category selectCategory(long id) {
        String query = "SELECT * FROM categories WHERE id = ?";
        Category category = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            set.next();
            category = new Category(
                    set.getLong("id"),
                    set.getString("name")
            );
            set.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return category;
    }

    @Override
    public boolean updateCategory(long id, Category category) {
        boolean success = false;
        String query = "UPDATE categories SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category.getName());
            statement.setLong(2, id);
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

    @Override
    public boolean deleteCategory(long id) {
        boolean success = false;
        String query = "DELETE FROM categories WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }
}
