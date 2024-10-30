package com.example.demo.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ImaginaryExpo;

@CrossOrigin(origins={"http://localhost:5257"})
@RestController
@RequestMapping("/imaexp")
public class ImaginaryController {
    
    @GetMapping
    public ImaginaryExpo Revert(Double A, Double b) {

        Double Re = new BigDecimal(A * Math.sin(b)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        Double Im = new BigDecimal(A * Math.cos(b)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        
        return new ImaginaryExpo(Re, Im);
    }   
}
