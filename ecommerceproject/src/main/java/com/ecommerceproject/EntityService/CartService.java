package com.ecommerceproject.EntityService;

import com.ecommerceproject.Dao.CartDao;
import com.ecommerceproject.Entity.Cart;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService implements CartServiceInterface{
    @Autowired
    private CartDao cartDao;
    @Override
    @Transactional
    public void save(Cart cart) {
        cartDao.save(cart);

    }

    @Override
    public Cart getCart(int id) {
        return cartDao.findById(id).get();

    }
    @Transactional
    public void saveandflush(Cart cart){
        cartDao.saveAndFlush(cart);
    }

    @Override
    @Transactional
    public void deleteCart(int id){
        cartDao.deleteByCartId(id);
    }
}
