package com.kinire.proyectointegrador.desktop.controller;

import com.kinire.proyectointegrador.desktop.ui.frame.MainFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrameController extends MouseAdapter {
    private final MainFrame frame;
    public MainFrameController(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
