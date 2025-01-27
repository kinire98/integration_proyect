package com.kinire.proyectointegrador.client;

import com.kinire.proyectointegrador.CommonValues;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import java.util.logging.Logger;


/**
 * Clase que maneja la conexión UDP
 */
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
           status();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
       }
    }


    /**
     *  Pregunta el status al servidor.
     *  Puede recibir dos valores, o conectado o que hay valores nuevos
     *  En el caso de que perdiese la conexión o hubiese productos nuevos se ejecutarían las promesas guardadas en la clase Connection.
     */
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
