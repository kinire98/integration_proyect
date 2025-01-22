package com.kinire.proyectointegrador.server.client_handling;

import com.kinire.proyectointegrador.CommonValues;

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
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UDPClientHandler extends Thread {

    private final InetAddress address;

    private final int port;

    private final int listeningPort;

    private final DatagramSocket socket;

    private volatile boolean running = true;

    private final Logger logger = Logger.getLogger(UDPClientHandler.class.getName());

    private volatile boolean productUpdateSignaled = false;

    public UDPClientHandler(InetAddress address, int port) {
        this.address = address;
        this.port = port + 1;
        this.listeningPort = port;
        try {
            this.socket = new DatagramSocket(this.listeningPort);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        this.running = false;
        socket.close();
        this.interrupt();
    }
    public void queueNotificationOfProductUpdate() {
        productUpdateSignaled = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                byte[] receiveBuffer = new byte[4096];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                connectionStatus();
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }
        }
    }




    private void connectionStatus() throws IOException {
        byte[] buffer = new byte[1024];
        if(productUpdateSignaled)
            buffer[0] = CommonValues.udpProductsUpdatedInDb;
        else
            buffer[0] = CommonValues.udpConnectionStatusSucceded;
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }
}
