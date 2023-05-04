package com.example.auth.demo.controller;

import com.example.auth.demo.dto.AuthRequest;
import com.example.auth.demo.entity.User;
import com.example.auth.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody User user)
    {

        return authService.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest)
    {

       Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));


       if(authentication.isAuthenticated())
        {
            return authService.generateToken(authRequest.getUsername());
        }
        else {
            throw new RuntimeException("User not found in database");
        }

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        try {
            authService.validateToken(token);
            return "Token is valid";
        } catch (Exception e)
        {
            throw new RuntimeException("invalid token");

        }



    }


}
