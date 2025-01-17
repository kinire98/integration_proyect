package com.kinire.proyectointegrador;

import com.kinire.proyectointegrador.client_handling.ClientHandler;
import com.kinire.proyectointegrador.graceful_shutdown.HandleConnections;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        HandleConnections connections = new HandleConnections();
        try (ServerSocket server = new ServerSocket(CommonValues.tcpListeningPort)) {
            Runtime.getRuntime().addShutdownHook(connections);
            while(true) {
                logger.log(Level.INFO, "Awaiting requests...");
                Socket socket = server.accept();
                logger.log(Level.INFO, "Request accepted...");
                ClientHandler handler = new ClientHandler(socket);
                connections.addHandler(handler);
                handler.start();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
    }
}