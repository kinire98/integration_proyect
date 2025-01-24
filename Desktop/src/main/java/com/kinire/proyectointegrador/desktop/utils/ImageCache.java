package com.kinire.proyectointegrador.desktop.utils;

import javax.swing.*;
import java.util.HashMap;

public class ImageCache {
    private static HashMap<String, ImageIcon> imageCache = new HashMap<>();
    public static void putImage(String imagePath, ImageIcon imageIcon) {
        imageCache.put(imagePath, imageIcon);
    }
    public static ImageIcon getImage(String imagePath) {
        return imageCache.get(imagePath);
    }
}
