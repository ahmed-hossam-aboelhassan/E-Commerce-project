package com.ecommerceproject.Dto;

import com.ecommerceproject.Entity.Address;
import com.ecommerceproject.Entity.CartItem;
import com.ecommerceproject.Entity.User;
import com.ecommerceproject.EntityService.ProductService;
import jakarta.annotation.PostConstruct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckOutDto {
    private List<ItemCheckOutDto> items=new ArrayList<>();;
    private BigDecimal taxCharge ;
    private int delieveryCharge ;
    private BigDecimal grandTotal = BigDecimal.valueOf(0);

    private AddressDto addressDto;

    private String chooseUrMethodOfPayment="[]Cash on Delievery                                  []Visa Or Master Card";



    public CheckOutDto(User ut, ProductService productService,Address address) {
        for (CartItem cartItem : ut.getCart().getItems()) {
            ItemCheckOutDto itemCheckOutDto = new ItemCheckOutDto(cartItem, productService);
            this.grandTotal = this.grandTotal.add(cartItem.getTotalPrice());
            items.add(itemCheckOutDto);

        }


            this.addressDto=new AddressDto(address);



        this.taxCharge=this.grandTotal.multiply(BigDecimal.valueOf(0.14));
        this.grandTotal=this.taxCharge.add(this.grandTotal);

        if(addressDto.getCity().equals("Cairo"))
            this.delieveryCharge=50;

        else
            this.delieveryCharge=10;

        this.grandTotal=this.grandTotal.add(BigDecimal.valueOf(delieveryCharge));



    }

    public List<ItemCheckOutDto> getItems() {
        return items;
    }

    public BigDecimal getTaxCharge() {
        return taxCharge;
    }

    public int getDelieveryCharge() {
        return delieveryCharge;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public String getChooseUrMethodOfPayment() {
        return chooseUrMethodOfPayment;
    }
}
