package com.kinire.proyectointegrador.components;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Purchase implements Serializable {

    @Serial
    private static final long serialVersionUID = 5L;

    private long id;

    private User user;

    private LocalDate purchaseDate;

    private ArrayList<ShoppingCartItem> shoppingCartItems;

    public Purchase(long id, LocalDate localDate, User user, ArrayList<ShoppingCartItem> shoppingCartItems) {
        this.id = id;
        this.user = user;
        this.shoppingCartItems = shoppingCartItems;
        this.purchaseDate = LocalDate.now();
    }
    public Purchase() {
        this.id = 0;
        this.user = new User();
        this.shoppingCartItems = new ArrayList<>();
        this.purchaseDate = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public ArrayList<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setShoppingCartItems(ArrayList<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    public float getTotalPrice() {
        float totalPrice = 0.0f;
        for(ShoppingCartItem shoppingCartItem : shoppingCartItems) {
            totalPrice += shoppingCartItem.getPrice();
        }
        return totalPrice;
    }
}
