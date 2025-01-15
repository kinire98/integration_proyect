package com.kinire.proyectointegrador.db.db_access.DAOs;

import com.kinire.proyectointegrador.components.Product;

import java.util.List;

public interface ProductDAO {
    boolean insertProduct(Product product);
    Product selectProductById(long id);
    List<Product> selectProductByCategory(long categoryId);
    List<Product> getProductsByIds(long[] ids);
    List<Product> getAllProducts();
    boolean updateProduct(long id, Product product);
    boolean deleteProduct(long id);
}
