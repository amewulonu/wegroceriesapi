package com.wegroceries.categories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegroceries.wegroceriesapi.categories.Category;
import com.wegroceries.wegroceriesapi.categories.CategoryController;
import com.wegroceries.wegroceriesapi.categories.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoiesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID categoryId;
    private Optional<Category> category;

    @BeforeEach
    void setUp() {
        categoryId = UUID.randomUUID();
        category = Optional.ofNullable(new Category("Electronics", "Category for all electronic products"));
    }

    // Test case: Create Category Successfully
    @Test
    void testCreateCategory_Success() throws Exception {
        when(categoryService.createCategory(any())).thenReturn(category.orElse(null));

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Electronics"))
                .andExpect(jsonPath("$.description").value("Category for all electronic products"));
    }

    // Test case: Get Category By ID - Success
    @Test
    void testGetCategoryById_Success() throws Exception {
        when(categoryService.getCategoryById(categoryId)).thenReturn(category);

        mockMvc.perform(get("/api/categories/" + categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    // Test case: Get Category By ID - Not Found
    @Test
    void testGetCategoryById_NotFound() throws Exception {
        when(categoryService.getCategoryById(categoryId)).thenThrow(new IllegalArgumentException("Category not found"));

        mockMvc.perform(get("/api/categories/" + categoryId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Category not found"));
    }

    // Test case: Get All Categories
    @Test
    void testGetAllCategories() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(List.of(category.orElse(null)));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    // Test case: Update Category Successfully
    @Test
    void testUpdateCategory_Success() throws Exception {
        when(categoryService.updateCategory(any(), any())).thenReturn(category);

        mockMvc.perform(put("/api/categories/" + categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    // Test case: Delete Category Successfully
    @Test
    void testDeleteCategory_Success() throws Exception {
        Mockito.doNothing().when(categoryService).deleteCategory(categoryId);

        mockMvc.perform(delete("/api/categories/" + categoryId))
                .andExpect(status().isNoContent());
    }

    // Test case: Delete Category - Not Found
    @Test
    void testDeleteCategory_NotFound() throws Exception {
        Mockito.doThrow(new IllegalArgumentException("Category not found")).when(categoryService).deleteCategory(categoryId);

        mockMvc.perform(delete("/api/categories/" + categoryId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Category not found"));
    }

    // Test case: Get Categories By Name
    @Test
    void testGetCategoriesByName() throws Exception {
        when(categoryService.getCategoriesByName("Electronics")).thenReturn(List.of(category));

        mockMvc.perform(get("/api/categories/name/Electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}

