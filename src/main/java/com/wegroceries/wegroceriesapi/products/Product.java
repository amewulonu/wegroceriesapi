package com.wegroceries.wegroceriesapi.products;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import com.wegroceries.wegroceriesapi.users.User; // Adjust the package path as necessary

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Product name cannot be null")
    @Size(min = 1, max = 100, message = "Product name must be between 1 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Category cannot be null")
    @Size(min = 1, max = 50, message = "Category must be between 1 and 50 characters")
    @Column(nullable = false)
    private String category;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be a positive value")
    @Column(nullable = false)
    private BigDecimal price;

    @Min(value = 0, message = "Quantity must be a non-negative value")
    @Column(nullable = false)
    private int quantity;

    @NotNull(message = "Seller cannot be null")
    @Size(min = 1, max = 100, message = "Seller name must be between 1 and 100 characters")
    @Column(nullable = false)
    private String seller;

    @Column(nullable = false)
    private Instant addedDate;

    // Add the user relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Assuming you have a User entity

    // Parameterized Constructor
    public Product(String name, String category, BigDecimal price, int quantity, String seller, Instant addedDate, User user) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.seller = seller;
        this.addedDate = addedDate;
        this.user = user; // Set the user
    }

    // Default Constructor
    public Product() {
        this("Default Product", "General", BigDecimal.ZERO, 0, "Default Seller", Instant.now(), null);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                ", user=" + user + // Include user in toString for better output
                '}';
    }
}
