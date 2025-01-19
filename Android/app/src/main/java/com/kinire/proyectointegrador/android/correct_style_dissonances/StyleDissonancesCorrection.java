package com.kinire.proyectointegrador.android.correct_style_dissonances;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.kinire.proyectointegrador.android.R;

public class StyleDissonancesCorrection {
    public static void setStatusBarCorrectColor(AppCompatActivity appCompatActivity) {
        appCompatActivity.getWindow().setStatusBarColor(appCompatActivity.getColor(R.color.main_blue));
    }
}
