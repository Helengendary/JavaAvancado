package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.CityDto;
import com.example.demo.dto.CountCity;
import com.example.demo.model.City;
import com.example.demo.model.State;
import com.example.demo.dto.CountState;
import com.example.demo.dto.StateDto;
import com.example.demo.dto.Token;
import com.example.demo.filters.JWTAuthenticationFilter;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.StateRepository;
import com.example.demo.service.JWTService;


@CrossOrigin(origins={"https://www.cepservice.gov."})
@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityRepository repo;

    @Autowired
    JWTService<Token> jwt;

    @Autowired
    JWTAuthenticationFilter filter = new JWTAuthenticationFilter(jwt);

    @GetMapping("/count")
    public CountCity count() {
        return new CountCity(repo.count(), repo.lastCode()+1);
    }

    @PostMapping("")
    public String create(@RequestBody CityDto data, @RequestAttribute("token") Token token) {
        var cities = repo.findAll();
        
        for (City city : cities) {
            if (city.getNome().toLowerCase().contentEquals(data.city().toLowerCase())) {
                return "Cidade já existe";
            } else if (city.getCodigo() == data.code()) {
                return "Código já existe";
            }
        }
        
        City newCity = new City();

        newCity.setCodigo(data.code());
        newCity.setNome(data.city());

        repo.save(newCity);
        return "Estado cadastrado";
    }
}