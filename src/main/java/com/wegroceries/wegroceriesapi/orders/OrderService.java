package com.wegroceries.wegroceriesapi.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
//     // Order save(Order order); // This method is inherited from JpaRepository
// public interface OrderRepository extends JpaRepository<Order, UUID> {

//     List<Order> findByBuyer(String buyer);

//     List<Order> findBySeller(String seller);

    
// }
    // Create a new order
    public Order createOrder(String itemName, String category, BigDecimal price, String seller, String buyer) {
        Order order = new Order(itemName, category, price, seller, buyer, Instant.now(), null);
        return orderRepository.save(order);
    }

    // Retrieve an order by ID
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));
    }

    // Retrieve all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Update an existing order
    public Order updateOrder(UUID id, Order updatedOrder) {
        // Fetch the existing order
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));

        // Update the fields
        existingOrder.setItemName(updatedOrder.getItemName());
        existingOrder.setCategory(updatedOrder.getCategory());
        existingOrder.setPrice(updatedOrder.getPrice());
        existingOrder.setSeller(updatedOrder.getSeller());
        existingOrder.setBuyer(updatedOrder.getBuyer());
        existingOrder.setTransactionDate(Instant.now()); // Update transaction date to the current time

        return orderRepository.save(existingOrder);
    }

    // Delete an order by ID
    public void deleteOrder(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }

    // Get orders by buyer
    public List<Order> getOrdersByBuyer(String buyer) {
        return orderRepository.findByBuyer(buyer);
    }

    // Get orders by seller
    public List<Order> getOrdersBySeller(String seller) {
        return orderRepository.findBySeller(seller);
    }
}
