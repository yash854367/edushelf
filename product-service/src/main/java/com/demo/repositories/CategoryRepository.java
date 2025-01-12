package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Custom query to fetch subcategories for a given category
    @Query("SELECT c FROM Category c WHERE c.parentCategory.id = :parentId")
    List<Category> findSubcategoriesByParentId(@Param("parentId") Long parentId);

    // Custom query to fetch top-level categories (no parent)
    @Query("SELECT c FROM Category c WHERE c.parentCategory IS NULL")
    List<Category> findTopLevelCategories();
    

}