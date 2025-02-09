package com.wegroceries.wegroceriesapi.orders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wegroceries.wegroceriesapi.products.Product;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create a new order with all parameters
    @PostMapping
    public ResponseEntity<Order> createOrder(
        @RequestParam String itemName,
        @RequestParam String category,
        @RequestParam BigDecimal price,
        @RequestParam String seller,
        @RequestParam String buyer,
        @RequestParam Instant transactionDate,
        @RequestParam Product product) {

        // Create the order using the provided parameters
        Order order = new Order(itemName, category, price, seller, buyer, transactionDate, product);
        
        // Save the order using the service
        Order createdOrder = orderService.createOrder(
            order.getItemName(),
            order.getCategory(),
            order.getPrice(),
            order.getSeller(),
            order.getBuyer()
        );

        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Update an order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable UUID id, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    // Delete an order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
