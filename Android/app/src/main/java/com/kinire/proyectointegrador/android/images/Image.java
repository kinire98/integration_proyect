package com.kinire.proyectointegrador.android.images;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.client.exceptions.FileDoesNotExistException;
import com.kinire.proyectointegrador.client.lamba_interfaces.ErrorFunction;

import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Image {
    private static Logger logger = Logger.getLogger(Image.class.getName());
    public static void getImage(Context context, String imagePath, DrawableFunction successPromise, ErrorFunction errorPromise) {
        Drawable image = ImageCache.getImage(imagePath);
        if(image != null) {
            successPromise.apply(image);
        }

        askForImage(context, imagePath, successPromise, errorPromise);
    }
    private static void askForImage(Context context, String imagePath, DrawableFunction successPromise, ErrorFunction errorPromise) {
        Connection.getInstance().getImage(imagePath, inputStream -> {
            Drawable drawable = Drawable.createFromStream(inputStream, "remote");
            ImageCache.setImage(imagePath, drawable);
            if(drawable == null) {
                askForImage(context, imagePath, successPromise, errorPromise);
                return;
            }
            successPromise.apply(drawable);
        }, e -> {
            errorPromise.apply(e);
        });
    }
}
