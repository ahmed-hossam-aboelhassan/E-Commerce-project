package com.ecommerceproject.Entity;

import com.ecommerceproject.Enum.CartStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_id")
    private int cartId;


    @OneToOne
    @JoinColumn(name="user_id")
    private User user;


    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name="cart_status")
    private CartStatus cartStatus ;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CartStatus getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(CartStatus cartStatus) {
        this.cartStatus = cartStatus;
    }

    @JsonIgnore
    public int getCartId() { return cartId; }

    public void setCartId(int cartId) { this.cartId = cartId; }

    @JsonIgnore
    public LocalDateTime getCreatedAt() { return createdAt; }

    @JsonIgnore
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @JsonIgnore
    public CartStatus getStatus() { return cartStatus; }

    public void setStatus(CartStatus status) { this.cartStatus = status; }
    @JsonIgnore
    public List<CartItem> getItems() { return items; }

    public void setItems(List<CartItem> items) { this.items = items; for(CartItem n:this.getItems()){
    n.setCart(this);}
    }

    public void addItem(CartItem cartItem){
        if(this.items==null)
            this.items=new ArrayList<>();

        this.items.add(cartItem);
        cartItem.setCart(this);
    }


}
