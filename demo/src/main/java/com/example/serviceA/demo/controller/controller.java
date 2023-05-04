package com.example.serviceA.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("serviceA")
public class controller {

    @GetMapping("/hello")
    public String hello()
    {
        return "Hello ServiceA";
    }

}
