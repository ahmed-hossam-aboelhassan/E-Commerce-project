package com.ecommerceproject.EntityService;

import com.ecommerceproject.Dao.UserDao;
import com.ecommerceproject.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface{
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public User loginByEmail(String email){
        return userDao.loginByemail(email);
    }
    @Override
    public User getUserByUserName(String username){
        return userDao.getUserByUserName(username);
    }
}
