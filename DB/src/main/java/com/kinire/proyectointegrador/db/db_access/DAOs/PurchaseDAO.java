package com.kinire.proyectointegrador.db.db_access.DAOs;

import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.User;

import java.time.LocalDate;
import java.util.List;


/**
 * Definición del DAO de las compras
 */
public interface PurchaseDAO {

    /**
     * Inserción de compras
     * @param purchase Compra a insertar
     * @return Inserción exitosa
     */
    boolean insertPurchase(Purchase purchase);

    /**
     * Selección de compra por identificador
     * @param id Identificador de compra
     * @return Compra
     */
    Purchase selectPurchase(long id);

    /**
     * Selección de todas las compras de la base de datos
     * @return Todas las compras de la base de datos
     */
    List<Purchase> getAllPurchases();

    /**
     * Selección de compras por cliente
     * @param user Cliente
     * @return Compras del dicho cliente
     */
    List<Purchase> selectPurchaseByClient(User user);

    /**
     * Selección de comrpas por mes
     * @param date Fecha de la que se quieren conoceer las compras
     * @return Lista con todas las compras
     */
    List<Purchase> selectPurchaseByMonth(LocalDate date);

    /**
     * Actualización de comrpra
     * @param id Identificador de la compra
     * @param purchase Inforamción sobre la compra
     * @return Actualización exitosa
     */
    boolean updatePurchase(long id, Purchase purchase);

    /**
     * Borrado de la compra
     * @param id Identificador de la compra
     * @return Borrado exitoso
     */
    boolean deletePurchase(long id);
}
