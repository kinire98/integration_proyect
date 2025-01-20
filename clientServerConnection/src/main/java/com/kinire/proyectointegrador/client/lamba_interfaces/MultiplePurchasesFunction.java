package com.kinire.proyectointegrador.client.lamba_interfaces;

import com.kinire.proyectointegrador.components.Purchase;

import java.util.List;

@FunctionalInterface
public interface MultiplePurchasesFunction {
    void apply(List<Purchase> purchases);
}
