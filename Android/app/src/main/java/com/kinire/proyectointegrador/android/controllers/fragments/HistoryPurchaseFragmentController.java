package com.kinire.proyectointegrador.android.controllers.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.parcelable_models.ParcelablePurchase;
import com.kinire.proyectointegrador.android.ui.activities.PurchaseViewActivity;
import com.kinire.proyectointegrador.android.ui.fragments.history.HistoryPurchaseFragment;
import com.kinire.proyectointegrador.android.ui.fragments.history.HistoryPurchaseViewModel;
import com.kinire.proyectointegrador.android.utils.UserAdmin;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.User;

/**
 * Controlador del fragmento del historial de compras.
 * Este se encargará de recibir todas las compras que ha realizado el usuario anteriormente y mostrarlas.
 * Si el usuario es administrador le enseñará todas las compras hechas.
 */
public class HistoryPurchaseFragmentController implements AdapterView.OnItemClickListener {

    private HistoryPurchaseFragment fragment;

    private HistoryPurchaseViewModel viewModel;

    private final String PURCHASE_PARCELABLE_KEY;

    public HistoryPurchaseFragmentController(HistoryPurchaseFragment fragment, HistoryPurchaseViewModel viewModel) {
        this.fragment = fragment;
        this.viewModel = viewModel;
        this.viewModel.setNoPurchasesMessage(fragment.getString(R.string.no_purchase_history));
        this.PURCHASE_PARCELABLE_KEY = fragment.getString(R.string.purchase_parcelable_key);
        initConnection();
    }
    private void initConnection() {
        UserAdmin userAdmin = new UserAdmin(fragment.requireContext());
        User user = userAdmin.getUser();
        if(user.isAdmin()) {
            Connection.getInstance().getAllPurchases(purchases -> {
                fragment.requireActivity().runOnUiThread(() -> {
                    this.viewModel.setPurchases(purchases);
                });
            }, e -> fragment.errorConnectionToServer());
        } else {
            Connection.getInstance().getClientPurchases(user, purchases -> {
                fragment.requireActivity().runOnUiThread(() -> {
                    this.viewModel.setPurchases(purchases);
                });
            }, e -> {
                fragment.errorConnectionToServer();
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Purchase purchase = viewModel.getPurchases().getValue().get(position);
        Intent intent = new Intent(fragment.requireActivity(), PurchaseViewActivity.class);
        intent.putExtra(PURCHASE_PARCELABLE_KEY, new ParcelablePurchase(purchase));
        fragment.requireActivity().startActivity(intent);
    }
}
