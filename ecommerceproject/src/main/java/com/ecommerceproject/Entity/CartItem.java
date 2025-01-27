package com.ecommerceproject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private int itemId;

    @Column(name="product_id")
    private int productId;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="total_price",insertable = false,updatable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;



    // Getters and Setters

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @JsonIgnore
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() { return totalPrice;
    }


}
