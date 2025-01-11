package com.kinire.android.ui.fragments.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kinire.android.databinding.FragmentProductsListBinding;

public class ProductsListFragment extends Fragment {

    private FragmentProductsListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProductsListViewModel productsListViewModel =
                new ViewModelProvider(this).get(ProductsListViewModel.class);

        binding = FragmentProductsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProductsList;
        productsListViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}