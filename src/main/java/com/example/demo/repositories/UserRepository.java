package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Userdata;

@Repository
public interface UserRepository extends JpaRepository<Userdata, Long>{
    List<Userdata> findByEmail(String email);
}
