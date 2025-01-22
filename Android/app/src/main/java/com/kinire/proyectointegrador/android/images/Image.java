package com.kinire.proyectointegrador.android.images;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.client.exceptions.FileDoesNotExistException;
import com.kinire.proyectointegrador.client.lamba_interfaces.ErrorFunction;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Image {
    private static Logger logger = Logger.getLogger(Image.class.getName());
    public static void getImage(Context context, String imagePath, DrawableFunction imagePromise, ErrorFunction failurePromise) {
        Drawable image = ImageCache.getImage(imagePath);
        if(image != null) {
            imagePromise.apply(image);
        }
        askForImage(context, imagePath, imagePromise, failurePromise);
    }
    private static void askForImage(Context context, String imagePath, DrawableFunction imagePromise, ErrorFunction failurePromise) {
        Connection.getInstance().getImage(imagePath, inputStream -> {
            Drawable drawable = Drawable.createFromStream(inputStream, "remote");
            ImageCache.setImage(imagePath, drawable);
            if(drawable == null) {
                askForImage(context, imagePath, imagePromise, failurePromise);
                return;
            }
            imagePromise.apply(drawable);
        }, e -> {
            if(e instanceof FileDoesNotExistException)
                imagePromise.apply(AppCompatResources.getDrawable(context, R.drawable.square_xmark_solid));
            logger.log(Level.SEVERE, "HERE");
        });
    }
}
