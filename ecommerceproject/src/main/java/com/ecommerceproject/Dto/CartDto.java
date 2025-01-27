package com.ecommerceproject.Dto;

import com.ecommerceproject.Entity.Cart;
import com.ecommerceproject.Entity.CartItem;
import com.ecommerceproject.EntityService.ProductService;
import com.ecommerceproject.Enum.CartStatus;
import lombok.Builder;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class CartDto {


    private CartStatus cartStatus ;

    private List<ItemDto> items = new ArrayList<>();

    private BigDecimal grandTotal= BigDecimal.ZERO;

    public CartDto(Cart cart,ProductService productService){
        this.cartStatus=cart.getStatus();
        for(CartItem cartItem:cart.getItems()){
            ItemDto itemDto=new ItemDto(cartItem,productService);
            this.grandTotal=this.grandTotal.add(itemDto.getTotalPrice());
            items.add(itemDto);
        }

    }

    public CartStatus getCartStatus() {
        return cartStatus;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }
}
