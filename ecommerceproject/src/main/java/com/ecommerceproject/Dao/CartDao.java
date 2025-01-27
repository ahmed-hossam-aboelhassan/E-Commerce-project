package com.ecommerceproject.Dao;

import com.ecommerceproject.Entity.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.beans.JavaBean;
@Repository
public interface CartDao extends JpaRepository<Cart,Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.cartId = :cartId")
    void deleteByCartId(@Param("cartId") int cartId);
}
