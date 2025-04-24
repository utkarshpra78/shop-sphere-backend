package com.example.foodapi.service;

import com.example.foodapi.io.UserRequest;
import com.example.foodapi.io.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRequest request);
    String findByUserId(String email);
    String getLoggedUser();
}
