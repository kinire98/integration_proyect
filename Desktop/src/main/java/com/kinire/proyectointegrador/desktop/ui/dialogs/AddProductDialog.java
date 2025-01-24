/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.kinire.proyectointegrador.desktop.ui.dialogs;

import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.desktop.ui.frame.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kinire
 */
public class AddProductDialog extends javax.swing.JDialog {

    /**
     * Creates new form AddProductDialog
     */
    private InputStream image;

    private static final String EMPTY_FIELD = "Campo vacío";
    private static final String INCORRECT_FIELD = "El campo es incorrecto";
    private static final String EMPTY_NAME_FIELD = "El campo del nombre está vacío";
    private static final String EMPTY_CATEGORY_FIELD = "El campo de la categoría está vacío";
    private static final String EMPTY_PRICE_FIELD = "El campo del precio está vacío";
    private static final String INCORRECT_PRICE_FIELD = "El campo del precio no es válido";
    private static final String EMPTY_IMAGE_FIELD = "No has seleccionado ninguna imagen";
    private static final String ERROR_UPLOADING_PRODUCT = "Error guardando el producto";
    private static final String ERROR = "Error";
    private static final String PRODUCT_UPLOADED_SUCCSESFULLY = "El producto se ha guardado con éxito";
    private static final String SUCCESS = "Éxito";
    private static final String TITLE = "Añadir producto";



    public AddProductDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle(TITLE);
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
        dataPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        categoryLabel = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        imageLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        categoryField = new javax.swing.JTextField();
        priceField = new javax.swing.JFormattedTextField();
        imageButon = new javax.swing.JButton();
        addProductButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        nameLabel.setText("Nombre: ");

        categoryLabel.setText("Categoría: ");

        priceLabel.setText("Precio: ");

        imageLabel.setText("Imagen:");

        nameField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 94, 149)));

        categoryField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 94, 149)));

        priceField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 94, 149)));
        priceField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###0.###"))));

        imageButon.setBackground(new java.awt.Color(21, 94, 149));
        imageButon.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        imageButon.setForeground(new java.awt.Color(255, 255, 255));
        imageButon.setText("Seleccionar imagen");
        imageButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageButonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataPanelLayout = new javax.swing.GroupLayout(dataPanel);
        dataPanel.setLayout(dataPanelLayout);
        dataPanelLayout.setHorizontalGroup(
            dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataPanelLayout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataPanelLayout.createSequentialGroup()
                        .addComponent(categoryLabel)
                        .addGap(18, 18, 18)
                        .addComponent(categoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataPanelLayout.createSequentialGroup()
                        .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(priceLabel)
                            .addComponent(imageLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(priceField)
                            .addComponent(imageButon, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        dataPanelLayout.setVerticalGroup(
            dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel))
                .addGap(18, 18, 18)
                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryLabel)
                    .addComponent(categoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceLabel))
                .addGap(18, 18, 18)
                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imageButon)
                    .addComponent(imageLabel))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        addProductButton.setBackground(new java.awt.Color(21, 94, 149));
        addProductButton.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        addProductButton.setForeground(new java.awt.Color(255, 255, 255));
        addProductButton.setText("Añadir producto");
        addProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addProductButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(dataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void imageButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageButonActionPerformed
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Selecciona una imagen");
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "png", "jpg", "webp", "jpeg"));
        int option = fileChooser.showOpenDialog(this);
        if(option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                image = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                System.out.println("Damn, tis' hard");
            }
        }
    }//GEN-LAST:event_imageButonActionPerformed

    private void addProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButtonActionPerformed
        if(
                !nameValid() ||
                   !categoryValid() ||
                   !priceValid() ||
                   !fileValid()
        ) {
            return;
        }
        Product product = new Product();
        product.setName(nameField.getText());
        product.getCategory().setName(categoryField.getText());
        product.setPrice(
                Float.parseFloat(priceField.getText())
        );
        product.setImagePath(LocalDateTime.now().format(DateTimeFormatter.ofPattern("GyyyyddMMHHmmssAAAAnnnnnnn")));
        product.setLastModified(LocalDate.now());
        Connection.startInstance(() -> {
            Connection.getInstance().updateProduct(product, image, () -> {
                JOptionPane.showMessageDialog(this, PRODUCT_UPLOADED_SUCCSESFULLY, SUCCESS, JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            }, e -> {
                JOptionPane.showMessageDialog(this, ERROR_UPLOADING_PRODUCT, ERROR, JOptionPane.ERROR_MESSAGE);
            });
        });

    }//GEN-LAST:event_addProductButtonActionPerformed

    private boolean nameValid() {
        if(nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, EMPTY_NAME_FIELD,
                        EMPTY_FIELD, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean categoryValid() {
        if(categoryField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, EMPTY_CATEGORY_FIELD,
                    EMPTY_FIELD, JOptionPane.ERROR_MESSAGE);
           return false;
        }
        return true;
    }

    private boolean priceValid() {
        String content = priceField.getText();
        if(content.isEmpty()) {
            JOptionPane.showMessageDialog(this, EMPTY_PRICE_FIELD,
                    EMPTY_FIELD, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Float.parseFloat(content);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, INCORRECT_PRICE_FIELD,
                    INCORRECT_FIELD, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean fileValid() {
        if(image == null) {
            JOptionPane.showMessageDialog(this, EMPTY_IMAGE_FIELD,
                    EMPTY_FIELD, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProductButton;
    private javax.swing.JTextField categoryField;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JPanel dataPanel;
    private javax.swing.JButton imageButon;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JFormattedTextField priceField;
    private javax.swing.JLabel priceLabel;
    // End of variables declaration//GEN-END:variables
}
