package com.kinire.android.ui.fragments.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kinire.android.databinding.FragmentHistoryPurchaseBinding;

public class HistoryPurchaseFragment extends Fragment {

    private FragmentHistoryPurchaseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistoryPurchaseViewModel historyPurchaseViewModel =
                new ViewModelProvider(this).get(HistoryPurchaseViewModel.class);

        binding = FragmentHistoryPurchaseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHistoryPurchase;
        historyPurchaseViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}