package com.kinire.proyectointegrador.client;

import com.kinire.proyectointegrador.CommonValues;
import com.kinire.proyectointegrador.client.lamba_interfaces.EmptyFunction;
import com.kinire.proyectointegrador.client.lamba_interfaces.ErrorFunction;
import com.kinire.proyectointegrador.client.lamba_interfaces.InputStreamFunction;
import com.kinire.proyectointegrador.client.lamba_interfaces.ProductArrayFunction;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Category;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.User;
import com.kinire.proyectointegrador.products.ProductMessageBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Connection {

    private static Connection self;

    private User user;

    private final Socket socket;

    private final ObjectOutputStream outputStream;

    private final ObjectInputStream inputStream;

    private final UDPConnection udpConnection;

    private final Logger logger = Logger.getLogger(Connection.class.getName());


    public static Connection startInstance(byte[] address, EmptyFunction promise) {
        if(self == null) {
            try {
                new Thread(() -> {
                    try {
                        self = new Connection(InetAddress.getByAddress(address));
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    promise.apply();
                }).start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return self;
    }
    public static Connection getInstance() {
        return self;
    }
    public void print() throws IOException {
        System.out.println(socket.isConnected());
        System.out.println(inputStream.available());
    }
    Connection(InetAddress address) throws IOException, ClassNotFoundException {
        this.socket = new Socket(address, CommonValues.tcpListeningPort);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        Integer port = (Integer) inputStream.readObject();
        this.udpConnection = new UDPConnection(address, port);
        udpConnection.start();
    }
    public void close() throws IOException {
        this.udpConnection.close();
        this.inputStream.close();
        this.outputStream.close();
        this.socket.close();
    }

    public void getProducts(ProductArrayFunction successPromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            List<Object> products = null;
            try {
                logger.log(Level.INFO, "Sending products request");
                outputStream.writeObject(
                        new ProductMessageBuilder()
                                .allProductsRequest()
                                .build()
                );
                Object obj = inputStream.readObject();
                if (obj.getClass().isArray()) {
                    products = Arrays.asList((Object[])obj);
                } else if (obj instanceof Collection) {
                    products= new ArrayList<>((Collection<?>)obj);
                }

            } catch (IOException | ClassNotFoundException e) {
                failurePromise.apply(e);
                return;
            }
            if(products == null || products.isEmpty()) {
                failurePromise.apply(null);
                return;
            }
            successPromise.apply(products.stream().map((product) -> (Product) product).collect(Collectors.toList()));
        }).start();
    }
    public void getImage(String path, InputStreamFunction successPromise, ErrorFunction errorPromise) {
        udpConnection.askForImage(path, successPromise, errorPromise);
    }
    public boolean isAdminPasswordCorrect(String password) {
        return true;
    }
    public void uploadPurchase(Purchase purchase) {}
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
