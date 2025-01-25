package com.kinire.proyectointegrador.desktop.controller;

import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.User;
import com.kinire.proyectointegrador.desktop.ui.dialogs.AddProductDialog;
import com.kinire.proyectointegrador.desktop.ui.dialogs.SettingsDialog;
import com.kinire.proyectointegrador.desktop.ui.frame.MainFrame;
import com.kinire.proyectointegrador.desktop.utils.DiviseCalculator;

import javax.swing.*;

public class SettingsDialogController {

    private SettingsDialog dialog;

    private static final String CHOOSE_CURRENCY = "Elige una divisa: ";
    private static final String CHOOSE_CURRENCY_TITLE = "Selector de divisas";

    private final MainFrame mainFrame;
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

    private String username;

    public SettingsDialogController(SettingsDialog dialog, MainFrame mainFrame) {
        this.dialog = dialog;
        this.mainFrame = mainFrame;
        if(mainFrame.getUser().isAdmin())
            dialog.showAddProductButton();
        else
            dialog.hideAddProductButton();
    }


    public void changeCurency() {
        DiviseCalculator.setSymbol(
                ((String) JOptionPane.showInputDialog(
                        dialog,
                        CHOOSE_CURRENCY,
                        CHOOSE_CURRENCY_TITLE,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        DiviseCalculator.getCurrencies(),
                        0
                ))
        );
        mainFrame.reloadList();
        mainFrame.recalculateCartValues();
    }

    public void addProduct() {
        AddProductDialog dialog = new AddProductDialog(mainFrame, true);
        dialog.setVisible(true);
        dialog.dispose();
    }


    public void finish() {
        dialog.setVisible(false);
    }

    public void changeUser() {
        String username = JOptionPane.showInputDialog(dialog, ENTER_USERNAME, ENTER_USERNAME_TITLE, JOptionPane.PLAIN_MESSAGE);
        if(username == null)
            return;
        this.username = username;
        Connection.getInstance().userExists(this.username, this::userExists, this::userDoesntExist, e -> {});
    }

    private void userExists() {
        String password = getPassword(ENTER_PASSWORD_TITLE, ENTER_PASSWORD);
        if(password == null) {
            JOptionPane.showMessageDialog(dialog, EMPTY_PASSWORD_FIELD, ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        User user = new User(username, password);
        Connection.getInstance().isUserDataCorrect(user, () -> {
            mainFrame.setUser(user);
            mainFrame.checkUserPrivileges();
            if(user.isAdmin())
                dialog.showAddProductButton();
            else
                dialog.hideAddProductButton();
        }, () -> {
            JOptionPane.showMessageDialog(dialog, WRONG_PASSWORD, WRONG_PASSWORD_TITLE, JOptionPane.ERROR_MESSAGE);
            userExists();
        }, e -> {

        } );

    }
    private void userDoesntExist() {
        String password = getPassword(ENTER_NEW_PASSWORD_TITLE, ENTER_NEW_PASSWORD);
        if(password == null) {
            JOptionPane.showMessageDialog(dialog, EMPTY_PASSWORD_FIELD, ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        User user = new User(username, password);
        Connection.getInstance().insertUserData(user, () -> {
            mainFrame.setUser(user);
            mainFrame.checkUserPrivileges();
            if(user.isAdmin())
                dialog.showAddProductButton();
            else
                dialog.hideAddProductButton();
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
