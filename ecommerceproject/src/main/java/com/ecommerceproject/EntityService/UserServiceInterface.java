package com.ecommerceproject.EntityService;

import com.ecommerceproject.Entity.User;

public interface UserServiceInterface {
    void save(User user);
    User findById(String id);
    User loginByEmail(String email);
    User getUserByUserName(String username);

}
