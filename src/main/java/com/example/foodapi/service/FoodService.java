package com.example.foodapi.service;

import com.example.foodapi.io.FoodRequest;
import com.example.foodapi.io.FoodResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FoodService {

    FoodResponse addFood(FoodRequest request, MultipartFile file);
    List<FoodResponse> readFoods();
    FoodResponse readFood(String id);
    void deleteFood(String id);
}
