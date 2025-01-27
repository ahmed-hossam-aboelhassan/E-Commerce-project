package com.ecommerceproject.Controller;
import com.ecommerceproject.ControllerService.CartControllerService;
import com.ecommerceproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shopping")
public class CartController {
    @Autowired
    private CartControllerService cartControllerService;

    @PostMapping("/add-to-cart/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable int productId, @RequestHeader("Authorization") String token){
        User ut=cartControllerService.extractUser(token);
        return cartControllerService.addToCart(ut,productId);

    }
  @GetMapping("view/cart")
  public ResponseEntity<?> getCart(@RequestHeader("Authorization") String token)  {
      User ut=cartControllerService.extractUser(token);

      return cartControllerService.getCart(ut);
  }

  @DeleteMapping("/remove-single-item-of/{productId}")
  public ResponseEntity<?> removeSingleItem(@PathVariable int productId,@RequestHeader("Authorization") String token){
    User ut=cartControllerService.extractUser(token);

   return cartControllerService.removeSingleItemOf(ut,productId);



  }
    @DeleteMapping("/remove-all-item-of/{productId}")
    public ResponseEntity<?> allItemOf(@PathVariable int productId,@RequestHeader("Authorization") String token){
        User ut=cartControllerService.extractUser(token);
       return  cartControllerService.removeAllItemOf(ut,productId);


    }

    @DeleteMapping("/delete/cart")
    public ResponseEntity<?> deleteCart(@RequestHeader("Authorization") String token){
        User ut=cartControllerService.extractUser(token);
        return cartControllerService.deleteCart(ut);

    }

    @GetMapping("checkout")
    public ResponseEntity<?> chooseAddress(@RequestHeader("Authorization") String token)  {
        return cartControllerService.checkOut(token);

    }
    @GetMapping("checkout/cash-on-delivery")
    public ResponseEntity confirmOrder(@RequestHeader("Authorization") String token){
        return cartControllerService.confirmOrder(token);


    }



}
