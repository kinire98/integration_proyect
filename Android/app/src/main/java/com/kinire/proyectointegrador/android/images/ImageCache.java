package com.kinire.proyectointegrador.android.images;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

class ImageCache {
    private static final HashMap<String, Drawable> imageCache = new HashMap<>();
    static Drawable getImage(String imagePath) {
        return imageCache.getOrDefault(imagePath, null);
    }
    static void setImage(String imagePath, Drawable image) {
        imageCache.put(imagePath, image);
    }
}
