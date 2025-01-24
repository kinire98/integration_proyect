package com.kinire.proyectointegrador.desktop.utils;

public class DiviseCalculator {
    public final static String dollar = "$";
    public final static String euro = "$";
    public final static String pound = "£";
    public final static String yen = "¥";

    private static String divise = "€";
    public static float getPrice(float price) {
        if(divise.equals(dollar))
            return price * 1.05f;
        if(divise.equals(pound))
            return price * 0.84f;
        if(divise.equals(yen))
            return price * 163.54f;
        return price;
    }
    public static String getSymbol() {
        return divise;
    }

    public static void setSymbol(String symbol) {
        if(
                !symbol.equals(dollar) ||
                        !symbol.equals(euro) ||
                        !symbol.equals(pound) ||
                        !symbol.equals(yen)
        )
            return;
        divise = symbol;
    }
}
