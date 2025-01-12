package com.kinire.android.controllers.fragments;

import com.kinire.android.ui.fragments.products.ProductsListViewModel;
import com.kinire.client.Connection;
import com.kinire.models.Product;

import java.sql.SQLException;
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
