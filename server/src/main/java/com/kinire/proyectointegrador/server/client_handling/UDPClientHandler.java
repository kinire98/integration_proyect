package com.kinire.proyectointegrador.server.client_handling;

import com.kinire.proyectointegrador.CommonValues;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPClientHandler extends Thread {

    private final InetAddress address;

    private final int port;


    private final DatagramSocket socket;

    private volatile boolean running = true;

    private final Logger logger = Logger.getLogger(UDPClientHandler.class.getName());

    public UDPClientHandler(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        try {
            this.socket = new DatagramSocket(this.port);
            this.socket.connect(address, port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        this.running = false;
        socket.disconnect();
        socket.close();
    }

    @Override
    public void run() {
        while (running) {
            try {

                DatagramPacket packet = new DatagramPacket(new byte[512], 512, address, port);
                socket.send(packet);


                byte[] receiveBuffer = new byte[4096];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                logger.log(Level.INFO, "Awaiting UPD request");
                socket.receive(receivePacket);
                if(receiveBuffer[0] == CommonValues.udpImageRequest) {
                    imageRequest(receiveBuffer);
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }
        }
    }

    /**
     *
     * @param buffer
     * @throws IOException
     */
    private void imageRequest(byte[] buffer) throws IOException {
        logger.log(Level.INFO, "Processing image request");
        String path = new String(Arrays.copyOfRange(buffer, 1, buffer.length)).trim();
        File file = new File(path);
        if(!file.exists()) {
            byte[] errorBuffer = new byte[512];
            DatagramPacket packet = new DatagramPacket(errorBuffer, errorBuffer.length, address, port);
            socket.send(packet);
            return;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = new FileInputStream(file)) {
               byte[] fileBuffer = new byte[1024];
               int bytesRead;
               while((bytesRead = inputStream.read(fileBuffer)) != -1) {
                   byte[] sendBuffer = new byte[1025];
                   sendBuffer[0] = CommonValues.udpImageRequestSucceded;
                   System.arraycopy(fileBuffer, 0, sendBuffer, 1, fileBuffer.length);
                   DatagramPacket packet =  new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
                   socket.send(packet);
               }
               byte[] lastImageBuffer = new byte[]{CommonValues.udpImageRequestEnded};
               DatagramPacket lastImagePacket = new DatagramPacket(lastImageBuffer, lastImageBuffer.length, address, port);
               socket.send(lastImagePacket);
               logger.log(Level.INFO, "Image request fulfilled");
        } catch (IOException e) {
            byte[] errorBuffer = new byte[512];
            errorBuffer[0] = CommonValues.udpImageRequestFailureInternalServerError;
            DatagramPacket errorPacket = new DatagramPacket(errorBuffer, errorBuffer.length, address, port);
            socket.send(errorPacket);
        }
    }
}
