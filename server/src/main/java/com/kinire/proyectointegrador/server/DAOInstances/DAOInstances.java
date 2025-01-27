package com.kinire.proyectointegrador.server.DAOInstances;

import com.kinire.proyectointegrador.db.db_access.DAOs.*;
import com.kinire.proyectointegrador.db.db_access.impls.*;

/**
 * Instancias de los DAOs
 */
public class DAOInstances {

    private static CategoryDAO categoryDAO;

    private static ProductDAO productDAO;

    private static PurchaseDAO purchaseDAO;

    private static PurchasedProductDAO purchasedProductDAO;

    private static UserDAO userDAO;

    public static CategoryDAO getCategoryDAO() {
        if(categoryDAO == null)
            categoryDAO = new CategoryImpl();
        return categoryDAO;
    }

    public static ProductDAO getProductDAO() {
        if(productDAO == null)
            productDAO = new ProductImpl();
        return productDAO;
    }

    public static PurchaseDAO getPurchaseDAO() {
        if(purchaseDAO == null)
            purchaseDAO = new PurchaseImpl();
        return purchaseDAO;
    }

    public static PurchasedProductDAO getPurchasedProductDAO() {
        if(purchasedProductDAO == null)
            purchasedProductDAO = new PurchaseProductImpl();
        return purchasedProductDAO;
    }

    public static UserDAO getUserDAO() {
        if(userDAO == null)
            userDAO = new UserImpl();
        return userDAO;
    }
}
