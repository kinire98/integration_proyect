package com.kinire.proyectointegrador.db.db_access.impls;

import com.kinire.proyectointegrador.components.User;
import com.kinire.proyectointegrador.db.db_access.DAOs.UserDAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserImpl implements UserDAO {
    private final Connection connection;

    private final Logger logger;

    public UserImpl() throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mariadb://localhost/tpv_test", "admin", "");
        this.logger = Logger.getLogger(CategoryImpl.class.getName());
    }

    public void close() throws SQLException {
        this.connection.close();
    }
    @Override
    public boolean insertUser(User user) {
        boolean success = false;
        String query = "INSERT INTO users VALUES (?, PASSWORD(?))";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();
            set.next();
            user = new User(
                    set.getString("username"),
                    set.getString("password")
            );
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return user;
    }

    @Override
    public boolean updateUser(String username, User user) {
        boolean success = false;
        String query = "UPDATE users SET username=?, password=PASSWORD(?) WHERE username=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            success = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return success;
    }
}
