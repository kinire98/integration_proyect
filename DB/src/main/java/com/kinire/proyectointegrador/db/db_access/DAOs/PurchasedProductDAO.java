package com.kinire.proyectointegrador.db.db_access.DAOs;

import com.kinire.proyectointegrador.components.ShoppingCartItem;

import java.util.List;

public interface PurchasedProductDAO {
    boolean insertPurchasedProduct(ShoppingCartItem shoppingCartItem, long purchaseId);
    int bulkInsertPurchasedProduct(List<ShoppingCartItem> shoppingCartItems, long purchaseId);
    ShoppingCartItem selectPurchasedProduct(long purchaseId, long productId);
    List<ShoppingCartItem> selectByPurchase(long purchaseId);
    List<ShoppingCartItem> selectByProduct(long productId);
    boolean updatePurchasedProduct(long purchaseId, long productId, ShoppingCartItem item);
    boolean deletePurchasedProduct(long purchaseId, long productId);
    boolean deleteByProductId(long productId);
    boolean deleteByPurchaseId(long purchaseId);
}
