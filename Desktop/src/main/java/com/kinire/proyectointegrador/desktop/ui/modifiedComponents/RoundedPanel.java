package com.kinire.proyectointegrador.desktop.ui.modifiedComponents;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel con los bordes redondeados
 */
public class RoundedPanel extends JPanel {
    protected int _strokeSize = 1;
    protected boolean _highQuality = true;
    protected Dimension _arcs = new Dimension(10, 10);

    protected Color _backgroundColor = new Color(21, 94, 149);

    public RoundedPanel() {
        super();
        setOpaque(false);
    }

    @Override
    public void setBackground(Color c) {
        _backgroundColor = c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;

        if(_highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        graphics.setColor(_backgroundColor);
        graphics.fillRoundRect(0,  0, width - 1, height - 1, _arcs.width, _arcs.height);

        graphics.setStroke(new BasicStroke(_strokeSize));
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0,  0, width - 1, height - 1, _arcs.width, _arcs.height);
        graphics.setStroke(new BasicStroke());
    }
}
