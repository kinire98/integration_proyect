package com.kinire.proyectointegrador.android.controllers.activities;

import android.view.View;

import androidx.annotation.IdRes;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.ui.activities.AddProductActivity;

public class AddProductActivityController implements View.OnClickListener {

    private AddProductActivity activity;

    private final @IdRes int plusButtonId;
    private final @IdRes int minusButtonId;
    private final @IdRes int addProductButtonId;

    private int amount;

    private float price;

    public AddProductActivityController(AddProductActivity activity) {
        this.activity = activity;
        this.plusButtonId = R.id.plus_button;
        this.minusButtonId = R.id.minus_button;
        this.addProductButtonId = R.id.add_products_button;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        activity.setAmount(amount);
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
    private void addProduct() {}


    @Override
    public void onClick(View v) {
        if(v.getId() == plusButtonId)
            addOne();
        else if(v.getId() == minusButtonId)
            substractOne();
        else if(v.getId() == addProductButtonId)
            addProduct();

    }
}
