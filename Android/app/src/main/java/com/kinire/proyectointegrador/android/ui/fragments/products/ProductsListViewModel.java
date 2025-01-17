package com.kinire.proyectointegrador.android.ui.fragments.products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kinire.proyectointegrador.components.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsListViewModel extends ViewModel {

    private final MutableLiveData<List<Product>> mProducts;

    public ProductsListViewModel() {
        mProducts = new MutableLiveData<>();
    }

    public LiveData<List<Product>> getProducts() {
        return mProducts;
    }
    public void setProducts(List<Product> products) {
        this.mProducts.setValue(products);
    }
    public List<Product> getProductsData() {
        return mProducts.getValue();
    }
}
