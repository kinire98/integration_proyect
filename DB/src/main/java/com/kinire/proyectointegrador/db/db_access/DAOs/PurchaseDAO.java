package com.kinire.proyectointegrador.db.db_access.DAOs;

import com.kinire.proyectointegrador.components.Purchase;
import com.kinire.proyectointegrador.components.User;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseDAO {
    boolean insertPurchase(Purchase purchase);
    Purchase selectPurchase(long id);
    List<Purchase> selectPurchaseByClient(User user);
    List<Purchase> selectPurchaseByMonth(LocalDate date);
    boolean updatePurchase(long id, Purchase purchase);
    boolean deletePurchase(long id);
}
