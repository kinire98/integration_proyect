package com.kinire.proyectointegrador.android.ui.activities;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.controllers.activities.AddProductActivityController;
import com.kinire.proyectointegrador.android.parcelable_models.ParcelableProduct;
import com.kinire.proyectointegrador.components.Product;

import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {


    private ImageView imageView;
    private TextView productName;
    private TextView productCategory;
    private TextView productPrice;
    private Button minusButton;
    private Button plusButton;
    private EditText productAmount;
    private Button addProductButton;

    private AddProductActivityController controller;

    private String PRODUCT_PARCELABLE_KEY;

    private String IMAGE_PARCELABLE_KEY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.setUpToolbar();
        this.initializeElements();
        this.setProductInformation();
        this.setListeners();
    }

    private void initializeElements() {
        this.PRODUCT_PARCELABLE_KEY = getString(R.string.product_parcelable_key);
        this.IMAGE_PARCELABLE_KEY = getString(R.string.image_parcelable_key);
        this.imageView = findViewById(R.id.product_image);
        this.productName = findViewById(R.id.product_name);
        this.productCategory = findViewById(R.id.product_category);
        this.productPrice = findViewById(R.id.product_price);
        this.minusButton = findViewById(R.id.minus_button);
        this.plusButton = findViewById(R.id.plus_button);
        this.productAmount = findViewById(R.id.product_amount);
        this.addProductButton = findViewById(R.id.add_products_button);
        this.controller = new AddProductActivityController(this);
    }

    private void setProductInformation() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            ParcelableProduct product = bundle.getParcelable(PRODUCT_PARCELABLE_KEY);
            byte[] imageByteArray = bundle.getByteArray(IMAGE_PARCELABLE_KEY);
            this.imageView.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(imageByteArray, 0 , imageByteArray.length)));
            this.productName.setText(product.getName());
            this.productCategory.setText(product.getCategoryName());
            this.productPrice.setText(String.format(Locale.getDefault(), "%.2f€", product.getPrice()));
            controller.setPrice(product.getPrice());
        }
    }

    private void setListeners() {
        this.plusButton.setOnClickListener(controller);
        this.minusButton.setOnClickListener(controller);
        this.addProductButton.setOnClickListener(controller);
    }

    private void setUpToolbar() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
    }

    public void setAmount(int amount) {
        this.productAmount.setText(String.valueOf(amount));
    }
}
