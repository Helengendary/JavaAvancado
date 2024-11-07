package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.example.demo.dto.Token;
import com.example.demo.impl.DefaultJWTService;
import com.example.demo.impl.PassEncoder;
import com.example.demo.service.EncodePass;
import com.example.demo.service.JWTService;

@Configuration
public class DependecyConfiguration {
    
    @Bean
    @Scope("singleton")
    public EncodePass encodePass () {
        return new PassEncoder();
    }
   
    @Bean
    @Scope("singleton")
    public JWTService<Token> jwt () {
        return new DefaultJWTService();
    }


}
