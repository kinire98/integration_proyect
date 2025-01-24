package com.kinire.proyectointegrador.android.controllers.activities;

import android.view.View;

import androidx.annotation.IdRes;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.parcelable_models.ParcelablePurchase;
import com.kinire.proyectointegrador.android.ui.activities.PurchaseViewActivity;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.ShoppingCartItem;
import com.kinire.proyectointegrador.components.User;

import java.util.ArrayList;

/**
 * Clase encargada de controlar el Activity PurchaseView.
 * Su única funcionalidad es la de borrar una compra previamente hecha
 */
public class PurchaseViewActivityController implements View.OnClickListener {

    private final PurchaseViewActivity activity;

    private final @IdRes int deleteButtonId;

    public PurchaseViewActivityController(PurchaseViewActivity activity) {
        this.activity = activity;
        this.deleteButtonId = R.id.delete_button;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == deleteButtonId)
            deletePurchase();
    }

    /**
     * Genera la compra de los datos recibidos y le envía la señal de borrado al servidor.
     * Si se borra con éxito finaliza el Activity, sino muestra un Toast con un mensaje de error
     */
    private void deletePurchase() {
        ParcelablePurchase parcelablePurchase = activity.getPurchase();
        User user = new User();
        user.setUser(parcelablePurchase.getUserName());
        Purchase purchase = new Purchase(parcelablePurchase.getId(), parcelablePurchase.getPurchaseDate(), user, (ArrayList<ShoppingCartItem>) parcelablePurchase.getShoppingCartItems());
        Connection.getInstance().deletePurchase(purchase, activity::finish, e -> {
            activity.error();
        });
    }
}
