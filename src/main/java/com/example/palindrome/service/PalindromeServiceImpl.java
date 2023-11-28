package com.example.palindrome.service;

import com.example.palindrome.dao.PalindromeDao;
import com.example.palindrome.model.PalindromeResponse;
import com.example.palindrome.utility.PalindromeCacheManager;
import com.example.palindrome.utility.PalindromeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class PalindromeServiceImpl implements PalindromeService{

    private static final String CACHE_NAME = "palindromeCache";

    @Autowired
    private PalindromeValidator palindromeValidator;
    @Autowired
    private PalindromeDao palindromeDao;
    @Autowired
    private PalindromeCacheManager palindromeCacheManager;

    @Override
    public PalindromeResponse validatePalindrome(String username, String text) throws IOException {
        String key = username + text;

        // checking the data is already present in the cache
        PalindromeResponse response = palindromeCacheManager.get(key);

        if (response == null) {
            // If the data is not present in the cache then put the data into Cache manager
            // and save the new data into file
            response = checkPalindromeText(username, text);
            palindromeCacheManager.put(key, response);
            palindromeDao.saveDataToFile(username, text, response.isPalindrome());
        }
        return response;
    }

    @Cacheable(value = CACHE_NAME, key = "#key")
    private PalindromeResponse checkPalindromeText(String username, String text) throws IOException {
        //if the input text with starting spaces or numbers then rejecting and throwing the exception
        if (!palindromeValidator.isValid(text)) {
            throw new IOException("Please pass valid text for checking palindrome");
        }
        //return the response as per validation
        return new PalindromeResponse(text, isPalindrome(text));
    }

    private boolean isPalindrome(String text) {
        // Logic to check the input text is palindrome or not
        //replacing everything with empty except Alphanumeric
        //This can be changed as per the requirement
        String cleanedText = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return cleanedText.equals(new StringBuilder(cleanedText).reverse().toString());
    }


}
