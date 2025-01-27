package com.ecommerceproject.EntityService;

import com.ecommerceproject.Dao.CartItemDao;
import com.ecommerceproject.Entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService implements CartItemServiceInterface{

    @Autowired
    private CartItemDao cartItemDao;


    @Transactional
    public void save(CartItem cartItem){
        cartItemDao.save(cartItem);
    }

    @Transactional
    public void deleteCartItem(int id){
        cartItemDao.deleteByItemId(id);

    }
}
