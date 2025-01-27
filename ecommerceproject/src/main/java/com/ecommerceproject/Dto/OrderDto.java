package com.ecommerceproject.Dto;

import com.ecommerceproject.Entity.Order;
import com.ecommerceproject.Enum.OrderStatus;
import com.ecommerceproject.Enum.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public class OrderDto {
    private Long orderNo;
    private String description;
    private String deliverAddress;
    private BigDecimal totalPrice;
    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus;

    public OrderDto(Order order){
        this.orderNo =order.getId();
        this.description=order.getDescription();
        this.deliverAddress=order.getDeliverAddress();
        this.totalPrice=order.getTotalPrice();
        this.paymentMethod=order.getPaymentMethod();
        this.orderStatus=order.getOrderStatus();

    }

    public Long getOrderNo() {
        return orderNo;
    }

    public String getDescription() {
        return description;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
