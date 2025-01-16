package com.kinire.proyectointegrador.db.db_access.DAOs;

import com.kinire.proyectointegrador.components.Product;

import java.util.List;

public interface ProductDAO {
    boolean insertProduct(Product product);
    Product selectProductById(long id);
    List<Product> selectProductByCategory(long categoryId);
    List<Product> selectProductsByIds(long[] ids);//
    List<Product> selectAllProducts();//
    List<Product> selectMissingProducts(List<Product> productsAlreadyFetched);
    List<Product> selectUpdatedProducts(List<Product> productsToCheck);
    boolean updateProduct(long id, Product product);
    boolean deleteProduct(long id);
}
