package com.example.demo.controllers;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CepDto;
import com.example.demo.dto.CepInfo;
import com.example.demo.dto.StateDto;
import com.example.demo.dto.UserCode;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Userdata;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.StateRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.EncodePass;

@CrossOrigin(origins={"https://www.cepservice.gov."})
@RestController
@RequestMapping("/cep")
public class CepController {

    Random ran = new Random();

    @Autowired
    StateRepository repoState;

    @Autowired
    CityRepository repoCity;

    @Autowired
    EncodePass encoder;

    @GetMapping("")
    public ResponseEntity<CepInfo> validation(@RequestBody CepDto data) {

        String cep = data.code();

        boolean versao = false;
        Integer versaoIndex = 0;
        boolean estado = false;
        boolean cidade = false;
        Integer cidadeIndex = 0;
        boolean residencial = false;
        Integer soma = 0;
        String atual = "";
        String nomeCidade = "";
        String nomeEstado = "";

        for (int i = 0; i < cep.length(); i++) {
            atual += cep.charAt(i);

            if (!versao && cep.charAt(i) == '-') {
                versao = true;
                versaoIndex = i;
                if (!atual.equals("01-")) {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }   
                atual = ""; 
            } 
            
            if (versao && !estado && i == versaoIndex+2 ) {
                estado = true;
                soma += Integer.valueOf(atual);
                if (repoState.findByCodigo(Integer.valueOf(atual)).size() == 0) {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                } else {
                    nomeEstado = repoState.nome(atual);
                }
                atual = "";
            }

            if (versao && estado && !cidade && cep.charAt(i) == '-') {
                cidade = true;
                atual = "";
                cidadeIndex = i;
                soma += Integer.valueOf(cep.charAt(i-2)+cep.charAt(i-1));
                if (repoCity.findByCodigo(Integer.valueOf(cep.charAt(i-2)+cep.charAt(i-1))).size() == 0) {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }else{
                    // nomeCidade = repoCity.nome(cep.charAt(i-2)+cep.charAt(i-1));
                    // não deu tempo de pegar a cidade;
                }
            }

            if (versao && estado && cidade && i < cidadeIndex+9) {
                soma += Integer.valueOf(cep.charAt(i));
            }

            if (versao && estado && cidade && i == cidadeIndex+9) {
                residencial = true;
                atual = "";
            }

            if (residencial && versao && estado && cidade && i == cep.length()-1) {
                if (!atual.contains(soma.toString())) {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
            }
        }

        return new ResponseEntity<>(new CepInfo("rua", "bairro", nomeEstado, atual), HttpStatus.OK);
    }
}