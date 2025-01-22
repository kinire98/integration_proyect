package com.kinire.proyectointegrador.client;

import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.User;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws IOException {
        Connection.startInstance(() -> {

            User user = new User("Iker", "1234");
            Connection.getInstance().getProducts((products) -> {
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
                    try {
                        FileOutputStream outputStream = new FileOutputStream(new File(product.getImagePath()));
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(product.getImage());
                        byte[] buffer = new byte[1048576];
                        int bytesRead;
                        while((bytesRead = inputStream.read(buffer)) != -1)
                            outputStream.write(buffer, 0, bytesRead);
                        outputStream.close();
                    } catch (IOException e) {}
                }
            }, e -> e.printStackTrace());
        });

    }
}
