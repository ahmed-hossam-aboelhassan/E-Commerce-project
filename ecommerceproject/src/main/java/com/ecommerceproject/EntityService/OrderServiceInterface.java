package com.ecommerceproject.EntityService;

import com.ecommerceproject.Entity.Order;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OrderServiceInterface {
    public Order getOrder(int id);


    List<Order> getAllOrders();
    public void save(Order order);
}
