package com.kinire.proyectointegrador.android.controllers.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.parcelable_models.ParcelableProduct;
import com.kinire.proyectointegrador.android.ui.activities.ProductActionActivity;
import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.fragments.cart.ShoppingCartFragment;
import com.kinire.proyectointegrador.android.ui.fragments.cart.ShoppingCartViewModel;
import com.kinire.proyectointegrador.android.utils.UserAdmin;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.ShoppingCartItem;
import com.kinire.proyectointegrador.components.User;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de controlar el carrito de la compra
 */
public class ShoppingCartFragmentController implements AdapterView.OnItemClickListener, View.OnClickListener {

    @SuppressLint("StaticFieldLeak")
    private static ShoppingCartFragment fragment;

    private static ShoppingCartViewModel viewModel;

    private static Purchase purchase = new Purchase();

    private boolean showPurchasesButtons;

    private final UserAdmin userAdmin;

    private final String PRODUCT_PARCELABLE_KEY;

    private final String AMOUNT_PARCELABLE_KEY;

    private final String POSITION_PARCELABLE_KEY;

    private final static Logger logger = Logger.getLogger(ShoppingCartFragmentController.class.getName());

    public ShoppingCartFragmentController(ShoppingCartFragment fragment, ShoppingCartViewModel viewModel) {
        ShoppingCartFragmentController.fragment = fragment;
        ShoppingCartFragmentController.viewModel = viewModel;
        ShoppingCartFragmentController.viewModel.setData(purchase);
        ShoppingCartFragmentController.viewModel.setEmptyMessage(fragment.getString(R.string.empty_shopping_cart));
        this.showPurchasesButtons = false;
        this.userAdmin = new UserAdmin(fragment.requireContext());
        this.PRODUCT_PARCELABLE_KEY = fragment.getString(R.string.product_parcelable_key);
        this.AMOUNT_PARCELABLE_KEY = fragment.getString(R.string.amount_parcelable_key);
        this.POSITION_PARCELABLE_KEY = fragment.getString(R.string.position_parcelable_key);
    }

    /**
     * Permite añadir un producto a la compra desde cualquier punto del programa que sea necesario
     * @param product El producto que se va a comprar
     * @param amount La cantidad de producto
     */
    public static void addProduct(Product product, int amount) {
        purchase.getShoppingCartItems().add(new ShoppingCartItem(product, amount));
    }

    /**
     * Permite modificar un producto del carrito desde cualquier punto del programa
     * @param product El producto a cambiar
     * @param amount La cantidad de producto
     * @param position La posición que ocupa en la compra
     */
    public static void changeProduct(Product product, int amount, int position) {
        purchase.getShoppingCartItems().set(position, new ShoppingCartItem(product, amount));
    }

    /**
     * Método que actualiza el precio total de la compra.
     * Hay que llamarlo con cuidado ya que sino se ha inicializado ni una sola vez el fragmento del carrito
     * de la compra, no tendrá el resultado esperado
     */
    public static void updatePrice() {
        if(fragment != null)
            fragment.setTotalPriceText(purchase.getTotalPrice());
    }

    /**
     * Comprueba si el producto ya existe en la compra
     * @param product El produto a comprobar
     * @return Si el producto existe o no
     */
    public static boolean productExists(Product product) {
        for (ShoppingCartItem item : purchase.getShoppingCartItems()) {
            if(item.getProduct().getId() == product.getId())
                return true;
        }
        return false;
    }

    /**
     * Devuelve la cantidad de producto que hay guardada en caso de que el producto exista
     * @param product El prooducto a conseguir la cantidad
     * @return La cantidad. -1 si no existe
     */
    public static int getAmount(Product product) {
        for (ShoppingCartItem item : purchase.getShoppingCartItems()) {
            if(item.getProduct().getId() == product.getId())
                return item.getAmount();
        }
        return -1;
    }

    /**
     * Devuelve la posicion en la compra del producto entregado
     * @param product El producto a comprobar la posicion
         * @return La posicion en la que se encuentra. -1 sino se encuentra en la lista
     */
    public static int getPosition(Product product) {
        for (int i = 0; i < purchase.getShoppingCartItems().size(); i++) {
            if(purchase.getShoppingCartItems().get(i).getProduct().getId() == product.getId())
                return i;
        }
        return -1;
    }

    /**
     * Vacía la compra, creando una nueva.
     * Este método se llama desde la vista de usuario para evitar que el carrito de un usuario pase
     * hacia otro
     */
    public static void emptyCart() {
        purchase = new Purchase();
    }

    /**
     * Inicia el Activity para editar los productos
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(((ImageView) view.findViewById(R.id.image)).getDrawable() == null)
            return;
        Intent intent = new Intent(fragment.requireActivity(), ProductActionActivity.class);
        intent.putExtra(PRODUCT_PARCELABLE_KEY, new ParcelableProduct(purchase.getShoppingCartItems().get(position).getProduct()));
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

    /**
     * Este método se encarga de actualizar los productos que se muestran en la vista del carrito
     */
    public static void refreshData() {
        if(viewModel != null)
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
        changeSelectedMenuItem();
        if(purchase.getShoppingCartItems().isEmpty())
            return;
        showPurchasesButtons = false;
        purchase = new Purchase();
        viewModel.setData(purchase);
        fragment.getAdapter().notifyDataSetChanged();
        fragment.hidePurchasesButton();
        viewModel.forceRefresh();
    }

    private void changeSelectedMenuItem() {
        BottomNavigationView navigationView = ((MainActivity) fragment.requireActivity()).findViewById(R.id.nav_view);
        navigationView.setSelectedItemId(R.id.navigation_products_list);
    }
    private void savePurchase() {
        if(purchase.getShoppingCartItems().isEmpty()) {
            changeSelectedMenuItem();
            return;
        }
        logger.log(Level.SEVERE, "Saving purchase");
        User user = userAdmin.getUser();
        purchase.setUser(user);
        purchase.setPurchaseDate(LocalDate.now());
        Connection.getInstance().uploadPurchase(purchase, () -> {
            fragment.successSavingPurchase();
            fragment.requireActivity().runOnUiThread(this::emptyPurchase);
        }, e -> fragment.errorSavingPurchase());
    }
}
