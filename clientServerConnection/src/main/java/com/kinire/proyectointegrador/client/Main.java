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
            },() -> {}, e -> e.printStackTrace());
        });

    }
}
