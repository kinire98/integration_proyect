/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.kinire.proyectointegrador.desktop.ui.frame;

import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.User;
import com.kinire.proyectointegrador.desktop.controller.MainFrameController;
import com.kinire.proyectointegrador.desktop.ui.list_renderer.CustomProductsRenderer;
import com.kinire.proyectointegrador.desktop.utils.DiviseCalculator;
import com.kinire.proyectointegrador.desktop.utils.ImageCache;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.util.Locale;

/**
 *
 * @author kinire
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */

    private final static String TITLE = "Karrefú";


    private final static Color MAIN_BLUE = new Color(21, 94, 149);
    private final DefaultListModel<Product> listModel = new DefaultListModel<>();

    private int i = 0;// No es una variable local por las lambdas


    private final MainFrameController controller;

    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle(TITLE);
        this.controller = new MainFrameController(this);
        controller.changeUser();
    }

    public void initList() {
        i = 0;
        listModel.clear();
        emptyPurchaseButton.setEnabled(false);
        savePurchaseButton.setEnabled(false);
        purchaseHistoryButton.setEnabled(false);
        settingsButton.setEnabled(false);
        fileMenu.setEnabled(false);
        userMenu.setEnabled(false);
        shoppingCartMenu.setEnabled(false);
        productsList.removeMouseListener(controller);
        Connection.getInstance().getProducts(product -> {
            controller.addProduct(product);
            try {
                ImageCache.putImage(product.getImagePath(), new ImageIcon(ImageIO.read(new ByteArrayInputStream(product.getImage()))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> {
                listModel.add(i, product);
                i++;
            });
        }, this::checkUserPrivileges, e -> {

        });
        productsList.setModel(listModel);
        productsList.setCellRenderer(new CustomProductsRenderer());
    }

    public void emptyTable() {
        DefaultTableModel model = (DefaultTableModel) shoppingCartTable.getModel();
        model.setRowCount(0);
        SwingUtilities.invokeLater(() -> {
            shoppingCartTable.repaint();
        });
    }

    public void setTableModel(Object[][] objects) {
        DefaultTableModel model = (DefaultTableModel) shoppingCartTable.getModel();
        model.setRowCount(0);
        for (int j = 0; j < objects.length; j++) {
            model.addRow(objects[j]);
        }
        SwingUtilities.invokeLater(() -> {
            shoppingCartTable.setModel(model);
        });
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        checkUserPrivileges();
    }

    public Purchase getCurrentPurchase() {
        return currentPurchase;
    }

    public void setCurrentPurchase(Purchase currentPurchase) {
        this.currentPurchase = currentPurchase;
    }

    public void recalculateCartValues() {
        controller.showShoppingCartState();
    }

    public void setTotalPrice(float price) {
        this.priceField.setText(
                String.format(
                        Locale.getDefault(),
                        "%.2f%s",
                        DiviseCalculator.getPrice(price),
                        DiviseCalculator.getSymbol()
                )
        );
    }

    public void checkUserPrivileges() {
        settingsButton.setEnabled(true);
        userMenu.setEnabled(true);
        shoppingCartMenu.setEnabled(true);
        purchaseHistoryButton.setEnabled(true);
        fileMenu.setEnabled(true);
        open.setEnabled(true);
        if(!user.isAdmin()) {
            emptyPurchaseButton.setEnabled(true);
            savePurchaseButton.setEnabled(true);
            saveTo.setEnabled(true);
            savePurchaseMenu.setEnabled(true);
            emptyPurchaseMenu.setEnabled(false);
            productsList.addMouseListener(controller);
        } else {
            saveTo.setEnabled(false);
            savePurchaseMenu.setEnabled(false);
            emptyPurchaseMenu.setEnabled(false);
            this.emptyTable();
            productsList.removeMouseListener(controller);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        user = new com.kinire.proyectointegrador.components.User();
        currentPurchase = new com.kinire.proyectointegrador.components.Purchase();
        mainPanel = new javax.swing.JPanel();
        settingsButton = new javax.swing.JButton();
        purchaseHistoryButton = new javax.swing.JButton();
        emptyPurchaseButton = new javax.swing.JButton();
        savePurchaseButton = new javax.swing.JButton();
        productsPanel = new javax.swing.JPanel();
        productsLabel = new javax.swing.JLabel();
        productsScroll = new javax.swing.JScrollPane();
        productsList = new javax.swing.JList();
        shoppingCartPanel = new javax.swing.JPanel();
        shoppingCartLabel = new javax.swing.JLabel();
        shoppingCartItemsScroll = new javax.swing.JScrollPane();
        shoppingCartTable = new javax.swing.JTable();
        roundedPanel2 = new com.kinire.proyectointegrador.desktop.ui.modifiedComponents.RoundedPanel();
        priceLabel = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        saveTo = new javax.swing.JMenuItem();
        open = new javax.swing.JMenuItem();
        userMenu = new javax.swing.JMenu();
        changeUser = new javax.swing.JMenuItem();
        shoppingCartMenu = new javax.swing.JMenu();
        savePurchaseMenu = new javax.swing.JMenuItem();
        emptyPurchaseMenu = new javax.swing.JMenuItem();
        previousPurchasesMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        settingsButton.setBackground(new java.awt.Color(21, 94, 149));
        settingsButton.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        settingsButton.setForeground(new java.awt.Color(255, 255, 255));
        settingsButton.setText("Ajustes");
        settingsButton.setEnabled(false);
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });

        purchaseHistoryButton.setBackground(new java.awt.Color(21, 94, 149));
        purchaseHistoryButton.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        purchaseHistoryButton.setForeground(new java.awt.Color(255, 255, 255));
        purchaseHistoryButton.setText("Ver historial");
        purchaseHistoryButton.setEnabled(false);
        purchaseHistoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchaseHistoryButtonActionPerformed(evt);
            }
        });

        emptyPurchaseButton.setBackground(new java.awt.Color(21, 94, 149));
        emptyPurchaseButton.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        emptyPurchaseButton.setForeground(new java.awt.Color(255, 255, 255));
        emptyPurchaseButton.setText("Vaciar compra");
        emptyPurchaseButton.setEnabled(false);
        emptyPurchaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emptyPurchaseButtonActionPerformed(evt);
            }
        });

        savePurchaseButton.setBackground(new java.awt.Color(21, 94, 149));
        savePurchaseButton.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        savePurchaseButton.setForeground(new java.awt.Color(255, 255, 255));
        savePurchaseButton.setText("Guardar compra");
        savePurchaseButton.setEnabled(false);
        savePurchaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePurchaseButtonActionPerformed(evt);
            }
        });

        productsLabel.setFont(new java.awt.Font("Dialog", 1, 32)); // NOI18N
        productsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        productsLabel.setText("Productos");

        productsList.setModel(this.listModel);
        productsScroll.setViewportView(productsList);

        javax.swing.GroupLayout productsPanelLayout = new javax.swing.GroupLayout(productsPanel);
        productsPanel.setLayout(productsPanelLayout);
        productsPanelLayout.setHorizontalGroup(
            productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(productsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );
        productsPanelLayout.setVerticalGroup(
            productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsPanelLayout.createSequentialGroup()
                .addComponent(productsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(productsScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        shoppingCartLabel.setFont(new java.awt.Font("Dialog", 1, 32)); // NOI18N
        shoppingCartLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        shoppingCartLabel.setText("Carrito");

        shoppingCartTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Producto", "Precio unitario", "Cantidad", "Precio total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        shoppingCartItemsScroll.setViewportView(shoppingCartTable);

        javax.swing.GroupLayout shoppingCartPanelLayout = new javax.swing.GroupLayout(shoppingCartPanel);
        shoppingCartPanel.setLayout(shoppingCartPanelLayout);
        shoppingCartPanelLayout.setHorizontalGroup(
            shoppingCartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shoppingCartPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(shoppingCartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(shoppingCartItemsScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(shoppingCartLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE))
                .addContainerGap())
        );
        shoppingCartPanelLayout.setVerticalGroup(
            shoppingCartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shoppingCartPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(shoppingCartLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(shoppingCartItemsScroll))
        );

        priceLabel.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        priceLabel.setForeground(new java.awt.Color(255, 255, 255));
        priceLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        priceLabel.setText("Precio");

        priceField.setEditable(false);
        priceField.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        priceField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(priceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(priceField)
                .addContainerGap())
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceField, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(priceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(productsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(shoppingCartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(emptyPurchaseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(savePurchaseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(purchaseHistoryButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(settingsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(settingsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(purchaseHistoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(emptyPurchaseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(savePurchaseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(shoppingCartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(productsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );

        fileMenu.setText("Archivo");
        fileMenu.setEnabled(false);

        saveTo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        saveTo.setText("Guardar compra");
        saveTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveToActionPerformed(evt);
            }
        });
        fileMenu.add(saveTo);

        open.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        open.setText("Cargar compra");
        open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openActionPerformed(evt);
            }
        });
        fileMenu.add(open);

        menuBar.add(fileMenu);

        userMenu.setText("Usuario");
        userMenu.setEnabled(false);

        changeUser.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        changeUser.setText("Cambiar usuario");
        changeUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeUserActionPerformed(evt);
            }
        });
        userMenu.add(changeUser);

        menuBar.add(userMenu);

        shoppingCartMenu.setText("Carrito");
        shoppingCartMenu.setEnabled(false);

        savePurchaseMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        savePurchaseMenu.setText("Guardar compra");
        savePurchaseMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePurchaseMenuActionPerformed(evt);
            }
        });
        shoppingCartMenu.add(savePurchaseMenu);

        emptyPurchaseMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        emptyPurchaseMenu.setText("Vaciar compras");
        emptyPurchaseMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emptyPurchaseMenuActionPerformed(evt);
            }
        });
        shoppingCartMenu.add(emptyPurchaseMenu);

        previousPurchasesMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        previousPurchasesMenu.setText("Ver compras anteriores");
        previousPurchasesMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousPurchasesMenuActionPerformed(evt);
            }
        });
        shoppingCartMenu.add(previousPurchasesMenu);

        menuBar.add(shoppingCartMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        controller.openSettings();
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void purchaseHistoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchaseHistoryButtonActionPerformed
        controller.seePurchaseHistory();
    }//GEN-LAST:event_purchaseHistoryButtonActionPerformed

    private void emptyPurchaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emptyPurchaseButtonActionPerformed
        controller.emptyPurchase();
    }//GEN-LAST:event_emptyPurchaseButtonActionPerformed

    private void savePurchaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePurchaseButtonActionPerformed
        controller.savePurchase();
    }//GEN-LAST:event_savePurchaseButtonActionPerformed

    private void previousPurchasesMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousPurchasesMenuActionPerformed
        controller.seePurchaseHistory();
    }//GEN-LAST:event_previousPurchasesMenuActionPerformed

    private void emptyPurchaseMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emptyPurchaseMenuActionPerformed
        controller.emptyPurchase();
    }//GEN-LAST:event_emptyPurchaseMenuActionPerformed

    private void savePurchaseMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePurchaseMenuActionPerformed
        controller.emptyPurchase();
    }//GEN-LAST:event_savePurchaseMenuActionPerformed

    private void changeUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeUserActionPerformed
        controller.changeUser();
    }//GEN-LAST:event_changeUserActionPerformed

    private void openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openActionPerformed
        controller.seePurchaseHistory();
    }//GEN-LAST:event_openActionPerformed

    private void saveToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveToActionPerformed
        controller.savePurchase();
    }//GEN-LAST:event_saveToActionPerformed

    //<editor-fold>
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem changeUser;
    private com.kinire.proyectointegrador.components.Purchase currentPurchase;
    private javax.swing.JButton emptyPurchaseButton;
    private javax.swing.JMenuItem emptyPurchaseMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem open;
    private javax.swing.JMenuItem previousPurchasesMenu;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JLabel productsLabel;
    private javax.swing.JList<Product> productsList;
    private javax.swing.JPanel productsPanel;
    private javax.swing.JScrollPane productsScroll;
    private javax.swing.JButton purchaseHistoryButton;
    private com.kinire.proyectointegrador.desktop.ui.modifiedComponents.RoundedPanel roundedPanel2;
    private javax.swing.JButton savePurchaseButton;
    private javax.swing.JMenuItem savePurchaseMenu;
    private javax.swing.JMenuItem saveTo;
    private javax.swing.JButton settingsButton;
    private javax.swing.JScrollPane shoppingCartItemsScroll;
    private javax.swing.JLabel shoppingCartLabel;
    private javax.swing.JMenu shoppingCartMenu;
    private javax.swing.JPanel shoppingCartPanel;
    private javax.swing.JTable shoppingCartTable;
    private com.kinire.proyectointegrador.components.User user;
    private javax.swing.JMenu userMenu;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}
