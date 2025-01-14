package com.kinire.proyectointegrador.android.controllers.fragments;

import com.kinire.proyectointegrador.android.ui.fragments.products.ProductsListViewModel;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.models.Product;

import java.util.ArrayList;

public class ProductListFragmentController {

    private ProductsListViewModel viewModel;

    public ProductListFragmentController(ProductsListViewModel viewModel) {
        this.viewModel = viewModel;
        ArrayList<Product> products = null;
        try {
            products = Connection.getInstance().getProducts();
        } catch (Exception e) {}
        if(products != null) {
            this.viewModel.setProducts(products);
        }
    }


}
