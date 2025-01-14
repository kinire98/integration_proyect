package com.kinire.proyectointegrador.models;

import java.io.Serializable;

public record Product(long id, String name, float price, String imagePath, Category category) implements Serializable {
}
