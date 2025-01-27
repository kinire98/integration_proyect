package com.kinire.proyectointegrador.db.db_access.DAOs;

import com.kinire.proyectointegrador.components.Category;

import java.util.List;

/**
 * Definición del DAO para las coategorías
 */
public interface CategoryDAO {
    /**
     * Inserta una categoría en la base de datos. Se debe crear una nueva categoría en caso de que el nombre no exista ya
     * @param category Categoría a insertar
     * @return Inserción exitosa
     */
    boolean insertCategory(Category category);

    /**
     * Selecciona la categoría por ID
     * @param id Identificador de la categoría
     * @return Categaría correspondiente al identificador. Nulo si no existe
     */
    Category selectCategory(long id);

    /**
     * Devuelve la primera categoría por nombre
     * @param name Nombre de la categoria
     * @return Primera categoría
     */
    Category selectByName(String name);

    /**
     * Devuelve todas las categorías
     * @return Todas las categorías
     */
    List<Category> selectAll();

    /**
     * Actualiza la categoría
     * @param id El identificador de la categoría
     * @param category Categoría per sé
     * @return Actualización exitosa
     */
    boolean updateCategory(long id, Category category);

    /**
     * Borra una categoría
     * @param id Identificador de la categoría
     * @return Borrado exitoso
     */
    boolean deleteCategory(long id);
}
