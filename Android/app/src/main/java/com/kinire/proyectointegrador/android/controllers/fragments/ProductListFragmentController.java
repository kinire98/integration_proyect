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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductListFragmentController implements AdapterView.OnItemClickListener {

    private ProductsListViewModel viewModel;

    private ProductsListFragment fragment;

    private final String PRODUCT_PARCELABLE_KEY;

    private final Logger logger = Logger.getLogger(ProductListFragmentController.class.getName());

    public ProductListFragmentController(ProductsListFragment fragment, ProductsListViewModel viewModel) {
        this.fragment = fragment;
        this.viewModel = viewModel;
        this.PRODUCT_PARCELABLE_KEY = fragment.getString(R.string.product_parcelable_key);
        try {
            Connection.startInstance(new byte[]{10, 0, 2, 2}, () -> {
                logger.log(Level.INFO, "Connection started");
                Connection.getInstance().getProducts((products) -> fragment.getActivity().runOnUiThread(() -> viewModel.setProducts(products)), (e) -> {fragment.errorFetchingProducts();});
                logger.log(Level.INFO, "Products request sent");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(fragment.requireActivity(), AddProductActivity.class);
        intent.putExtra(PRODUCT_PARCELABLE_KEY, new ParcelableProduct(viewModel.getProductsData().get(position), ((ImageView) view.findViewById(R.id.image_field)).getDrawable()));
        fragment.getActivity().startActivity(intent);
    }
}
