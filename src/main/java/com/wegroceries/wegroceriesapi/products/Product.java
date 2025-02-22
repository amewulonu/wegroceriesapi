package com.wegroceries.wegroceriesapi.products;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @jakarta.validation.constraints.NotNull(message = "Product name cannot be null")
    @jakarta.validation.constraints.Size(min = 1, max = 100, message = "Product name must be between 1 and 100 characters")
    @Column(nullable = false)
    private String name;

    @jakarta.validation.constraints.NotNull(message = "Category cannot be null")
    @jakarta.validation.constraints.Size(min = 1, max = 50, message = "Category must be between 1 and 50 characters")
    @Column(nullable = false)
    private String category;

    @jakarta.validation.constraints.NotNull(message = "Price cannot be null")
    @jakarta.validation.constraints.Min(value = 0, message = "Price must be a positive value")
    @Column(nullable = false)
    private BigDecimal price;

    @jakarta.validation.constraints.Min(value = 0, message = "Quantity must be a non-negative value")
    @Column(nullable = false)
    private int quantity;

    @jakarta.validation.constraints.NotNull(message = "Seller cannot be null")
    @jakarta.validation.constraints.Size(min = 1, max = 100, message = "Seller name must be between 1 and 100 characters")
    @Column(nullable = false)
    private String seller;

    @Column(nullable = false)
    private Instant addedDate;

    // Parameterized Constructor
    public Product(String name, String category, BigDecimal price, int quantity, String seller, Instant addedDate) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.seller = seller;
        this.addedDate = addedDate != null ? addedDate : Instant.now();
    }

    // Default Constructor
    public Product() {
        this("Default Product", "General", BigDecimal.ZERO, 0, "Default Seller", Instant.now());
    }

    // Add @PrePersist to automatically set addedDate before persisting
    @PrePersist
    public void prePersist() {
        if (addedDate == null) {
            addedDate = Instant.now();
        }
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Instant getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Instant addedDate) {
        this.addedDate = addedDate;
    }

    // Method to check if product is in stock
    public boolean isInStock() {
        return quantity > 0;
    }

    // equals() and hashCode() methods for comparison and collection operations
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id != null && id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    // toString Method
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", seller='" + seller + '\'' +
                ", addedDate=" + addedDate +
                '}';
    }
}
