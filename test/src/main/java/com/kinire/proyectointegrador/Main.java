package com.kinire.proyectointegrador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        DatagramSocket socket = new DatagramSocket(55555);

        DatagramPacket packet = new DatagramPacket(new byte[0], 0);
        socket.receive(packet);
        System.out.println(packet);

        socket.close();
    }
}