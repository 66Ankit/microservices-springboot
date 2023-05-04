package com.example.apigateway.demo.filters;


import com.example.apigateway.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
                if(exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                {

                }
                else {
                    throw new RuntimeException("missing auth header");
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
                    restTemplate.getForObject("http://localhost:8080/auth/validate?token="+authheaders,String.class);
//                    jwtUtil.validateToken(authheaders);
                }
                catch (Exception e){
//                    System.out.println("error"+e.getMessage());
                    throw new RuntimeException("Un auth user");

                }
            }

            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
