package com.kinire.proyectointegrador.client;

import com.kinire.proyectointegrador.CommonValues;
import com.kinire.proyectointegrador.client.lamba_interfaces.*;
import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.User;
import com.kinire.proyectointegrador.products.ProductMessageBuilder;
import com.kinire.proyectointegrador.purchases.PurchaseMessageBuilder;
import com.kinire.proyectointegrador.users.UserMessageBuilder;

import java.io.IOException;
import java.io.InputStream;
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

    private EmptyFunction productsUpdatedPromise;
    private EmptyFunction connectionLostPromise;

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
    private Connection() throws IOException, ClassNotFoundException {
        InetAddress inetAddress = InetAddress.getByAddress(ADDRESS);
        this.socket = new Socket(inetAddress, CommonValues.tcpListeningPort);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        Integer port = (Integer) inputStream.readObject();
        this.udpConnection = new UDPConnection(inetAddress, port, this);
        logger.log(Level.INFO, String.valueOf(port));
        udpConnection.start();
    }

    public void setProductsUpdatedPromise(EmptyFunction productsUpdatedPromise) {
        this.productsUpdatedPromise = productsUpdatedPromise;
    }

    public void setConnectionLostPromise(EmptyFunction connectionLostPromise) {
        this.connectionLostPromise = connectionLostPromise;
    }

    public void close() throws IOException {
        this.udpConnection.close();
        this.inputStream.close();
        this.outputStream.close();
        this.socket.close();
    }

    public void getProducts(ProductFunction successPromise, EmptyFunction finishPromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            try {
                logger.log(Level.INFO, "Sending products request");
                outputStream.writeObject(
                        new ProductMessageBuilder()
                                .allProductsRequest()
                                .build()
                );
                Integer numOfProducts = (Integer) inputStream.readObject();
                for (int i = 0; i < numOfProducts; i++) {
                    Product product = (Product) inputStream.readObject();
                    successPromise.apply(product);
                }
                finishPromise.apply();
            } catch (IOException | ClassNotFoundException e) {
                failurePromise.apply(e);
                return;
            }
        }).start();
    }
    //Update as in create
    public void updateProduct(Product product, InputStream imageStream, EmptyFunction successPromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            try {
                outputStream.writeObject(
                        new ProductMessageBuilder()
                                .insertProductRequest(product, imageStream)
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
    public void deleteProduct(Product product, EmptyFunction successPromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            try {
                outputStream.writeObject(
                        new ProductMessageBuilder()
                                .deleteProductRequest(product)
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
    public void userExists(String username, EmptyFunction truePromise, EmptyFunction falsePromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            try {
                logger.log(Level.INFO, "Sending user information");
                outputStream.writeObject(
                        new UserMessageBuilder()
                                .selectUserRequest(username)
                                .build()
                );
                logger.log(Level.INFO, "Reading user information");
                Boolean user = (Boolean) inputStream.readObject();
                logger.log(Level.INFO, "User information read");
                if(user) {
                    logger.log(Level.INFO, "User exists");
                    truePromise.apply();
                }
                else {
                    logger.log(Level.INFO, "User doesn't exist");
                    falsePromise.apply();
                }
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
    public void uploadPurchase(Purchase purchase, EmptyFunction successPromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            try {
                outputStream.writeObject(
                        new PurchaseMessageBuilder()
                                .insertPurchaseRequest(purchase)
                                .build()
                );
                logger.log(Level.INFO, "Reading value of uploaded purchase");
                Boolean result = (Boolean) inputStream.readObject();
                if(result)
                    successPromise.apply();
                else
                    failurePromise.apply(null);
            } catch (IOException | ClassNotFoundException e) {
                failurePromise.apply(e);
            }
        }).start();
    }
    public void deletePurchase(Purchase purchase, EmptyFunction successPromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            try {
                outputStream.writeObject(
                        new PurchaseMessageBuilder()
                                .deletePurchaseRequest(purchase.getId())
                                .build()
                );
                Boolean result = (Boolean) inputStream.readObject();
                if(result)
                    successPromise.apply();
                else
                    failurePromise.apply(null);
            } catch (IOException | ClassNotFoundException e) {
                failurePromise.apply(e);
            }
        }).start();
    }
    public void getClientPurchases(User user, MultiplePurchasesFunction successPromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            List<Object> purchases = null;
            try {
                outputStream.writeObject(
                        new PurchaseMessageBuilder()
                                .selectPurchasesByClientRequest(user)
                                .build()
                );
                Object obj = inputStream.readObject();
                if (obj.getClass().isArray()) {
                    purchases = Arrays.asList((Object[])obj);
                } else if (obj instanceof Collection) {
                    purchases = new ArrayList<>((Collection<?>)obj);
                }

            } catch (IOException | ClassNotFoundException e) {
                failurePromise.apply(e);
                return;
            }
            if(purchases == null || purchases.isEmpty()) {
                failurePromise.apply(null);
                return;
            }
            successPromise.apply(
                    purchases.stream().map(o -> (Purchase) o).collect(Collectors.toList())
            );
        }).start();
    }
    public void getAllPurchases(MultiplePurchasesFunction successPromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            List<Object> purchases = null;
            try {
                outputStream.writeObject(
                        new PurchaseMessageBuilder()
                                .selectAllPurchases()
                                .build()
                );
                Object obj = inputStream.readObject();
                if (obj.getClass().isArray()) {
                    purchases = Arrays.asList((Object[])obj);
                } else if (obj instanceof Collection) {
                    purchases = new ArrayList<>((Collection<?>)obj);
                }

            } catch (IOException | ClassNotFoundException e) {
                failurePromise.apply(e);
                return;
            }
            if(purchases == null || purchases.isEmpty()) {
                failurePromise.apply(null);
                return;
            }
            successPromise.apply(
                    purchases.stream().map(o -> (Purchase) o).collect(Collectors.toList())
            );
        }).start();
    }
    public void getNotStoredProducts(List<Product> products, ProductFunction successPromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            products.forEach(product -> product.setImage(null));
            try {
                outputStream.writeObject(
                        new ProductMessageBuilder()
                                .requestOfMissingProducts((ArrayList<Product>) products)
                                .build()
                );
                Integer numOfProducts = (Integer) inputStream.readObject();
                for (int i = 0; i < numOfProducts; i++) {
                    Product product = (Product) inputStream.readObject();
                    successPromise.apply(product);
                }
            } catch (IOException | ClassNotFoundException e) {
                failurePromise.apply(e);
                return;
            }
        }).start();
    }
    public void getUpdatedProducts(List<Product> products, ProductFunction successPromise, ErrorFunction failurePromise) {
        new Thread(() -> {
            try {
                outputStream.writeObject(
                        new ProductMessageBuilder()
                                .requestOfUpdatedProducts((ArrayList<Product>) products)
                                .build()
                );

                Integer numOfProducts = (Integer) inputStream.readObject();
                for (int i = 0; i < numOfProducts; i++) {
                    Product product = (Product) inputStream.readObject();
                    successPromise.apply(product);
                }
            } catch (IOException | ClassNotFoundException e) {
                failurePromise.apply(e);
                return;
            }
        }).start();
    }
    void productsUpdated() {
        new Thread(productsUpdatedPromise::apply).start();
    }
    void connectionLost() {
        new Thread(connectionLostPromise::apply).start();
    }
}
