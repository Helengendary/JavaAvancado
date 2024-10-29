package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.SumResult;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @GetMapping("/{a}")
    public SumResult test(@PathVariable Integer a, Integer b) {
        if (b == null) {
            b = 2;
        }

        int result = a + b;
        boolean isEven = result % 2 == 0;

        return new SumResult(result, isEven);
    }
    
}
