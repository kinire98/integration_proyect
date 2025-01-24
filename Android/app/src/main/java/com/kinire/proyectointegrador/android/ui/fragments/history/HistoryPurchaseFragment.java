package com.kinire.proyectointegrador.android.ui.fragments.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.adapters.PurchaseHistoryAdapter;
import com.kinire.proyectointegrador.android.controllers.fragments.HistoryPurchaseFragmentController;
import com.kinire.proyectointegrador.android.databinding.FragmentHistoryPurchaseBinding;
import com.kinire.proyectointegrador.client.Connection;


import io.shubh.superiortoastlibrary.SuperiorToastWithHeadersPreDesigned;

/**
 * Fragmento encargado de mostrar el historial de compras
 */
public class HistoryPurchaseFragment extends Fragment {

    private FragmentHistoryPurchaseBinding binding;

    private HistoryPurchaseViewModel viewModel;

    private ListView purchasesHistoryList;

    private TextView noPurchaseHistoryText;

    private HistoryPurchaseFragmentController controller;

    private String SERVER_NOT_CONNECTED;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(HistoryPurchaseViewModel.class);

        binding = FragmentHistoryPurchaseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        this.initalizeElements();
        this.setObservers();
        this.setListeners();
        return root;
    }

    private void initalizeElements() {
        this.purchasesHistoryList = binding.historyPurchaseList;
        this.noPurchaseHistoryText = binding.historyPurchaseListEmpty;
        this.purchasesHistoryList.setEmptyView(noPurchaseHistoryText);
        this.SERVER_NOT_CONNECTED = getString(R.string.connectivity_error);
        this.controller = new HistoryPurchaseFragmentController(this, viewModel);
    }

    private void setObservers() {
        viewModel.getNoPurchasesMessage().observe(getViewLifecycleOwner(), noPurchaseHistoryText::setText);
        viewModel.getPurchases().observe(getViewLifecycleOwner(), purchases -> {
            PurchaseHistoryAdapter adapter = new PurchaseHistoryAdapter(this.requireContext(), R.layout.purchase_history_list_item, purchases);
            if(purchases.get(0) != null)
                this.purchasesHistoryList.setAdapter(adapter);
        });
    }

    private void setListeners() {
        this.purchasesHistoryList.setOnItemClickListener(controller);
    }

    /**
     * Muestra un Toast de error cuándo haya un error de conexión con el servidor
     */
    public void errorConnectionToServer() {
        requireActivity().runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(requireContext(),
                            SuperiorToastWithHeadersPreDesigned.ERROR_TOAST)
                    .setToastHeaderText(SERVER_NOT_CONNECTED)
                    .show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
