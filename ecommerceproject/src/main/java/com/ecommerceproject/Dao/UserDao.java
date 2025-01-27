package com.ecommerceproject.Dao;

import com.ecommerceproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,String> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User loginByemail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User getUserByUserName(@Param("username") String username);


}
