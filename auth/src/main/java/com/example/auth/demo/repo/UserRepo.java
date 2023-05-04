package com.example.auth.demo.repo;

import com.example.auth.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

     User findByName(String username);
}
