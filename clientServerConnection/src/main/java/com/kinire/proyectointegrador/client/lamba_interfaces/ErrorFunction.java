package com.kinire.proyectointegrador.client.lamba_interfaces;

@FunctionalInterface
public interface ErrorFunction {
    void apply(Exception e);
}
