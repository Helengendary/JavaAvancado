package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Animal;
import com.example.demo.repositories.AnimalRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.AnimalDto;


@RestController
@RequestMapping("/animal")
public class AnimalController {
    
    @Autowired
    AnimalRepository repo;
    
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getById(@PathVariable long id) {

        var animal = repo.findById(id);

        if (!animal.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/find/{type}")
    public ResponseEntity<List<AnimalDto>> getByType(@PathVariable String type) {
        
        var animals = repo.findByType(type).stream().map(a -> new AnimalDto(a.getNome(), a.getEspecie())).collect(Collectors.toList());

        return new ResponseEntity<>(animals, HttpStatus.OK);
    }
    
}
