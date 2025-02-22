package com.wegroceries.wegroceriesapi.categories;

import com.wegroceries.wegroceriesapi.products.Product;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    // One category can have many products
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    // Self-referencing Many-to-Many relationship for parent-child categories
    @ManyToMany
    @JoinTable(
        name = "category_hierarchy",
        joinColumns = @JoinColumn(name = "parent_id"),
        inverseJoinColumns = @JoinColumn(name = "child_id")
    )
    private Set<Category> subCategories = new HashSet<>();

    @ManyToMany(mappedBy = "subCategories")
    private Set<Category> parentCategories = new HashSet<>();

    // Constructors
    public Category() {}

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Set<Product> getProducts() { return products; }
    public void setProducts(Set<Product> products) { this.products = products; }

    public Set<Category> getSubCategories() { return subCategories; }
    public void setSubCategories(Set<Category> subCategories) { this.subCategories = subCategories; }

    public Set<Category> getParentCategories() { return parentCategories; }
    public void setParentCategories(Set<Category> parentCategories) { this.parentCategories = parentCategories; }
}
