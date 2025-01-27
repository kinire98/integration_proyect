package com.kinire.proyectointegrador.desktop.ui.list_renderer;

import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.desktop.utils.DiviseCalculator;
import com.kinire.proyectointegrador.desktop.utils.ImageCache;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Locale;

/**
 * Renderizador para la lista de productos de la vista principal
 */
public class CustomProductsRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {
    private static final int iconTextGap = 10;
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Product product = (Product) value;
        setText("<html><div style=\"width:300px;\"></div><center><i>" + product.getCategory().getName() + ":</i> <b>" + product.getName() + "</b><br><hr><br>" +
                "<i><b>" + String.format(Locale.getDefault(), "%.2f", DiviseCalculator.getPrice(product.getPrice())) + DiviseCalculator.getSymbol() + "</b></i></center></html>");
        ImageIcon imageIcon = ImageCache.getImage(product.getImagePath());
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
