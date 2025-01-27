package com.ecommerceproject.Dto;

import com.ecommerceproject.Entity.CartItem;
import com.ecommerceproject.EntityService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class ItemDto {



    private String itemName;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;



    public ItemDto(CartItem cartItem,ProductService productService){
        this.itemName=productService.getProduct(cartItem.getProductId()).getName();
        this.quantity=cartItem.getQuantity();
        this.price=cartItem.getPrice();
        this.totalPrice=cartItem.getTotalPrice();

    }


    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
