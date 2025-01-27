package com.ecommerceproject.Controller;

import com.ecommerceproject.ControllerService.PreLoginControllerService;
import com.ecommerceproject.Dto.AuthRequest;
import com.ecommerceproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trial")
public class PreLoginController {
@Autowired
 private PreLoginControllerService preLoginControllerService;



 @PostMapping("/register")
 public ResponseEntity<?> register(@RequestBody User user){
   return preLoginControllerService.register(user);




 }
    @PostMapping("/loginbyid")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        return preLoginControllerService.loginById(authRequest);
    }
    @PostMapping("/loginbyemail")
    public ResponseEntity<?> loginByEmail(@RequestBody AuthRequest authRequest) {
        //User u=preLoginControllerService.loginByEmail(authRequest);
        return preLoginControllerService.loginByEmail(authRequest);

    }


}
