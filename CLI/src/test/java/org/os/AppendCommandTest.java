package org.os;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class AppendCommandTest {
    private final String testFileName = "testFile.txt";


    @BeforeEach
    void setUp() throws IOException {
        // Create
        new FileWriter(testFileName, false).close();
    }

    @AfterEach
    void tearDown() {
        // Delete
        new File(testFileName).delete();
    }

    @Test
    void testAppendToFile()throws IOException {
        String content1 = "First line of text";
        String content2 = "Second line of text";

        AppendCommand.execute(testFileName, content1);
        AppendCommand.execute(testFileName, content2);

        try (BufferedReader reader = new BufferedReader(new FileReader(testFileName))) {
            assertEquals(content1, reader.readLine());
            assertEquals(content2, reader.readLine());
        }
    }

    @Test
    void testAppendCreatesFileIfNotExists() throws IOException {
        String nonExistentFileName = "nonExistentTestFile.txt";
        String content = "New content";
        File file = new File(nonExistentFileName);

        if (file.exists()) {
            assertTrue(file.delete());
        }

        AppendCommand.execute(nonExistentFileName, content);

        try (BufferedReader reader = new BufferedReader(new FileReader(nonExistentFileName))) {
            assertEquals(content, reader.readLine());
        }

        assertTrue(file.exists());
    }
    @Test
    void testNullInput() {
        assertDoesNotThrow(() -> AppendCommand.execute(testFileName, null));
    }


}