package com.kinire.proyectointegrador.products;

import com.kinire.proyectointegrador.components.Product;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class ProductMessage implements Serializable {


    @Serial
    private static final long serialVersionUID = 6L;

    private final boolean allProductsRequest;

    private final boolean singleProductRequest;

    private final boolean insertProductRequest;

    private final boolean requestByCategory;

    private final boolean requestOfMissingProducts;

    private final boolean requestOfUpdatedProducts;

    private final boolean requestByIds;

    private final boolean deleteProductRequest;

    private final long[] ids;

    private final long id;

    private final ArrayList<Product> products;

    private final Product product;

    private final byte[] imageStream;


    ProductMessage(boolean allProductsRequest, boolean singleProductRequest, boolean insertProductRequest,
                    boolean requestByCategory, boolean requestOfMissingProducts, boolean requestOfUpdatedProducts,
                    boolean requestByIds, boolean deleteProductRequest, long[] ids, long id, ArrayList<Product> products, Product product,
                   byte[] imageStream) {
        this.allProductsRequest = allProductsRequest;
        this.singleProductRequest = singleProductRequest;
        this.insertProductRequest = insertProductRequest;
        this.requestByCategory = requestByCategory;
        this.requestOfMissingProducts = requestOfMissingProducts;
        this.requestOfUpdatedProducts = requestOfUpdatedProducts;
        this.requestByIds = requestByIds;
        this.deleteProductRequest = deleteProductRequest;
        this.ids = ids;
        this.id = id;
        this.products = products;
        this.product = product;
        this.imageStream = imageStream;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public long getId() {
        return id;
    }

    public long[] getIds() {
        return ids;
    }

    public Product getProduct() {
        return product;
    }

    public InputStream getImageStream() {
        return new ByteArrayInputStream(imageStream);
    }

    public boolean isAllProductsRequest() {
        return allProductsRequest;
    }

    public boolean isSingleProductRequest() {
        return singleProductRequest;
    }

    public boolean isRequestByCategory() {
        return requestByCategory;
    }

    public boolean isRequestByIds() {
        return requestByIds;
    }

    public boolean isRequestOfMissingProducts() {
        return requestOfMissingProducts;
    }

    public boolean isRequestOfUpdatedProducts() {
        return requestOfUpdatedProducts;
    }

    public boolean isInsertProductRequest() {
        return insertProductRequest;
    }

    public boolean isDeleteProductRequest() {
        return deleteProductRequest;
    }
}
