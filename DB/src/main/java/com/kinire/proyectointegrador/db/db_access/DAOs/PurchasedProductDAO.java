package com.kinire.proyectointegrador.db.db_access.DAOs;

import com.kinire.proyectointegrador.components.ShoppingCartItem;

import java.util.List;

/**
 * Definición del DAO para la tabla purchased_products
 */
public interface PurchasedProductDAO {
    /**
     * Inserción de una sola compra
     * @param shoppingCartItem Item de la compra
     * @param purchaseId Identificador de la compra
     * @return Inserción exitosa
     */
    boolean insertPurchasedProduct(ShoppingCartItem shoppingCartItem, long purchaseId);

    /**
     * Inserción al por mayor de compras de productos
     * @param shoppingCartItems Lista con productos comprados
     * @param purchaseId Identificador de compra
     * @return Número de productos comprados insertados
     */
    int bulkInsertPurchasedProduct(List<ShoppingCartItem> shoppingCartItems, long purchaseId);

    /**
     * Selección de un único producto comprado
     * @param purchaseId Identificador de compra
     * @param productId identificador de producto
     * @return Producto comprado
     */
    ShoppingCartItem selectPurchasedProduct(long purchaseId, long productId);

    /**
     * Devuelve todos los productos comprados en una compra
     * @param purchaseId Identificador de compra
     * @return Lista con todos los productos comprados
     */
    List<ShoppingCartItem> selectByPurchase(long purchaseId);

    /**
     * Devuelve todos los productos comprados de un producto
     * @param productId Identificador del producto
     * @return Lista de todos los productos comprados
     */
    List<ShoppingCartItem> selectByProduct(long productId);

    /**
     * Acutalización de producto
     * @param purchaseId Identificador de compra
     * @param productId Identificador de producto
     * @param item Información de producto
     * @return Actualización exitosa
     */
    boolean updatePurchasedProduct(long purchaseId, long productId, ShoppingCartItem item);

    /**
     * Borrado de un producto únicamente
     * @param purchaseId Identificador de compra
     * @param productId Identificador de producto
     * @return Borrado exitoso
     */
    boolean deletePurchasedProduct(long purchaseId, long productId);

    /**
     * Borrado de producto comprado por identificador de producto
     * @param productId Identificador de producto
     * @return Borrado exitoso
     */
    boolean deleteByProductId(long productId);

    /**
     * Borrado de producot comprado por identificado de compra
     * @param purchaseId Identificador de compra
     * @return Borrado exitoso
     */
    boolean deleteByPurchaseId(long purchaseId);
}
