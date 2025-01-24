package com.kinire.proyectointegrador.android.utils;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

/**
 * Caché de imágenes muy simple. Consiste en un simple HashMap que mapea la ruta de las imágenes a la
 * imagen ya creada desde el Stream de entrada. Las distintas vistas pueden acceder aquí cuando quieran.
 */
public class ImageCache {
    private final static HashMap<String, Drawable> cache = new HashMap<>();
    public static void setImage(String path, Drawable image) {
        cache.put(path, image);
    }
    public static Drawable getImage(String path) {
        return cache.get(path);
    }
}
