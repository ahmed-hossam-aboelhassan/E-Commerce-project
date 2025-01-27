package com.ecommerceproject.Dao;

import com.ecommerceproject.Entity.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<Address,Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Address a WHERE a.addressId = :addressId")
    void deleteByAddressId(@Param("addressId") int addressId);


}
