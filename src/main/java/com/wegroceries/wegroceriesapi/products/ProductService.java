package com.wegroceries.wegroceriesapi.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create a new product
    @Transactional
    public Product createProduct(Product product) {
        // Validate price and quantity before saving
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        return productRepository.save(product);
    }

    // Get a product by ID
    public Product getProductById(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Update a product
    @Transactional
    public Product updateProduct(UUID id, Product productDetails) {
        Product existingProduct = getProductById(id);  // Throws exception if not found

        // Update product details
        existingProduct.setName(productDetails.getName());
        existingProduct.setCategory(productDetails.getCategory());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setQuantity(productDetails.getQuantity());
        existingProduct.setSeller(productDetails.getSeller());
        existingProduct.setAddedDate(productDetails.getAddedDate());

        return productRepository.save(existingProduct);
    }

    // Delete a product by ID
    @Transactional
    public void deleteProduct(UUID id) {
        Product product = getProductById(id);  // Throws exception if not found
        productRepository.delete(product);
    }

    // Check if a product with the same name already exists in the same category
    public boolean productExists(String name, String category) {
        return productRepository.existsByNameAndCategory(name, category);
    }
}
