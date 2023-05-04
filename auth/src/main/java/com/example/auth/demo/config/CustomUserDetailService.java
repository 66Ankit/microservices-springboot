package com.example.auth.demo.config;

import com.example.auth.demo.entity.User;
import com.example.auth.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.Optional;
import java.util.function.Function;


public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Optional<User> user = Optional.ofNullable(userRepo.findByName(username));

        Function<? super User, ?> CustomUserDetails;
        return user.map(com.example.auth.demo.config.CustomUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }
}
