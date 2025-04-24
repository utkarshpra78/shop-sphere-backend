package com.example.foodapi.Controller;

import com.example.foodapi.entity.FoodEntity;
import com.example.foodapi.io.FoodRequest;
import com.example.foodapi.io.FoodResponse;
import com.example.foodapi.service.FoodServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin("*")
public class FoodController {

    @Autowired
    FoodServiceImpl service;

    @PostMapping
    public ResponseEntity<FoodResponse> addFood(@RequestPart("food")String request, @RequestPart("file") MultipartFile file) {
        ObjectMapper mapper=new ObjectMapper();
        FoodRequest request1=null;
        try{
            request1=mapper.readValue(request, FoodRequest.class);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"failed to load json");
        }
        FoodResponse response=service.addFood(request1,file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<FoodResponse> readFood(){
        return service.readFoods();
    }

    @GetMapping("/{id}")
    public FoodResponse readFood(@PathVariable String id){
        return service.readFood(id);
    }

    @DeleteMapping("/{id}")
    public void DeleteFood(@PathVariable String id){
        service.deleteFood(id);
    }

    @GetMapping("/image/{productId}")
    public ResponseEntity<byte[]>getImageById(@PathVariable String productId){
        FoodEntity entity=service.getProductById(productId);
        byte[] imageFile= entity.getImage();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(entity.getImageType()))
                .body(imageFile);
    }
}
