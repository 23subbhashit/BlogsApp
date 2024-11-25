package com.example.blogs.controllers;


import com.example.blogs.Contollers.CategoryController;
import com.example.blogs.payloads.ApiResponse;
import com.example.blogs.payloads.CategoryDto;
import com.example.blogs.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    public CategoryControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        // Given
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryTitle("Tech");
        categoryDto.setCategoryDescription("Technology-related posts");

        when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(categoryDto);

        // When
        ResponseEntity<CategoryDto> response = categoryController.createCategory(categoryDto);

        // Then
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Tech", response.getBody().getCategoryTitle());
        verify(categoryService, times(1)).createCategory(any(CategoryDto.class));
    }

    @Test
    public void testUpdateCategory() {
        // Given
        Integer categoryId = 1;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryTitle("Updated Tech");
        categoryDto.setCategoryDescription("Updated Technology-related posts");

        when(categoryService.updateCategory(any(CategoryDto.class), eq(categoryId))).thenReturn(categoryDto);

        // When
        ResponseEntity<CategoryDto> response = categoryController.updateCategory(categoryDto, categoryId);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Updated Tech", response.getBody().getCategoryTitle());
        verify(categoryService, times(1)).updateCategory(any(CategoryDto.class), eq(categoryId));
    }

    @Test
    public void testDeleteCategory() {
        // Given
        Integer categoryId = 1;
        doNothing().when(categoryService).deleteCategory(categoryId);

        // When
        ResponseEntity<ApiResponse> response = categoryController.deleteCategory(categoryId);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Category is deleted Successfully", response.getBody().getMessage());
        verify(categoryService, times(1)).deleteCategory(categoryId);
    }

    @Test
    public void testGetCategory() {
        // Given
        Integer categoryId = 1;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryTitle("Tech");
        categoryDto.setCategoryDescription("Technology-related posts");

        when(categoryService.getCategory(categoryId)).thenReturn(categoryDto);

        // When
        ResponseEntity<CategoryDto> response = categoryController.getCategory(categoryId);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Tech", response.getBody().getCategoryTitle());
        verify(categoryService, times(1)).getCategory(categoryId);
    }

    @Test
    public void testGetCategories() {
        // Given
        List<CategoryDto> categories = new ArrayList<>();
        CategoryDto techCategory = new CategoryDto();
        techCategory.setCategoryTitle("Tech");
        techCategory.setCategoryDescription("Technology-related posts");

        CategoryDto healthCategory = new CategoryDto();
        healthCategory.setCategoryTitle("Health");
        healthCategory.setCategoryDescription("Health-related posts");

        categories.add(techCategory);
        categories.add(healthCategory);

        when(categoryService.getCategories()).thenReturn(categories);

        // When
        ResponseEntity<List<CategoryDto>> response = categoryController.getCategories();

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Tech", response.getBody().get(0).getCategoryTitle());
        verify(categoryService, times(1)).getCategories();
    }
}
