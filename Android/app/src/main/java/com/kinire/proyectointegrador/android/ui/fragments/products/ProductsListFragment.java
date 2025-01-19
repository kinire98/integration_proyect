package com.kinire.proyectointegrador.android.ui.fragments.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.adapters.ProductListAdapter;
import com.kinire.proyectointegrador.android.controllers.fragments.ProductListFragmentController;
import com.kinire.proyectointegrador.android.databinding.FragmentProductsListBinding;
import com.kinire.proyectointegrador.components.Product;

import java.util.List;

public class ProductsListFragment extends Fragment {

    private final static String CANT_GET_PRODUCTS_ERROR_MESSAGE = "No se pudo obtener la lista de productos";

    private FragmentProductsListBinding binding;

    private ListView productList;

    private ProductListFragmentController controller;

    private ProductsListViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.viewModel =
                new ViewModelProvider(this).get(ProductsListViewModel.class);

        binding = FragmentProductsListBinding.inflate(inflater, container, false);
        binding.getRoot().setOnApplyWindowInsetsListener((v, insets) -> {
            // Consume the system window insets so they don't add margins
            return insets.consumeSystemWindowInsets();
        });
        this.getElements();
        this.setAdapter();
        this.setListeners();
        return binding.getRoot();
    }

    private void getElements() {
        this.productList = binding.productsList;
        System.out.println("Aqui getElements");
        this.controller = new
                ProductListFragmentController(this, viewModel);
    }

    private void setAdapter() {
        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            ProductListAdapter adapter = new ProductListAdapter(ProductsListFragment.this.getActivity(), R.layout.product_list_layout, products);
            productList.setAdapter(adapter);
        });
    }
    private void setListeners() {
        this.productList.setOnItemClickListener(controller);
    }

    public void errorFetchingProducts() {
        Toast.makeText(this.getContext(), CANT_GET_PRODUCTS_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
