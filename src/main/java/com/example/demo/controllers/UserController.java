package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.NewPassword;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Userdata;
import com.example.demo.repositories.UserRepository;

@CrossOrigin(origins={"http://localhost:5257"})
@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    UserRepository repo;

    @PostMapping("/create")
    public String login(@RequestBody UserDto data) {

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
        
        if (!numero || !maiscula || !minuscula || data.password().length() < 4) {
            return "Senha necessite ser maior que 4 carácteres e ter número, letra maiscula e letra minuscúla";
        }
   
        var users = repo.findAll();

        for (Userdata userdata : users) {
            if (data.email().equals(userdata.getEmail())) {
                return "Email existente";
            }
            
            if (data.username().equals(userdata.getApelido())) {
                return "Apelido existente";
            }
        }

        Userdata newUser = new Userdata();
        newUser.setApelido(data.username());
        newUser.setEmail(data.email());
        newUser.setSenha(data.password());

        repo.save(newUser);

        return "Usuário logado!";
    }

    @PatchMapping("/changepassword")
    public String update(@RequestBody NewPassword data) {

        boolean minuscula = false;
        boolean maiscula = false;
        boolean numero = false;

        if (data.repeatPassword() == null || data.newPassword() == null || data.password() == null || data.username() == null) {
            return "Nenhum campo pode estar vazio";
        }
        
        for (int i = 0; i < data.newPassword().length(); i++) {
            if (data.newPassword().charAt(i) >= 48 && data.newPassword().charAt(i) <= 57) {
                numero = true;
            }
            
            if (data.newPassword().charAt(i) >= 65 && data.newPassword().charAt(i) <= 90) {
                maiscula = true;
            }
            
            if (data.newPassword().charAt(i) >= 97 && data.newPassword().charAt(i) <= 122) {
                minuscula = true;
            }
        }

        if (!data.newPassword().contentEquals(data.repeatPassword())) {
            return "Senhas diferentes"; 
        }
        
        if (!numero || !maiscula || !minuscula || data.password().length() < 4) {
            return "Senha necessita ser maior que 4 carácteres e ter número, letra maiscula e letra minuscúla";
        }
   
        var users = repo.findAll();

        for (Userdata userdata : users) {
            if (data.password().equals(userdata.getSenha()) && data.username().equals(userdata.getApelido())) {
                userdata.setSenha(data.newPassword());
                repo.save(userdata);
                return "Usuário updatado!";
            }
        }
        
        return "Usuário não existe!";
    }
}