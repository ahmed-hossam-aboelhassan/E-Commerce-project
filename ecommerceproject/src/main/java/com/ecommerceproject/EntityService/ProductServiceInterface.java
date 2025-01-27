package com.ecommerceproject.EntityService;

import com.ecommerceproject.Entity.Product;
import org.springframework.stereotype.Service;


public interface ProductServiceInterface {
    Product getProduct(int id);
}
