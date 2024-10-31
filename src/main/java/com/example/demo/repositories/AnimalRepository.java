package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByType(String type); 
}
