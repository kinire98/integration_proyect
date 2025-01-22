package com.kinire.proyectointegrador.android.controllers.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IdRes;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.ui.fragments.add.AddProductFragment;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddProductFragmentController implements View.OnClickListener {

    private AddProductFragment fragment = null;

    private @IdRes int imageButtonId;
    private @IdRes int uploadProductId;

    private ActivityResultLauncher<Intent> launcher;

    private InputStream imageStream;

    public AddProductFragmentController(AddProductFragment fragment) {
        this.fragment = fragment;
        this.imageButtonId = R.id.image_button;
        this.uploadProductId = R.id.upload_button;
        this.launcher = fragment.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    System.out.println("Aqui1");
                    if(result.getResultCode() != Activity.RESULT_OK)
                        return;
                    System.out.println("Aqui2");
                    Intent data = result.getData();
                    if(data == null || data.getData() == null)
                        return;
                    System.out.println("Aqui3");
                    Uri selectedImage = data.getData();
                    try {
                        System.out.println("Aqui4");
                        imageStream = fragment.requireActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        System.out.println("Aqui5");
                        fragment.error();
                    }
                }
        );
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == imageButtonId)
            getImage();
        else if(v.getId() == uploadProductId)
            uploadProduct();
    }

    public void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }
    public void uploadProduct() {
        String name = fragment.getNameField();
        String category = fragment.getCategoryField();
        float price = fragment.getPriceField();
        if(
                name == null || name.isEmpty() ||
                        category == null || category.isEmpty() ||
                        price == 0.0f || imageStream == null
        ) {
            fragment.dataNotSelected();
            return;
        }
        Product product = new Product();
        String imageName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("GyyyyddMMHHmmssAAAAnnnnnnn"));
        product.setName(name);
        product.getCategory().setName(category);
        product.setPrice(price);
        product.setImagePath(imageName);
        product.setLastModified(LocalDate.now());
        Connection.getInstance().updateProduct(
                product,
                imageStream,
                () -> {
                    fragment.productSavedSuccesfully();
                },
                e -> {
                    fragment.productNotSaved();
                }
        );
    }

}
