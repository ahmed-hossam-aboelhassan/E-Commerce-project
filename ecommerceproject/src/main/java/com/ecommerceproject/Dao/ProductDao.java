package com.ecommerceproject.Dao;

import com.ecommerceproject.Entity.Product;
import com.ecommerceproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product,Integer> {
    @Query("SELECT u FROM Product u WHERE u.name = :name")
    Product findByName(@Param("name") String name);
}
