package com.kinire.proyectointegrador.db.db_access.DAOs;

import com.kinire.proyectointegrador.components.Category;

public interface CategoryDAO {
    boolean insertCategory(Category category);
    Category selectCategory(long id);
    boolean updateCategory(long id, Category category);
    boolean deleteCategory(long id);
}
