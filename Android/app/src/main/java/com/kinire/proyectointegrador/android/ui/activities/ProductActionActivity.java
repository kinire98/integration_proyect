package com.kinire.proyectointegrador.android.ui.activities;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.controllers.activities.ProductActionActivityController;
import com.kinire.proyectointegrador.android.correct_style_dissonances.StyleDissonancesCorrection;
import com.kinire.proyectointegrador.android.image_cache.ImageCache;
import com.kinire.proyectointegrador.android.parcelable_models.ParcelableProduct;
import com.kinire.proyectointegrador.components.Category;
import com.kinire.proyectointegrador.components.Product;

import java.util.Locale;

public class ProductActionActivity extends AppCompatActivity {


    private ImageView imageView;
    private TextView productName;
    private TextView productCategory;
    private TextView productPrice;
    private Button minusButton;
    private Button plusButton;
    private EditText productAmount;
    private Button addProductButton;

    private Product product;

    private ProductActionActivityController controller;

    private String PRODUCT_PARCELABLE_KEY;

    private String AMOUNT_PARCELABLE_KEY;
    private String POSITION_PARCELABLE_KEY;

    private String ACTION_BAR_ADD_TITLE_PREFIX;
    private String ACTION_BAR_CHANGE_TITLE_PREFIX;

    private String CHANGE_PRODUCT_BUTTON_TEXT;
    private String ADD_PRODUCT_BUTTON_TEXT;

    private boolean changeProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_action);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.initializeElements();
        this.setProductInformation();
        this.setListeners();
        StyleDissonancesCorrection.setStatusBarCorrectColor(this);
    }

    private void initializeElements() {
        this.PRODUCT_PARCELABLE_KEY = getString(R.string.product_parcelable_key);
        this.AMOUNT_PARCELABLE_KEY = getString(R.string.amount_parcelable_key);
        this.POSITION_PARCELABLE_KEY = getString(R.string.position_parcelable_key);
        this.imageView = findViewById(R.id.product_image);
        this.productName = findViewById(R.id.product_name);
        this.productCategory = findViewById(R.id.product_category);
        this.productPrice = findViewById(R.id.product_price);
        this.minusButton = findViewById(R.id.minus_button);
        this.plusButton = findViewById(R.id.plus_button);
        this.productAmount = findViewById(R.id.product_amount);
        this.addProductButton = findViewById(R.id.add_products_button);
        this.ACTION_BAR_ADD_TITLE_PREFIX = getString(R.string.add_to_cart_menu_bar);
        this.ACTION_BAR_CHANGE_TITLE_PREFIX = getString(R.string.change_product_menu_bar);
        this.CHANGE_PRODUCT_BUTTON_TEXT = getString(R.string.change_product);
        this.ADD_PRODUCT_BUTTON_TEXT = getString(R.string.add_to_cart);
        this.controller = new ProductActionActivityController(this);
    }

    private void setProductInformation() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            ParcelableProduct product = bundle.getParcelable(PRODUCT_PARCELABLE_KEY);
            this.product = new Product(product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getImagePath(),
                    product.getDate(),
                    new Category(product.getCategoryId(), product.getCategoryName()),
                    null);
            this.imageView.setImageDrawable(ImageCache.getImage(product.getImagePath()));
            this.productName.setText(product.getName());
            this.productCategory.setText(product.getCategoryName());
            this.productPrice.setText(String.format(Locale.getDefault(), "%.2f€", product.getPrice()));
            controller.setPrice(product.getPrice());
            int amount = bundle.getInt(AMOUNT_PARCELABLE_KEY);
            changeProduct = amount >= 1;
            controller.setChangeProduct(changeProduct);
            if(changeProduct) {
                setUpToolbar(ACTION_BAR_CHANGE_TITLE_PREFIX);
                addProductButton.setText(CHANGE_PRODUCT_BUTTON_TEXT);
                controller.setPosition(bundle.getInt(POSITION_PARCELABLE_KEY));
            } else {
                setUpToolbar(ACTION_BAR_ADD_TITLE_PREFIX);
                addProductButton.setText(ADD_PRODUCT_BUTTON_TEXT);
            }
            controller.setAmount(changeProduct ? amount : 1);
        }
    }

    private void setListeners() {
        this.plusButton.setOnClickListener(controller);
        this.minusButton.setOnClickListener(controller);
        this.addProductButton.setOnClickListener(controller);
    }

    private void setUpToolbar(String prefix) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(prefix + " " + this.productName.getText().toString());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setAmount(int amount) {
        this.productAmount.setText(String.valueOf(amount));
    }

    public Product getProduct() {
        return product;
    }
    public int getAmount() {
        if(this.productAmount.getText() == null || this.productAmount.getText().toString().isEmpty())
            return 0;
        assert this.productAmount.getText() != null && !this.productAmount.getText().toString().isEmpty();
        return Integer.parseInt(this.productAmount.getText().toString());
    }
}
