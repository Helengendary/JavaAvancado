package com.example.demo.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.service.EncodePass;

public class PassEncoder implements EncodePass {

    @Override
    public String encode(String pass) {
        return new BCryptPasswordEncoder().encode(pass);
    }

    @Override
    public boolean matches(String pass, String encodePass) {
        return new BCryptPasswordEncoder().matches(pass, encodePass);
    }
}
