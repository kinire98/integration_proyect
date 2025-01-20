package com.kinire.proyectointegrador.android.controllers.activities;

import android.view.View;

import androidx.annotation.IdRes;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.controllers.fragments.ShoppingCartFragmentController;
import com.kinire.proyectointegrador.android.ui.activities.ProductActionActivity;

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

    public void setAmount(int amount) {
        this.amount = amount;
        activity.setAmount(amount);
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setChangeProduct(boolean changeProduct) {
        this.changeProduct = changeProduct;
    }

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
