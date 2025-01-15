package com.kinire.proyectointegrador.components;

import java.io.Serial;
import java.io.Serializable;

public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private float price;

    private String imagePath;

    private Category category;

    public Product(long id, String name, float price, String imagePath, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.category = category;
    }

    public Product() {
        this.id = 0L;
        this.name = "";
        this.price = 0.0f;
        this.imagePath = "";
        this.category = new Category();
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

    public String getImagePath() {
        return imagePath;
    }

    public Category getCategory() {
        return category;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
