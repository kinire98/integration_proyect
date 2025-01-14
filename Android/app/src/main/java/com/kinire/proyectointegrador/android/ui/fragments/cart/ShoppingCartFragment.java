package com.kinire.proyectointegrador.android.ui.fragments.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kinire.proyectointegrador.android.databinding.FragmentShoppingCartBinding;

public class ShoppingCartFragment extends Fragment {

    private FragmentShoppingCartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShoppingCartViewModel shoppingCartViewModel =
                new ViewModelProvider(this).get(ShoppingCartViewModel.class);

        binding = FragmentShoppingCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textShoppingCart;
        shoppingCartViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
