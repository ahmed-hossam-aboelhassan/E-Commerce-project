package com.ecommerceproject.Dao;

import com.ecommerceproject.Entity.Order;
import com.ecommerceproject.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order,Integer> {

}
