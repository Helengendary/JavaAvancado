package com.example.demo.dto;

public record NewPassword(
    String username,
    String password,
    String newPassword,
    String repeatPassword
) {}
