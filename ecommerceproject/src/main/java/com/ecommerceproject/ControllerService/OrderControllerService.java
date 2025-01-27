package com.ecommerceproject.ControllerService;

import com.ecommerceproject.Dto.OrderDto;
import com.ecommerceproject.Entity.Order;
import com.ecommerceproject.Entity.Product;
import com.ecommerceproject.Entity.User;
import com.ecommerceproject.EntityService.OrderService;
import com.ecommerceproject.EntityService.ProductService;
import com.ecommerceproject.EntityService.UserService;
import com.ecommerceproject.Enum.OrderStatus;
import com.ecommerceproject.Exception.NoOrderFoundException;
import com.ecommerceproject.Exception.NoOrdersYetException;
import com.ecommerceproject.Exception.NonExistingStatusException;
import com.ecommerceproject.Exception.UsernameNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderControllerService {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    // Reusable method to get an order by its number and handle errors.
    private Order getOrderOrThrow(int orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            throw new NoOrderFoundException("Order id is incorrect");
        }
        return order;
    }

    // Reusable method to convert a list of orders into OrderDto.
    private List<OrderDto> convertOrdersToOrderDtos(List<Order> orders) {
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            orderDtos.add(new OrderDto(order));
        }
        return orderDtos;
    }

    public ResponseEntity<?> getUserByOrder(int orderId) {
        Order order = getOrderOrThrow(orderId); // Use the reusable method
        return ResponseEntity.ok(order.getUser().getUsername());
    }
    private void checkIfAtLeastOneOrderFound(List<Order> orders){
        if(orders==null||orders.size()==0){
            throw new NoOrdersYetException("No orders have been made yet");
        }
    }

    public ResponseEntity<?> getAllOrders() {

        List<Order> orders = orderService.getAllOrders();
        checkIfAtLeastOneOrderFound(orders);
        List<OrderDto> orderDtos = convertOrdersToOrderDtos(orders); // Reuse conversion method
        return ResponseEntity.ok(orderDtos);
    }

    public ResponseEntity<?> getAllUserOrders(String username) {
        User user = userService.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username is incorrect");
        }
        List<Order> orders = user.getOrders();
        checkIfAtLeastOneOrderFound(orders);
        List<OrderDto> orderDtos = convertOrdersToOrderDtos(orders); // Reuse conversion method
        return ResponseEntity.ok(orderDtos);
    }

    public ResponseEntity<?> getOrder(int orderId) {
        Order order = getOrderOrThrow(orderId); // Use the reusable method
        return ResponseEntity.ok(new OrderDto(order));
    }

    public ResponseEntity<?> updateOrderStatus(int orderId, String status) {
        Order order = getOrderOrThrow(orderId); // Use the reusable method

        switch (status) {
            case "DELIVERED":
                order.setOrderStatus(OrderStatus.DELIVERED);
                break;
            case "CONFIRMED":
                order.setOrderStatus(OrderStatus.CONFIRMED);
                break;
            case "SHIPPED":
                order.setOrderStatus(OrderStatus.SHIPPED);
                break;
            case "CANCELLED":
                handleCancelledOrder(order); // Handle cancellation logic
                order.setOrderStatus(OrderStatus.CANCELLED);
                break;
            default:
                throw new NonExistingStatusException("Please type a suitable status");
        }

        orderService.save(order);
        return ResponseEntity.ok("Status of Order " + order.getId() + " has been updated");
    }

    // Reusable method for handling product restocking when the order is cancelled.
    private void handleCancelledOrder(Order order) {
        String description = order.getDescription();
        String[] items = description.split(",");
        for (String item : items) {
            String[] parts = item.split("\\*");
            Product product = productService.getProductByName(parts[0]);
            if (product != null) {
                int quantity = Integer.parseInt(parts[1]);
                product.setStock(product.getStock() + quantity);
                productService.saveProduct(product);
                System.out.println(product.getName() + " is restocked");
            }
        }
    }
}

