package com.kinire.proyectointegrador.android.controllers.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.parcelable_models.ParcelableProduct;
import com.kinire.proyectointegrador.android.shared_preferences.SharedPreferencesManager;
import com.kinire.proyectointegrador.android.ui.activities.ProductActionActivity;
import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.fragments.cart.ShoppingCartFragment;
import com.kinire.proyectointegrador.android.ui.fragments.cart.ShoppingCartViewModel;
import com.kinire.proyectointegrador.android.utils.ImageCompression;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.ShoppingCartItem;
import com.kinire.proyectointegrador.components.User;

import java.time.LocalDate;

public class ShoppingCartFragmentController implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static ShoppingCartFragment fragment;

    private static ShoppingCartViewModel viewModel;

    private static Purchase purchase = new Purchase();

    private boolean showPurchasesButtons;

    private final SharedPreferencesManager sharedPreferencesManager;

    private final String PRODUCT_PARCELABLE_KEY;

    private final String IMAGE_PARCELABLE_KEY;

    private final String AMOUNT_PARCELABLE_KEY;

    private final String POSITION_PARCELABLE_KEY;

    public ShoppingCartFragmentController(ShoppingCartFragment fragment, ShoppingCartViewModel viewModel) {
        this.fragment = fragment;
        ShoppingCartFragmentController.viewModel = viewModel;
        ShoppingCartFragmentController.viewModel.setData(purchase);
        ShoppingCartFragmentController.viewModel.setEmptyMessage(fragment.getString(R.string.empty_shopping_cart));
        this.showPurchasesButtons = false;
        this.sharedPreferencesManager = new SharedPreferencesManager(fragment.requireContext());
        this.PRODUCT_PARCELABLE_KEY = fragment.getString(R.string.product_parcelable_key);
        this.IMAGE_PARCELABLE_KEY = fragment.getString(R.string.image_parcelable_key);
        this.AMOUNT_PARCELABLE_KEY = fragment.getString(R.string.amount_parcelable_key);
        this.POSITION_PARCELABLE_KEY = fragment.getString(R.string.position_parcelable_key);
    }
    public static void addProduct(Product product, int amount) {
        purchase.getShoppingCartItems().add(new ShoppingCartItem(product, amount));
    }
    public static void changeProduct(Product product, int amount, int position) {
        purchase.getShoppingCartItems().set(position, new ShoppingCartItem(product, amount));
    }

    /**
     * Este metodo debe ser llamado con MUCHO cuidado.
     * Se tiene que haber asegurado que se haya abierto el fragmento del carrito por lo menos una vez
     */
    public static void updatePrice() {
        fragment.setTotalPriceText(purchase.getTotalPrice());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(((ImageView) view.findViewById(R.id.image)).getDrawable() == null)
            return;
        Intent intent = new Intent(fragment.requireActivity(), ProductActionActivity.class);
        intent.putExtra(PRODUCT_PARCELABLE_KEY, new ParcelableProduct(purchase.getShoppingCartItems().get(position).getProduct()));
        intent.putExtra(IMAGE_PARCELABLE_KEY, ImageCompression.compressImage(((ImageView) view.findViewById(R.id.image)).getDrawable()));;
        intent.putExtra(AMOUNT_PARCELABLE_KEY, purchase.getShoppingCartItems().get(position).getAmount());
        intent.putExtra(POSITION_PARCELABLE_KEY, position);
        fragment.requireActivity().startActivity(intent);
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

    public static void refreshData() {
        viewModel.setData(purchase);
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
