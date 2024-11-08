package com.example.demo.controllers;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserCode;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Userdata;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.EncodePass;

@CrossOrigin(origins={"https://www.cepservice.gov."})
@RestController
@RequestMapping("")
public class UserController {

    Random ran = new Random();

    @Autowired
    UserRepository repo;

    @Autowired
    EncodePass encoder;

    @PostMapping("/user")
    public UserCode create(@RequestBody UserDto data) {

        var users = repo.findAll();

        for (Userdata userdata : users) {            
            if (data.username().equals(userdata.getApelido())) {
                return new UserCode(null, "Apelido existente");
            }
        }

        Userdata newUser = new Userdata();
        newUser.setApelido(data.username());
        newUser.setSenha(encoder.encode(data.password()));

        
        String usercode = "";
        
        for (int i = 0; i < data.username().length() + data.password().length(); i++) {
            int aleatorio = ran.nextInt(10);
            
            if (aleatorio > 10) {
                usercode += data.password().charAt(ran.nextInt(data.password().length()));
            } else {
                usercode += data.username().charAt(ran.nextInt(data.username().length()));
            }
        }
        
        newUser.setUsercode(usercode);

        repo.save(newUser);
        return new UserCode(usercode, "Usuário logado!");
    }
}