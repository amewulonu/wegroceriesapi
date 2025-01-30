package com.wegroceries.wegroceriesapi.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    // Custom query to check if a product exists by name and category
    boolean existsByNameAndCategory(String name, String category);

    // Custom query to find a product by name and category
    Optional<Product> findByNameAndCategory(String name, String category);
}

