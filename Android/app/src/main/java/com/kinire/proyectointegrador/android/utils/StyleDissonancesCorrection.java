package com.kinire.proyectointegrador.android.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.kinire.proyectointegrador.android.R;

public class StyleDissonancesCorrection {
    public static void setStatusBarCorrectColor(AppCompatActivity appCompatActivity) {
        appCompatActivity.getWindow().setStatusBarColor(appCompatActivity.getColor(R.color.main_blue));
    }
}
