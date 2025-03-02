package com.wegroceries.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegroceries.wegroceriesapi.products.Product;
import com.wegroceries.wegroceriesapi.products.ProductController;
import com.wegroceries.wegroceriesapi.products.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID productId;
    private Product product;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        product = new Product("Laptop", "Electronics", BigDecimal.valueOf(1500), 10, "TechStore", Instant.now());
        product.setId(productId);
    }

    // Test case: Create Product Successfully
    @Test
    void testCreateProduct_Success() throws Exception {
        when(productService.createProduct(any())).thenReturn(product);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.category").value("Electronics"));
    }

    // Test case: Create Product with Validation Error
    @Test
    void testCreateProduct_ValidationError() throws Exception {
        Product invalidProduct = new Product("", "Electronics", BigDecimal.valueOf(1500), 10, "TechStore", Instant.now());

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest());
    }

    // Test case: Get Product By ID - Success
    @Test
    void testGetProductById_Success() throws Exception {
        when(productService.getProductById(productId)).thenReturn(product);

        mockMvc.perform(get("/api/products/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    // Test case: Get Product By ID - Not Found
    @Test
    void testGetProductById_NotFound() throws Exception {
        when(productService.getProductById(productId)).thenThrow(new IllegalArgumentException("Product not found"));

        mockMvc.perform(get("/api/products/" + productId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }

    // Test case: Get All Products
    @Test
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    // Test case: Update Product Successfully
    @Test
    void testUpdateProduct_Success() throws Exception {
        when(productService.updateProduct(any(), any())).thenReturn(product);

        mockMvc.perform(put("/api/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    // Test case: Update Product - Not Found
    @Test
    void testUpdateProduct_NotFound() throws Exception {
        when(productService.updateProduct(any(), any())).thenThrow(new IllegalArgumentException("Product not found"));

        mockMvc.perform(put("/api/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }

    // Test case: Delete Product Successfully
    @Test
    void testDeleteProduct_Success() throws Exception {
        Mockito.doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/products/" + productId))
                .andExpect(status().isNoContent());
    }

    // Test case: Delete Product - Not Found
    @Test
    void testDeleteProduct_NotFound() throws Exception {
        Mockito.doThrow(new IllegalArgumentException("Product not found")).when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/products/" + productId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }

    // Test case: Check if Product Exists - True
    @Test
    void testProductExists_True() throws Exception {
        when(productService.productExists("Laptop", "Electronics")).thenReturn(true);

        mockMvc.perform(get("/api/products/exists")
                .param("name", "Laptop")
                .param("category", "Electronics"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // Test case: Check if Product Exists - False
    @Test
    void testProductExists_False() throws Exception {
        when(productService.productExists("Laptop", "Electronics")).thenReturn(false);

        mockMvc.perform(get("/api/products/exists")
                .param("name", "Laptop")
                .param("category", "Electronics"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}

