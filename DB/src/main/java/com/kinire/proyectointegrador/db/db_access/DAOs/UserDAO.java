package com.kinire.proyectointegrador.db.db_access.DAOs;

import com.kinire.proyectointegrador.components.User;


/**
 * Definición del DAO para la tabla usuarios
 */
public interface UserDAO {

    /**
     * Inserción de usuario
     * @param user Información del usuario. El nombre deberá ser único
     * @return Inserción exitosa
     */
    boolean insertUser(User user);

    /**
     * Selección del usuario por su nombre de usuario
     * @param username Nombre de usuario
     * @return Usuario
     */
    User selectUser(String username);

    /**
     * Actualización de un usuario
     * @param username Nombre de usuario
     * @param user Información de usuario
     * @return Actualización exitosa
     */
    boolean updateUser(String username, User user);

    /**
     * Borrado de usuario
     * @param username Nombre de usuario a borrar
     * @return Borrado exitoso
     */
    boolean deleteUser(String username);

    /**
     * Comprueba si la información del usuario es correcta
     * @param user Información del usuario
     * @return Información del usuario correcta
     */
    boolean correctUserData(User user);
}
