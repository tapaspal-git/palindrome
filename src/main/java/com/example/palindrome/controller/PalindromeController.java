package com.example.palindrome.controller;

import com.example.palindrome.service.PalindromeService;
import com.example.palindrome.model.PalindromeRequest;
import com.example.palindrome.model.PalindromeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PalindromeController {

    @Autowired
    private PalindromeService palindromeService;

    @PostMapping("/validate/palindrome")
    public ResponseEntity<Object> validatePalindrome(@RequestBody PalindromeRequest request) {

        try {
            PalindromeResponse response = palindromeService.validatePalindrome(request.getUsername(), request.getText());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }
}
