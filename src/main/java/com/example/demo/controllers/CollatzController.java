package com.example.demo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CollatzConjecture;

@CrossOrigin(origins={"http://localhost:5257"})
@RestController
@RequestMapping("/collatz")
public class CollatzController {
    
    @GetMapping
    public CollatzConjecture Revert(Integer current, Integer step) {

        Integer new_current;

        if (current%2 == 0) {
            new_current = current /2;
        } else {
            new_current = (step*3) * current + step;
        }

        return new CollatzConjecture(new_current);
    }   
}
