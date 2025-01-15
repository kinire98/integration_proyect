package com.kinire.proyectointegrador.components;

import java.io.Serial;
import java.io.Serializable;

public class ShoppingCartItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    private Product product;

    private int amount;

    public ShoppingCartItem() {
        this.product = new Product();
        this.amount = 0;
    }

    public ShoppingCartItem(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public float getPrice() {
        if(product == null) {
            throw new IllegalStateException("Can't call this method if you don't set a product");
        }
        return product.getPrice() * amount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
