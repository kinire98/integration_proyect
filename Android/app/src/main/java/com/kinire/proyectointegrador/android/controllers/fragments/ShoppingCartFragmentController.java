package com.kinire.proyectointegrador.android.controllers.fragments;

import android.view.View;
import android.widget.AdapterView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.adapters.ShopingCartAdapter;
import com.kinire.proyectointegrador.android.shared_preferences.SharedPreferencesManager;
import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.fragments.cart.ShoppingCartFragment;
import com.kinire.proyectointegrador.android.ui.fragments.cart.ShoppingCartViewModel;
import com.kinire.proyectointegrador.android.ui.fragments.products.ProductsListFragment;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.ShoppingCartItem;
import com.kinire.proyectointegrador.components.User;

import java.time.LocalDate;

import io.shubh.superiortoastlibrary.SuperiorToast;
import io.shubh.superiortoastlibrary.SuperiorToastWithHeadersPreDesigned;

public class ShoppingCartFragmentController implements AdapterView.OnItemClickListener, View.OnClickListener {

    private final ShoppingCartFragment fragment;

    private final ShoppingCartViewModel viewModel;

    private static Purchase purchase = new Purchase();

    private boolean showPurchasesButtons;

    private final SharedPreferencesManager sharedPreferencesManager;


    public ShoppingCartFragmentController(ShoppingCartFragment fragment, ShoppingCartViewModel viewModel) {
        this.fragment = fragment;
        this.viewModel = viewModel;
        this.viewModel.setData(purchase);
        this.viewModel.setEmptyMessage(fragment.getString(R.string.empty_shopping_cart));
        this.showPurchasesButtons = false;
        this.sharedPreferencesManager = new SharedPreferencesManager(fragment.requireContext());
    }
    public static void addProduct(Product product, int amount) {
        purchase.getShoppingCartItems().add(new ShoppingCartItem(product, amount));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Open activity for changing amount
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.show_shopping_cart_actions)
            showHideButtons();
        else if(v.getId() == R.id.delete_shopping_cart)
            emptyPurchase();
        else if(v.getId() == R.id.save_shopping_cart)
            savePurchase();
    }

    private void showHideButtons() {
        if(showPurchasesButtons) {
            fragment.hidePurchasesButton();
            showPurchasesButtons = false;
        } else {
            fragment.showPurchasesButton();
            showPurchasesButtons = true;
        }
    }
    private void emptyPurchase() {
        showPurchasesButtons = false;
        purchase = new Purchase();
        viewModel.setData(purchase);
        fragment.getAdapter().notifyDataSetChanged();
        fragment.hidePurchasesButton();
        viewModel.forceRefresh();
        changeSelectedMenuItem();
    }

    private void changeSelectedMenuItem() {
        BottomNavigationView navigationView = ((MainActivity) fragment.requireActivity()).findViewById(R.id.nav_view);
        navigationView.setSelectedItemId(R.id.navigation_products_list);
    }
    private void savePurchase() {
        User user = new User();
        user.setUser(sharedPreferencesManager.getUser());
        purchase.setUser(user);
        purchase.setPurchaseDate(LocalDate.now());
        Connection.getInstance().uploadPurchase(purchase, () -> {
            fragment.successSavingPurchase();
            fragment.requireActivity().runOnUiThread(this::emptyPurchase);
        }, e -> fragment.errorSavingPurchase());
    }
}
