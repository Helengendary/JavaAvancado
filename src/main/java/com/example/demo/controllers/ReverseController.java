package com.example.demo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Reverse;

@CrossOrigin(origins={"http://localhost:5257"})
@RestController
@RequestMapping("/reverse")
public class ReverseController {

    @GetMapping("/{word}")
    public Reverse Revert(@PathVariable String word) {
        
        String wordRevert = "";
        Boolean palindrome = false;

        for (int i = word.length(); i > 0 ; i--) {
            wordRevert += word.charAt(i-1);
        }

        if (wordRevert.equals(word)) {
            palindrome = true;
        }

        return new Reverse(wordRevert, palindrome);
    }   
}