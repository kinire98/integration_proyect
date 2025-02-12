package com.kinire.proyectointegrador.server.graceful_shutdown;

import com.kinire.proyectointegrador.server.client_handling.ClientHandler;
import com.kinire.proyectointegrador.server.data_consistency.DataConsistency;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase que se encarga del cierre de todas las conexiones y de la interrupción correcta de los hilos
 */
public class HandleConnections extends Thread {
    private final ArrayList<ClientHandler> handlers;


    private final DataConsistency consistency;

    public HandleConnections(DataConsistency consistency) {
        this.handlers = new ArrayList<>();
        this.consistency = consistency;
    }

    /**
     * Añade una conexión a cerrar más adelante
     * @param handler Conexión a cerrar más adelante
     */
    public void addHandler(ClientHandler handler) {
        this.handlers.add(handler);
    }

    @Override
    public void run() {
        consistency.finish();
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
