package com.ecommerceproject.Dto;

import com.ecommerceproject.Entity.Product;
import jakarta.persistence.Column;

import java.math.BigDecimal;

public class ProductDto {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    public ProductDto() {
    }

    public ProductDto(Product product){
        this.productId=product.getProductId();
        this.name=product.getName();
        this.description= product.getDescription();
        this.price=product.getPrice();
        this.stock= product.getStock();
    }

    public Long getProductId() {
        return productId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
