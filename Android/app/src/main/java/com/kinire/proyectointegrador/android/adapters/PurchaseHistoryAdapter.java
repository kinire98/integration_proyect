package com.kinire.proyectointegrador.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Purchase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Adaptador para la lista de historial de compras.
 */
public class PurchaseHistoryAdapter extends BaseAdapter {

    private final Context context;

    private final @LayoutRes int layout;

    private final List<Purchase> data;

    private final String ID_FIELD_PREFIX;

    public PurchaseHistoryAdapter(Context context, @LayoutRes int layout, List<Purchase> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
        this.ID_FIELD_PREFIX = context.getString(R.string.purchase_num);
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
            v = LayoutInflater.from(context).inflate(layout, null);
            holder = new ViewHolder();
            holder.idField = v.findViewById(R.id.id_field);
            holder.userNameField = v.findViewById(R.id.user_name);
            holder.purchaseDate = v.findViewById(R.id.purchase_date);
            holder.priceField = v.findViewById(R.id.price_field);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        Purchase purchase = data.get(position);
        holder.idField.setText(String.format(ID_FIELD_PREFIX, purchase.getId()));
        holder.userNameField.setText(purchase.getUser().getUser());
        holder.purchaseDate.setText(purchase.getPurchaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        holder.priceField.setText(String.format(Locale.getDefault(), "%.2f€", purchase.getTotalPrice()));
        return v;
    }
    private static class ViewHolder {
        private TextView idField;
        private TextView userNameField;
        private TextView purchaseDate;
        private TextView priceField;
    }
}
