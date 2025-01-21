package com.kinire.proyectointegrador.server.graceful_shutdown;

import com.kinire.proyectointegrador.server.client_handling.ClientHandler;

import java.io.IOException;
import java.util.ArrayList;

public class HandleConnections extends Thread {
    private final ArrayList<ClientHandler> handlers;

    public HandleConnections() {
        this.handlers = new ArrayList<>();
    }

    public void addHandler(ClientHandler handler) {
        this.handlers.add(handler);
    }

    @Override
    public void run() {
        int attempt = 0;
        while (true) {
            System.out.println(attempt);
            try {
                while (attempt < handlers.size()) {
                    handlers.get(attempt).close();
                    attempt++;
                }
            } catch (IOException e) {
                attempt++;
                continue;
            }
            return;
        }
    }
}
