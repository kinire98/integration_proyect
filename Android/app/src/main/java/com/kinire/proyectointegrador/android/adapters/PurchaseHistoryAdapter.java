package com.kinire.proyectointegrador.android.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.LayoutRes;

import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Purchase;

import java.util.List;

public class PurchaseHistoryAdapter extends BaseAdapter {

    private Context context;

    private @LayoutRes int layout;

    private List<Purchase> data;

    public PurchaseHistoryAdapter(Context context, @LayoutRes int layout, List<Purchase> data) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
