package com.kinire.proyectointegrador.android.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

public class ImageCompression {
    public static byte[] compressImage(Drawable drawable) {
        Bitmap largeIcon = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        largeIcon.compress(Bitmap.CompressFormat.PNG, 50, out);
        return out.toByteArray();
    }

}
