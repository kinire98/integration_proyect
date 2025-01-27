package com.kinire.proyectointegrador.server.report;

import com.kinire.proyectointegrador.components.Category;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.ShoppingCartItem;
import com.kinire.proyectointegrador.server.DAOInstances.DAOInstances;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * Clase encargada de generar un archivo con informes de productos vendidos
 */
public class Main {
    public static void main(String[] args) {
        File file = new File("/root/reports", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
        try {
            if(!file.createNewFile()) {
                System.err.println("There was an error creating the file for the report. Make sure you have the right" +
                        "permissions");
                return;
            }
            StringBuilder message = new StringBuilder();
            List<Purchase> purchases = DAOInstances.getPurchaseDAO().selectPurchaseByMonth(LocalDate.now());
            message.append("----------------------------------").append(System.lineSeparator());
            message.append("Number of purchases this month: ").append(purchases.size());
            message.append(System.lineSeparator());
            HashMap<String, Integer> products = new HashMap<>();
            HashMap<String, Integer> category = new HashMap<>();
            double totalSold = 0.0;
            int totalSales = 0;
            for (Purchase purchase : purchases) {
                for(ShoppingCartItem shoppingCartItem : purchase.getShoppingCartItems()) {
                    products.put(shoppingCartItem.getProduct().getName(), products.getOrDefault(shoppingCartItem.getProduct().getName(), 0) + 1);
                    category.put(shoppingCartItem.getProduct().getCategory().getName(), category.getOrDefault(shoppingCartItem.getProduct().getCategory().getName(), 0) + 1);
                    totalSales++;
                }
            }
            double averagePerSale = totalSold / totalSales;
            message.append("----------------------------------").append(System.lineSeparator());
            message.append("Total sold: ").append(totalSold).append("€").append(System.lineSeparator());
            message.append("Total sales: ").append(totalSales).append(System.lineSeparator());
            message.append("Average per sale: ").append(averagePerSale).append("€").append(System.lineSeparator());
            message.append("----------------------------------").append(System.lineSeparator());
            message.append("Total of products: ").append(System.lineSeparator());
            for(String product : products.keySet()) {
                message.append("Total sold of ").append(product).append(":").append(System.lineSeparator());
                message.append('\t').append(products.get(product)).append(System.lineSeparator());
            }
            message.append("----------------------------------").append(System.lineSeparator());
            message.append("Total of categories: ").append(System.lineSeparator());
            for (String category1 : category.keySet()) {
                message.append("Total sold of ").append(category1).append(":").append(System.lineSeparator());
                message.append('\t').append(category.get(category1)).append(System.lineSeparator());
            }
            message.append("----------------------------------").append(System.lineSeparator());
            String[] messageToWrite = message.toString().split(System.lineSeparator());
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String s : messageToWrite) {
                writer.write(s);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            System.err.println("There was an error generating the report");
        }

    }
}
