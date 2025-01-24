package com.kinire.proyectointegrador.android.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.kinire.proyectointegrador.android.R;

/**
 * Método simple para arreglar unas disonancias de estilo.
 * Con los estilos definidos en XML la barra de estado (la barra encima del ActionBar) no cambiaban de
 * color excepto en el Activity principal, al no encontrar ninguna solución se creo este método para
 * hacer sencillo el posible cambio de color
 */
public class StyleDissonancesCorrection {
    public static void setStatusBarCorrectColor(AppCompatActivity appCompatActivity) {
        appCompatActivity.getWindow().setStatusBarColor(appCompatActivity.getColor(R.color.main_blue));
    }
}
