package com.example.palindrome.utility;

import com.example.palindrome.model.PalindromeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class PalindromeCacheManager {

    @Autowired
    private CacheManager cacheManager;

    public Cache getCache() {
        return cacheManager.getCache("palindromeCache");
    }
    public PalindromeResponse get(String key) {
        Cache.ValueWrapper wrapper = getCache().get(key);
        return (wrapper != null) ? (PalindromeResponse) wrapper.get() : null;
    }
    public void put(String key, PalindromeResponse palindromeResult) {
        getCache().put(key, palindromeResult);
    }
}