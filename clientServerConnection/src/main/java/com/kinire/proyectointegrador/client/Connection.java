package com.kinire.proyectointegrador.client;

import com.kinire.proyectointegrador.CommonValues;
import com.kinire.proyectointegrador.client.lamba_interfaces.EmptyFunction;
import com.kinire.proyectointegrador.client.lamba_interfaces.ErrorFunction;
import com.kinire.proyectointegrador.client.lamba_interfaces.InputStreamFunction;
import com.kinire.proyectointegrador.client.lamba_interfaces.ProductArrayFunction;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.User;
import com.kinire.proyectointegrador.products.ProductMessageBuilder;
import com.kinire.proyectointegrador.users.UserMessageBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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

    private static final byte[] ADDRESS = new byte[] {(byte) 147, 93, 53, 48};

    public static boolean isInstanceStarted() {
        return self != null;
    }
    public static void startInstance(EmptyFunction promise) {
        if(self == null) {
            try {
                new Thread(() -> {
                    try {
                        self = new Connection();
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    promise.apply();
                }).start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Connection getInstance() {
        return self;
    }
    public void print() throws IOException {
        System.out.println(socket.isConnected());
        System.out.println(inputStream.available());
    }
    Connection() throws IOException, ClassNotFoundException {
        InetAddress inetAddress = InetAddress.getByAddress(ADDRESS);
        this.socket = new Socket(inetAddress, CommonValues.tcpListeningPort);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        Integer port = (Integer) inputStream.readObject();
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.udpConnection = new UDPConnection(inetAddress, port);
        logger.log(Level.INFO, String.valueOf(port));
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
    public void uploadPurchase(Purchase purchase) {}
    public void userExists(String username, EmptyFunction truePromise, EmptyFunction falsePromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            try {
                outputStream.writeObject(
                        new UserMessageBuilder()
                                .selectUserRequest(username)
                                .build()
                );
                User user = (User) inputStream.readObject();
                if(user != null)
                    truePromise.apply();
                else
                    falsePromise.apply();
            } catch (IOException | ClassNotFoundException e) {
                failurePromise.apply(e);
            }
        }).start();

    }

    public void isUserDataCorrect(User user, EmptyFunction truePromise, EmptyFunction falsePromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            try {
                outputStream.writeObject(
                        new UserMessageBuilder()
                                .isUserDataCorrect(user)
                                .build()
                );
                Boolean isCorrect = (Boolean) inputStream.readObject();
                if(isCorrect)
                    truePromise.apply();
                else
                    falsePromise.apply();
            } catch (IOException | ClassNotFoundException e) {
                failurePromise.apply(e);
            }
        }).start();
    }

    public void insertUserData(User user, EmptyFunction successPromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            try {
                outputStream.writeObject(
                        new UserMessageBuilder()
                                .insertUserRequest(user)
                                .build()
                );
                Boolean success = (Boolean) inputStream.readObject();
                if(success)
                    successPromise.apply();
                else
                    failurePromise.apply(null);
            } catch (IOException | ClassNotFoundException e) {
                failurePromise.apply(e);
            }
        }).start();
    }
}
