/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.kinire.proyectointegrador.desktop.ui.dialogs;

import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.ShoppingCartItem;
import com.kinire.proyectointegrador.desktop.ui.list_renderer.CustomPurchaseRenderer;
import com.kinire.proyectointegrador.desktop.utils.DiviseCalculator;

import javax.swing.*;
import java.util.Locale;

/**
 *
 * @author kinire
 */
public class PurchaseDialog extends javax.swing.JDialog {

    /**
     * Creates new form SettingDialog
     */


    private final DefaultListModel<ShoppingCartItem> listModel = new DefaultListModel<>();
    private final Purchase purchase;
    private boolean delete = false;

    private final static String TITLE = "Ver compra - Karrefú";

    public PurchaseDialog(java.awt.Frame parent, boolean modal, Purchase purchase) {
        super(parent, modal);
        initComponents();
        this.purchase = purchase;
        this.userField.setText(purchase.getUser().getUser());
        this.priceField.setText(
                String.format(
                        Locale.getDefault(),
                        "%.2f%s",
                        DiviseCalculator.getPrice(purchase.getTotalPrice()),
                        DiviseCalculator.getSymbol()
                )        );
        initList();
    }

    private void initList() {
        listModel.clear();

        for (int i = 0; i < purchase.getShoppingCartItems().size(); i++) {
            final int finalI = i;
            SwingUtilities.invokeLater(() -> {
                listModel.add(finalI, purchase.getShoppingCartItems().get(finalI));
            });
        }
        productsList.setModel(listModel);
        productsList.setCellRenderer(new CustomPurchaseRenderer());
    }

    public boolean isDelete() {
        return delete;
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        productsPanel = new javax.swing.JPanel();
        productsLabel = new javax.swing.JLabel();
        productsScrollPanel = new javax.swing.JScrollPane();
        productsList = new javax.swing.JList<>();
        deletePurchaseButton = new javax.swing.JButton();
        userLabel = new javax.swing.JLabel();
        userField = new javax.swing.JLabel();
        roundedPanel = new com.kinire.proyectointegrador.desktop.ui.modifiedComponents.RoundedPanel();
        priceLabel = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        productsPanel.setBackground(new java.awt.Color(255, 255, 255));

        productsLabel.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        productsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        productsLabel.setText("Productos");

        productsScrollPanel.setViewportView(productsList);

        javax.swing.GroupLayout productsPanelLayout = new javax.swing.GroupLayout(productsPanel);
        productsPanel.setLayout(productsPanelLayout);
        productsPanelLayout.setHorizontalGroup(
            productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(productsScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                    .addComponent(productsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        productsPanelLayout.setVerticalGroup(
            productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productsScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );

        deletePurchaseButton.setBackground(new java.awt.Color(21, 94, 149));
        deletePurchaseButton.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        deletePurchaseButton.setForeground(new java.awt.Color(255, 255, 255));
        deletePurchaseButton.setText("Borrar compra");
        deletePurchaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePurchaseButtonActionPerformed(evt);
            }
        });

        userLabel.setText("Usuario:");

        priceLabel.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        priceLabel.setForeground(new java.awt.Color(255, 255, 255));
        priceLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        priceLabel.setText("Precio");

        priceField.setEditable(false);
        priceField.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        priceField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout roundedPanelLayout = new javax.swing.GroupLayout(roundedPanel);
        roundedPanel.setLayout(roundedPanelLayout);
        roundedPanelLayout.setHorizontalGroup(
            roundedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(priceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(priceField)
                .addContainerGap())
        );
        roundedPanelLayout.setVerticalGroup(
            roundedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceField, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(priceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(userLabel)
                    .addComponent(userField)
                    .addComponent(roundedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deletePurchaseButton, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(productsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(userLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(roundedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deletePurchaseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deletePurchaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePurchaseButtonActionPerformed
        this.setVisible(false);
        delete = true;
    }//GEN-LAST:event_deletePurchaseButtonActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deletePurchaseButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JLabel productsLabel;
    private javax.swing.JList<ShoppingCartItem> productsList;
    private javax.swing.JPanel productsPanel;
    private javax.swing.JScrollPane productsScrollPanel;
    private com.kinire.proyectointegrador.desktop.ui.modifiedComponents.RoundedPanel roundedPanel;
    private javax.swing.JLabel userField;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
