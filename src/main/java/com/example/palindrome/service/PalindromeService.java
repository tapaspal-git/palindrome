package com.example.palindrome.service;
import com.example.palindrome.model.PalindromeResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface PalindromeService {

     PalindromeResponse validatePalindrome(String username, String text) throws IOException;
}
