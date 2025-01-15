package com.kinire.proyectointegrador.components;

import java.io.Serial;
import java.io.Serializable;

public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Category() {
        this.id = 0;
        this.name = "";
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
