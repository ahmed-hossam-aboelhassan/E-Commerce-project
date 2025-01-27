package com.ecommerceproject.EntityService;

import com.ecommerceproject.Entity.Cart;
import org.springframework.web.servlet.resource.CachingResourceTransformer;

public interface CartServiceInterface {
    void save(Cart cart);
    Cart getCart(int id);
    void deleteCart(int id);
}
