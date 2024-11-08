package com.example.demo.controllers;

import java.util.ArrayList;
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

import com.example.demo.model.State;
import com.example.demo.dto.CountState;
import com.example.demo.dto.StateDto;
import com.example.demo.dto.Token;
import com.example.demo.filters.JWTAuthenticationFilter;
import com.example.demo.repositories.StateRepository;
import com.example.demo.service.JWTService;


@CrossOrigin(origins={"https://www.cepservice.gov."})
@RestController
@RequestMapping("/state")
public class StateController {

    @Autowired
    StateRepository repo;
    
    @Autowired
    JWTService<Token> jwt;

    @Autowired
    JWTAuthenticationFilter filter = new JWTAuthenticationFilter(jwt);

    private ArrayList<Integer> deletados = new ArrayList<>();

    @GetMapping("")
    public ResponseEntity<List<StateDto>> states(Integer page, Integer count) {
        // 5 estados por página
        
        if (count == -1) {
            return new ResponseEntity<>(repo.findAll().stream().map(a -> new StateDto(a.getNome(), a.getCodigo())).collect(Collectors.toList()), HttpStatus.OK);
        } else if (count > -1 && page > 0){
            List<State> statesRepo = repo.findAll();
            List<State> adds = repo.findAll();

            Integer cont = 0;
            adds.clear();
            for (int i = 0; i < statesRepo.size(); i++) {

                if (i >= page*5 && cont <= count) {
                    adds.add(statesRepo.get(i));
                }
            }
            return new ResponseEntity<>(adds.stream().map(a -> new StateDto(a.getNome(), a.getCodigo())).collect(Collectors.toList()), HttpStatus.OK);
        } 
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/count")
    public CountState count() {
        return new CountState(repo.count(), repo.lastCode()+1);
    }

    @PostMapping("")
    public String create(@RequestBody StateDto data, @RequestAttribute("token") Token token) {
        var states = repo.findAll();
        
        for (State state : states) {
            if (state.getNome().toLowerCase().contentEquals(data.state().toLowerCase())) {
                return "Estado já existe";
            } else if (state.getCodigo() == data.code()) {
                return "Código já existe";
            } else if (deletado(state.getCodigo())) {
                return "Código já foi usado";
            }
        }
        

        State newState = new State();

        newState.setCodigo(data.code());
        newState.setNome(data.state());

        repo.save(newState);
        return "Estado cadastrado";
    }
    
    @PutMapping("/{id}")
    public String change(@PathVariable long id, @RequestBody StateDto data, String jwtAdmin) {
        var state = repo.findById(id);
        var states = repo.findAll();
        
        if (state == null) {
            return "id não encontrado";
        }

        Integer estados = 0;
        Integer codigos = 0;

        for (State estado : states) {
            if (estado.getNome().toLowerCase().contentEquals(data.state().toLowerCase())) {
                estados++;
            } else if (estado.getCodigo() == data.code()) {
                codigos++;
            }  else if (deletado(estado.getCodigo())) {
                return "Código já foi usado";
            }
        }
        
        if (estados > 1) {
            return "Estado já existe";
        } else if (codigos > 1) {
            return "Código já existe";
        }

        for (State estado : states) {
            if (estado.getNome().toLowerCase().contentEquals(data.state().toLowerCase())) {
                estado.setNome(data.state());
                estado.setCodigo(data.code());
                repo.save(estado);
            }
        }

        return "Estado atualizado";
    }
   
    @PatchMapping("/{id}")
    public String changeOne(@PathVariable long id, @RequestBody StateDto data,  @RequestAttribute("token") Token token) {
        var state = repo.findById(id);
        var states = repo.findAll();
        
        if (state == null) {
            return "id não encontrado";
        }

        Integer estados = 0;
        Integer codigos = 0;

        if (data.state() != null && data.code() == null) {
            
            for (State estado : states) {
                if (estado.getNome().toLowerCase().contentEquals(data.state().toLowerCase())) {
                    estados++;
                }
            }
            
            if (estados > 1) {
                return "Estado já existe";
            }
    
            for (State estado : states) {
                if (estado.getNome().toLowerCase().contentEquals(data.state().toLowerCase())) {
                    estado.setNome(data.state());
                    repo.save(estado);
                }
            }

            return "Estado atualizado";
        } else if (data.code() != null && data.state() == null) {
            for (State estado : states) {
                if (estado.getCodigo() == data.code()) {
                    codigos++;
                } else if (deletado(estado.getCodigo())) {
                    return "Código já foi usado";
                }
            }
            
            if (codigos > 1) {
                return "Código já existe";
            }
    
            for (State estado : states) {
                if (estado.getCodigo() == data.code()) {
                    estado.setCodigo(data.code());
                    repo.save(estado);
                }
            }
            
            return "código cadastrado";
        } else if (data.code() != null && data.state() != null) {
            for (State estado : states) {
                if (estado.getNome().toLowerCase().contentEquals(data.state().toLowerCase())) {
                    estados++;
                } else if (estado.getCodigo() == data.code()) {
                    codigos++;
                }  else if (deletado(estado.getCodigo())) {
                    return "Código já foi usado";
                }
            }
            
            if (estados > 1) {
                return "Estado já existe";
            } else if (codigos > 1) {
                return "Código já existe";
            }
    
            for (State estado : states) {
                if (estado.getNome().toLowerCase().contentEquals(data.state().toLowerCase())) {
                    estado.setNome(data.state());
                    estado.setCodigo(data.code());
                    repo.save(estado);
                }
            }   
            
            return "Estado atualizado";
        }

        return "Ambos campos nulos";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, @RequestBody StateDto data,  @RequestAttribute("token") Token token) {
        var state = repo.findById(id);
        var states = repo.findAll();
        
        if (state == null) {
            return "id não encontrado";
        }

        for (State estado : states) {
            if (estado.getNome().toLowerCase().contentEquals(data.state().toLowerCase())) {
                repo.deleteById(estado.getId());
                deletados.add(estado.getCodigo());
            }
        }

        return "Estado deletado";
    }

    public boolean deletado(Integer i) {
        for (Integer integer : deletados) {
            if (integer == i) {
                return true;
            }
        }

        return false;
    }
}