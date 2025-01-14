package com.kinire.proyectointegrador.android.controllers.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.databinding.FragmentAddProductBinding;
import com.kinire.proyectointegrador.android.parcelable_models.ParcelableProduct;
import com.kinire.proyectointegrador.android.ui.activities.AddProductActivity;
import com.kinire.proyectointegrador.android.ui.fragments.products.ProductsListFragment;
import com.kinire.proyectointegrador.android.ui.fragments.products.ProductsListViewModel;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductListFragmentController implements AdapterView.OnItemClickListener {

    private ProductsListViewModel viewModel;

    private ProductsListFragment fragment;

    private ArrayList<Product> products;

    private final String PRODUCT_PARCELABLE_KEY;

    public ProductListFragmentController(ProductsListFragment fragment, ProductsListViewModel viewModel) {
        this.fragment = fragment;
        this.viewModel = viewModel;
        this.PRODUCT_PARCELABLE_KEY = fragment.getString(R.string.product_parcelable_key);
        try {
            this.products = Connection.getInstance().getProducts();
        } catch (Exception e) {}
        if(products != null) {
            this.viewModel.setProducts(products);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(fragment.requireActivity(), AddProductActivity.class);
        intent.putExtra(PRODUCT_PARCELABLE_KEY, new ParcelableProduct(products.get(position), ((ImageView) view.findViewById(R.id.image_field)).getDrawable()));
        fragment.getActivity().startActivity(intent);
    }
}
