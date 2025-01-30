package com.wegroceries.wegroceriesapi.orders;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

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

  // Parameterized Constructor
  public Order(String itemName, String category, BigDecimal price, String seller, String buyer, Instant transactionDate) {
    this.itemName = itemName;
    this.category = category;
    this.price = price;
    this.seller = seller;
    this.buyer = buyer;
    this.transactionDate = transactionDate;
  }

  // Default Constructor
  public Order() {
    this("Default Item", "General", BigDecimal.ZERO, "Default Seller", "Default Buyer", Instant.now());
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
            ", buyer='" + buyer + '\'' +
            ", transactionDate=" + transactionDate +
            '}';
  }
}

