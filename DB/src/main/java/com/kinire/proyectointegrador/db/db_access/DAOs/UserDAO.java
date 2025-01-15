package com.kinire.proyectointegrador.db.db_access.DAOs;

import com.kinire.proyectointegrador.components.User;

public interface UserDAO {
    boolean insertUser(User user);
    User selectUser(String username);
    boolean updateUser(String username, User user);
    boolean deleteUser(String username);
    boolean correctUserData(User user);
}
