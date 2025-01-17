package com.kinire.proyectointegrador.free_ports;

import java.util.HashSet;

public class UDPPorts {
    private static final HashSet<Integer> takenPorts = new HashSet<>();

    public static int getFreePort() {
        while(true) {
            int randomPort = (int) ((Math.random() * 16383) + 49153);
            if(!takenPorts.contains(randomPort)) {
                takenPorts.add(randomPort);
                return randomPort;
            }
        }
    }
}
