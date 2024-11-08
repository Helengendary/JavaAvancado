package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JWTdto;
import com.example.demo.dto.Token;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Userdata;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.EncodePass;
import com.example.demo.service.JWTService;

@CrossOrigin(origins={"https://www.cepservice.gov."})
@RestController
@RequestMapping("")
public class AuthController {

    @Autowired
    UserRepository repo;

    @Autowired
    EncodePass encoder;

    @Autowired
    JWTService<Token> jwt;

    @PostMapping("/auth")
    public JWTdto login(@RequestBody UserDto data) {

        var users = repo.findAll();

        for (Userdata userdata : users) {            
            if (data.username().equals(userdata.getApelido())) {
                if (encoder.matches(data.password(), userdata.getSenha())) {
                    return new JWTdto(jwt.get(new Token(userdata.getId())), "Usuário logado");
                } else {
                    return new JWTdto(null, "Senha incorreta");
                }
            }
        }
        
        return new JWTdto(null, "Usuário não cadastrado");
    }
}