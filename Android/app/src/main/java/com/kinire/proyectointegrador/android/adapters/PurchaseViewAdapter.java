package com.kinire.proyectointegrador.android.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.image_cache.ImageCache;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.ShoppingCartItem;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Locale;

public class PurchaseViewAdapter extends BaseAdapter {

    private Context context;
    private @LayoutRes int layout;
    private List<ShoppingCartItem> data;

    public PurchaseViewAdapter(Context context, @LayoutRes int layout, List<ShoppingCartItem> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder;
        if(v == null) {
            holder = new ViewHolder();
            v = LayoutInflater.from(context).inflate(layout, null);
            holder.imageField = v.findViewById(R.id.image_field);
            holder.nameField = v.findViewById(R.id.name_field);
            holder.unitPriceField = v.findViewById(R.id.unit_price_field);
            holder.amountField = v.findViewById(R.id.amount_field);
            holder.totalPriceField = v.findViewById(R.id.total_price_field);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        ShoppingCartItem item = data.get(position);
        Product product = item.getProduct();
        holder.nameField.setText(product.getName());
        holder.unitPriceField.setText(String.format(Locale.getDefault(), "%.2f€", product.getPrice()));
        holder.amountField.setText(String.valueOf(item.getAmount()));
        holder.totalPriceField.setText(String.format(Locale.getDefault(), "%.2f€", item.getPrice()));
        holder.imageField.setImageDrawable(ImageCache.getImage(product.getImagePath()));
        return v;
    }
    private static class ViewHolder {
        private ImageView imageField;
        private TextView nameField;
        private TextView unitPriceField;
        private TextView amountField;
        private TextView totalPriceField;
    }
}
