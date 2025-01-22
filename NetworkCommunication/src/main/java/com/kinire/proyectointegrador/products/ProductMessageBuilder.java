package com.kinire.proyectointegrador.products;

import com.kinire.proyectointegrador.components.Product;
import com.kinire.proyectointegrador.components.User;
import com.kinire.proyectointegrador.purchases.PurchaseMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProductMessageBuilder {

    private boolean allProductsRequest;

    private boolean singleProductRequest;

    private boolean insertProductRequest;

    private boolean requestByCategory;

    private boolean requestOfMissingProducts;

    private boolean requestOfUpdatedProducts;

    private boolean requestByIds;

    private long[] ids;

    private long id;

    private ArrayList<Product> products;

    private Product product;

    private byte[] imageStream;


    public ProductMessageBuilder() {}

    public ProductMessageBuilder allProductsRequest() {
        if(
                this.singleProductRequest ||
                        this.requestByCategory ||
                        this.requestOfMissingProducts ||
                        this.requestOfUpdatedProducts ||
                        this.requestByIds ||
                        this.insertProductRequest
        )
            throw new IllegalStateException("Only one request at the same time");
        this.allProductsRequest = true;
        return this;
    }

    public ProductMessageBuilder singleProductRequest(long id) {
        if(
                this.allProductsRequest ||
                        this.requestByCategory ||
                        this.requestOfMissingProducts ||
                        this.requestOfUpdatedProducts ||
                        this.requestByIds ||
                        this.insertProductRequest
        )
            throw new IllegalStateException("Only one request at the same time");

        this.singleProductRequest = true;
        this.id = id;
        return this;
    }

    public ProductMessageBuilder requestByCategory(long id) {
        if(
                this.allProductsRequest ||
                        this.singleProductRequest ||
                        this.requestOfMissingProducts ||
                        this.requestOfUpdatedProducts ||
                        this.requestByIds ||
                        this.insertProductRequest
        )
            throw new IllegalStateException("Only one request at the same time");
        this.requestByCategory = true;
        this.id = id;
        return this;
    }

    public ProductMessageBuilder requestOfMissingProducts(ArrayList<Product> products) {
        if(
                this.allProductsRequest ||
                        this.singleProductRequest ||
                        this.requestByCategory ||
                        this.requestOfUpdatedProducts ||
                        this.requestByIds ||
                        this.insertProductRequest
        )
            throw new IllegalStateException("Only one request at the same time");

        this.requestOfMissingProducts = true;
        this.products = products;
        return this;
    }

    public ProductMessageBuilder requestOfUpdatedProducts(ArrayList<Product> products) {
        if(
                this.allProductsRequest ||
                        this.singleProductRequest ||
                        this.requestByCategory ||
                        this.requestOfMissingProducts ||
                        this.requestByIds ||
                        this.insertProductRequest
        )
            throw new IllegalStateException("Only one request at the same time");
        this.requestOfUpdatedProducts = true;
        this.products = products;
        return this;
    }

    public ProductMessageBuilder requestByIds(long[] ids) {
        if(
                this.allProductsRequest ||
                        this.singleProductRequest ||
                        this.requestByCategory ||
                        this.requestOfMissingProducts ||
                        this.requestOfUpdatedProducts ||
                        this.insertProductRequest
        )
            throw new IllegalStateException("Only one request at the same time");
        this.requestByIds = true;
        this.ids = ids;
        return this;
    }
    public ProductMessageBuilder insertProductRequest(Product product, InputStream imageStream) throws IOException {
        if(
                this.allProductsRequest ||
                        this.singleProductRequest ||
                        this.requestByCategory ||
                        this.requestOfMissingProducts ||
                        this.requestOfUpdatedProducts ||
                        this.requestByIds
        )
            throw new IllegalStateException("Only one request at the same time");
        this.insertProductRequest = true;
        this.product = product;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1048576];
        while(imageStream.read(buffer) != -1) {
            outputStream.write(buffer);
        }
        this.imageStream = outputStream.toByteArray();
        return this;
    }

    public ProductMessage build() {
        if(
                       !this.allProductsRequest &&
                       !this.singleProductRequest &&
                       !this.requestByCategory &&
                       !this.requestOfMissingProducts &&
                       !this.requestOfUpdatedProducts &&
                       !this.requestByIds &&
                               !this.insertProductRequest
        )
            throw new IllegalStateException("You have to make at least one request");
        return new ProductMessage(
                allProductsRequest,
                singleProductRequest,
                insertProductRequest,
                requestByCategory,
                requestOfMissingProducts,
                requestOfUpdatedProducts,
                requestByIds,
                ids,
                id,
                products,
                product,
                imageStream
        );
    }
}
