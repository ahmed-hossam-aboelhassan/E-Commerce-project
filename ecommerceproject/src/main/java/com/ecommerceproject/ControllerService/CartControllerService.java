package com.ecommerceproject.ControllerService;

import com.ecommerceproject.Dto.AddressDto;
import com.ecommerceproject.Dto.CartDto;
import com.ecommerceproject.Dto.CheckOutDto;
import com.ecommerceproject.Dto.ItemCheckOutDto;
import com.ecommerceproject.Entity.*;
import com.ecommerceproject.EntityService.*;
import com.ecommerceproject.Enum.CartStatus;
import com.ecommerceproject.Enum.OrderStatus;
import com.ecommerceproject.Enum.PaymentMethod;
import com.ecommerceproject.Exception.*;
import com.ecommerceproject.JwtService.JwtUtil;
import jakarta.transaction.Transactional;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CartControllerService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final ProductService productService;
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final AddressService addressService;

    @Autowired
    public CartControllerService(AddressService addressService, UserService userService, JwtUtil jwtUtil, ProductService productService,
                                 CartService cartService, CartItemService cartItemService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.productService = productService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.addressService = addressService;
    }

    public User extractUser(String token) {
        String jwtToken = token.substring(7); // Assumes the token starts with "Bearer "
        String id = jwtUtil.extractUsername(jwtToken); // Extract username from token
        return userService.findById(id);
    }

    private CartItem createCartItem(int productId, int quantity, BigDecimal price) {
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(price);
        return cartItem;
    }

    private void validateStock(int requestedQuantity, int availableStock, String productName) {
        if (requestedQuantity > availableStock) {
            throw new ItemOutOfStockException("Item " + productName + " is out of stock");
        }
    }

    private void checkIfCartIsEmptyAfterRemoval(User ut) {
        if (ut.getCart().getItems().size() == 1) {
            deleteCart(ut);
            userService.save(ut);
        }
    }

    public ResponseEntity<?> addToCart(User ut, int productId) {
        Product product = productService.getProduct(productId);
        if (product == null) {
            throw new NotAnAvailableProductException("No Such Product Available");
        }

        if (ut.getCart() == null) {
            ut.setCart(new Cart());
            ut.getCart().setStatus(CartStatus.ACTIVE);
            ut.getCart().setCreatedAt(LocalDateTime.now());
            CartItem newItem = createCartItem(productId, 1, product.getPrice());
            validateStock(newItem.getQuantity(), product.getStock(), product.getName());
            ut.getCart().addItem(newItem);
        } else {
            boolean exists = false;
            for (CartItem item : ut.getCart().getItems()) {
                if (item.getProductId() == productId) {
                    item.setQuantity(item.getQuantity() + 1);
                    validateStock(item.getQuantity(), product.getStock(), product.getName());
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                CartItem newItem = createCartItem(productId, 1, product.getPrice());
                validateStock(newItem.getQuantity(), product.getStock(), product.getName());
                ut.getCart().addItem(newItem);
            }
        }
        ut.getCart().setUpdatedAt(LocalDateTime.now());
        cartService.save(ut.getCart());
        return ResponseEntity.ok("Item Added");
    }

    public ResponseEntity<?> getCart(User ut) {
        if (ut.getCart() == null) {
            throw new EmptyCartException("Cart is empty");
        }
        CartDto cartDto = new CartDto(ut.getCart(), productService);
        return ResponseEntity.ok(cartDto);
    }

    public ResponseEntity<?> removeSingleItemOf(User ut, int productId) {
        if (ut.getCart() == null) {
            throw new EmptyCartException("Cart is empty");
        }

        boolean itemFound = false;
        for (CartItem item : ut.getCart().getItems()) {
            if (item.getProductId() == productId) {
                itemFound = true;
                item.setQuantity(item.getQuantity() - 1);
                cartItemService.save(item);
                if (item.getQuantity().equals(0)) {
                    cartItemService.deleteCartItem(item.getItemId());
                    checkIfCartIsEmptyAfterRemoval(ut);
                }
                ut.getCart().setUpdatedAt(LocalDateTime.now());
                break;
            }
        }

        if (itemFound) {
            return ResponseEntity.ok("Item Removed Successfully");
        } else {
            throw new ItemNotInCartException("Item not in cart");
        }
    }

    public ResponseEntity<?> removeAllItemOf(User ut, int productId) {
        if (ut.getCart() == null) {
            throw new EmptyCartException("Cart is empty");
        }

        boolean itemFound = false;
        for (CartItem item : ut.getCart().getItems()) {
            if (item.getProductId() == productId) {
                itemFound = true;
                cartItemService.deleteCartItem(item.getItemId());
                ut.getCart().setUpdatedAt(LocalDateTime.now());
                checkIfCartIsEmptyAfterRemoval(ut);
                break;
            }
        }

        if (itemFound) {
            return ResponseEntity.ok("Items Removed Successfully");
        } else {
            throw new ItemNotInCartException("Item not in cart");
        }
    }

    public ResponseEntity<?> deleteCart(User ut) {
        if (ut.getCart() == null) {
            throw new EmptyCartException("Cart is empty");
        }
        cartService.deleteCart(ut.getCart().getCartId());
        userService.save(ut);
        return ResponseEntity.ok("Cart Deleted Successfully");
    }

    public ResponseEntity<?> checkOut(String token) {
        User ut = extractUser(token);
        checkoutChecker(ut);
        return ResponseEntity.ok(new CheckOutDto(ut, productService, addressService.findById(ut.getDefaultAddress())));
    }

    public ResponseEntity<?> confirmOrder(String token) {
        User ut = extractUser(token);
        checkoutChecker(ut);

        Order order = new Order();
        order.setDescription("");
        CheckOutDto checkOutDto = new CheckOutDto(ut, productService, addressService.findById(ut.getDefaultAddress()));
        HashMap<Product, Integer> tracer = new HashMap<>();

        for (CartItem item : ut.getCart().getItems()) {
            Product product = productService.getProduct(item.getProductId());
            validateStock(item.getQuantity(), product.getStock(), product.getName());
            tracer.put(product, item.getQuantity());
            order.setDescription(order.getDescription() + product.getName() + "*" + item.getQuantity() + ",");
        }

        order.setTotalPrice(checkOutDto.getGrandTotal());
        AddressDto address = checkOutDto.getAddressDto();
        order.setDeliverAddress(address.getCountry() + "," + address.getCity() + "," + address.getAddressLine() + "," + address.getPostalCode());
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY);
        ut.addOrder(order);

        for (Product product : tracer.keySet()) {
            product.setStock(product.getStock() - tracer.get(product));
            productService.saveProduct(product);
        }

        deleteCart(ut);
        userService.save(ut);
        return ResponseEntity.ok("Order Confirmed");
    }
    private void checkoutChecker(User ut){
        if (ut.getCart() == null) {
            throw new EmptyCartException("Cart is empty");
        }
        if (ut.getProfile() == null || ut.getProfile().getFirstName() == null || ut.getProfile().getLastName() == null || ut.getProfile().getPhoneNumber() == null) {
            throw new ProfileIncompleteException("Please complete your profile");
        }
        if (ut.getAddresses().isEmpty()) {
            throw new NoAvailableAddressesException("Please add an address first");
        }
    }
}
