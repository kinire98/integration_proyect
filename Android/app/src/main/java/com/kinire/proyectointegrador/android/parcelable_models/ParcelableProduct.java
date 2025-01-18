package com.kinire.proyectointegrador.android.parcelable_models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.kinire.proyectointegrador.components.Product;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ParcelableProduct implements Parcelable {

    private final long id;
    private final String name;
    private final float price;
    private final String categoryName;


    public ParcelableProduct(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.categoryName = product.getCategory().getName();
    }

    protected ParcelableProduct(Parcel in) {
        id = in.readLong();
        name = in.readString();
        price = in.readFloat();
        categoryName = in.readString();
    }

    public static final Creator<ParcelableProduct> CREATOR = new Creator<ParcelableProduct>() {
        @Override
        public ParcelableProduct createFromParcel(Parcel in) {
            return new ParcelableProduct(in);
        }

        @Override
        public ParcelableProduct[] newArray(int size) {
            return new ParcelableProduct[size];
        }
    };

    @Override
    public int describeContents() {
        return Parcelable.CONTENTS_FILE_DESCRIPTOR;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeString(categoryName);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
