package com.kinire.proyectointegrador.db.db_access.impls;

import com.kinire.proyectointegrador.components.User;
import com.kinire.proyectointegrador.db.db_access.DAOs.UserDAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserImpl implements UserDAO {

    private final Logger logger;

    public UserImpl() {
        this.logger = Logger.getLogger(CategoryImpl.class.getName());
    }

    @Override
    public boolean insertUser(User user) {
        boolean success = false;
        String query = "INSERT INTO users VALUES (?, PASSWORD(?))";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUser());
            statement.setString(2, user.getPassword());
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

    @Override
    public User selectUser(String username) {
        User user = null;
        String query = "SELECT * FROM users WHERE username=?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();
            if(set.next()) {
                user = new User(
                        set.getString("username"),
                        set.getString("password")
                );
            }
            set.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return user;
    }

    @Override
    public boolean updateUser(String username, User user) {
        boolean success = false;
        String query = "UPDATE users SET username=?, password=PASSWORD(?) WHERE username=?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUser());
            statement.setString(2, user.getPassword());
            statement.setString(3, username);
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

    @Override
    public boolean deleteUser(String username) {
        boolean success = false;
        String query = "DELETE FROM users WHERE username=?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }

    @Override
    public boolean correctUserData(User user) {
        boolean correct = false;
        String query = "SELECT * FROM users WHERE username = ? AND password = PASSWORD(?)";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUser());
            statement.setString(2, user.getPassword());
            ResultSet set = statement.executeQuery();
            correct = set.next();
            set.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return correct;
    }
}
