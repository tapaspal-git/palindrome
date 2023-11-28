package com.example.palindrome.utility;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PalindromeValidatorTest {

    private final PalindromeValidator palindromeValidator = new PalindromeValidator();

    @Test
    void testTextIsValidPalindrome() {
        // Testing for a valid palindrome
        assertTrue(palindromeValidator.isValid("madam"));
        assertTrue(palindromeValidator.isValid("kayak"));
        assertTrue(palindromeValidator.isValid("A man a plan a canal Panama"));
    }

    @Test
    void testTextIsNotPalindrome() {
        // Testing for invalid palindrome (contains numbers or spaces in the first word)
        assertFalse(palindromeValidator.isValid("12321"));
        assertFalse(palindromeValidator.isValid("  we are testing with space"));
    }

    @Test
    void testTextIsAnEmptyString() {
        // Testing for an empty string
        assertTrue(palindromeValidator.isValid(""));
    }
}
