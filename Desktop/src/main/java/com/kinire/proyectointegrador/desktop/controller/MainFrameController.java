package com.kinire.proyectointegrador.desktop.controller;

import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.ShoppingCartItem;
import com.kinire.proyectointegrador.components.User;
import com.kinire.proyectointegrador.desktop.ui.dialogs.PurchaseDialog;
import com.kinire.proyectointegrador.desktop.ui.dialogs.SettingsDialog;
import com.kinire.proyectointegrador.desktop.ui.frame.MainFrame;
import com.kinire.proyectointegrador.desktop.utils.DiviseCalculator;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MainFrameController extends MouseAdapter {

    private final static String SELECT_AMOUNT_TITLE = "Selecciona una cantidad";
    private final static String SELECT_AMOUNT_MESSAGE = "Selecciona una cantidad: ";
    private final static String NOT_A_NUMBER_ERROR = "Introduce únicamente números";

    private final static String ERROR = "Error";
    private final static String SUCCESS = "Éxito";
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


    private final static String PURCHASE_SAVED_SUCCESFULLY = "Compra guardada con éxito";
    private final static String PURCHASE_NOT_SAVED = "Ocurrió un error guardando la compra";

    private final static String SELECT_PURCHASE = "Selecciona una compra: ";
    private final static String SELECT_PURCHASE_TITLE = "Seleccionar compra";
    private final static String PURCHASE_DELETED_SUCCESSFULLY = "Compra borrada con éxito";
    private final static String PURCHASE_NOT_DELETED = "Ocurrió un error borrando la compra";
    private final static String NO_PURCHASES = "No has hecho ninguna compra";

    private final static String EMPTY_PURCHASE = "La compra está vacía";

    private final MainFrame frame;

    private final ArrayList<Product> products;

    private boolean connectionStarted = false;


    private String username;

    private boolean previousClick = false;

    public MainFrameController(MainFrame frame) {
        this.frame = frame;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }


    public void seePurchaseHistory() {
        if(frame.getUser().isAdmin()) {
            Connection.getInstance().getAllPurchases(this::showPurchaseChooser, e -> {});
        } else {
            Connection.getInstance().getClientPurchases(frame.getUser(), this::showPurchaseChooser, e -> {});
        }
    }

    private void showPurchaseChooser(List<Purchase> purchases) {
        purchases.removeIf(Objects::isNull);
        if(purchases.isEmpty()) {
            JOptionPane.showMessageDialog(frame, NO_PURCHASES, ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<String> purchasesString = purchases
                .stream()
                .map(purchase -> purchase.getPurchaseDate().format(DateTimeFormatter
                        .ofPattern("dd/MM/yyyy")) + ": " +String.format(
                        Locale.getDefault(),
                        "%.2f%s",
                        DiviseCalculator.getPrice(purchase.getTotalPrice()),
                        DiviseCalculator.getSymbol()
                ))
                .collect(Collectors.toList());
        String selectedPurchase = (String) JOptionPane.showInputDialog(
                frame,
                SELECT_PURCHASE,
                SELECT_PURCHASE_TITLE,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                purchasesString.toArray(),
                0
        );
        if (selectedPurchase == null)
            return;
        int option = purchasesString.indexOf(selectedPurchase);
        PurchaseDialog dialog = new PurchaseDialog(frame, true, purchases.get(option));
        dialog.setVisible(true);
        boolean delete = dialog.isDelete();
        dialog.dispose();
        if(delete) {
            Connection.getInstance().deletePurchase(purchases.get(option), () -> {
                JOptionPane.showMessageDialog(frame, PURCHASE_DELETED_SUCCESSFULLY, SUCCESS, JOptionPane.INFORMATION_MESSAGE);
            }, e -> {
                JOptionPane.showMessageDialog(frame, PURCHASE_NOT_DELETED, ERROR, JOptionPane.ERROR_MESSAGE);
            });
        }
    }


    public void savePurchase() {
        if(frame.getCurrentPurchase().getShoppingCartItems().isEmpty()) {
            JOptionPane.showMessageDialog(frame, EMPTY_PURCHASE, ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        frame.getCurrentPurchase().setUser(frame.getUser());
        Connection.getInstance().uploadPurchase(frame.getCurrentPurchase(), () -> {
            JOptionPane.showMessageDialog(frame, PURCHASE_SAVED_SUCCESFULLY, SUCCESS, JOptionPane.INFORMATION_MESSAGE);
            frame.emptyTable();
        }, e -> {
            JOptionPane.showMessageDialog(frame, PURCHASE_NOT_SAVED, ERROR, JOptionPane.ERROR_MESSAGE);
        });
    }

    public void emptyPurchase() {
        frame.setCurrentPurchase(new Purchase());
        frame.emptyTable();
    }

    public void openSettings() {
        SettingsDialog dialog = new SettingsDialog(frame, true);
        dialog.setVisible(true);
        dialog.dispose();
    }



    public void showShoppingCartState() {
        Object[][] objects = new Object[4][frame.getCurrentPurchase().getShoppingCartItems().size()];
        for (int i = 0; i < frame.getCurrentPurchase().getShoppingCartItems().size(); i++) {
            ShoppingCartItem item = frame.getCurrentPurchase().getShoppingCartItems().get(i);
            objects[i] = new Object[]{
                    item.getProduct().getName(),
                    String.format(
                            Locale.getDefault(), "%.2f%s",
                            DiviseCalculator.getPrice(item.getProduct().getPrice()),
                            DiviseCalculator.getSymbol()),
                    item.getAmount(),
                    String.format(
                            Locale.getDefault(), "%.2f%s",
                            DiviseCalculator.getPrice(item.getPrice()),
                            DiviseCalculator.getSymbol())
            };
        }
        frame.setTableModel(objects);
        frame.setTotalPrice(frame.getCurrentPurchase().getTotalPrice());
    }

    public void connectionLost() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2 && !previousClick) {
            previousClick = true;
            JList<Product> list = (JList<Product>) e.getSource();
            int amount = getAmount();
            if(amount == -1)
                return;
            frame.getCurrentPurchase().getShoppingCartItems().add(new ShoppingCartItem(list.getSelectedValue(), amount));
            showShoppingCartState();
            previousClick = false;
        }
    }
    public void changeUser() {
        this.username = getData(ENTER_USERNAME_TITLE, ENTER_USERNAME, EMPTY_USER_FIELD, ERROR);
        if(!connectionStarted) {
            Connection.startInstance(() -> {
                Connection.getInstance().setConnectionLostPromise(this::connectionLost);
                Connection.getInstance().setProductsUpdatedPromise(frame::initList);
                Connection.getInstance().userExists(this.username, this::userExists, this::userDoesntExist, (e) -> {
                    e.printStackTrace();
                });

            });
            connectionStarted = true;
        } else {
            Connection.getInstance().userExists(this.username, this::userExists, this::userDoesntExist, e -> {});
        }
    }

    private void userExists() {
        String password = getPassword(ENTER_PASSWORD_TITLE, ENTER_PASSWORD);
        while(password == null) {
            JOptionPane.showMessageDialog(frame, EMPTY_PASSWORD_FIELD, ERROR, JOptionPane.ERROR_MESSAGE);
            password = getPassword(ENTER_PASSWORD_TITLE, ENTER_PASSWORD);
        }
        User user = new User(username, password);
        Connection.getInstance().isUserDataCorrect(user, () -> {
            frame.setUser(user);
            frame.initList();
        }, () -> {
            JOptionPane.showMessageDialog(frame, WRONG_PASSWORD, WRONG_PASSWORD_TITLE, JOptionPane.ERROR_MESSAGE);
            userExists();
        }, e -> {

        } );

    }
    private void userDoesntExist() {
        String password = getPassword(ENTER_NEW_PASSWORD_TITLE, ENTER_NEW_PASSWORD);
        while(password == null) {
            JOptionPane.showMessageDialog(frame, EMPTY_PASSWORD_FIELD, ERROR, JOptionPane.ERROR_MESSAGE);
            password = getPassword(ENTER_NEW_PASSWORD_TITLE, ENTER_NEW_PASSWORD);
        }
        User user = new User(username, password);
        Connection.getInstance().insertUserData(user, () -> {
            frame.setUser(user);
            frame.initList();
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

    private String getData(String title, String message, String errorMessage, String errorTitle) {
        String data = JOptionPane.showInputDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
        if(data == null || data.isEmpty()) {
            JOptionPane.showMessageDialog(frame, errorMessage, errorTitle, JOptionPane.ERROR_MESSAGE);
            return getData(title, message, errorMessage, errorTitle);
        }
        return data;
    }

    private int getAmount() {
        String value = JOptionPane.showInputDialog(frame, SELECT_AMOUNT_MESSAGE, SELECT_AMOUNT_TITLE, JOptionPane.PLAIN_MESSAGE);
        if(value == null)
            return -1;
        int amount;
        try {
            amount = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, NOT_A_NUMBER_ERROR, ERROR, JOptionPane.ERROR_MESSAGE);
            amount = getAmount();
        }
        return amount;
    }
}
