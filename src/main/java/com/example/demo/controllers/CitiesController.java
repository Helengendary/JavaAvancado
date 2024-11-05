package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CitiesDto;
import com.example.demo.repositories.CitiesRepository;

@CrossOrigin(origins={"http://localhost:5257"})
@RestController
@RequestMapping("/cities")
public class CitiesController {
    @Autowired
    CitiesRepository repo;

    @GetMapping("")
    public ResponseEntity<List<CitiesDto>> getAll() {

        var cities = repo.findAll().stream().map(a -> new CitiesDto( a.getPais(), a.getCidade(), a.getEstado())).collect(Collectors.toList());

        System.out.println(cities);
        
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
    
    @GetMapping("/{type}")
    public ResponseEntity<List<CitiesDto>> getByCidade(@PathVariable String type) {
        
        var cities = repo.findByCidade(type).stream().map(a -> new CitiesDto( a.getPais(), a.getCidade(), a.getEstado())).collect(Collectors.toList());
        System.out.println(cities);

        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}
