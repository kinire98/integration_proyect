package com.kinire.proyectointegrador.db.db_access.impls;

import com.kinire.proyectointegrador.components.Category;
import com.kinire.proyectointegrador.db.db_access.DAOs.CategoryDAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CategoryImpl implements CategoryDAO {

    private final Logger logger;

    public CategoryImpl() {
        this.logger = Logger.getLogger(CategoryImpl.class.getName());
    }


    @Override
    public boolean insertCategory(Category category) {
        boolean success = false;
        String query = "INSERT IGNORE INTO categories (name) VALUES (?)";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category.getName());
            int tmp = statement.executeUpdate();
            success = tmp == 1 || tmp == 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

    @Override
    public Category selectCategory(long id) {
        String query = "SELECT * FROM categories WHERE id = ?";
        Category category = null;
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
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
    public Category selectByName(String name) {
        String query = "SELECT * FROM categories WHERE name = ?";
        Category category = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
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
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
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
