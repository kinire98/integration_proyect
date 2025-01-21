package com.kinire.proyectointegrador.android.parcelable_models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.kinire.proyectointegrador.components.Category;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.ShoppingCartItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ParcelablePurchase implements Parcelable {

    private final String userName;
    private final LocalDate purchaseDate;
    private final List<ShoppingCartItem> shoppingCartItems;
    private final float totalPrice;

    public ParcelablePurchase(Purchase purchase) {
        this.userName = purchase.getUser().getUser();
        this.purchaseDate = purchase.getPurchaseDate();
        this.shoppingCartItems = purchase.getShoppingCartItems();
        this.totalPrice = purchase.getTotalPrice();
    }
    protected ParcelablePurchase(Parcel in) {
        userName = in.readString();
        purchaseDate = LocalDate.parse(in.readString(), DateTimeFormatter.ISO_DATE);
        totalPrice = in.readFloat();
        shoppingCartItems = new ArrayList<>();
        int limit = in.readInt();
        for (int i = 0; i < limit; i++) {
            ShoppingCartItem item = new ShoppingCartItem();
            shoppingCartItems.add(item);
            item.setAmount(in.readInt());
            item.setProduct(
                    new Product(
                            in.readLong(),
                            in.readString(),
                            in.readFloat(),
                            in.readString(),
                            LocalDate.parse(in.readString(), DateTimeFormatter.ISO_DATE),
                            new Category(
                                    in.readLong(),
                                    in.readString()
                            )
                    )
            );
        }
    }

    @Override
    public int describeContents() {
        return Parcelable.CONTENTS_FILE_DESCRIPTOR;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(purchaseDate.format(DateTimeFormatter.ISO_DATE));
        dest.writeFloat(totalPrice);
        dest.writeInt(shoppingCartItems.size());
        for (ShoppingCartItem item : shoppingCartItems) {
            dest.writeInt(item.getAmount());
            dest.writeLong(item.getProduct().getId());
            dest.writeString(item.getProduct().getName());
            dest.writeFloat(item.getProduct().getPrice());
            dest.writeString(item.getProduct().getImagePath());
            dest.writeString(item.getProduct().getLastModified().format(DateTimeFormatter.ISO_DATE));
            dest.writeLong(item.getProduct().getCategory().getId());
            dest.writeString(item.getProduct().getCategory().getName());
        }
    }

    public static final Creator<ParcelablePurchase> CREATOR = new Creator<ParcelablePurchase>() {
        @Override
        public ParcelablePurchase createFromParcel(Parcel in) {
            return new ParcelablePurchase(in);
        }

        @Override
        public ParcelablePurchase[] newArray(int size) {
            return new ParcelablePurchase[0];
        }
    };

    public float getTotalPrice() {
        return totalPrice;
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public String getUserName() {
        return userName;
    }
}
