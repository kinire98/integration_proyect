package com.kinire.proyectointegrador.android.ui.fragments.products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kinire.proyectointegrador.models.Product;

import java.util.ArrayList;

public class ProductsListViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Product>> mProducts;

    public ProductsListViewModel() {
        mProducts = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Product>> getProducts() {
        return mProducts;
    }
    public void setProducts(ArrayList<Product> products) {
        this.mProducts.setValue(products);
    }
}
