package com.kinire.proyectointegrador.server.client_handling;

import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.purchases.PurchaseMessage;
import com.kinire.proyectointegrador.server.DAOInstances.DAOInstances;
import com.kinire.proyectointegrador.server.free_ports.UDPPorts;
import com.kinire.proyectointegrador.products.ProductMessage;
import com.kinire.proyectointegrador.server.updates_signaling.UpdateSignaler;
import com.kinire.proyectointegrador.users.UserMessage;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.Socket;
import java.util.List;
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

    private final UpdateSignaler signaler;

    public ClientHandler(Socket socket, UpdateSignaler signaler) {
        this.socket = socket;
        this.signaler = signaler;
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
    public boolean isConnected() {
        return socket.isConnected();
    }

    public void notifyProductUpdate() {
        udpClientHandler.queueNotificationOfProductUpdate();
    }


    @Override
    public void run() {
        try {
            handleConnections();
        } catch (IOException e) {
            e.printStackTrace();
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
            else if(message instanceof UserMessage)
                handleUserMessage((UserMessage) message);
            else if(message instanceof PurchaseMessage)
                handlePurchaseMessage((PurchaseMessage) message);
        }
    }
    private void handleProductMessage(ProductMessage message) throws IOException {
        logger.log(Level.INFO, "Processing product request");
        if(message.isAllProductsRequest()) {
            List<Product> products = setImages(DAOInstances.getProductDAO().selectAllProducts());
            outputStream.writeObject(products.size());
            for(Product product : products) {
                outputStream.writeObject(
                        product
                );
            }
        } else if(message.isSingleProductRequest()) {
            Product product = DAOInstances.getProductDAO().selectProductById(message.getId());
            product.setImage(readImage(product.getImagePath()));
            outputStream.writeObject(
                    product
            );
        } else if(message.isRequestByCategory()) {
            List<Product> products = setImages(DAOInstances.getProductDAO().selectProductByCategory(message.getId()));
            outputStream.writeObject(products.size());
            for(Product product : products) {
                outputStream.writeObject(
                        product
                );
            }
        } else if(message.isRequestByIds()) {
            List<Product> products = setImages(DAOInstances.getProductDAO().selectProductsByIds(message.getIds()));
            outputStream.writeObject(products.size());
            for(Product product : products) {
                outputStream.writeObject(
                        product
                );
            }
        } else if(message.isRequestOfMissingProducts()) {
            List<Product> products = setImages(DAOInstances.getProductDAO().selectMissingProducts(message.getProducts()));
            outputStream.writeObject(products.size());
            for(Product product : products) {
                outputStream.writeObject(
                        product
                );
            }
        } else if(message.isRequestOfUpdatedProducts()) {
            List<Product> products = setImages(DAOInstances.getProductDAO().selectUpdatedProducts(message.getProducts()));
            outputStream.writeObject(products.size());
            for(Product product : products) {
                outputStream.writeObject(
                    product
                );
            }
        } else if(message.isInsertProductRequest()) {
            if(getBytes(message.getImageStream()).length > 1_000_000_000) {
                outputStream.writeObject(false);
                return;
            }
            if(ImageIO.read(message.getImageStream()) == null) {
                outputStream.writeObject(false);
                return;
            }
            message.getProduct().setImagePath("/root/pictures/" + message.getProduct().getImagePath() + ".png");
            DAOInstances.getCategoryDAO().insertCategory(message.getProduct().getCategory());
            message.getProduct().setCategory(DAOInstances.getCategoryDAO().selectByName(message.getProduct().getCategory().getName()));
            outputStream.writeObject(
                    DAOInstances.getProductDAO().insertProduct(message.getProduct())
            );
            createImage(message.getProduct().getImagePath(), message.getImageStream());
            signaler.signalProductUpdate();
        } else if(message.isDeleteProductRequest()) {
            outputStream.writeObject(
                    DAOInstances.getProductDAO().deleteProduct(message.getProduct().getId())
            );
        }
    }
    private void handleUserMessage(UserMessage message) throws IOException {
        logger.log(Level.INFO, "Processing user request");
        if(message.isSelectUserRequest()) {
            logger.log(Level.INFO, "Processing User data request");
            outputStream.writeObject(
                    DAOInstances.getUserDAO().selectUser(message.getUsername()) != null
            );
        } else if(message.isInsertUserRequest()) {
            logger.log(Level.INFO, "Processing insert User data request");
            outputStream.writeObject(
                    DAOInstances.getUserDAO().insertUser(message.getUser())
            );
        } else if(message.isUserDataCorrectRequest()) {
            logger.log(Level.INFO, "Processing check User data request");
            outputStream.writeObject(
                    DAOInstances.getUserDAO().correctUserData(message.getUser())
            );
        }
        logger.log(Level.INFO, "User request processed");
    }
    private void handlePurchaseMessage(PurchaseMessage message) throws IOException {
        logger.log(Level.INFO, "Processing purchase request");
        if(message.isInsertPurchaseRequest()) {
            outputStream.writeObject(
                    DAOInstances.getPurchaseDAO().insertPurchase(message.getPurchase())
            );
        } else if (message.isSelectSinglePurchaseRequest()) {
            outputStream.writeObject(
                    DAOInstances.getPurchaseDAO().selectPurchase(message.getId())
            );
        } else if (message.isSelectPurchasesByClientRequest()) {
            outputStream.writeObject(
                    DAOInstances.getPurchaseDAO().selectPurchaseByClient(message.getUser())
            );
        } else if(message.isDeletePurchaseRequest()) {
            outputStream.writeObject(
                    DAOInstances.getPurchaseDAO().deletePurchase(message.getId())
            );
        } else if(message.isSelectAllPurchases()) {
            outputStream.writeObject(
                    DAOInstances.getPurchaseDAO().getAllPurchases()
            );
        }
        logger.log(Level.INFO, "Purchase request processed");

    }
    private void createImage(String imagePath, InputStream imageStream) throws IOException {
        File file = new File(imagePath);
        boolean success = file.createNewFile();
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1048576];
        int bytesRead;
        while((bytesRead = imageStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
    }

    private List<Product> setImages(List<Product> products) throws IOException {
        for (Product product : products) {
            product.setImage(readImage(product.getImagePath()));
        }
        return products;
    }
    private byte[] readImage(String imagePath) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = new FileInputStream(new File(imagePath));
        byte[] buffer =  new byte[1048576];
        int bytesRead;
        while((bytesRead = inputStream.read(buffer)) != -1)
            outputStream.write(buffer, 0, bytesRead);
        inputStream.close();
        return outputStream.toByteArray();
    }
    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while((bytesRead = inputStream.read(buffer)) != -1)
            outputStream.write(buffer, 0, bytesRead);
        return outputStream.toByteArray();
    }

}

