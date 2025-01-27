package com.ecommerceproject.Controller;

import com.ecommerceproject.ControllerService.PreLoginControllerService;
import com.ecommerceproject.ControllerService.ProductControllerService;
import com.ecommerceproject.Dto.ProductDto;
import com.ecommerceproject.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/product-access")
public class ProductController {
    @Autowired
    ProductControllerService productControllerService;


    @GetMapping("/get-product-no/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id){
        return productControllerService.getProductById(id);
    }
    @GetMapping("/get-product-name/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name){
        return productControllerService.getProductByName(name);
    }
    @GetMapping("/get-all-products")
    public ResponseEntity<?> getAllProducts(){
        return productControllerService.getAllProducts();
    }
    @PostMapping("/add-new-product")
    public ResponseEntity<?> addNewProduct(@RequestBody Product product){
        return productControllerService.addNewProduct(product);
    }
    @PutMapping("/update-product-no/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id,@RequestBody ProductDto productDto){
        return productControllerService.updateProduct(id,productDto);
    }
    @DeleteMapping("/delete-product-no/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id){
        return productControllerService.deleteProduct(id);
    }
}
