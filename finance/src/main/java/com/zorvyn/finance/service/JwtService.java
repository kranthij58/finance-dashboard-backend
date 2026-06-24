package com.zorvyn.finance.service;

import com.zorvyn.finance.dto.request.LoginRequest;
import com.zorvyn.finance.dto.response.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final String secretKey;

    public JwtService(){
        this.secretKey = keyGenerator();
    }

    public String keyGenerator()  {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGenerator.generateKey();
            return Encoders.BASE64.encode(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
    public JwtResponse generateToken(@Valid LoginRequest loginRequest) {
        Map<String,Object> claims = new HashMap<>();
        Date issuedAt = new Date();
        Date expiration = new Date(System.currentTimeMillis() + 600 * 1000);

        String token =  Jwts.builder()
                .claims(claims)
                .subject(loginRequest.getEmail())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(getKey())
                .compact();
        System.out.println(token);
        return JwtResponse.builder()
                .token(token)
                .issuedAt(issuedAt)
                .expirationDate(expiration)
                .build();
    }

    public Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
