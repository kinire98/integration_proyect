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
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;

import java.util.List;

import io.shubh.superiortoastlibrary.SuperiorToastWithHeadersPreDesigned;

public class ProductsListFragment extends Fragment {


    private FragmentProductsListBinding binding;

    private ListView productList;

    private ProductListFragmentController controller;

    private ProductsListViewModel viewModel;

    private ProductListAdapter adapter;

    private String ERROR_SAVED_PURCHASE_MESSAGE;
    private String CONNECTIVITY_LOST;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.viewModel =
                new ViewModelProvider(this).get(ProductsListViewModel.class);

        binding = FragmentProductsListBinding.inflate(inflater, container, false);
        binding.getRoot().setOnApplyWindowInsetsListener((v, insets) -> {
            // Consume the system window insets so they don't add margins
            return insets.consumeSystemWindowInsets();
        });
        this.initalizeElements();
        this.setAdapter();
        this.setListeners();
        return binding.getRoot();
    }

    private void initalizeElements() {
        this.ERROR_SAVED_PURCHASE_MESSAGE = getString(R.string.error_occured);
        this.CONNECTIVITY_LOST = getString(R.string.connectivity_error);
        this.productList = binding.productsList;
        this.controller = new
                ProductListFragmentController(this, viewModel);
    }

    private void setAdapter() {
        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            if(adapter == null) {
                ProductListAdapter adapter = new ProductListAdapter(requireContext(), R.layout.product_list_layout, products);
                productList.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void setListeners() {
        this.productList.setOnItemClickListener(controller);
    }

    public void error() {
        requireActivity().runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(requireContext().getApplicationContext()
                            ,SuperiorToastWithHeadersPreDesigned.ERROR_TOAST)
                    .setToastHeaderText(ERROR_SAVED_PURCHASE_MESSAGE)
                    .show();
        });
    }
    public void errorConnectionLost() {
        requireActivity().runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(requireContext().getApplicationContext()
                            ,SuperiorToastWithHeadersPreDesigned.ERROR_TOAST)
                    .setToastHeaderText(CONNECTIVITY_LOST)
                    .show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
