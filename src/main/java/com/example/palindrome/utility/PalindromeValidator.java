package com.example.palindrome.utility;

import org.springframework.stereotype.Component;

@Component
public class PalindromeValidator {

    public boolean isValid(String text) {

        // Reject if the word of the text contains spaces or numbers
        return !text.matches("^[\\d\\s].*");
    }
}
