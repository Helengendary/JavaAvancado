package com.example.demo.service;

public interface EncodePass {
    public String encode(String pass);
    public boolean matches(String pass, String encodePass);
}
