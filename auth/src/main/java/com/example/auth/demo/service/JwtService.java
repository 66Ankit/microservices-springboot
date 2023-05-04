package com.example.auth.demo.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    public static final String SECRET="24423F4528482B4D6251655468576D5A7134743777217A25432A46294A404E63";

    public Jws<Claims> validateToken(final String token)
    {
        try{
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            System.out.println(claimsJws.getBody());
            return  claimsJws;
        }
        catch (Exception e)
        {
            System.out.println("error while decoding claims");
            throw  new RuntimeException("Could not fetch claims");
        }

    }

    public String generateToken(String userName)
    {
        HashMap<String,Object> claims = new HashMap<>();
        return createToken(claims,userName);
    }

    private String createToken(HashMap<String,Object> claims,String username)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
