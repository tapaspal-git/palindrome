package com.example.palindrome.dao;
import com.example.palindrome.model.PalindromeResponse;
import com.example.palindrome.utility.PalindromeCacheManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PalindromeDaoImplTest {

    @TempDir
    Path tempDir;

    @Mock
    private PalindromeCacheManager cacheManager;

    @Value("${file.path}")
    private String filePath;

    @InjectMocks
    private PalindromeDaoImpl palindromeDao;

    @BeforeEach
    void setUp() {
        filePath = tempDir.resolve("testFile.txt").toString();
        palindromeDao.FILE_PATH = filePath;
    }

    @Test
    void testLoadCacheFromFile() throws IOException {
        // Creating a temporary file with sample data
        String sampleData = "user1,,,text1,,,true\nuser2,,,text2,,,false";
        Files.write(Path.of(filePath), sampleData.getBytes());

        // calling the method
        palindromeDao.loadCacheFromFile();

        // Verifying the cacheManager.put is called for each line in the file
        verify(cacheManager, times(2)).put(anyString(), any(PalindromeResponse.class));

        // Verifying the contents of the cache
        verify(cacheManager).put(eq("user1text1"), any(PalindromeResponse.class));
        verify(cacheManager).put(eq("user2text2"), any(PalindromeResponse.class));
    }

    @Test
    void testLoadCacheFromFileEmptyFile() throws IOException {
        // Set up the file path
        palindromeDao.FILE_PATH = filePath;
        Files.createFile(Path.of(filePath));

        // calling the method
        palindromeDao.loadCacheFromFile();

        // Verifying that cacheManager.put is not called
        verify(cacheManager, never()).put(anyString(), any(PalindromeResponse.class));
    }

    @Test
    void testSaveDataToFile() throws IOException {
        // Set up the file path
        palindromeDao.FILE_PATH = filePath;

        Files.createFile(Path.of(filePath));

        // calling the method
        palindromeDao.saveDataToFile("user1", "text1", true);

        // Verifying that the file contains the correct data
        List<String> lines = Files.readAllLines(Path.of(filePath));
        assertEquals(1, lines.size());
        assertEquals("user1,,,text1,,,true", lines.get(0));
    }

}
