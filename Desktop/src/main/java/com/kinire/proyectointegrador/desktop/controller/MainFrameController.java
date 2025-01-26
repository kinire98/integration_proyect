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
import com.kinire.proyectointegrador.desktop.utils.UserAdmin;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainFrameController extends MouseAdapter implements ListSelectionListener {

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

    private final static Color MAIN_BLUE = new Color(21, 94, 149);

    private final UserAdmin userAdmin;

    private String username;

    private boolean previousClick = false;

    public MainFrameController(MainFrame frame) {
        this.frame = frame;
        this.products = new ArrayList<>();
        this.userAdmin = new UserAdmin(frame);
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void seePurchaseHistory() {
        if(frame.getUser().isAdmin()) {
            Connection.getInstance().getAllPurchases(this::showPurchaseChooser, e -> {});
        } else {
            Connection.getInstance().getClientPurchases(frame.getUser(), this::showPurchaseChooser, e -> {});
        }
    }
    public void changeUserStarted() {
        userAdmin.changeUser();
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
        tableOperationOngoing = true;
        frame.getCurrentPurchase().setUser(frame.getUser());
        Connection.getInstance().uploadPurchase(frame.getCurrentPurchase(), () -> {
            JOptionPane.showMessageDialog(frame, PURCHASE_SAVED_SUCCESFULLY, SUCCESS, JOptionPane.INFORMATION_MESSAGE);
            frame.emptyTable();
        }, e -> {
            JOptionPane.showMessageDialog(frame, PURCHASE_NOT_SAVED, ERROR, JOptionPane.ERROR_MESSAGE);
        });
        emptyPurchase();
    }

    public void emptyPurchase() {
        if(frame.getCurrentPurchase().getShoppingCartItems().isEmpty()) {
            JOptionPane.showMessageDialog(frame, EMPTY_PURCHASE, ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        tableOperationOngoing = true;
        frame.setCurrentPurchase(new Purchase());
        frame.emptyTable();
    }

    public void openSettings() {
        SettingsDialog dialog = new SettingsDialog(frame, true);
        dialog.setVisible(true);
        dialog.dispose();
    }



    public void showShoppingCartState() {
        Object[][] objects = new Object[frame.getCurrentPurchase().getShoppingCartItems().size()][4];
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
            tableOperationOngoing = true;
            JList<Product> list = (JList<Product>) e.getSource();
            if(isPresent(list
                    .getSelectedValue()))
                presentProduct(list.getSelectedValue());
            else
                newProduct(list.getSelectedValue());
            showShoppingCartState();
            previousClick = false;
        }
    }

    private boolean isPresent(Product product) {
        if(frame.getCurrentPurchase().getShoppingCartItems().isEmpty())
            return false;
        for(ShoppingCartItem item : frame.getCurrentPurchase().getShoppingCartItems()) {
            if(item.getProduct().getId() == product.getId())
                return true;
        }
        return false;
    }


    private void newProduct(Product product) {
        int amount = getAmount();
        if(amount <= 0)
            return;
        frame.getCurrentPurchase().getShoppingCartItems().add(new ShoppingCartItem(product, amount));
    }
    private void presentProduct(Product product) {
        int amount = getAmount(getProductAmount(product));
        if(amount <= -1)
            return;
        if(amount == 0) {
            deleteProduct(product);
            return;
        }

        setProductAmount(product, amount);
    }

    private void deleteProduct(Product product) {
        Iterator<ShoppingCartItem> itemIterator = frame.getCurrentPurchase().getShoppingCartItems().iterator();
        while(itemIterator.hasNext()) {
            ShoppingCartItem item = itemIterator.next();
            if(item.getProduct().getId() == product.getId())
                itemIterator.remove();
        }

    }

    private int getProductAmount(Product product)  {
        for(ShoppingCartItem item : frame.getCurrentPurchase().getShoppingCartItems()) {
            if(item.getProduct().getId() == product.getId())
                return item.getAmount();
        }
        return -1;
    }

    private void setProductAmount(Product product, int amount) {
        for(ShoppingCartItem item : frame.getCurrentPurchase().getShoppingCartItems()) {
            if(item.getProduct().getId() == product.getId())
                item.setAmount(amount);
        }
    }
    public void changeUser() {
        this.username = getData(ENTER_USERNAME_TITLE, ENTER_USERNAME, EMPTY_USER_FIELD, ERROR);
        if(!connectionStarted) {
            Connection.startInstance(() -> {
                Connection.getInstance().setConnectionLostPromise(this::connectionLost);
                Connection.getInstance().setProductsUpdatedPromise(() -> {
                    Connection.getInstance().getUpdatedProducts(products, product -> {
                        products.add(product);
                        frame.reloadList();
                    }, e -> {});
                });
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
        while(password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, EMPTY_PASSWORD_FIELD, ERROR, JOptionPane.ERROR_MESSAGE);
            password = getPassword(ENTER_PASSWORD_TITLE, ENTER_PASSWORD);
        }
        User user = new User(username, password);
        Connection.getInstance().isUserDataCorrect(user, () -> {
            userCorrectOperations(user);
        }, () -> {
            JOptionPane.showMessageDialog(frame, WRONG_PASSWORD, WRONG_PASSWORD_TITLE, JOptionPane.ERROR_MESSAGE);
            userExists();
        }, e -> {

        } );

    }
    private void userDoesntExist() {
        String password = getPassword(ENTER_NEW_PASSWORD_TITLE, ENTER_NEW_PASSWORD);
        while(password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, EMPTY_PASSWORD_FIELD, ERROR, JOptionPane.ERROR_MESSAGE);
            password = getPassword(ENTER_NEW_PASSWORD_TITLE, ENTER_NEW_PASSWORD);
        }
        User user = new User(username, password);
        Connection.getInstance().insertUserData(user, () -> {
            userCorrectOperations(user);
        }, e -> {

        } );
    }
    private void userCorrectOperations(User user) {
        frame.setUser(user);
        if(products.isEmpty())
            frame.initList();
        frame.setCurrentPurchase(new Purchase());
        showShoppingCartState();
    }

    private String getPassword(String title, String message) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        JPasswordField pass = new JPasswordField();
        pass.setBorder(BorderFactory.createLineBorder(MAIN_BLUE, 1, true));
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
    private int getAmount(int defaultValue) {
        String value = (String) JOptionPane.showInputDialog(frame, SELECT_AMOUNT_MESSAGE, SELECT_AMOUNT_TITLE, JOptionPane.PLAIN_MESSAGE, null, null, String.valueOf(defaultValue));
        if(value == null)
            return -1;
        int amount;
        try {
            amount = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, NOT_A_NUMBER_ERROR, ERROR, JOptionPane.ERROR_MESSAGE);
            amount = getAmount(defaultValue);
        }
        return amount;
    }

    private boolean tableOperationOngoing = false;
    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        if(tableOperationOngoing)
            return;
        tableOperationOngoing = true;
        String productName = (String) frame.getShoppingCartTable().getValueAt(
                frame.getShoppingCartTable().getSelectedRow(), 0
        );
        ShoppingCartItem item = getItemByName(productName);
        if(item == null)
            return;
        int amount = getAmount(item.getAmount());
        if(amount <= -1)
            return;
        if(amount == 0) {
            deleteProduct(item.getProduct());
            return;
        }
        setProductAmount(item.getProduct(), amount);
        showShoppingCartState();
    }

    public void completedTableOperation() {
        tableOperationOngoing = false;
    }

    private ShoppingCartItem getItemByName(String name) {
        for(ShoppingCartItem item : frame.getCurrentPurchase().getShoppingCartItems()) {
            if(item.getProduct().getName().equals(name))
                return item;
        }
        return null;
    }
}
