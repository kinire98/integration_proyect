package com.kinire.proyectointegrador.UPDInfo;

import java.io.Serial;
import java.io.Serializable;

public class UDPPortCommunication implements Serializable {

    @Serial
    private static final long serialVersionUID = 8L;

    private final int port;

    public UDPPortCommunication(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
