package com.kinire.proyectointegrador.server.data_consistency;

import com.kinire.proyectointegrador.components.Category;
import com.kinire.proyectointegrador.server.DAOInstances.DAOInstances;

import java.util.List;

/**
 * Clase que se ejecuta en Daemon que se encarga de evitar que haya nombres de clases duplicados
 */
public class DataConsistency extends Thread {

    private volatile boolean running = true;

    /**
     * Interrumpe el hilo
     */
    public void finish() {
        running = false;
        this.interrupt();
    }
    @Override
    public void run() {
        while (running) {
            List<Category> categories = DAOInstances.getCategoryDAO().selectAll();
            for(Category category : categories) {
                if(DAOInstances.getProductDAO().selectProductByCategory(category.getId()).isEmpty())
                    DAOInstances.getCategoryDAO().deleteCategory(category.getId());
            }
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {}
        }
    }
}
