package com.ecommerceproject.Dao;

import com.ecommerceproject.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileDao extends JpaRepository<Profile,String> {
}
