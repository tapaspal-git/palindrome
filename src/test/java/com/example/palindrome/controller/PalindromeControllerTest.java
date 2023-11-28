package com.example.palindrome.controller;

import com.example.palindrome.model.PalindromeRequest;
import com.example.palindrome.model.PalindromeResponse;
import com.example.palindrome.service.PalindromeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PalindromeControllerTest {

    @Mock
    private PalindromeService palindromeService;

    @InjectMocks
    private PalindromeController palindromeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidatePalindromeForSuccessResponse() throws IOException {
        //preparing test data
        PalindromeRequest request = new PalindromeRequest("tapas", "kayak");
        PalindromeResponse expectedResponse = new PalindromeResponse("kayak", true);

        // Mocking the PalindromeService
        when(palindromeService.validatePalindrome(request.getUsername(), request.getText())).thenReturn(expectedResponse);

        // calling the method
        ResponseEntity<Object> response = palindromeController.validatePalindrome(request);

        // Verifying the response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifying the response body
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testValidatePalindromeForInvalidInputText() throws IOException {

        PalindromeRequest request = new PalindromeRequest("user", "123");

        // Mocking the PalindromeService
        when(palindromeService.validatePalindrome(request.getUsername(), request.getText())).thenThrow(new IOException("Please pass valid text for checking palindrome"));

        // calling the method
        ResponseEntity<Object> responseEntity = palindromeController.validatePalindrome(request);

        // Verifying the response status
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Please pass valid text for checking palindrome", responseEntity.getBody());
    }

    @Test
    public void testValidatePalindromeForFileNotFound() throws IOException {

        PalindromeRequest request = new PalindromeRequest("user", "abc");

        // Mocking the PalindromeService
        when(palindromeService.validatePalindrome(request.getUsername(), request.getText())).thenThrow(new IOException("File not found"));

        // calling the method
        ResponseEntity<Object> responseEntity = palindromeController.validatePalindrome(request);

        // Verifying the response status
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("File not found", responseEntity.getBody());
    }

}

