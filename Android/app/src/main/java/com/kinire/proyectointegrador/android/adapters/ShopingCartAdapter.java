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
import com.kinire.proyectointegrador.android.images.Image;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Purchase;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShopingCartAdapter extends BaseAdapter {

    private @LayoutRes int layout;

    private Context context;

    private Purchase data;

    private final Logger logger = Logger.getLogger(ShopingCartAdapter.class.getName());

    public ShopingCartAdapter(Context context, @LayoutRes int layout, Purchase data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.getShoppingCartItems().size();
    }

    @Override
    public Object getItem(int position) {
        return data.getShoppingCartItems().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder;
        if(v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.shopping_cart_item_layout, null);
            holder = new ViewHolder();
            holder.imageView = v.findViewById(R.id.image);
            holder.productAmount = v.findViewById(R.id.product_amount);
            holder.productName = v.findViewById(R.id.product_name);
            holder.productPrice = v.findViewById(R.id.product_price);
            askForImage(position, holder);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
            askForImage(position, holder);
        }
        holder.imageView.setImageDrawable(holder.image);
        holder.productName.setText(data.getShoppingCartItems().get(position).getProduct().getName());
        holder.productAmount.setText(String.valueOf(data.getShoppingCartItems().get(position).getAmount()));
        holder.productPrice.setText(String.format(Locale.getDefault(), "%.2f€", data.getShoppingCartItems().get(position).getPrice()));
        return v;
    }
    private void askForImage(int position, ViewHolder holder) {
        if(holder.image != null)
            return;
        Image.getImage(data.getShoppingCartItems().get(position).getProduct().getImagePath(), (drawable) -> {
            holder.image = drawable;
            ((AppCompatActivity) context).runOnUiThread(() -> holder.imageView.setImageDrawable(holder.image));
        }, (e) -> {
            logger.log(Level.SEVERE, "Error while loading image");
            ((AppCompatActivity) context).runOnUiThread(() -> holder.imageView.setImageDrawable(holder.image));
        });
    }

    private static class ViewHolder {
        private ImageView imageView;
        private Drawable image;
        private TextView productName;
        private TextView productPrice;
        private TextView productAmount;

    }
}
