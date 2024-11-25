package com.example.blogs.controllers;

import com.example.blogs.Repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        categoryRepository.deleteAll(); // Clear repository before each test
    }

    @Test
    public void testCreateCategoryIntegration() throws Exception {
        String categoryJson = """
            {
                "categoryTitle": "Tech",
                "categoryDescription": "Technology-related posts"
            }
            """;

        mockMvc.perform(post("/api/v1/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Origin", "http://localhost:3000") // Set allowed origin
                        .content(categoryJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryTitle").value("Tech"))
                .andExpect(jsonPath("$.categoryDescription").value("Technology-related posts"));
    }

    @Test
    public void testGetCategoriesIntegration() throws Exception {
        String categoryJson = """
            {
                "categoryTitle": "Tech",
                "categoryDescription": "Technology-related posts"
            }
            """;



        // Get all categories
        mockMvc.perform(get("/api/v1/categories/")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Origin", "http://localhost:3000")) // Set allowed origin
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateCategoryDisallowedOrigin() throws Exception {
        String categoryJson = """
            {
                "categoryTitle": "Hack",
                "categoryDescription": "Malicious posts"
            }
            """;

        mockMvc.perform(post("/api/v1/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Origin", "http://evil.com") // Disallowed origin
                        .content(categoryJson))
                .andExpect(status().isForbidden()); // Expect 403 Forbidden for disallowed origin
    }
}
