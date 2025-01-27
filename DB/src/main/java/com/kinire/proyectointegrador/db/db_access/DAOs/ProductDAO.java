package com.kinire.proyectointegrador.db.db_access.DAOs;

import com.kinire.proyectointegrador.components.Product;

import java.util.List;

/**
 * Definición del DAO de los productos
 */
public interface ProductDAO {

    /**
     * Inserción del producto
     * @param product Producto a insertar. No se guardará la imagen en la base de datos. Se debería guardar en el sistema de archivos
     * @return Inserción exitosa
     */
    boolean insertProduct(Product product);

    /**
     * Selección de producto por identificador
     * @param id Identificador del producto
     * @return Producto correspondiente al identificador
     */
    Product selectProductById(long id);

    /**
     * Selección de productos por identificador de categoría
     * @param categoryId Identificador de categoría
     * @return Lista de categorías
     */
    List<Product> selectProductByCategory(long categoryId);

    /**
     * Selección de productos que coincidan con los identificadores correspondientes
     * @param ids Lista de identificadores
     * @return Lista de productos
     */
    List<Product> selectProductsByIds(long[] ids);//

    /**
     * Selección de todos los productos
     * @return Lista con todos los productos
     */
    List<Product> selectAllProducts();//

    /**
     * Selección con todos los productos que no se tienen ya
     * @param productsAlreadyFetched Productos que ya se tienen
     * @return Lista con los productos que no se tienen
     */
    List<Product> selectMissingProducts(List<Product> productsAlreadyFetched);

    /**
     * Selección de los productos actualizados
     * @param productsToCheck Productos a comprobar
     * @return Lista de productos que han sido actualizados
     */
    List<Product> selectUpdatedProducts(List<Product> productsToCheck);

    /**
     * Actualización de productos
     * @param id Identificador de producto
     * @param product Producto cuya información se va a guardar
     * @return Actualización exitosa
     */
    boolean updateProduct(long id, Product product);

    /**
     * Borrado de producto
     * @param id Identificador de productos
     * @return Borrado exitoso
     */
    boolean deleteProduct(long id);
}
