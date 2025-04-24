package com.example.foodapi.service;

import com.example.foodapi.entity.UserEntity;
import com.example.foodapi.io.AuthenticationResponse;
import com.example.foodapi.io.UserRequest;
import com.example.foodapi.io.UserResponse;
import com.example.foodapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repo;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationFacade authenticationFacade;
    @Override
    public UserResponse registerUser(UserRequest request) {
        UserEntity newUser=convertToEntity(request);
        repo.save(newUser);
        return entityToResponse(newUser);
    }

    @Override
    public String findByUserId(String email) {
        String loggedInUserEmail = authenticationFacade.getAuthentication().getName();
        UserEntity user =  repo.findByEmail(loggedInUserEmail).orElseThrow(()->new UsernameNotFoundException("no user found"));
        return user.getId();
    }

    @Override
    public String getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername(); // or getId() if custom user
        } else {
            return principal.toString();
        }
    }


    private UserEntity convertToEntity(UserRequest request){
        return UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
    }

    private UserResponse entityToResponse(UserEntity entity){
        return UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}
