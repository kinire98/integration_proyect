package com.kinire.proyectointegrador.android.ui.fragments.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.controllers.fragments.AddProductFragmentController;
import com.kinire.proyectointegrador.android.databinding.FragmentAddProductBinding;

import java.util.Locale;

import io.shubh.superiortoastlibrary.SuperiorToastWithHeadersPreDesigned;

public class AddProductFragment extends Fragment {

    private FragmentAddProductBinding binding;

    private AddProductFragmentController controller;

    private Button imageButton;
    private Button uploadButton;
    private EditText priceField;
    private EditText nameField;
    private EditText categoryField;

    private String PRODUCT_SAVED_SUCCESFULLY;
    private String IMAGE_SAVED_SUCCESFULLY;
    private String IMAGE_NOT_SAVED;
    private String PRODUCT_NOT_SAVED;
    private String EMPTY_FIELDS;
    private String ERROR;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.initializeElements();
        this.setListeners();
        return root;
    }

    private void initializeElements() {
        this.imageButton = binding.imageButton;
        this.nameField = binding.nameField;
        this.priceField = binding.priceField;
        this.uploadButton = binding.uploadButton;
        this.categoryField = binding.categoryField;
        this.PRODUCT_SAVED_SUCCESFULLY = getString(R.string.product_save);
        this.IMAGE_SAVED_SUCCESFULLY = getString(R.string.image_saved);
        this.IMAGE_NOT_SAVED = getString(R.string.image_not_saved);
        this.PRODUCT_NOT_SAVED = getString(R.string.product_not_save);
        this.EMPTY_FIELDS = getString(R.string.empty_fields);
        this.ERROR = getString(R.string.error);
        this.controller = new AddProductFragmentController(this);
    }

    private void setListeners() {
        imageButton.setOnClickListener(controller);
        uploadButton.setOnClickListener(controller);
    }

    public String getNameField() {
        return nameField.getText().toString();
    }

    public String getCategoryField() {
        return categoryField.getText().toString();
    }

public float getPriceField() {
        if(priceField.getText().toString().isEmpty())
            return 0.0f;
        return Float.parseFloat(priceField.getText().toString());
    }

    public void error() {
        requireActivity().runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(requireContext().getApplicationContext()
                            ,SuperiorToastWithHeadersPreDesigned.ERROR_TOAST)
                    .setToastHeaderText(ERROR)
                    .show();
        });
    }

    public void dataNotSelected() {
        requireActivity().runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(requireContext().getApplicationContext()
                            ,SuperiorToastWithHeadersPreDesigned.ERROR_TOAST)
                    .setToastHeaderText(EMPTY_FIELDS)
                    .show();
        });
    }
    public void productSavedSuccesfully() {
        requireActivity().runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(requireContext().getApplicationContext()
                            ,SuperiorToastWithHeadersPreDesigned.SUCCESS_TOAST)
                    .setToastHeaderText(PRODUCT_SAVED_SUCCESFULLY)
                    .show();
        });
    }
    public void imageSavedSuccesfully() {
        requireActivity().runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(requireContext().getApplicationContext()
                            ,SuperiorToastWithHeadersPreDesigned.SUCCESS_TOAST)
                    .setToastHeaderText(IMAGE_SAVED_SUCCESFULLY)
                    .show();
        });
    }
    public void productNotSaved() {
        requireActivity().runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(requireContext().getApplicationContext()
                            ,SuperiorToastWithHeadersPreDesigned.ERROR_TOAST)
                    .setToastHeaderText(PRODUCT_NOT_SAVED)
                    .show();
        });
    }
    public void imageNotSaved() {
        requireActivity().runOnUiThread(() -> {
            SuperiorToastWithHeadersPreDesigned.makeSuperiorToast(requireContext().getApplicationContext()
                            ,SuperiorToastWithHeadersPreDesigned.ERROR_TOAST)
                    .setToastHeaderText(IMAGE_NOT_SAVED)
                    .show();
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
