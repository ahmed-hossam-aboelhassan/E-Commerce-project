package com.ecommerceproject.Controller;

import com.ecommerceproject.ControllerService.UserControllerService;
import com.ecommerceproject.Entity.Address;
import com.ecommerceproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserControllerService userControllerService;



    @GetMapping("/view/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {


        return userControllerService.viewProfile(token);
    }

    @GetMapping("/view/addresses")
    public ResponseEntity<?> getAddresses(@RequestHeader("Authorization") String token){

        return userControllerService.viewAddresses(token);

}
    @PutMapping("/update/profile")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token,@RequestBody HashMap<String, Object> updates) {

        return userControllerService.updateProfile(token,updates);


    }
    @PutMapping("/addaddress")
    public ResponseEntity<?> getAddresses(@RequestHeader("Authorization") String token,@RequestBody Address address){

        userControllerService.addAddress(token,address);
        return userControllerService.viewAddresses(token);

    }
    @PutMapping("/update/address/{no}")
    public ResponseEntity<?> updateAddress(@RequestHeader("Authorization") String token,@RequestBody HashMap<String, Object> updates,@PathVariable int no){


        return userControllerService.updateAddress(token,updates,no);


    }
    @PutMapping("/set-default-address/{addressno}")
    public ResponseEntity<?> setDefaultAddress(@RequestHeader("Authorization") String token,@PathVariable int addressno){

        return userControllerService.setDefaultAddress(token,addressno);

    }
    @GetMapping("/get-default-address")
    public ResponseEntity<?> getDefaultAddress(@RequestHeader("Authorization") String token){

        return userControllerService.viewDefaultAddress(token);

    }
    @DeleteMapping("delete/address/{addressno}")
    public ResponseEntity<?> deleteAddress(@RequestHeader("Authorization") String token, @PathVariable int addressno){

        return userControllerService.deleteAddress(token,addressno);

    }
    @GetMapping("/view/orders")
    public ResponseEntity<?> viewOrders(@RequestHeader("Authorization") String token){

        return userControllerService.viewOrders(token);

    }
    @PutMapping("/order-cancel/{orderno}")
    public ResponseEntity<?> cancelOrder(@RequestHeader("Authorization") String token,@PathVariable int orderno){

        return userControllerService.cancelOrder(token,orderno);

    }



}