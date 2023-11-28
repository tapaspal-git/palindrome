package com.example.palindrome.utility;

import com.example.palindrome.model.PalindromeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PalindromeCacheManagerTest {

    @Mock
    private CacheManager cacheManager;
    @Mock
    private Cache cache;
    @InjectMocks
    private PalindromeCacheManager palindromeCacheManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(cacheManager.getCache("palindromeCache")).thenReturn(cache);
    }

    @Test
    void testGetCacheMethod() {
        when(cacheManager.getCache("palindromeCache")).thenReturn(cache);
        assertNotNull(palindromeCacheManager.getCache());
        verify(cacheManager, times(1)).getCache("palindromeCache");
    }


    @Test
    void testGetMethodForExisting() {
        //preparing test data
        String key = "testKey";
        PalindromeResponse expectedResult = new PalindromeResponse("text",true);

        // Correct stubbing
        when(cache.get(key)).thenReturn(new Cache.ValueWrapper() {
            @Override
            public Object get() {
                return expectedResult;
            }
        });

        // Calling the method
        PalindromeResponse result = palindromeCacheManager.get(key);

        // Verifying the result
        assertNotNull(result);
        assertEquals(expectedResult, result);
        verify(cache, times(1)).get(key);
    }


    @Test
    void testGetMethodForNonExisting() {
        //preparing test data
        String key = "nonExistingKey";

        // Mocking
        when(cache.get(key)).thenReturn(null);

        // Calling the method
        PalindromeResponse result = palindromeCacheManager.get(key);

        // Verifying the result
        assertNull(result);
        verify(cache, times(1)).get(key);
    }

    @Test
    void testPutMethod() {
        //preparing test data
        String key = "testKey";
        PalindromeResponse palindromeResult = new PalindromeResponse("abc", true);

        // Calling the method
        palindromeCacheManager.put(key, palindromeResult);

        // Verifying the result
        verify(cache, times(1)).put(eq(key), eq(palindromeResult));
    }
}

