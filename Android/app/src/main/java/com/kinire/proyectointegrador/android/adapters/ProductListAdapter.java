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

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class ProductListAdapter extends BaseAdapter {

    private Context context;

    private @LayoutRes int layout;

    private List<Product> data;

    private Logger logger = Logger.getLogger(ProductListAdapter.class.getName());

    public ProductListAdapter(Context context, @LayoutRes int layout, List<Product> data) {
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.productField = view.findViewById(R.id.name_field);
            holder.categoryField = view.findViewById(R.id.category_field);
            holder.priceField = view.findViewById(R.id.price_field);
            holder.imageView = view.findViewById(R.id.image_field);
            logger.log(Level.INFO, "Asking for image");
            Connection.getInstance().getImage(data.get(position).getImagePath(), (stream) -> {
                logger.log(Level.SEVERE, "Asking for image");
                holder.image = Drawable.createFromStream(stream, "remote");
                ((AppCompatActivity) context).runOnUiThread(() -> holder.imageView.setImageDrawable(holder.image));
            }, (e) -> {
                logger.log(Level.SEVERE, "Error while loading image");
            });
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.imageView.setImageDrawable(holder.image);
        holder.productField.setText(data.get(position).getName());
        holder.categoryField.setText(data.get(position).getCategory().getName());
        holder.priceField.setText(String.format(Locale.getDefault(),"%.2f€", data.get(position).getPrice()));
        return view;
    }
    private static class ViewHolder {
        private TextView productField;
        private TextView categoryField;
        private TextView priceField;
        private ImageView imageView;
        private Drawable image;
    }
}
