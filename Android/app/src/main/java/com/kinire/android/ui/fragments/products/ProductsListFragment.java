package com.kinire.android.ui.fragments.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kinire.android.controllers.fragments.ProductListFragmentController;
import com.kinire.android.databinding.FragmentProductsListBinding;

public class ProductsListFragment extends Fragment {

    private FragmentProductsListBinding binding;

    private ListView productList;

    private ProductListFragmentController controller;

    private ProductsListViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.viewModel =
                new ViewModelProvider(this).get(ProductsListViewModel.class);

        binding = FragmentProductsListBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    private void getElements() {
        this.productList = binding.productsList;
        this.controller = new
                ProductListFragmentController(viewModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}