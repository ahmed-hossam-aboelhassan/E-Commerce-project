package com.ecommerceproject.ControllerService;

import com.ecommerceproject.Dto.AddressDto;
import com.ecommerceproject.Dto.OrderDto;
import com.ecommerceproject.Dto.UserProfileDto;
import com.ecommerceproject.Entity.*;
import com.ecommerceproject.EntityService.*;
import com.ecommerceproject.Enum.Gender;
import com.ecommerceproject.Enum.OrderStatus;
import com.ecommerceproject.Exception.*;
import com.ecommerceproject.JwtService.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserControllerService {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public UserControllerService(ProductService productService, OrderService orderService, PasswordEncoder passwordEncoder,
                                 AddressService addressService, UserService userService, JwtUtil jwtUtil) {
        this.addressService = addressService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
        this.productService = productService;
    }

    private User extractUser(String token) {
        String jwtToken = token.substring(7); // Assumes the token starts with "Bearer "
        String id = jwtUtil.extractUsername(jwtToken);
        return userService.findById(id);
    }

    private ResponseEntity<?> buildProfileResponse(User ut) {
        if (ut.getProfile() == null) {
            throw new ProfileNotInstantiatedException("Please update ur profile first");
        }
        return ResponseEntity.ok(new UserProfileDto(ut));
    }

    public ResponseEntity<?> viewProfile(String token) {
        return buildProfileResponse(extractUser(token));
    }

    private ResponseEntity<?> buildAddressResponse(List<Address> addresses) {
        if (addresses == null || addresses.isEmpty()) {
           throw new NoAvailableAddressesException("Please add an address first");
        }
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address address : addresses) {
            addressDtos.add(new AddressDto(address));
        }
        return ResponseEntity.ok(addressDtos);
    }

    public ResponseEntity<?> viewAddresses(String token) {
        return buildAddressResponse(extractUser(token).getAddresses());
    }

    private ResponseEntity<?> buildDefaultAddressResponse(int defaultAddressId) {
        if (defaultAddressId == 0) {
            throw new NoAvailableAddressesException("Please add an address first");
        }
        AddressDto addressDto = new AddressDto(addressService.findById(defaultAddressId));
        return ResponseEntity.ok(addressDto);
    }

    public ResponseEntity<?> viewDefaultAddress(String token) {
        return buildDefaultAddressResponse(extractUser(token).getDefaultAddress());
    }

    public ResponseEntity<?> updateProfile(String token, HashMap<String, Object> updates) {
        User ut = extractUser(token);
        if (ut.getProfile() == null) {
            ut.setProfile(new Profile());
        }
        updates.forEach((key, value) -> {
            switch (key) {
                case "email" -> ut.setEmail((String) value);
                case "password" -> ut.setPassword(passwordEncoder.encode((String) value));
                case "firstName" -> ut.getProfile().setFirstName((String) value);
                case "lastName" -> ut.getProfile().setLastName((String) value);
                case "dateOfBirth" -> ut.getProfile().setDateOfBirth(LocalDate.parse((String) value));
                case "gender" -> ut.getProfile().setGender("Male".equals(value) ? Gender.MALE : Gender.FEMALE);
                case "phoneNumber" -> ut.getProfile().setPhoneNumber((String) value);
                default -> throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
        ut.setUpdatedAt(LocalDateTime.now());
        userService.save(ut);
        return buildProfileResponse(ut);
    }

    public void addAddress(String token, Address address) {
        User ut = extractUser(token);

        ut.addaddress(address);
        userService.save(ut);
        if (ut.getAddresses().size()==1){
            System.out.println("505 besh");
            ut.setDefaultAddress(ut.getAddresses().get(0).getAddressId());
            userService.save(ut);
        }

        ut.setUpdatedAt(LocalDateTime.now());

    }
    public ResponseEntity<?> updateAddress(String token,HashMap<String, Object> updates,int no){
        User ut =  extractUser(token);
        if(no>ut.getAddresses().size())
            throw new NonExistingAddressException("Please choose an available address to update");
        updates.forEach((key, value) -> {
            switch (key) {
                case "country":
                    ut.getAddresses().get(no -1).setCountry((String) value);
                    break;
                case "city":
                    ut.getAddresses().get(no -1).setCity((String) value);
                    break;
                case "postalCode":
                    ut.getAddresses().get(no -1).setPostalCode((String) value);
                    break;
                case "addressLine":
                    ut.getAddresses().get(no -1).setAddressLine((String) value);
                    break;
                // Add more cases as needed for other fields
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
        ut.setUpdatedAt(LocalDateTime.now());
        userService.save(ut);
        return this.buildAddressResponse(ut.getAddresses());
    }

    public ResponseEntity<?> setDefaultAddress(String token, int index) {
        User ut = extractUser(token);
        if (index > ut.getAddresses().size()) {
            throw new NonExistingAddressException("Please choose an available address to set as default");
        }
        ut.setDefaultAddress(ut.getAddresses().get(index - 1).getAddressId());
        userService.save(ut);
        return buildDefaultAddressResponse(ut.getDefaultAddress());
    }

    public ResponseEntity<?> deleteAddress(String token, int index) {
        User ut = extractUser(token);

        if (index > ut.getAddresses().size()) {
            throw new NonExistingAddressException("Please choose an available address to delete");
        }
        Address address = ut.getAddresses().get(index - 1);
        boolean isDefault = address.getAddressId() == ut.getDefaultAddress();
        boolean isLastAddress = ut.getAddresses().size() == 1;

        addressService.deleteAddress(address.getAddressId());
        ut.getAddresses().remove(index - 1);

        if (isLastAddress) {
            ut.setDefaultAddress(0);
        } else if (isDefault) {
            ut.setDefaultAddress(ut.getAddresses().get(0).getAddressId());
        }
        userService.save(ut);

        return ResponseEntity.ok(isLastAddress ? "All Addresses Have Been Removed" : "Address Has Been Removed");
    }

    public ResponseEntity<?> viewOrders(String token) {
        User ut = extractUser(token);
        if (ut.getOrders() == null || ut.getOrders().isEmpty()) {
            throw new NoAvailableOrdersException("No orders have been made yet");
        }
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : ut.getOrders()) {
            orderDtos.add(new OrderDto(order));
        }
        return ResponseEntity.ok(orderDtos);
    }

    public ResponseEntity<?> cancelOrder(String token, int orderId) {
        User ut = extractUser(token);
        Order order = orderService.getOrder(orderId);
        if (order == null || !order.getUser().equals(ut)) {
            throw new NotThisUserOrderException("Please select an existing order");
        }

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new OrderAlreadyDeliveredException("Order has already been delivered");
        }

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new OrderAlreadyCancelledException("Order has already been cancelled");
        }

        if (order.getOrderStatus() == OrderStatus.SHIPPED) {
            throw new OrderAlreadyShippedException("Order has already been shipped");
        }

        String[] items = order.getDescription().split(",");
        for (String item : items) {
            String[] parts = item.split("\\*");
            Product product = productService.getProductByName(parts[0]);
            if (product != null) {
                int quantity = Integer.parseInt(parts[1]);
                product.setStock(product.getStock() + quantity);
                productService.saveProduct(product);
            }
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        orderService.save(order);

        return ResponseEntity.ok("Order has been cancelled");
    }
}
