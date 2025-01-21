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

    private final ArrayList<ImageSolicitude> imageSolicitudes;

    private final ArrayList<ImageUpdateSolicitude> imageUpdateSolicitudes;

    private final Logger logger = Logger.getLogger(UDPConnection.class.getName());

    private final Connection connection;

    UDPConnection(InetAddress address, int port, Connection connection) throws SocketException {
        this.address = address;
        System.out.println(address.getCanonicalHostName());
        this.port = port;
        this.listeningPort = port + 1;
        this.socket = new DatagramSocket(listeningPort);
        this.imageSolicitudes = new ArrayList<>();
        this.imageUpdateSolicitudes = new ArrayList<>();
        this.connection = connection;
    }

    void askForImage(String imagePath, InputStreamFunction successPromise, ErrorFunction failurePromise) {
        ImageSolicitude imageSolicitude = new ImageSolicitude();
        imageSolicitude.imagePath = imagePath;
        imageSolicitude.successPromise = successPromise;
        imageSolicitude.failurePromise = failurePromise;
        imageSolicitudes.add(
                imageSolicitude
        );
    }
    void askForSendingImage(String imageName, InputStream imageStream, EmptyFunction successPromise, ErrorFunction failurePromise) {
        ImageUpdateSolicitude solicitude = new ImageUpdateSolicitude();
        solicitude.imageName = imageName;
        solicitude.imageStream = imageStream;
        solicitude.successPromise = successPromise;
        solicitude.failurePromise = failurePromise;
        imageUpdateSolicitudes.add(solicitude);
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
           if(!imageSolicitudes.isEmpty()) {
               askforImageToServer();
               continue;
           }
           if(!imageUpdateSolicitudes.isEmpty()) {
               updateImageToServer();
               continue;
           }
           status();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
       }
    }

    private void askforImageToServer() {
        ImageSolicitude imageSolicitude = imageSolicitudes.remove(0);
        logger.log(Level.INFO, "Asking for image to the server");
        try {
            logger.log(Level.INFO, "Started Thread for connection");
            byte[] sendBuffer = new byte[4096];
            sendBuffer[0] = CommonValues.udpImageRequest;
            byte[] imagePath = imageSolicitude.imagePath.getBytes(StandardCharsets.UTF_8);
            System.arraycopy(imagePath, 0, sendBuffer, 1, imagePath.length);
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
            logger.log(Level.INFO, "Sending image request info");
            socket.send(sendPacket);
            logger.log(Level.INFO, "Image request sent");


            byte[] receiveBuffer = new byte[65001];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            logger.log(Level.INFO, "Starting image reception");
            socket.receive(receivePacket);
            logger.log(Level.INFO, "First package received");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while(receiveBuffer[0] == CommonValues.udpImageRequestSucceded) {
                outputStream.write(Arrays.copyOfRange(receiveBuffer, 1, receiveBuffer.length));
                socket.receive(receivePacket);
            }
            if (receiveBuffer[0] == CommonValues.udpImageRequestEnded) {
                logger.log(Level.INFO, "Image reception has finished");
                imageSolicitude.successPromise.apply(
                        new ByteArrayInputStream(
                                outputStream.toByteArray()
                        )
                );
            } else if(receiveBuffer[0] == CommonValues.udpImageRequestFailure) {
                logger.log(Level.SEVERE, "Image reception has stopped, due to an error");
                if(receiveBuffer[1] == CommonValues.udpImageRequestFailureFileNotFound) {
                    imageSolicitude.failurePromise.apply(new FileDoesNotExistException(""));
                } else if(receiveBuffer[1] == CommonValues.udpImageRequestFailureInternalServerError) {
                    imageSolicitude.failurePromise.apply(new RequestNotFulfilledException("The image request could not bu fullfilled"));
                }
            } else {
                imageSolicitude.failurePromise.apply(new UnknownException("An unknown error ocurred"));
            }

        } catch (IOException e) {
            imageSolicitude.failurePromise.apply(e);
        }
        logger.log(Level.INFO, "Image request ended");
    }

    private void updateImageToServer() {
        ImageUpdateSolicitude solicitude = imageUpdateSolicitudes.remove(0);
        logger.log(Level.INFO, "Uploading image to the server");
        updateAttempt(solicitude, 0);
    }
    private void updateAttempt(ImageUpdateSolicitude solicitude, int attempt) {
        if(attempt == 5)
            return;
        try {
            byte[] buffer = new byte[solicitude.imageName.length() * 2];
            buffer[0] = CommonValues.updSendImageToServer;
            byte[] fileName = solicitude.imageName.getBytes(StandardCharsets.UTF_8);
            System.arraycopy(fileName, 0, buffer, 1, fileName.length);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] fileBuffer = new byte[65000];
            int bytesRead;
            while((bytesRead = solicitude.imageStream.read(fileBuffer)) != -1) {
                byte[] sendBuffer = new byte[65001];
                sendBuffer[0] = CommonValues.udpImageRequestSucceded;
                System.arraycopy(fileBuffer, 0, sendBuffer, 1, fileBuffer.length);
                DatagramPacket packetFile =  new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
                socket.send(packetFile);
            }
            byte[] lastImageBuffer = new byte[]{CommonValues.udpImageRequestEnded};
            DatagramPacket lastImagePacket = new DatagramPacket(lastImageBuffer, lastImageBuffer.length, address, port);
            socket.send(lastImagePacket);
            logger.log(Level.INFO, "Image request fulfilled");
            solicitude.successPromise.apply();
        } catch (IOException e) {
            if(attempt < 10) {
                attempt++;
                updateAttempt(solicitude, attempt);
                return;
            }
            try {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(packet);
            } catch (IOException ex) {
                solicitude.failurePromise.apply(ex);
                return;
            }
            solicitude.failurePromise.apply(e);
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

    private static class ImageSolicitude {
        String imagePath;
        InputStreamFunction successPromise;
        ErrorFunction failurePromise;
    }

    private static class ImageUpdateSolicitude {
        String imageName;
        InputStream imageStream;
        EmptyFunction successPromise;
        ErrorFunction failurePromise;
    }
}
