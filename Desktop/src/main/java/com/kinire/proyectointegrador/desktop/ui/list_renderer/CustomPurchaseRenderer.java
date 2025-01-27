package com.kinire.proyectointegrador.desktop.ui.list_renderer;

import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.ShoppingCartItem;
import com.kinire.proyectointegrador.desktop.utils.DiviseCalculator;
import com.kinire.proyectointegrador.desktop.utils.ImageCache;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Locale;

/**
 * Renderizador para la lista de compras de la vista de compra
 */
public class CustomPurchaseRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {
    private static final int iconTextGap = 10;
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        ShoppingCartItem item = (ShoppingCartItem) value;
        setText("<html><div style=\"width:300px;\"></div><center><i>" + item.getProduct().getCategory().getName() + ":</i> <b>" + item.getProduct().getName() + "</b><br><hr><br>" +
                "<i><b>" + String.format(Locale.getDefault(), "%.2f", DiviseCalculator.getPrice(item.getProduct().getPrice())) + DiviseCalculator.getSymbol() + " * " + item.getAmount() +
                " = " + String.format(Locale.getDefault(), "%.2f", DiviseCalculator.getPrice(item.getPrice())) + DiviseCalculator.getSymbol() +  "</b></i></center></html>");
        ImageIcon imageIcon = ImageCache.getImage(item.getProduct().getImagePath());
        setIcon(resizeImage(imageIcon, 200, (imageIcon.getIconHeight() * 200) / imageIcon.getIconWidth()));
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
    private ImageIcon resizeImage(ImageIcon srcImage, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImage.getImage(), 0, 0, w, h, null);
        g2.dispose();

        return new ImageIcon(resizedImg);
    }
}
