package com.kinire.proyectointegrador.android.controllers.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IdRes;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.ui.activities.MainActivity;
import com.kinire.proyectointegrador.android.ui.fragments.add.AddProductFragment;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase controlador encargada de manejar el fragmento dónde se añaden nuevos productos
 */
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
                    if(result.getResultCode() != Activity.RESULT_OK)
                        return;
                    Intent data = result.getData();
                    if(data == null || data.getData() == null)
                        return;
                    Uri selectedImage = data.getData();
                    try {
                        imageStream = fragment.requireActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
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

    /**
     * Clase encargada de lanzar el activity para que el usuario seleccione una imagen de su galería.
     * Una vez seleccionada, en el ActivityResultLauncher se recibe la URI del elemento y se recoge
     * su flujo de entrada. Al ser algo que el usuario hace voluntaria y conscientemente, no es necesario pedir
     * ningún tipo de permiso especial
     */
    private void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    /**
     * Sube un producto al servidor con todos los datos necesarios. Nombre, precio, categoría, ruta imagen,
     * última modificación y la imagen cómo tal. Se crea el componente Purchase necesario y en envía a través de la red
     * Si la subida es exitosa aparecerá un toast con un mensaje de Producto guardado, sino de error de conexión, en cualquiera
     * de los casos volverá a la vista de los productos
     */
    private void uploadProduct() {
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
                    e.printStackTrace();
                    fragment.productNotSaved();
                }
        );
        returnToProductFragment();
    }
    private void returnToProductFragment() {
        BottomNavigationView navigationView = ((MainActivity) fragment.requireActivity()).findViewById(R.id.nav_view);
        navigationView.setSelectedItemId(R.id.navigation_products_list);
    }

}
