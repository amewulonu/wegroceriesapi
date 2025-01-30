package com.wegroceries.wegroceriesapi.orders;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

 // Order save(Order order); // This method is inherited from JpaRepository
 public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByBuyer(String buyer);

    List<Order> findBySeller(String seller);

    
}