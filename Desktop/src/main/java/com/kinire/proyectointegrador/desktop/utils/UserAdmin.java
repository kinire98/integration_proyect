package com.kinire.proyectointegrador.desktop.utils;

import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.User;
import com.kinire.proyectointegrador.desktop.ui.frame.MainFrame;

import javax.swing.*;

/**
 * Clase para la administración de los usuarios
 */
public class UserAdmin {
    private final static String ERROR = "Error";
    private final static String ENTER_USERNAME = "Cliente:";
    private final static String ENTER_USERNAME_TITLE = "Introducir nombre de cliente";
    private final static String ENTER_PASSWORD = "Contraseña:";
    private final static String ENTER_PASSWORD_TITLE = "Introducir contraseña";
    private final static String ENTER_NEW_PASSWORD = "Crea una contraseña:";
    private final static String ENTER_NEW_PASSWORD_TITLE = "Crear contraseña";
    private final static String WRONG_PASSWORD = "La contraseña no es correcta";
    private final static String WRONG_PASSWORD_TITLE = "Contraseña incorrecta";
    private final static String EMPTY_USER_FIELD = "No dejes el campo de usuario vacío";
    private final static String EMPTY_PASSWORD_FIELD = "No dejes el campo de la contraseña vacío";

    private final MainFrame mainFrame;

    private String username;

    public UserAdmin(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * Inicio del proceso del cambio de usuario
     */
    public void changeUser() {
        String username = JOptionPane.showInputDialog(mainFrame, ENTER_USERNAME, ENTER_USERNAME_TITLE, JOptionPane.PLAIN_MESSAGE);
        if(username == null || username.isEmpty())
            return;
        this.username = username;
        Connection.getInstance().userExists(this.username, this::userExists, this::userDoesntExist, e -> {});
    }

    private void userExists() {
        String password = getPassword(ENTER_PASSWORD_TITLE, ENTER_PASSWORD);
        if(password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, EMPTY_PASSWORD_FIELD, ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        User user = new User(username, password);
        Connection.getInstance().isUserDataCorrect(user, () -> {
            mainFrame.setUser(user);
            mainFrame.checkUserPrivileges();

        }, () -> {
            JOptionPane.showMessageDialog(mainFrame, WRONG_PASSWORD, WRONG_PASSWORD_TITLE, JOptionPane.ERROR_MESSAGE);
            userExists();
        }, e -> {

        } );

    }
    private void userDoesntExist() {
        String password = getPassword(ENTER_NEW_PASSWORD_TITLE, ENTER_NEW_PASSWORD);
        if(password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, EMPTY_PASSWORD_FIELD, ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        User user = new User(username, password);
        Connection.getInstance().insertUserData(user, () -> {
            mainFrame.setUser(user);
            mainFrame.checkUserPrivileges();

        }, e -> {

        } );
    }



    private String getPassword(String title, String message) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        JPasswordField pass = new JPasswordField();
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if(option == 0) // pressing OK button
        {
            char[] password = pass.getPassword();
            return new String(password);
        }
        return null;
    }
}
