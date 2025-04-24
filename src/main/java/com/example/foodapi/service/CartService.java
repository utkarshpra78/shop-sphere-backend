package com.example.foodapi.service;

import com.example.foodapi.io.CartRequest;
import com.example.foodapi.io.CartResponse;

public interface CartService {

    CartResponse addToCart(CartRequest foodId);
    CartResponse getCart();
    void clearCart();
    CartResponse removeFromCard(CartRequest request);
}
