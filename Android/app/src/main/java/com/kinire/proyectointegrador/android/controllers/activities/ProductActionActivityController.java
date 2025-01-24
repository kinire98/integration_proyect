package com.kinire.proyectointegrador.android.controllers.activities;

import android.view.View;

import androidx.annotation.IdRes;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.controllers.fragments.ShoppingCartFragmentController;
import com.kinire.proyectointegrador.android.ui.activities.ProductActionActivity;

/**
 * Clase encargada de controlar el Activity para gestionar los productos de la compra.
 * Se puede iniciar de dos maneras, pinchando en la lista general de productos para añadirlo al carrito
 * o en la cuadrícula del carrito para cambiar la cantidad de un producto.
 *
 */
public class ProductActionActivityController implements View.OnClickListener {

    private ProductActionActivity activity;

    private final @IdRes int plusButtonId;
    private final @IdRes int minusButtonId;
    private final @IdRes int addProductButtonId;

    private int amount;

    private float price;

    private boolean changeProduct;

    private int position;

    public ProductActionActivityController(ProductActionActivity activity) {
        this.activity = activity;
        this.plusButtonId = R.id.plus_button;
        this.minusButtonId = R.id.minus_button;
        this.addProductButtonId = R.id.add_products_button;
    }

    /**
     * Utilizado para establecer la cantidad inicial de producto.
     * Si se recibe desde la lista de productos será 0.
     * Si se recibe desde el carrito será la cantidad anteriormente seleccionadad por el usuario
     * @param amount La cantidad de producto con la que se inicia el activity
     */
    public void setAmount(int amount) {
        this.amount = amount;
        activity.setAmount(amount);
    }

    /**
     * Setter para el precio del producto
     * @param price Precio del producto
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Setter para establecer si el producto es nuevo en el carrito o si se va a editar su cantidad
     * @param changeProduct True si se va a editar, false si es nuevo
     */
    public void setChangeProduct(boolean changeProduct) {
        this.changeProduct = changeProduct;
    }

    /**
     * Setter para la posición que ocupa el producto a editar en el carrito.
     * Este método solo se llamará en caso de que se esté editando el producto.
     * @param position La posición que ocupa el producto en el carrito
     */
    public void setPosition(int position) {
        this.position = position;
    }

    private void addOne() {
        if(amount < 1 || amount * price < 10000)
            amount++;
        activity.setAmount(amount);
    }

    private void substractOne() {
        if(amount > 1)
            amount--;
        activity.setAmount(amount);
    }
    private void productAction() {
        if(changeProduct) {
            ShoppingCartFragmentController.changeProduct(activity.getProduct(), amount, position);
            activity.finish();
            ShoppingCartFragmentController.refreshData();
            ShoppingCartFragmentController.updatePrice();
        } else {
            ShoppingCartFragmentController.addProduct(activity.getProduct(), amount);
            activity.finish();
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == plusButtonId)
            addOne();
        else if(v.getId() == minusButtonId)
            substractOne();
        else if(v.getId() == addProductButtonId)
            productAction();

    }
}
