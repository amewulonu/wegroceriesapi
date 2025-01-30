package com.wegroceries.wegroceriesapi.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create a new order
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestParam String itemName,
                                         @RequestParam String category,
                                         @RequestParam BigDecimal price,
                                         @RequestParam String seller,
                                         @RequestParam String buyer) {
        try {
            Order order = orderService.createOrder(itemName, category, price, seller, buyer);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get an order by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable UUID id) {
        try {
            Order order = orderService.getOrderById(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Update an existing order
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable UUID id, @Valid @RequestBody Order updatedOrder, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try {
            Order order = orderService.updateOrder(id, updatedOrder);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Delete an order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
        try {
            orderService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get orders by buyer
    @GetMapping("/buyer/{buyer}")
    public ResponseEntity<List<Order>> getOrdersByBuyer(@PathVariable String buyer) {
        List<Order> orders = orderService.getOrdersByBuyer(buyer);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Get orders by seller
    @GetMapping("/seller/{seller}")
    public ResponseEntity<List<Order>> getOrdersBySeller(@PathVariable String seller) {
        List<Order> orders = orderService.getOrdersBySeller(seller);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}

