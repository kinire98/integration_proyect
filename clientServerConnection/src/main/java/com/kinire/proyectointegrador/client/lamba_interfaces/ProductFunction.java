package com.kinire.proyectointegrador.client.lamba_interfaces;

import com.kinire.proyectointegrador.components.Product;

@FunctionalInterface
public interface ProductFunction {
    void apply(Product product);
}
