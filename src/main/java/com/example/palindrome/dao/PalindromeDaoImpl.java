package com.example.palindrome.dao;

import com.example.palindrome.model.PalindromeResponse;
import com.example.palindrome.utility.PalindromeCacheManager;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Repository
public class PalindromeDaoImpl implements PalindromeDao{

    @Value("${file.path}")
    public String FILE_PATH;
    @Autowired
    private PalindromeCacheManager cacheManager;

    //Added @PostConstruct annotation to load data into Cache when application starts
    @PostConstruct
    public void loadCacheFromFile() throws IOException {
            List<String> lines = Files.readAllLines(Path.of(FILE_PATH));

            for (String line : lines) {
                //splitting the values as ,,,(seperator) and saved into text file
                String[] parts = line.split(",,,");
                //key as username and text
                //assumed the for every user we are checking the palindrome
                //this can be changed as par requirement
                String key = parts[0] + parts[1];
                cacheManager.put(key, new PalindromeResponse(parts[1], Boolean.parseBoolean(parts[2])));
            }
    }

    //save the data if the data is not present in the file
    @Override
    public void saveDataToFile(String username, String text, Boolean isPalindrome) throws IOException {
            //below line to check the method is called or not, this can be commented
            System.out.println("saveDataToFile method is called!!");
            //adding separator as ,,, and saved into text file
            String data = username + ",,," + text+ ",,," + isPalindrome + System.lineSeparator();
            Files.write(Path.of(FILE_PATH), data.getBytes(), java.nio.file.StandardOpenOption.APPEND);
        }
}
