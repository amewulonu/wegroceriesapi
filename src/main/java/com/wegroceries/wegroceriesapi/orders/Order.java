package com.wegroceries.wegroceriesapi.orders;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.Set;

import com.wegroceries.wegroceriesapi.products.Product;
import com.wegroceries.wegroceriesapi.users.User;

@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String itemName;
  private String category;
  private BigDecimal price;
  private String seller;
  private String buyer;
  private Instant transactionDate;

@ManyToOne(fetch = FetchType.LAZY) //Many orders can belong one user
@JoinColumn(name = "user_id", nullable = false) //Foreign key in order table
private User user;

 // Many-to-One relationship with Product
@ManyToOne
@JoinColumn(name = "product_id", nullable = false)
    private Product product;

  // Many-to-Many relationship with User (Many users can be associated with one order)
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "order_users", // Name of the join table
      joinColumns = @JoinColumn(name = "order_id"), // Foreign key for orders
      inverseJoinColumns = @JoinColumn(name = "user_id") // Foreign key for users
  )
  private Set<User> users; // Set to store multiple users

  // Many-to-Many relationship with Product
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "order_products", // Name of the join table
      joinColumns = @JoinColumn(name = "order_id"), // Foreign key for orders
      inverseJoinColumns = @JoinColumn(name = "product_id") // Foreign key for products
  )
  private Set<Product> products; // Set to store multiple products

  // Parameterized Constructor
  public Order(String itemName, String category, BigDecimal price, String seller, String buyer, Instant transactionDate, Product product) {
    this.itemName = itemName;
    this.category = category;
    this.price = price;
    this.seller = seller;
    this.buyer = buyer;
    this.transactionDate = transactionDate;
    this.product = product;  // Set the products
  }

  // Default Constructor
  public Order() {
    this("Default Item", "General", BigDecimal.ZERO, "Default Seller", "Default Buyer", Instant.now(), new Product());
  }

  // Getters and Setters
  public UUID getId() {
    return id;
    }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
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

  public String getSeller() {
    return seller;
  }

  public void setSeller(String seller) {
    this.seller = seller;
  }

  public Product getProduct() {
    return product;
}

public void setProduct(Product product) {
    this.product = product;
}

  public String getBuyer() {
    return buyer;
  }

  public void setBuyer(String buyer) {
    this.buyer = buyer;
  }

  public Instant getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Instant transactionDate) {
    this.transactionDate = transactionDate;
  }

  // toString Method
  @Override
  public String toString() {
    return "Order{" +
            "id=" + id +
            ", itemName='" + itemName + '\'' +
            ", category='" + category + '\'' +
            ", price=" + price +
            ", seller='" + seller + '\'' +
            ", transactionDate=" + transactionDate +
           ", buyer='" + (buyer !=null ? user.getId() : "null") + 
            '}';
  }

  public void setId(UUID orderId) {
    throw new UnsupportedOperationException("Unimplemented method 'setId'");
  }

public void setUser(User user2) {
    throw new UnsupportedOperationException("Unimplemented method 'setUser'");
}
}

