package com.kinire.proyectointegrador.desktop.ui.list_renderer;

import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.desktop.utils.ImageCache;

import javax.swing.*;
import java.awt.*;

public class CustomRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {
    private static final int iconTextGap = 10;
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Product product = (Product) value;
        setText("<html><i>" + product.getCategory().getName() + ":</i> <b>" + product.getName() + "</b></html>");
        /*ImageIcon icon = ;
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);*/
        setIcon(ImageCache.getImage(product.getImagePath()));
        setIconTextGap(iconTextGap);
        if(isSelected) {
            setForeground(list.getSelectionForeground());
            setBackground(list.getSelectionBackground());
        } else {
            setForeground(list.getForeground());
            setBackground(list.getBackground());
        }
        setEnabled(true);
        setFont(list.getFont());
        return this;
    }
}
