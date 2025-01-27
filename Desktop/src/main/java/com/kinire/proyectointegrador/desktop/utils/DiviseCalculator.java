package com.kinire.proyectointegrador.desktop.utils;

/**
 * Calculador de divias
 */
public class DiviseCalculator {
    public final static String dollar = "$";
    public final static String euro = "€";
    public final static String pound = "£";
    public final static String yen = "¥";
    private final static String peseta = "₧";
    private static final String[] currencies = {
            peseta,
            euro,
            dollar,
            pound,
            yen
    };

    private static String divise = "€";

    /**
     * Introducido el precio en euros devuelve el precio en la divisa escogida por el usuario
     * @param price El precio en euros
     * @return El precio en la divisa seleccionada
     */
    public static float getPrice(float price) {
        if(divise.equals(dollar))
            return price * 1.05f;
        if(divise.equals(pound))
            return price * 0.84f;
        if(divise.equals(yen))
            return price * 163.54f;
        if(divise.equals(peseta))
            return price * 166.39f;
        return price;
    }

    /**
     * Getter del símbolo de la divisa seleccionada
     * @return Símbolo de la divisa seleccionada
     */
    public static String getSymbol() {
        return divise;
    }

    /**
     * Setter del símbolo de la moneda seleccionada
     * @param symbol El símbolo de la moneda seleccionada. Solo hay cinco valores validos: dólar, euro, libra, yen y peseta
     */
    public static void setSymbol(String symbol) {
        if(
                !symbol.equals(dollar) &&
                        !symbol.equals(euro) &&
                        !symbol.equals(pound) &&
                        !symbol.equals(yen) &&
                        !symbol.equals(peseta)
        )
            return;
        divise = symbol;
    }

    /**
     * Devuelve una lista con las divisas disponibles
     * @return Divisas disponibles
     */
    public static String[] getCurrencies() {
        return currencies;
    }
}
