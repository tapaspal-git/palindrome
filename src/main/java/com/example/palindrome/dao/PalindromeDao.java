package com.example.palindrome.dao;

import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public interface PalindromeDao {

    void saveDataToFile(String username, String text, Boolean isPalindrome) throws IOException;
}
