package com.example.palindrome.service;
import com.example.palindrome.dao.PalindromeDao;
import com.example.palindrome.model.PalindromeResponse;
import com.example.palindrome.utility.PalindromeCacheManager;
import com.example.palindrome.utility.PalindromeValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class PalindromeServiceImplTest {

    @Mock
    private PalindromeValidator palindromeValidator;

    @Mock
    private PalindromeDao palindromeDao;

    @Mock
    private PalindromeCacheManager palindromeCacheManager;

    @InjectMocks
    private PalindromeServiceImpl palindromeService;

    @Test
    void testValidatePalindromeWithPalindromeText() throws IOException {
        //preparing test data
        String username = "testUser";
        String text = "kayak";
        String key = username + text;

        // Mocking the PalindromeCacheManager
        when(palindromeCacheManager.get(key)).thenReturn(new PalindromeResponse(text, true));

        // calling the method
        PalindromeResponse response = palindromeService.validatePalindrome(username, text);

        // Verifying the cache manager get method is called
        verify(palindromeCacheManager, times(1)).get(key);

        // Verifying that the saveDataToFile method is not called
        verify(palindromeDao, never()).saveDataToFile(username, text, true);

        // Verifying the response
        assertNotNull(response);
        assertTrue(response.isPalindrome());
    }

    @Test
    void testValidatePalindromeWithNotAPalindromeText() throws IOException {
        //preparing test data
        String username = "testUser";
        String text = "abc";
        String key = username + text;

        // Mocking the PalindromeCacheManager
        when(palindromeCacheManager.get(key)).thenReturn(new PalindromeResponse(text, false));

        // calling the method
        PalindromeResponse response = palindromeService.validatePalindrome(username, text);

        // Verifying the cache manager get method is called
        verify(palindromeCacheManager, times(1)).get(key);

        // Verifying that the saveDataToFile method is not called
        verify(palindromeDao, never()).saveDataToFile(username, text, false);

        // Verifying the response
        assertNotNull(response);
        assertFalse(response.isPalindrome());
    }
    @Test
    void testValidatePalindromeWithPalindromeTextAndNoCached() throws IOException {
        //preparing test data
        String username = "testUser";
        String text = "madam";
        String key = username + text;

        // Mocking the cache miss scenario
        when(palindromeCacheManager.get(key)).thenReturn(null);
        when(palindromeValidator.isValid(text)).thenReturn(true);

        // Calling the method
        PalindromeResponse response = palindromeService.validatePalindrome(username, text);

        // Verifying the cache manager put method is called once
        verify(palindromeCacheManager, times(1)).put(eq(key), any(PalindromeResponse.class));

        // Verify that the saveDataToFile method is called once
        verify(palindromeDao, times(1)).saveDataToFile(username, text, true);

        // Verify the response
        assertNotNull(response);
        assertTrue(response.isPalindrome());
    }

    @Test
    void testValidatePalindromeWithNotAPalindromeTextAndNoCached() throws IOException {
        //preparing test data
        String username = "testUser";
        String text = "NotCached";
        String key = username + text;

        // Mocking the cache miss scenario
        when(palindromeCacheManager.get(key)).thenReturn(null);
        when(palindromeValidator.isValid(text)).thenReturn(true);

        // Calling the method
        PalindromeResponse response = palindromeService.validatePalindrome(username, text);

        // Verifying the cache manager put method is called once
        verify(palindromeCacheManager, times(1)).put(eq(key), any(PalindromeResponse.class));

        // Verifying that the saveDataToFile method is called once
        verify(palindromeDao, times(1)).saveDataToFile(username, text, false);

        // Verifying the response
        assertNotNull(response);
        assertFalse(response.isPalindrome());
    }

    @Test
    void testValidatePalindromeWithNotAValidText() throws IOException {
        //preparing test data
        String username = "testUser";
        String text = "123 NotCached";
        String key = username + text;

        // Mocking the cache miss scenario
        when(palindromeCacheManager.get(key)).thenReturn(null);
        when(palindromeValidator.isValid(text)).thenReturn(false);

        // Calling the method
        IOException exception = assertThrows(IOException.class, () -> {
            palindromeService.validatePalindrome(username, text);
        });

        // Verifying the cache manager put method is called
        verify(palindromeCacheManager, never()).put(eq(key), any(PalindromeResponse.class));

        // Verifying that the saveDataToFile method is not called
        verify(palindromeDao, never()).saveDataToFile(username, text, false);
        // Verifying the response
        assertEquals("Please pass valid text for checking palindrome",exception.getMessage());
    }
}
