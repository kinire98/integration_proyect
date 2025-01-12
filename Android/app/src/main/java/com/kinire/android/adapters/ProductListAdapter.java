package com.kinire.android.adapters;

import android.annotation.SuppressLint;
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

import com.kinire.android.R;
import com.kinire.client.Connection;
import com.kinire.models.Product;

import java.util.List;
import java.util.Locale;


public class ProductListAdapter extends BaseAdapter {

    private Context context;

    private @LayoutRes int layout;

    private List<Product> data;

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
            new Thread(() -> {
                holder.image = Drawable.createFromStream(Connection.getInstance().getImage(data.get(position).imagePath()), "remote");
                ((AppCompatActivity) context).runOnUiThread(() -> holder.imageView.setImageDrawable(holder.image));
            }).start();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.imageView.setImageDrawable(holder.image);
        holder.productField.setText(data.get(position).name());
        holder.categoryField.setText(data.get(position).category().name());
        holder.priceField.setText(String.format(Locale.getDefault(),"%.2f€", data.get(position).price()));
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
