package com.kinire.proyectointegrador.client.lamba_interfaces;

import com.kinire.proyectointegrador.components.Product;

import java.io.IOException;
import java.util.List;

@FunctionalInterface
public interface ProductArrayFunction {
    void apply(List<Product> products);
}
