package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JWTdto;
import com.example.demo.dto.NewPassword;
import com.example.demo.dto.Produtodto;
import com.example.demo.dto.Token;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserLogar;
import com.example.demo.impl.DefaultJWTService;
import com.example.demo.model.Userdata;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.EncodePass;
import com.example.demo.service.JWTService;

import jakarta.servlet.http.HttpSessionAttributeListener;

@CrossOrigin(origins={"http://localhost:5257"})
@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    UserRepository repo;

    @Autowired
    EncodePass encoder;

    @Autowired
    JWTService<Token> jwt;

    @PostMapping("/create")
    public String create(@RequestBody UserDto data) {

        boolean padraoEmail = false;
        boolean minuscula = false;
        boolean maiscula = false;
        boolean numero = false;

        if (data.email() == null || data.password() == null || data.username() == null) {
            return "Nenhum campo pode estar vazio";
        }

//JEITO DO EDUARDO DE VER "@"        
    // @Override
    // public Boolean validateEmail(String email)
    // {
    //     int at = email.indexOf("@");

    //     if (at > 0 && at != email.length-1) {
    //         return true
    //     } else {
    //         return false
    //     }
    // }
        
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
        newUser.setSenha(encoder.encode(data.password()));

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
            if (encoder.matches(data.password(), userdata.getSenha()) && data.username().equals(userdata.getApelido())) {
                userdata.setSenha(data.newPassword());
                repo.save(userdata);
                return "Usuário updatado!";
            }
        }
        
        return "Usuário não existe!";
    }

    @PostMapping("/login")
    public JWTdto login(@RequestBody UserLogar data) {
   
        var users = repo.findAll();

        for (Userdata userdata : users) {
            if (data.login().equals(userdata.getEmail()) || data.login().equals(userdata.getApelido())) {

                if (encoder.matches(data.password(), userdata.getSenha())) {
                    return new JWTdto("logou!", jwt.get(new Token(userdata.getId())));
                } else {
                    return new JWTdto("Senha incorreta!", "");
                }
            }
        }
        return new JWTdto("Usuário não cadastrado!", "");
    }

    @PostMapping("/product")
    public ResponseEntity<String> validar(@RequestBody Produtodto data, @RequestHeader("Authorization") String token) {
   
        System.out.print("VAAAAAAAAAAAA " + token);

        Token atual = jwt.validate(token);

        var users = repo.findAll();
        boolean padraoEmail = false;
        String dominio = "";

        for (Userdata userdata : users) {
            if (atual.id() == userdata.getId()) {
                for (int i = 0; i < userdata.getEmail().length(); i++) {
                    if (userdata.getEmail().charAt(i) == '@' && i>0) {
                        padraoEmail = true;
                    } else if (padraoEmail) {
                        dominio += userdata.getEmail().charAt(i);
                    }
                }
            }
        }

        if (users.size() == 0) {
            return new ResponseEntity<>("dfdsf", HttpStatus.valueOf(401));
        }

        if (dominio.contentEquals("@loja.com")) {
            return new ResponseEntity<>("dfdsf", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("dfdsf", HttpStatus.valueOf(403));
        }

    }
}
