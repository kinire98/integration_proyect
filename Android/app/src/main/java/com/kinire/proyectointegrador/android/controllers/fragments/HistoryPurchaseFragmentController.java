package com.kinire.proyectointegrador.android.controllers.fragments;

import android.view.View;
import android.widget.AdapterView;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.shared_preferences.SharedPreferencesManager;
import com.kinire.proyectointegrador.android.ui.fragments.history.HistoryPurchaseFragment;
import com.kinire.proyectointegrador.android.ui.fragments.history.HistoryPurchaseViewModel;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.User;


public class HistoryPurchaseFragmentController implements AdapterView.OnItemClickListener {

    private HistoryPurchaseFragment fragment;

    private HistoryPurchaseViewModel viewModel;

    public HistoryPurchaseFragmentController(HistoryPurchaseFragment fragment, HistoryPurchaseViewModel viewModel) {
        this.fragment = fragment;
        this.viewModel = viewModel;
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(fragment.requireContext());
        User user = new User();
        user.setUser(sharedPreferencesManager.getUser());
        this.viewModel.setNoPurchasesMessage(fragment.getString(R.string.no_purchase_history));
        Connection.getInstance().getClientPurchases(user, purchases -> {
            fragment.requireActivity().runOnUiThread(() -> {
                this.viewModel.setPurchases(purchases);
            });
        }, e -> {
            fragment.errorConnectionToServer();
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
