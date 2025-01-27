package com.kinire.proyectointegrador.server.updates_signaling;

import com.kinire.proyectointegrador.server.client_handling.ClientHandler;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase que se encarga de avisar a todas la conexiones que otra conexión ha actualizado los productos
 */
public class UpdateSignaler {
    private ArrayList<ClientHandler> clientHandlers;

    public UpdateSignaler() {
        this.clientHandlers = new ArrayList<>();
    }

    /**
     * Añade una conexión a la que avistar
     * @param clientHandler Conexión a la que avisar
     */
    public void addClientHandler(ClientHandler clientHandler) {
        this.clientHandlers.add(clientHandler);
    }

    /**
     * Notifica a todas las conexiones.
     * En caso de que no estuviera conectada se elimina
     */
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
