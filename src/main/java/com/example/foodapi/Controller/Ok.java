package com.example.foodapi.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Ok {
    @GetMapping("/")
    private String check(){
        return "everything was fine lets go";
    }
}
