package com.ecommerceproject.EntityService;

import com.ecommerceproject.Dao.OrderDao;
import com.ecommerceproject.Entity.Order;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    OrderDao orderDao;

    public Order getOrder(int id){
        return orderDao.findById(id).orElse(null);
    }

    @Transactional
    public void save(Order order){
        orderDao.save(order);
    }

    public List<Order> getAllOrders(){
        return orderDao.findAll();
    }
}
