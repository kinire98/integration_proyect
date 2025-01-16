package com.kinire.proyectointegrador.desktop.ui.modifiedComponents;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    protected int _strokeSize = 1;
    protected Color _shadowColor = Color.BLACK;
    protected boolean _shadowed = false;
    protected boolean _highQuality = true;
    protected Dimension _arcs = new Dimension(10, 10);
    protected int _shadowGap = 0;
    protected int _shadowOffset = 0;
    protected int _shadowAlpha = 0;

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
        int shadowGap = this._shadowGap;
        Color shadowColorA = new Color(_shadowColor.getRed(), _shadowColor.getGreen(), _shadowColor.getBlue(), _shadowAlpha);
        Graphics2D graphics = (Graphics2D) g;

        if(_highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        if(_shadowed) {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(_shadowOffset, _shadowOffset, width - _strokeSize - _shadowOffset,
                    height - _strokeSize - _shadowOffset, _arcs.width, _arcs.height);
        } else {
            _shadowGap = 0;
        }

        graphics.setColor(_backgroundColor);
        graphics.fillRoundRect(0,  0, width - 1, height - 1, _arcs.width, _arcs.height);

        graphics.setStroke(new BasicStroke(_strokeSize));
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0,  0, width - 1, height - 1, _arcs.width, _arcs.height);
        graphics.setStroke(new BasicStroke());
    }
}
