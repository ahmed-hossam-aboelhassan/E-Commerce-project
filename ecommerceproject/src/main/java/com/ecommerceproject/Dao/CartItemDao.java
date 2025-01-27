package com.ecommerceproject.Dao;

import com.ecommerceproject.Entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface   CartItemDao extends JpaRepository<CartItem,Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.itemId = :itemId")
    void deleteByItemId(@Param("itemId") int itemId);


}
