package com.kinire.proyectointegrador.android.controllers.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.utils.ImageCache;
import com.kinire.proyectointegrador.android.parcelable_models.ParcelableProduct;
import com.kinire.proyectointegrador.android.ui.activities.ProductActionActivity;
import com.kinire.proyectointegrador.android.ui.fragments.products.ProductsListFragment;
import com.kinire.proyectointegrador.android.ui.fragments.products.ProductsListViewModel;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
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
        fragment.requireActivity().runOnUiThread(() -> {
            viewModel.setProducts(new ArrayList<>());
        });
        initConnection();
    }
    private void initConnection() {
        try {
            if(!Connection.isInstanceStarted()) {
                Connection.startInstance(() -> {
                    Connection.getInstance().setProductsUpdatedPromise(() -> {
                        Connection.getInstance().getUpdatedProducts(viewModel.getProductsData(), this::productGot, (e) -> fragment.error());
                    });
                    Connection.getInstance().setConnectionLostPromise(fragment::errorConnectionLost);
                    logger.log(Level.INFO, "Connection started");
                    Connection.getInstance().getProducts(this::productGot, (e) -> {fragment.error();});
                    logger.log(Level.INFO, "Products request sent");
                });
            } else {
                Connection.getInstance().getProducts(this::productGot, (e) -> {fragment.error();});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void productGot(Product product) {
        ImageCache.setImage(product.getImagePath(), Drawable.createFromStream(new ByteArrayInputStream(product.getImage()), "remote"));
        List<Product> products = viewModel.getProductsData();
        products.add(product);
        fragment.requireActivity().runOnUiThread(() -> {
            viewModel.setProducts(products);
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // If image not loaded, it won't open the product
        if(((ImageView) view.findViewById(R.id.image_field)).getDrawable() == null)
            return;
        Intent intent = new Intent(fragment.requireActivity(), ProductActionActivity.class);
        intent.putExtra(PRODUCT_PARCELABLE_KEY, new ParcelableProduct(viewModel.getProductsData().get(position)));
        fragment.requireActivity().startActivity(intent);
    }
}
