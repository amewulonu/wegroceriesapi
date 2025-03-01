package com.wegroceries.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegroceries.wegroceriesapi.products.Product;
import com.wegroceries.wegroceriesapi.users.User;
import com.wegroceries.wegroceriesapi.orders.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.wegroceries.wegroceriesapi.orders.OrderController;
import com.wegroceries.wegroceriesapi.orders.OrderService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper; // Converts objects to JSON

    private UUID orderId;
    private Order order;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        Product product = new Product("Laptop", "Electronics", BigDecimal.valueOf(1500), 5, "TechStore", Instant.now());
        User user = new User("john_doe", "john@example.com", "password123", "John", "Doe", Instant.now(), Instant.now());
        order = new Order("Laptop", "Electronics", BigDecimal.valueOf(1500), "TechStore", "JohnDoe", Instant.now(), product);
        order.setId(orderId);
        order.setUser(user);
    }

    // Test case: Create Order Successfully
    @Test
    void testCreateOrder_Success() throws Exception {
        when(orderService.createOrder(any(), any(), any(), any(), any())).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                .param("itemName", "Laptop")
                .param("category", "Electronics")
                .param("price", "1500")
                .param("seller", "TechStore")
                .param("buyer", "JohnDoe"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.itemName").value("Laptop"))
                .andExpect(jsonPath("$.price").value(1500));
    }

    // Test case: Get Order By ID - Success
    @Test
    void testGetOrderById_Success() throws Exception {
        when(orderService.getOrderById(orderId)).thenReturn(order);

        mockMvc.perform(get("/api/orders/" + orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value("Laptop"));
    }

    // Test case: Get Order By ID - Not Found
    @Test
    void testGetOrderById_NotFound() throws Exception {
        when(orderService.getOrderById(orderId)).thenThrow(new IllegalArgumentException("Order not found"));

        mockMvc.perform(get("/api/orders/" + orderId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order not found"));
    }

    // Test case: Get All Orders
    @Test
    void testGetAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of(order));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    // Test case: Update Order Successfully
    @Test
    void testUpdateOrder_Success() throws Exception {
        when(orderService.updateOrder(any(), any())).thenReturn(order);

        mockMvc.perform(put("/api/orders/" + orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value("Laptop"));
    }

    // Test case: Delete Order Successfully
    @Test
    void testDeleteOrder_Success() throws Exception {
        Mockito.doNothing().when(orderService).deleteOrder(orderId);

        mockMvc.perform(delete("/api/orders/" + orderId))
                .andExpect(status().isNoContent());
    }

    // Test case: Delete Order - Not Found
    @Test
    void testDeleteOrder_NotFound() throws Exception {
        Mockito.doThrow(new IllegalArgumentException("Order not found")).when(orderService).deleteOrder(orderId);

        mockMvc.perform(delete("/api/orders/" + orderId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order not found"));
    }

    // Test case: Get Orders By Buyer
    @Test
    void testGetOrdersByBuyer() throws Exception {
        when(orderService.getOrdersByBuyer("JohnDoe")).thenReturn(List.of(order));

        mockMvc.perform(get("/api/orders/buyer/JohnDoe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    // Test case: Get Orders By Seller
    @Test
    void testGetOrdersBySeller() throws Exception {
        when(orderService.getOrdersBySeller("TechStore")).thenReturn(List.of(order));

        mockMvc.perform(get("/api/orders/seller/TechStore"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}
