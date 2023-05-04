package com.example.apigateway.demo.config;

import com.example.apigateway.demo.filters.JwtAuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RoutesConfig {

//    @Autowired
//    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder,JwtAuthFilter jwtAuthFilter)
    {
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p
                        .path("/serviceA/**")
                        .filters(f ->f
                                .filter(jwtAuthFilter.apply(new JwtAuthFilter.Config())))
                        .uri("http://localhost:7001"))
                .route(p -> p
                        .path("/auth/**")
                        .uri("http://localhost:8080"))
                .build();
    }

}
