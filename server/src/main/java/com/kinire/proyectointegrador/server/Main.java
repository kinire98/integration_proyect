package com.kinire.proyectointegrador.server;

import com.kinire.proyectointegrador.CommonValues;
import com.kinire.proyectointegrador.server.client_handling.ClientHandler;
import com.kinire.proyectointegrador.server.data_consistency.DataConsistency;
import com.kinire.proyectointegrador.server.graceful_shutdown.HandleConnections;
import com.kinire.proyectointegrador.server.updates_signaling.UpdateSignaler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        DataConsistency consistency = new DataConsistency();
        consistency.setDaemon(true);
        consistency.start();
        HandleConnections connections = new HandleConnections(consistency);
        UpdateSignaler updateSignaler = new UpdateSignaler();
        try (ServerSocket server = new ServerSocket(CommonValues.tcpListeningPort)) {
            Runtime.getRuntime().addShutdownHook(connections);
            while(true) {
                if(System.currentTimeMillis() - startTime >= 60) {
                    new ProcessBuilder("mvn exec:java -Dexec.mainClass=com.kinire.proyectointegrador.server.report.Main").start();
                }
                logger.log(Level.INFO, "Awaiting requests...");
                Socket socket = server.accept();
                logger.log(Level.INFO, "Request accepted...");
                ClientHandler handler = new ClientHandler(socket, updateSignaler);
                updateSignaler.addClientHandler(handler);
                connections.addHandler(handler);
                handler.start();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
    }
}