package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.repositories.UserRepository;

@CrossOrigin(origins={"http://localhost:5257"})
@RestController
@RequestMapping("/create")
public class UserController {

    @Autowired
    UserRepository repo;

    @PostMapping("")
    public String login(@RequestBody UserDto data) {
        
        var users = repo.findAll();

        System.out.println(users);

        boolean padraoEmail = false;
        boolean minuscula = false;
        boolean maiscula = false;
        boolean numero = false;

        if (data.email() == null || data.password() == null || data.username() == null) {
            return "Nenhum campo pode estar vazio";
        }

        for (int i = 0; i < data.email().length(); i++) {
            if (data.email().charAt(i) == '@' && i>0) {
                padraoEmail = true;
            }
        }
        
        for (int i = 0; i < data.password().length(); i++) {
            if (data.password().charAt(i) >= 48 && data.password().charAt(i) <= 57) {
                numero = true;
            }
            
            if (data.password().charAt(i) >= 65 && data.password().charAt(i) <= 90) {
                maiscula = true;
            }
            
            if (data.password().charAt(i) >= 97 && data.password().charAt(i) <= 122) {
                minuscula = true;
            }
        }

        if (!padraoEmail) {
            return "Email inválido"; 
        }
        
        if (numero && maiscula && minuscula && data.password().length() >= 4) {
            return "Senha necessite ser maior que 4 carácteres e ter número, letra maiscula e letra minuscúla";
        }

        return "Usuário logado!";
    
    }
    
}
