package com.example.demo.service;

public interface JWTService<T> {
    String get(T token);
    T validate (String jwt);
}
