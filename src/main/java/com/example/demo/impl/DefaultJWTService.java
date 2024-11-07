package com.example.demo.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import com.example.demo.dto.Token;
import com.example.demo.service.JWTService;

public class DefaultJWTService implements JWTService<Token> {

    public static String get(org.apache.tomcat.util.json.Token token) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private final String SECRET_KEY = "uouoououououououuououoououououououououououoououououou";
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    @Override
    public String get(Token token) {
        var claims = new HashMap<String, Object>();

        claims.put("id", token.id());

        return get(claims);
    }

    @Override
    public Token validate(String jwt) {
        try {
            var map = validateJwt(jwt);

            return new Token(Long.parseLong(map.get("id").toString()));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private String get(Map<String, Object> customClaims) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().claims().add(customClaims).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).and().signWith(key).compact();
    }

    private Map<String, Object> validateJwt(String jwt) {
        
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
    
        return new HashMap<>(claims);
    }
}
