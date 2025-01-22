package com.kinire.proyectointegrador.client;

import com.kinire.proyectointegrador.CommonValues;
import com.kinire.proyectointegrador.client.exceptions.FileDoesNotExistException;
import com.kinire.proyectointegrador.client.exceptions.RequestNotFulfilledException;
import com.kinire.proyectointegrador.client.exceptions.UnknownException;
import com.kinire.proyectointegrador.client.lamba_interfaces.EmptyFunction;
import com.kinire.proyectointegrador.client.lamba_interfaces.ErrorFunction;
import com.kinire.proyectointegrador.client.lamba_interfaces.InputStreamFunction;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

class UDPConnection extends Thread {

    private final InetAddress address;

    private final int port;

    private final int listeningPort;

    private volatile boolean running = true;

    private final DatagramSocket socket;


    private final Logger logger = Logger.getLogger(UDPConnection.class.getName());

    private final Connection connection;

    UDPConnection(InetAddress address, int port, Connection connection) throws SocketException {
        this.address = address;
        this.port = port;
        this.listeningPort = port + 1;
        this.socket = new DatagramSocket(listeningPort);
        this.connection = connection;
    }





    void close() {
        this.running = false;
        this.socket.close();
        this.interrupt();
    }


    @Override
    public void run() {
       while(running) {
           //Si hay una solicitud para una imagen se atiende primero, antes que la prueba de conexion
           //logger.log(Level.INFO, "Number of image requests awaiting" + imageSolicitudes.size());


           status();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
       }
    }


    private void status() {
        try {
            byte[] buffer = new byte[1024];
            buffer[0] = CommonValues.udpAskForConnectionStatus;
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            if(receiveBuffer[0] == CommonValues.udpProductsUpdatedInDb)
                connection.productsUpdated();
            else if(receiveBuffer[0] == CommonValues.udpConnectionStatusSucceded) {}
        } catch (IOException e) {
            connection.connectionLost();
        }
    }



}
