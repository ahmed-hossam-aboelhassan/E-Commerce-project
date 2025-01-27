package com.ecommerceproject.Controller;

import com.ecommerceproject.ControllerService.OrderControllerService;
import com.ecommerceproject.Dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/orders-access")
public class OrderController {
    @Autowired
    OrderControllerService orderControllerService;

    @GetMapping("get/user-of-order-no/{no}")
    public ResponseEntity<?> getUser(@PathVariable int no){
        return orderControllerService.getUserByOrder(no);
    }
    @GetMapping("get/order-no/{no}")
    public ResponseEntity<?> getOrder(@PathVariable int no){
        return orderControllerService.getOrder(no);
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<?> getAllOrders(){
        return orderControllerService.getAllOrders();
    }
    @GetMapping("get-all-orders-of/{username}")
    public ResponseEntity<?> getUserOrders(@PathVariable String username){

        return orderControllerService.getAllUserOrders(username);
    }

    @PutMapping("/update-status-of-order-no/{no}")
    public ResponseEntity<?>updateOrderStatus(@PathVariable int no,@RequestBody StatusDto status){

    return orderControllerService.updateOrderStatus(no,status.getStatus());

    }

}
