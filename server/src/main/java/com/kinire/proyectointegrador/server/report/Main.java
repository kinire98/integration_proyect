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
            HashMap<Product, Integer> products = new HashMap<>();
            HashMap<Category, Integer> category = new HashMap<>();
            double totalSold = 0.0;
            int totalSales = 0;
            for (Purchase purchase : purchases) {
                for(ShoppingCartItem shoppingCartItem : purchase.getShoppingCartItems()) {
                    products.put(shoppingCartItem.getProduct(), products.getOrDefault(shoppingCartItem.getProduct(), 0) + 1);
                    category.put(shoppingCartItem.getProduct().getCategory(), category.getOrDefault(shoppingCartItem.getProduct().getCategory(), 0) + 1);
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
            for(Product product : products.keySet()) {
                message.append("Total sold of ").append(product.getName()).append(":").append(System.lineSeparator());
                message.append('\t').append(products.get(product)).append(System.lineSeparator());
            }
            message.append("----------------------------------").append(System.lineSeparator());
            message.append("Total of categories: ").append(System.lineSeparator());
            for (Category category1 : category.keySet()) {
                message.append("Total sold of ").append(category1.getName()).append(":").append(System.lineSeparator());
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
