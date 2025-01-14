package com.kinire.proyectointegrador.client;

import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.ShoppingCartItem;

import java.util.ArrayList;

public class ShoppingCart {

    private final ArrayList<ShoppingCartItem> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public ArrayList<ShoppingCartItem> getProducts() {
        return products;
    }

    public void addProduct(ShoppingCartItem shoppingCartItem) {
        products.add(shoppingCartItem);
    }
}
