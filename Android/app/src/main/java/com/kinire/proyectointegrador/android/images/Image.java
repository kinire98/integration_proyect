package com.kinire.proyectointegrador.android.images;

import android.graphics.drawable.Drawable;

import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.client.lamba_interfaces.ErrorFunction;


public class Image {
    public static void getImage(String imagePath, DrawableFunction imagePromise, ErrorFunction failurePromise) {
        Drawable image = ImageCache.getImage(imagePath);
        if(image != null) {
            imagePromise.apply(image);
        }
        askForImage(imagePath, imagePromise, failurePromise);
    }
    private static void askForImage(String imagePath, DrawableFunction imagePromise, ErrorFunction failurePromise) {
        Connection.getInstance().getImage(imagePath, inputStream -> {
            System.out.println(inputStream);
            Drawable drawable = Drawable.createFromStream(inputStream, "remote");
            ImageCache.setImage(imagePath, drawable);
            if(drawable == null) {
                askForImage(imagePath, imagePromise, failurePromise);
                return;
            }
            imagePromise.apply(drawable);
        }, failurePromise);
    }
}
