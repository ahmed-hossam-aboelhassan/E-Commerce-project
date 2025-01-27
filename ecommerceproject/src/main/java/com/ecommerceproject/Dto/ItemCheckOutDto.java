package com.ecommerceproject.Dto;

import com.ecommerceproject.Entity.CartItem;
import com.ecommerceproject.EntityService.ProductService;

import java.math.BigDecimal;

public class ItemCheckOutDto {
    private ProductService productService;

    private String itemName;
    private int quantity;




    public ItemCheckOutDto(CartItem cartItem, ProductService productService){
        this.productService=productService;
        this.itemName=productService.getProduct(cartItem.getProductId()).getName();
        this.quantity=cartItem.getQuantity();


    }


    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }


}
