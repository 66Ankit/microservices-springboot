package com.example.apigateway.demo.filters;


import com.example.apigateway.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;

@Component
public class JwtAuthFilter  extends AbstractGatewayFilterFactory<JwtAuthFilter.Config>  {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RouteValidator routeValidator;

    public JwtAuthFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            System.out.println("filter invoked");

            if(routeValidator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();

                }

                String authheaders =exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                System.out.println(authheaders);
                if(authheaders!=null && authheaders.startsWith("Bearer "))
                {
                    authheaders = authheaders.substring(7);
                    System.out.println(authheaders);

                }
                try{
                    //rest call to auth service
                    String  response= restTemplate.getForObject("http://localhost:8080/auth/validate?token="+authheaders,String.class);

//                  jwtUtil.validateToken(authheaders);
                }
                catch (Exception e){
                   exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                   return exchange.getResponse().setComplete();

                }
            }

            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
