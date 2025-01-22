package com.kinire.proyectointegrador.android.image_cache;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

public class ImageCache {
    private static HashMap<String, Drawable> cache = new HashMap<>();
    public static void setImage(String path, Drawable image) {
        cache.put(path, image);
    }
    public static Drawable getImage(String path) {
        return cache.get(path);
    }
}
