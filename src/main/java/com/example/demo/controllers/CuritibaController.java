package com.example.demo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins={"http://localhost:5257"})
@RestController
@RequestMapping("/curitiba")
public class CuritibaController {

        // @GetMapping
        // public Curitiba Revert(String cep, String cpf) {

        
        //     return new Curitiba();
        // }   
}
