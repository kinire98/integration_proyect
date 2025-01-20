package com.kinire.proyectointegrador.android.ui.fragments.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.adapters.ShopingCartAdapter;
import com.kinire.proyectointegrador.android.controllers.fragments.ShoppingCartFragmentController;
import com.kinire.proyectointegrador.android.databinding.FragmentShoppingCartBinding;

import java.util.Locale;
import java.util.Objects;

import io.shubh.superiortoastlibrary.SuperiorToastWithHeadersPreDesigned;

public class ShoppingCartFragment extends Fragment {

    private FragmentShoppingCartBinding binding;

    private FloatingActionButton hideShowButton;
    private FloatingActionButton savePurchaseButton;
    private FloatingActionButton emptyPurchaseButton;

    private GridView shoppingList;

    private TextView savePurchaseText;
    private TextView emptyPurchaseText;

    private TextView noProductsText;

    private TextView totalPriceText;

    private ShoppingCartViewModel viewModel;

    private ShoppingCartFragmentController controller;

    private ShopingCartAdapter adapter;


    private String SUCCESS_SAVED_PURCHASE_MESSAGE;
    private String ERROR_SAVED_PURCHASE_MESSAGE;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.viewModel =
                new ViewModelProvider(this).get(ShoppingCartViewModel.class);

        binding = FragmentShoppingCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        this.initalizeElements();
        this.setListeners();
        this.setObservers();
        return root;
    }

    private void initalizeElements() {
        this.ERROR_SAVED_PURCHASE_MESSAGE = getString(R.string.connectivity_error);
        this.hideShowButton = binding.showShoppingCartActions;
        this.savePurchaseButton = binding.saveShoppingCart;
        this.emptyPurchaseButton = binding.deleteShoppingCart;
        this.savePurchaseText = binding.saveShoppingCartDescriptor;
        this.emptyPurchaseText = binding.deleteShoppingCartDescriptor;
        this.noProductsText = binding.shoppingCartListEmpty;
        this.shoppingList = binding.shoppingCartList;
        this.totalPriceText = binding.totalPriceField;
        this.SUCCESS_SAVED_PURCHASE_MESSAGE = getString(R.string.purchase_saved_succesfully);
        this.controller = new ShoppingCartFragmentController(this, viewModel);
        this.controller.updatePrice();
    }

    private void setListeners() {
        this.shoppingList.setEmptyView(noProductsText);
        this.hideShowButton.setOnClickListener(controller);
        this.savePurchaseButton.setOnClickListener(controller);
        this.emptyPurchaseButton.setOnClickListener(controller);
        this.shoppingList.setOnItemClickListener(controller);
    }

    public void setObservers() {
        viewModel.getData().observe(getViewLifecycleOwner(), purchase -> {
            if(adapter == null) {
                adapter = new ShopingCartAdapter(getContext(), R.layout.shopping_cart_item_layout, purchase);
                this.shoppingList.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.getmEmptyMessage().observe(getViewLifecycleOwner(), noProductsText::setText);
    }
    public void hidePurchasesButton() {
        this.savePurchaseButton.hide();
        this.savePurchaseText.setVisibility(View.INVISIBLE);
        this.emptyPurchaseButton.hide();
        this.emptyPurchaseText.setVisibility(View.INVISIBLE);
        this.hideShowButton.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.sort_up_solid));
    }

    public void showPurchasesButton() {
        this.savePurchaseButton.show();
        this.savePurchaseText.setVisibility(View.VISIBLE);
        this.emptyPurchaseButton.show();
        this.emptyPurchaseText.setVisibility(View.VISIBLE);
        this.hideShowButton.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.sort_down_solid));
    }

    public ShopingCartAdapter getAdapter() {
        return adapter;
    }

    public void successSavingPurchase() {
        requireActivity().runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(requireContext().getApplicationContext()
                            ,SuperiorToastWithHeadersPreDesigned.SUCCESS_TOAST)
                    .setToastHeaderText(SUCCESS_SAVED_PURCHASE_MESSAGE)
                    .show();
        });
    }

    public void errorSavingPurchase() {
        requireActivity().runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(requireContext().getApplicationContext()
                            ,SuperiorToastWithHeadersPreDesigned.ERROR_TOAST)
                    .setToastHeaderText(ERROR_SAVED_PURCHASE_MESSAGE)
                    .show();
        });
    }

    public void setTotalPriceText(float price) {
        if(price == 0.0f) {
            this.totalPriceText.setText(null);
        } else {
            this.totalPriceText.setText(String.format(Locale.getDefault(), "%.2f€", price));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
