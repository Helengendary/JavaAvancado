package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Token;
import com.example.demo.dto.UsercodeSend;
import com.example.demo.filters.JWTAuthenticationFilter;
import com.example.demo.model.Userdata;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.EncodePass;
import com.example.demo.service.JWTService;

@CrossOrigin(origins={"https://www.cepservice.gov."})
@RestController
@RequestMapping("")
public class AdminController {

    @Autowired
    UserRepository repo;

    @Autowired
    JWTService<Token> jwt;

    @Autowired
    JWTAuthenticationFilter filter = new JWTAuthenticationFilter(jwt);

    @PostMapping("/admin")
    public String status(@RequestBody UsercodeSend data, @RequestAttribute("token") Token token) {

        var users = repo.findAll();

        for (Userdata userdata : users) {            
            if (data.usercode().equals(userdata.getUsercode())) {
                userdata.setApelido("admin");
                repo.save(userdata);
                return "Usuário atualizado";
            }
        }        
        return "Usuário não cadastrado";
    }    
}