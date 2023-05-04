package com.example.auth.demo.controller;

import com.example.auth.demo.dto.AuthRequest;
import com.example.auth.demo.entity.User;
import com.example.auth.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
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
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        try {
            authService.validateToken(token);
            return new ResponseEntity<String>("Welcome",HttpStatus.OK);
        } catch (Exception e)
        {
           return new ResponseEntity<String>("Invalid auth token",HttpStatus.UNAUTHORIZED);

        }



    }


}
