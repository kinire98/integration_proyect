package com.kinire.proyectointegrador.server.updates_signaling;

import com.kinire.proyectointegrador.server.client_handling.ClientHandler;

import java.util.ArrayList;
import java.util.Iterator;

public class UpdateSignaler {
    private ArrayList<ClientHandler> clientHandlers;

    public UpdateSignaler() {
        this.clientHandlers = new ArrayList<>();
    }

    public void addClientHandler(ClientHandler clientHandler) {
        this.clientHandlers.add(clientHandler);
    }

    public void signalProductUpdate() {
        Iterator<ClientHandler> it = clientHandlers.iterator();
        while(it.hasNext()) {
            ClientHandler handler = it.next();
            if(handler.isConnected()) {
               handler.notifyProductUpdate();
            } else {
                clientHandlers.remove(handler);
            }
        }
    }
}
