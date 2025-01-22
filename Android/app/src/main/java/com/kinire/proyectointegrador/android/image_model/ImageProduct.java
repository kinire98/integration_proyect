package com.kinire.proyectointegrador.android.image_model;

import android.graphics.drawable.Drawable;

import com.kinire.proyectointegrador.components.Product;

public class ImageProduct {

    private Product product;

    private Drawable drawable;

    public ImageProduct(Product product, Drawable drawable) {
        this.product = product;
        this.drawable = drawable;
    }

    public Product getProduct() {
        return product;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
