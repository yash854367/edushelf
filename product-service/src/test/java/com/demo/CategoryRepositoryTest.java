package com.demo;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.entities.Category;
import com.demo.repositories.CategoryRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateAndReadCategory() {
        // Create a top-level category
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("All electronic items");
        categoryRepository.save(electronics);

        // Create a subcategory
        Category smartphones = new Category();
        smartphones.setName("Smartphones");
        smartphones.setDescription("All smartphones");
        smartphones.setParentCategory(electronics);
        categoryRepository.save(smartphones);

        // Fetch and assert
        Optional<Category> fetchedElectronics = categoryRepository.findById(electronics.getId());
        Assertions.assertTrue(fetchedElectronics.isPresent());
        Assertions.assertEquals("Electronics", fetchedElectronics.get().getName());

        // Fetch subcategories
        List<Category> subcategories = categoryRepository.findSubcategoriesByParentId(electronics.getId());
        Assertions.assertEquals(1, subcategories.size());
        Assertions.assertEquals("Smartphones", subcategories.get(0).getName());
    }

//    @Test
//    public void testUpdateCategory() {
//        // Create and save category
//        Category category = new Category();
//        category.setName("Fashion");
//        category.setDescription("Clothing and accessories");
//        categoryRepository.save(category);
//
//        // Update and save
//        category.setDescription("Updated description for fashion");
//        categoryRepository.save(category);
//
//        // Fetch and assert
//        Optional<Category> updatedCategory = categoryRepository.findById(category.getId());
//        Assertions.assertTrue(updatedCategory.isPresent());
//        Assertions.assertEquals("Updated description for fashion", updatedCategory.get().getDescription());
//    }

//    @Test
//    public void testDeleteCategory() {
//        // Create and save category
//        Category category = new Category();
//        category.setName("Books");
//        category.setDescription("All kinds of books");
//        categoryRepository.save(category);
//
//        // Delete category
//        categoryRepository.deleteById(category.getId());
//
//        // Verify deletion
//        Optional<Category> deletedCategory = categoryRepository.findById(category.getId());
//        Assertions.assertFalse(deletedCategory.isPresent());
//    }

    @Test
    public void testFindTopLevelCategories() {
        // Create top-level categories
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("All electronic items");
        categoryRepository.save(electronics);

        Category fashion = new Category();
        fashion.setName("Fashion");
        fashion.setDescription("Clothing and accessories");
        categoryRepository.save(fashion);

        // Create subcategory
        Category smartphones = new Category();
        smartphones.setName("Smartphones");
        smartphones.setDescription("All smartphones");
        smartphones.setParentCategory(electronics);
        categoryRepository.save(smartphones);

        // Fetch and assert
        List<Category> topLevelCategories = categoryRepository.findTopLevelCategories();
        Assertions.assertEquals(2, topLevelCategories.size());
        topLevelCategories.forEach(System.out::println);
    }
}
