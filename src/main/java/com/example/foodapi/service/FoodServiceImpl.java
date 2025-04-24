package com.example.foodapi.service;

import com.example.foodapi.entity.FoodEntity;
import com.example.foodapi.io.FoodRequest;
import com.example.foodapi.io.FoodResponse;
import com.example.foodapi.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    FoodRepository repo;
    @Override
    public FoodResponse addFood(FoodRequest request,MultipartFile file) {
        FoodEntity entity=requestToObject(request);

        entity.setImageType(file.getContentType());

        try{
            entity.setImage(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        repo.save(entity);
        return objectToResponse(entity);
    }


    @Override
    public List<FoodResponse> readFoods() {
        List<FoodEntity> foodEntityList= repo.findAll();
        return foodEntityList.stream().map(this::objectToResponse).toList();
    }

    @Override
    public FoodResponse readFood(String id) {
        FoodEntity food= repo.findById(id).orElseThrow(()-> new RuntimeException("food not found for the id"));
        return objectToResponse(food);
    }

    @Override
    public void deleteFood(String id) {
        try{
            repo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FoodEntity requestToObject(FoodRequest request){
        return FoodEntity.builder()
                .price(request.getPrice())
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .build();
    }

    private FoodResponse objectToResponse(FoodEntity entity){
        return FoodResponse.builder()
                .category(entity.getCategory())
                .description(entity.getCategory())
                .id(entity.getId())
                .price(entity.getPrice())
                .name(entity.getName())
                .build();
    }

    public FoodEntity getProductById(String productId) {
        return repo.findById(productId).orElse(new FoodEntity());
    }
}
