package com.kinire.proyectointegrador.client.lamba_interfaces;

import com.kinire.proyectointegrador.components.Purchase;

@FunctionalInterface
public interface PurchaseFunction {
    void apply(Purchase purchase);
}
