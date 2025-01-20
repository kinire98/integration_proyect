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

import com.kinire.proyectointegrador.android.controllers.fragments.HistoryPurchaseFragmentController;
import com.kinire.proyectointegrador.android.databinding.FragmentHistoryPurchaseBinding;

public class HistoryPurchaseFragment extends Fragment {

    private FragmentHistoryPurchaseBinding binding;

    private HistoryPurchaseViewModel viewModel;

    private ListView purchasesHistoryList;

    private TextView noPurchaseHistoryText;

    private HistoryPurchaseFragmentController controller;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(HistoryPurchaseViewModel.class);

        binding = FragmentHistoryPurchaseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        this.initalizeElements();
        this.setObservers();
        return root;
    }

    private void initalizeElements() {
        this.purchasesHistoryList = binding.historyPurchaseList;
        this.noPurchaseHistoryText = binding.historyPurchaseListEmpty;
        this.purchasesHistoryList.setEmptyView(noPurchaseHistoryText);
        this.controller = new HistoryPurchaseFragmentController(this, viewModel);
    }

    private void setObservers() {
        viewModel.getNoPurchasesMessage().observe(getViewLifecycleOwner(), noPurchaseHistoryText::setText);
        viewModel.getPurchases().observe(getViewLifecycleOwner(), purchases -> {});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
