package com.example.auth.demo.service;

import com.example.auth.demo.entity.User;
import com.example.auth.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "User added to the database";
    }


    public String generateToken(String userName){
       return jwtService.generateToken(userName);
    }

    public void validateToken(String token)
    {
        jwtService.validateToken(token);
    }


}
