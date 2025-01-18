package com.kinire.proyectointegrador.client;

import com.kinire.proyectointegrador.CommonValues;
import com.kinire.proyectointegrador.client.exceptions.FileDoesNotExistException;
import com.kinire.proyectointegrador.client.exceptions.RequestNotFulfilledException;
import com.kinire.proyectointegrador.client.exceptions.UnknownException;
import com.kinire.proyectointegrador.client.lamba_interfaces.ErrorFunction;
import com.kinire.proyectointegrador.client.lamba_interfaces.InputStreamFunction;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

class UDPConnection extends Thread {

    private final InetAddress address;

    private final int port;

    private final int listeningPort;

    private volatile boolean running = true;

    private final DatagramSocket socket;

    private final ArrayList<ImageSolicitude> imageSolicitudes;

    private final Logger logger = Logger.getLogger(UDPConnection.class.getName());

    UDPConnection(InetAddress address, int port) throws SocketException {
        this.address = address;
        System.out.println(address.getCanonicalHostName());
        this.port = port;
        this.listeningPort = port + 1;
        this.socket = new DatagramSocket(listeningPort);
        System.out.println(this.port);
        System.out.println(listeningPort);
        System.out.println(address.getCanonicalHostName());
        this.imageSolicitudes = new ArrayList<>();
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


            /*System.out.println("before");
            byte[] buffer = new byte[] {1};
            DatagramPacket packet = new DatagramPacket(buffer,  buffer.length, address, port);
            socket.send(packet);
            System.out.println("after");

            logger.log(Level.SEVERE, "xd?");
            logger.log(Level.SEVERE, String.valueOf(socket.isConnected()));
            logger.log(Level.SEVERE, String.valueOf(socket.isBound()));
            DatagramPacket packet1 = new DatagramPacket(new byte[0], 0, address, port);
            socket.receive(packet1);
            logger.log(Level.SEVERE, "xd");*/

            logger.log(Level.INFO, "Started Thread for connection");
            byte[] sendBuffer = new byte[4096];
            sendBuffer[0] = CommonValues.udpImageRequest;
            byte[] imagePath = imageSolicitude.imagePath.getBytes(StandardCharsets.UTF_8);
            System.arraycopy(imagePath, 0, sendBuffer, 1, imagePath.length);
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
            logger.log(Level.INFO, "Sending image request info");
            socket.send(sendPacket);
            logger.log(Level.INFO, "Image request sent");


            byte[] receiveBuffer = new byte[1025];
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

    private static class ImageSolicitude {
        String imagePath;
        InputStreamFunction successPromise;
        ErrorFunction failurePromise;
    }
}
