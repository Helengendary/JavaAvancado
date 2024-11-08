package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.City;

@Repository
public interface  CityRepository extends JpaRepository<City, Long> {
    List<City> findByCodigo(Integer codigo);

    @Query("SELECT COUNT(*) FROM City")
    long count();

    @Query("SELECT s.codigo FROM City s ORDER BY id DESC LIMIT 1")
    Integer lastCode();

    @Query("SELECT s.nome FROM City s WHERE s.codigo = :code")
    String nome(@Param("code") String code);
}
