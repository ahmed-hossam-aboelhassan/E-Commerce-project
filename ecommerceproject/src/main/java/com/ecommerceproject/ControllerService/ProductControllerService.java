package com.ecommerceproject.ControllerService;

import com.ecommerceproject.Dto.ProductDto;
import com.ecommerceproject.Entity.Product;
import com.ecommerceproject.EntityService.ProductService;
import com.ecommerceproject.Exception.NoProductFoundException;
import com.ecommerceproject.Exception.NoProductsYetException;
import com.ecommerceproject.Exception.ProductAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductControllerService {
    @Autowired
    private ProductService productService;

    // Reusable method to check if a product exists by ID.
    private Product getProductOrThrow(int id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            throw new NoProductFoundException("Incorrect id please try an existing id");
        }
        return product;
    }

    // Reusable method to check if a product exists by name.
    private Product getProductByNameOrThrow(String name) {
        Product product = productService.getProductByName(name);
        if (product == null) {
            throw new NoProductFoundException("Incorrect name please try an existing name");
        }
        return product;
    }

    // Reusable method to convert a product into ProductDto.
    private ProductDto convertToProductDto(Product product) {
        return new ProductDto(product);
    }

    public ResponseEntity<?> getProductById(int id) {
        Product product = getProductOrThrow(id); // Use the reusable method
        return ResponseEntity.ok(convertToProductDto(product)); // Use conversion method
    }

    public ResponseEntity<?> getProductByName(String name) {
        Product product = getProductByNameOrThrow(name); // Use the reusable method
        return ResponseEntity.ok(convertToProductDto(product)); // Use conversion method
    }

    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products == null || products.isEmpty()) {
            throw new NoProductsYetException("Please add a product first");
        }

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(new ProductDto(product)); // Directly adding new DTOs
        }
        return ResponseEntity.ok(productDtos);
    }

    public ResponseEntity<?> addNewProduct(Product product) {
        if (productService.getProductByName(product.getName()) != null) {
            throw new ProductAlreadyExistsException("Product already exists, try updating it");
        }
        productService.saveProduct(product);
        return ResponseEntity.ok("Product Added Successfully");
    }

    public ResponseEntity<?> updateProduct(int id, ProductDto productDto) {
        Product product = getProductOrThrow(id); // Use the reusable method

        if (productDto.getName() != null) product.setName(productDto.getName());
        if (productDto.getDescription() != null) product.setDescription(productDto.getDescription());
        if (productDto.getPrice() != null) product.setPrice(productDto.getPrice());
        if (productDto.getStock() != null) product.setStock(productDto.getStock());

        productService.saveProduct(product);
        return ResponseEntity.ok("Product Updated Successfully");
    }

    public ResponseEntity<?> deleteProduct(int id) {
        Product product = getProductOrThrow(id); // Use the reusable method
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product Deleted Successfully");
    }
}

