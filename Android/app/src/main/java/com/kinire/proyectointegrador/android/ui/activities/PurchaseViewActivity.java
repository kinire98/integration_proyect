package com.kinire.proyectointegrador.android.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.adapters.PurchaseViewAdapter;
import com.kinire.proyectointegrador.android.correct_style_dissonances.StyleDissonancesCorrection;
import com.kinire.proyectointegrador.android.parcelable_models.ParcelablePurchase;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PurchaseViewActivity extends AppCompatActivity {

    private ParcelablePurchase purchase;
    private ListView list;
    private TextView dateField;
    private TextView priceField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_purchase_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.getExtras();
        this.initializeElements();
        this.setAdapter();
        this.setUpToolbar();
        this.setFieldValues();
        StyleDissonancesCorrection.setStatusBarCorrectColor(this);
    }

    private void getExtras() {
        String PURCHASE_PARCELABLE_KEY = getString(R.string.purchase_parcelable_key);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.getParcelable(PURCHASE_PARCELABLE_KEY) != null)
            this.purchase = bundle.getParcelable(PURCHASE_PARCELABLE_KEY);
    }

    private void initializeElements() {
        this.list = findViewById(R.id.list);
        this.dateField = findViewById(R.id.date_field);
        this.priceField = findViewById(R.id.price_field);
    }

    private void setAdapter() {
        PurchaseViewAdapter adapter = new PurchaseViewAdapter(this, R.layout.purchase_view_list_element, purchase.getShoppingCartItems());
        list.setAdapter(adapter);
    }

    private void setFieldValues() {
        this.dateField.setText(purchase.getPurchaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.priceField.setText(String.format(Locale.getDefault(), "%.2f€", purchase.getTotalPrice()));
    }

    private void setUpToolbar() {
        if(getSupportActionBar() != null) {
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
}