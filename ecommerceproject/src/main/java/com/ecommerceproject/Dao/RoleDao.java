package com.ecommerceproject.Dao;

import com.ecommerceproject.Entity.Role;
import com.ecommerceproject.Entity.RoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, RoleId> {
}
