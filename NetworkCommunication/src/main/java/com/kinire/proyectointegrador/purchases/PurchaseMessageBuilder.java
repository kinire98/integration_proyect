package com.kinire.proyectointegrador.purchases;

import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.User;

public class PurchaseMessageBuilder {

    private boolean insertPurchaseRequest;

    private boolean selectAllPurchases;

    private boolean selectSinglePurchaseRequest;

    private boolean selectPurchasesByClientRequest;

    private boolean deletePurchaseRequest;

    private long id;

    private User user;

    private Purchase purchase;

    public PurchaseMessageBuilder() {}

    public PurchaseMessageBuilder insertPurchaseRequest(Purchase purchase) {
        if(
                this.selectSinglePurchaseRequest ||
                        this.selectAllPurchases ||
                        this.selectPurchasesByClientRequest ||
                        this.deletePurchaseRequest
        )
            throw new IllegalStateException("Only one request at a time");
        this.insertPurchaseRequest = true;
        this.purchase = purchase;
        return this;
    }

    public PurchaseMessageBuilder selectAllPurchases() {
        if(
                this.selectSinglePurchaseRequest ||
                        this.insertPurchaseRequest ||
                        this.selectPurchasesByClientRequest ||
                        this.deletePurchaseRequest
        )
            throw new IllegalStateException("Only one request at a time");
        this.selectAllPurchases = true;
        return this;
    }

    public PurchaseMessageBuilder selectSinglePurchaseRequest(long id) {
        if(
                this.insertPurchaseRequest ||
                        this.selectAllPurchases ||
                        this.selectPurchasesByClientRequest ||
                        this.deletePurchaseRequest
        )
            throw new IllegalStateException("Only one request at a time");
        this.selectSinglePurchaseRequest = true;
        this.id = id;
        return this;
    }

    public PurchaseMessageBuilder selectPurchasesByClientRequest(User user) {
        if(
                this.insertPurchaseRequest ||
                        this.selectAllPurchases ||
                        this.selectSinglePurchaseRequest ||
                        this.deletePurchaseRequest
        )
            throw new IllegalStateException("Only one request at a time");
        this.selectPurchasesByClientRequest = true;
        this.user = user;
        return this;
    }

    public PurchaseMessageBuilder deletePurchaseRequest(long id) {
        if(
                this.insertPurchaseRequest ||
                        this.selectAllPurchases ||
                        this.selectSinglePurchaseRequest ||
                        this.selectPurchasesByClientRequest
        )
            throw new IllegalStateException("Only one request at a time");
        this.deletePurchaseRequest = true;
        this.id = id;
        return this;
    }

    public PurchaseMessage build() {
        return new PurchaseMessage(
                insertPurchaseRequest,
                selectSinglePurchaseRequest,
                selectAllPurchases,
                selectPurchasesByClientRequest,
                deletePurchaseRequest,
                id,
                user,
                purchase
        );
    }

}
