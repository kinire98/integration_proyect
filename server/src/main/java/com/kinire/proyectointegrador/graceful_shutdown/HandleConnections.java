package com.kinire.proyectointegrador.graceful_shutdown;

import com.kinire.proyectointegrador.client_handling.ClientHandler;

import java.util.ArrayList;

public class HandleConnections extends Thread {
    private ArrayList<ClientHandler> handlers;

    public HandleConnections() {
        this.handlers = new ArrayList<>();
    }

    public void addHandler(ClientHandler handler) {
        this.handlers.add(handler);
    }

    @Override
    public void run() {
        super.run();
    }
}
