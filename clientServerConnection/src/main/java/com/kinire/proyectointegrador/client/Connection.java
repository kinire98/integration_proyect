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

/**
 * Clase manejadora de las conexiones TCP
 */
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

    /**
     * Comprueba si se ha inicado la instancia de la conexión
     * @return Si se ha iniciado la instancia de la conexión
     */
    public static boolean isInstanceStarted() {
        return self != null;
    }

    /**
     * Inicia la instancia de la conexión y ejecuta una promesa justo después
     * @param promise Promesa a ejeutar tras iniciar la conexión
     */
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

    /**
     * Establece una promesa que se ejecutará en caso de que actualice algún producto
     * @param productsUpdatedPromise Promesa a ejecutar
     */
    public void setProductsUpdatedPromise(EmptyFunction productsUpdatedPromise) {
        this.productsUpdatedPromise = productsUpdatedPromise;
    }

    /**
     * Establece una promesa que se ejecutará en caso de que se pierda la conexión
     * @param connectionLostPromise Promesa a ejecutar
     */
    public void setConnectionLostPromise(EmptyFunction connectionLostPromise) {
        this.connectionLostPromise = connectionLostPromise;
    }

    /**
     * Cierra la conexión
     * @throws IOException Excepción de entrada salida
     */
    public void close() throws IOException {
        this.udpConnection.close();
        this.inputStream.close();
        this.outputStream.close();
        this.socket.close();
    }

    /**
     * Pide todos los productos que contiene la base de datos remota
     * @param successPromise Promesa que recibe un producto que se ejecutará cada vez que se reciba uno
     * @param finishPromise Promesa que se ejecutará al final de recibir todos los productos
     * @param failurePromise Promesa en caso de que se produzca algún fallo
     */
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

    /**
     * Añade un producto (es update cómo en cargar o insertar no actualizar)
     * @param product El producto a añadir
     * @param imageStream El flujo de la imagen
     * @param successPromise Promesa en caso de éxito
     * @param failurePromise Promesa en caso de fracaso
     */
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

    /**
     * Borra un prodcuto de la base de datos (sin uso en el proeycto)
     * @param product El producto a eliminar
     * @param successPromise Promesa en caso de éxito
     * @param failurePromise Promesa en caso de fracaso
     */
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

    /**
     * Función que se encarga de comprobar si existe un usuario con el nombre de usuario recibido por parámetro
     * @param username El nombre de usuario
     * @param truePromise Promesa en caso de que sí exista
     * @param falsePromise Promesa en caso de que no exista
     * @param failurePromise Promesa en caso de fracaso
     */
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

    /**
     * Comprueba si la información del usuario es correcta, incluyendo la contraseña.
     * SE DEBE DE HABER LLAMADO ANTES AL MÉTODO userExists
     * @param user El usuario a comprobar
     * @param truePromise Promesa en caso de datos correctos
     * @param falsePromise Promesa en caso de datos incorrectos
     * @param failurePromise Promesa en caso de fallo
     */
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

    /**
     * Envía una solicitud para crear un nuevo usuario
     * @param user El usuario a crear
     * @param successPromise Promesa en caso de éxito
     * @param failurePromise Promesa en caso de fracaso
     */
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

    /**
     * Envía una solicitud de guardado de compra
     * @param purchase La compra a guardar
     * @param successPromise Promesa en caso de éxito
     * @param failurePromise Promesa en caso de fracaso
     */
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

    /**
     * Envía una solicitud para borrar una compra
     * @param purchase La compra a borrar
     * @param successPromise Promesa en caso de éxito
     * @param failurePromise Promesa en caso de fracaso
     */
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

    /**
     * Solicitud para obtener todas las compras de un usuario
     * @param user El usuario en cuestión
     * @param successPromise Promesa que recibe una lista de compras en caso de éxito
     * @param failurePromise Promesa en caso de error
     */
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

    /**
     * Envía una solicitud para obtener todas las compras.
     * Solo debe ejecutarse en caso de que el usuario sea un usuario administrador
     * @param successPromise Promesa que recibe las compras en caso de éxito
     * @param failurePromise Promesa en caso de fracaso
     */
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

    /**
     * Enviá una solititud para recibir todos los productos que no se tienen actualmente
     * @param products Los productos que se tienen actualmente
     * @param successPromise Promesa que recibe un producto a la vez que no tiene el usuario
     * @param failurePromise Promesa en caso de fracaso
     */
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

    /**
     * Envía una solicitud para obtener los productos que han sido actualizados.
     * (No implementado en los clientes)
     * @param products Los productos que se tienen actualmente
     * @param successPromise Promesa en caso de éxito que recibe un producto al mismo tiempo
     * @param failurePromise Promesa en caso de fracaso
     */
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

    /**
     * Ejecuta la promesa guardad para cuándo se actualizan los productos
     */
    void productsUpdated() {
        new Thread(productsUpdatedPromise::apply).start();
    }

    /**
     * Ejercuta la promesa guardad para cuándo se pierde la conexión
     */
    void connectionLost() {
        new Thread(connectionLostPromise::apply).start();
    }
}
