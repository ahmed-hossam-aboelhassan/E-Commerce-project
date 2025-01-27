package com.ecommerceproject.EntityService;

import com.ecommerceproject.Dao.ProductDao;
import com.ecommerceproject.Dao.ProfileDao;
import com.ecommerceproject.Entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductServiceInterface {
    @Autowired
    private ProductDao productDao;
    @Override
    public Product getProduct(int id) {
        return productDao.findById(id).orElse(null);
    }

    @Transactional
    public void saveProduct(Product product){
        productDao.save(product);
    }
    @Transactional
    public void deleteProduct(int id){
        productDao.deleteById(id);
    }

    public Product getProductByName(String name) {
        return productDao.findByName(name);
    }
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }


}
