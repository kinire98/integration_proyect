package com.kinire.proyectointegrador.purchases;

import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class PurchaseMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 7L;

    private final boolean insertPurchaseRequest;

    private final boolean selectSinglePurchaseRequest;

    private final boolean selectPurchasesByClientRequest;

    private final boolean deletePurchaseRequest;

    private final long id;

    private final User user;

    private final Purchase purchase;

    public PurchaseMessage(boolean insertPurchaseRequest, boolean selectSinglePurchaseRequest,
                           boolean selectPurchasesByClientRequest, boolean deletePurchaseRequest,
                           long id, User user, Purchase purchase) {
        this.insertPurchaseRequest = insertPurchaseRequest;
        this.selectSinglePurchaseRequest = selectSinglePurchaseRequest;
        this.selectPurchasesByClientRequest = selectPurchasesByClientRequest;
        this.deletePurchaseRequest = deletePurchaseRequest;
        this.id = id;
        this.user = user;
        this.purchase = purchase;
    }

    public boolean isInsertPurchaseRequest() {
        return insertPurchaseRequest;
    }

    public boolean isSelectSinglePurchaseRequest() {
        return selectSinglePurchaseRequest;
    }

    public boolean isSelectPurchasesByClientRequest() {
        return selectPurchasesByClientRequest;
    }

    public boolean isDeletePurchaseRequest() {
        return deletePurchaseRequest;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Purchase getPurchase() {
        return purchase;
    }
}
