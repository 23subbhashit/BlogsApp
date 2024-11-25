package com.example.blogs.services;

import com.example.blogs.Repositories.CategoryRepository;
import com.example.blogs.entities.Category;
import com.example.blogs.exceptions.ResourceNotFoundException;
import com.example.blogs.payloads.CategoryDto;
import com.example.blogs.services.implementations.CategoryImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class  CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryImplementation categoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        // Given
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryTitle("Tech");
        category.setCategoryDescription("Technology-related posts");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryTitle("Tech");
        categoryDto.setCategoryDescription("Technology-related posts");

        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        // When
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);

        // Then
        assertNotNull(createdCategory);
        assertEquals("Tech", createdCategory.getCategoryTitle());
        assertEquals("Technology-related posts", createdCategory.getCategoryDescription());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testUpdateCategory() {
        // Given
        Integer categoryId = 1;
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryTitle("Tech");
        category.setCategoryDescription("Technology-related posts");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryTitle("Updated Tech");
        categoryDto.setCategoryDescription("Updated Technology-related posts");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        // When
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);

        // Then
        assertNotNull(updatedCategory);
        assertEquals("Updated Tech", updatedCategory.getCategoryTitle());
        assertEquals("Updated Technology-related posts", updatedCategory.getCategoryDescription());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testDeleteCategory() {
        // Given
        Integer categoryId = 1;
        Category category = new Category();
        category.setCategoryId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);

        // When
        categoryService.deleteCategory(categoryId);

        // Then
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    public void testGetCategory() {
        // Given
        Integer categoryId = 1;
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryTitle("Tech");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryTitle("Tech");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        // When
        CategoryDto fetchedCategory = categoryService.getCategory(categoryId);

        // Then
        assertNotNull(fetchedCategory);
        assertEquals("Tech", fetchedCategory.getCategoryTitle());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    public void testGetCategories() {
        // Given
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setCategoryTitle("Tech");

        Category category2 = new Category();
        category2.setCategoryTitle("Health");

        categories.add(category1);
        categories.add(category2);

        CategoryDto dto1 = new CategoryDto();
        dto1.setCategoryTitle("Tech");

        CategoryDto dto2 = new CategoryDto();
        dto2.setCategoryTitle("Health");

        when(categoryRepository.findAll()).thenReturn(categories);
        when(modelMapper.map(category1, CategoryDto.class)).thenReturn(dto1);
        when(modelMapper.map(category2, CategoryDto.class)).thenReturn(dto2);

        // When
        List<CategoryDto> fetchedCategories = categoryService.getCategories();

        // Then
        assertNotNull(fetchedCategories);
        assertEquals(2, fetchedCategories.size());
        assertEquals("Tech", fetchedCategories.get(0).getCategoryTitle());
        assertEquals("Health", fetchedCategories.get(1).getCategoryTitle());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testGetCategoryNotFound() {
        // Given
        Integer categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategory(categoryId));
        verify(categoryRepository, times(1)).findById(categoryId);
    }
}
