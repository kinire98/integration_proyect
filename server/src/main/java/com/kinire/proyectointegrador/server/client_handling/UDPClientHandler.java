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

    private final boolean productUpdateSignaled = false;

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
    public void queueNotificationOfProductUpdate() {}

    @Override
    public void run() {
        while (running) {
            try {

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
        try (InputStream inputStream = getImageCompressed(file)) {
               byte[] fileBuffer = new byte[65000];
               int bytesRead;
               while((bytesRead = inputStream.read(fileBuffer)) != -1) {
                   byte[] sendBuffer = new byte[65001];
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

            e.printStackTrace();
            byte[] errorBuffer = new byte[512];
            errorBuffer[0] = CommonValues.udpImageRequestFailureInternalServerError;
            DatagramPacket errorPacket = new DatagramPacket(errorBuffer, errorBuffer.length, address, port);
            socket.send(errorPacket);
        }
    }
    private InputStream getImageCompressed(File file) throws IOException {
        BufferedImage img = ImageIO.read(file);
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageOutputStream outputStream = ImageIO.createImageOutputStream(byteArrayOutputStream);

        writer.setOutput(outputStream);
        ImageWriteParam params = writer.getDefaultWriteParam();
        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        params.setCompressionQuality(.75f);
        writer.write(null, new IIOImage(img, null, null), params);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        outputStream.close();
        byteArrayInputStream.close();
        //inputStream.close();
        return byteArrayInputStream;
    }
}
