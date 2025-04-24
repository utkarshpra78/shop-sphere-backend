package com.example.foodapi.service;

import com.example.foodapi.entity.CartEntity;
import com.example.foodapi.io.CartRequest;
import com.example.foodapi.io.CartResponse;
import com.example.foodapi.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository repository;
    @Autowired
    UserService userService;
    @Override
    public CartResponse addToCart(CartRequest request) {
        String loggedInUserId = userService.getLoggedUser();
        Optional<CartEntity> cartOptional =  repository.findByUserId(loggedInUserId);
        CartEntity entity = cartOptional.orElseGet(()->new CartEntity(loggedInUserId,new HashMap<>()));
        Map<String,Integer>cartItems = entity.getItems();
        cartItems.put(request.getFoodId(),cartItems.getOrDefault(request.getFoodId(),0)+1);
        entity.setItems(cartItems);
        entity = repository.save(entity);
        return convertToResponse(entity);
    }

    @Override
    public CartResponse getCart() {
        String loggedInUserId = userService.getLoggedUser();
        CartEntity entity = repository.findByUserId(loggedInUserId).orElse(new CartEntity(null,loggedInUserId,new HashMap<>()));
        return convertToResponse(entity);
    }

    @Override
    public void clearCart() {
        String loggedInUserId = userService.getLoggedUser();
        repository.deleteByUserId(loggedInUserId);
    }

    @Override
    public CartResponse removeFromCard(CartRequest request) {
        String loggedInUserId = userService.getLoggedUser();
        Optional<CartEntity> cartOptional =  repository.findByUserId(loggedInUserId);
        CartEntity entity = cartOptional.orElseGet(()->new CartEntity(loggedInUserId,new HashMap<>()));
        Map<String,Integer>cartItems = entity.getItems();
        if(cartItems.containsKey(request.getFoodId())){
            int currentQty = cartItems.get(request.getFoodId());
            if(currentQty>0){
                cartItems.put(request.getFoodId(),currentQty-1);
            }else{
                cartItems.remove(request.getFoodId());
            }
            entity=repository.save(entity);
        }
        return convertToResponse(entity);
    }

    private CartResponse convertToResponse(CartEntity entity){
        return CartResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .items(entity.getItems())
                .build();
    }
}
