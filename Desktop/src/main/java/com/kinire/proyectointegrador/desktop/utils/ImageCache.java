package com.kinire.proyectointegrador.desktop.utils;

import javax.swing.*;
import java.util.HashMap;

/**
 * Clase para implementar una simple caché de imágenes y evitar peticiones recurrentes al servidor
 */
public class ImageCache {
    private final static HashMap<String, ImageIcon> imageCache = new HashMap<>();

    /**
     * Introduce una imagen en la caché de imágenes
     * @param imagePath Ruta de la imagen
     * @param imageIcon Imagen per sé
     */
    public static void putImage(String imagePath, ImageIcon imageIcon) {
        imageCache.put(imagePath, imageIcon);
    }

    /**
     * Devuelve la imagen correspondiente con la ruta dada
     * @param imagePath La ruta de la imagen
     * @return La imagen per sé
     */
    public static ImageIcon getImage(String imagePath) {
        return imageCache.get(imagePath);
    }
}
