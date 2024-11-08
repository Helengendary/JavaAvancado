package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.State;

@Repository
public interface  StateRepository extends JpaRepository<State, Long> {
    List<State> findByCodigo(Integer codigo);

    @Query("SELECT COUNT(*) FROM State")
    long count();

    @Query("SELECT s.codigo FROM State s ORDER BY id DESC LIMIT 1")
    Integer lastCode();

    @Query("SELECT s.nome FROM State s WHERE s.codigo = :code")
    String nome(@Param("code") String code);
}
