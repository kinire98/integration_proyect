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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParcelableProduct implements Parcelable {

    private final long id;
    private final String name;
    private final float price;
    private final long categoryId;
    private final String categoryName;
    private final String imagePath;
    private final LocalDate date;


    public ParcelableProduct(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.categoryId = product.getCategory().getId();
        this.categoryName = product.getCategory().getName();
        this.imagePath = product.getImagePath();
        this.date = product.getLastModified();
    }

    protected ParcelableProduct(Parcel in) {
        id = in.readLong();
        name = in.readString();
        price = in.readFloat();
        categoryId = in.readLong();
        categoryName = in.readString();
        imagePath = in.readString();
        date = LocalDate.parse(in.readString(), DateTimeFormatter.ISO_DATE);
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
        dest.writeLong(categoryId);
        dest.writeString(categoryName);
        dest.writeString(imagePath);
        dest.writeString(date.format(DateTimeFormatter.ISO_DATE));
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

    public String getImagePath() {
        return imagePath;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public LocalDate getDate() {
        return date;
    }
}
