package com.example.foodapi.Controller;

import com.example.foodapi.io.CartRequest;
import com.example.foodapi.io.CartResponse;
import com.example.foodapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService service;
    @PostMapping
    public CartResponse addToCart(@RequestBody CartRequest request){
        String foodId=request.getFoodId();
        if(foodId==null || foodId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"food id not found");
        }
        return service.addToCart(request);
    }

    @GetMapping
    public CartResponse getCard(){
        return service.getCart();
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(){
        service.clearCart();
    }

    @PostMapping("/remove")
    public CartResponse removeFromCart(@RequestBody CartRequest request){
        String foodId=request.getFoodId();
        if(foodId==null || foodId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"food id not found");
        }
        return service.removeFromCard(request);
    }
}
