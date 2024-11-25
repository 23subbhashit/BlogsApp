package com.example.blogs.repositories;

import com.example.blogs.Repositories.CategoryRepository;
import com.example.blogs.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        categoryRepository.deleteAll(); // Clean up the database before each test
    }

    @Test
    public void testSaveCategory() {
        // Given
        Category category = new Category();
        category.setCategoryTitle("Tech");
        category.setCategoryDescription("Technology-related posts");

        // When
        Category savedCategory = categoryRepository.save(category);

        // Then
        assertNotNull(savedCategory.getCategoryId());
        assertEquals("Tech", savedCategory.getCategoryTitle());
        assertEquals("Technology-related posts", savedCategory.getCategoryDescription());
    }

    @Test
    public void testFindById() {
        // Given
        Category category = new Category();
        category.setCategoryTitle("Tech");
        category.setCategoryDescription("Technology-related posts");
        Category savedCategory = categoryRepository.save(category);

        // When
        Optional<Category> fetchedCategory = categoryRepository.findById(savedCategory.getCategoryId());

        // Then
        assertTrue(fetchedCategory.isPresent());
        assertEquals("Tech", fetchedCategory.get().getCategoryTitle());
    }

    @Test
    public void testFindAll() {
        // Given
        Category category1 = new Category();
        category1.setCategoryTitle("Tech");
        category1.setCategoryDescription("Technology-related posts");

        Category category2 = new Category();
        category2.setCategoryTitle("Health");
        category2.setCategoryDescription("Health-related posts");

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        // When
        List<Category> categories = categoryRepository.findAll();

        // Then
        assertEquals(2, categories.size());
        assertEquals("Tech", categories.get(0).getCategoryTitle());
        assertEquals("Health", categories.get(1).getCategoryTitle());
    }

    @Test
    public void testDeleteCategory() {
        // Given
        Category category = new Category();
        category.setCategoryTitle("Tech");
        category.setCategoryDescription("Technology-related posts");
        Category savedCategory = categoryRepository.save(category);

        // When
        categoryRepository.deleteById(savedCategory.getCategoryId());
        Optional<Category> deletedCategory = categoryRepository.findById(savedCategory.getCategoryId());

        // Then
        assertFalse(deletedCategory.isPresent());
    }
}
