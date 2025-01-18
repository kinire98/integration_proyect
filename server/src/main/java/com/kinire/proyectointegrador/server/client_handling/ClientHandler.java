package com.kinire.proyectointegrador.server.client_handling;

import com.kinire.proyectointegrador.server.DAOInstances.DAOInstances;
import com.kinire.proyectointegrador.server.free_ports.UDPPorts;
import com.kinire.proyectointegrador.products.ProductMessage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends Thread {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ClientHandler.class);
    private final Socket socket;

    private ObjectInputStream inputStream;

    private ObjectOutputStream outputStream;

    private UDPClientHandler udpClientHandler;

    private Logger logger = Logger.getLogger(ClientHandler.class.getName());

    private volatile boolean running = true;

    private final int udpPort;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.udpPort = UDPPorts.getFreePort();
        try {
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            logger.log(Level.INFO, socket.getInetAddress().getHostAddress() + ": Writing UDP Port");
            logger.log(Level.INFO, String.valueOf(udpPort));
            outputStream.writeObject(udpPort);
            logger.log(Level.INFO, socket.getInetAddress().getHostAddress() + ": UDP Port written");
            this.udpClientHandler = new UDPClientHandler(socket.getInetAddress(), udpPort);
            this.udpClientHandler.start();
        } catch (IOException e) {}
    }

    public void close() throws IOException {
        this.running = false;
        this.udpClientHandler.close();
        this.outputStream.close();
        this.inputStream.close();
        this.socket.close();
        this.interrupt();
    }


    @Override
    public void run() {
        try {
            handleConnections();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Sike, that's the wrong class");
        }
    }

    private void handleConnections() throws IOException, ClassNotFoundException {
        while(running) {
            logger.log(Level.INFO, socket.getInetAddress().getHostAddress() + ": Awaiting request");
            Object message = inputStream.readObject();
            if(message instanceof ProductMessage)
                handleProductMessage((ProductMessage) message);
        }
    }
    private void handleProductMessage(ProductMessage message) throws IOException {
        logger.log(Level.INFO, socket.getInetAddress().getHostAddress() + ": Processing product request");
        if(message.isAllProductsRequest()) {
            outputStream.writeObject(
                    DAOInstances.getProductDAO().selectAllProducts()
            );
        } else if(message.isSingleProductRequest()) {
            outputStream.writeObject(
                    DAOInstances.getProductDAO().selectProductById(message.getId())
            );
        } else if(message.isRequestByCategory()) {
            outputStream.writeObject(
                    DAOInstances.getProductDAO().selectProductByCategory(message.getId())
            );
        } else if(message.isRequestByIds()) {
            outputStream.writeObject(
                    DAOInstances.getProductDAO().selectProductsByIds(message.getIds())
            );
        } else if(message.isRequestOfMissingProducts()) {
            outputStream.writeObject(
                    DAOInstances.getProductDAO().selectMissingProducts(message.getProducts())
            );
        } else if(message.isRequestOfUpdatedProducts()) {
            outputStream.writeObject(
                    DAOInstances.getProductDAO().selectUpdatedProducts(message.getProducts())
            );
        }


    }
}
