package org.os;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LSCommandTest {
    private static final String TEST_DIR = "testDir";
    private static File testDirectory;

    @BeforeAll
    static void setUp() {
        testDirectory = new File(TEST_DIR);
        testDirectory.mkdir();
        try {
            // Create test files
            createTestFile("file1.txt");
            createTestFile("file2.txt");
            createTestFile(".hiddenFile1");
            createTestFile(".hiddenFile2");
            createTestFile("test.java");
            // Create a subdirectory
            new File(TEST_DIR + "/subdir").mkdir();
        } catch (IOException e) {
            fail("Test setup failed: " + e.getMessage());
        }
        // Set the current directory for tests
        DirectoryUtil.setCurrentDirectory(System.getProperty("user.dir"));
    }

    private static void createTestFile(String filename) throws IOException {
        File file = new File(TEST_DIR + "/" + filename);
        file.createNewFile();
        if (filename.startsWith(".")) {
            Files.setAttribute(file.toPath(), "dos:hidden", true);
        }
    }

    @AfterAll
    static void tearDown() {
        deleteDirectory(testDirectory);
    }

    private static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    @Test
    void testBasicLs() {
        String output = LsCommand.execute(new String[]{"ls", TEST_DIR}).trim();
        List<String> files = Arrays.asList(output.split(System.lineSeparator()));

        assertTrue(files.contains("file1.txt"));
        assertTrue(files.contains("file2.txt"));
        assertTrue(files.contains("test.java"));
        assertTrue(files.contains("subdir"));
        assertFalse(files.contains(".hiddenFile1"));
        assertFalse(files.contains(".hiddenFile2"));
    }



    @Test
    void testLsReverse() {
        String output = LsCommand.execute(new String[]{"ls", "-r", TEST_DIR}).trim();
        List<String> files = Arrays.asList(output.split(System.lineSeparator()));

        // Verify reverse alphabetical order
        assertTrue(files.indexOf("test.java") < files.indexOf("file2.txt"));
        assertTrue(files.indexOf("file2.txt") < files.indexOf("file1.txt"));
    }

    @Test
    void testLsShowAll() {
        String output = LsCommand.execute(new String[]{"ls", "-a", TEST_DIR}).trim();
        List<String> files = Arrays.asList(output.split(System.lineSeparator()));

        assertTrue(files.contains("file1.txt"));
        assertTrue(files.contains("file2.txt"));
        assertTrue(files.contains(".hiddenFile1"));
        assertTrue(files.contains(".hiddenFile2"));
        assertTrue(files.contains("test.java"));
        assertTrue(files.contains("subdir"));
    }

    @Test
    void testLsWithInvalidDirectory() {
        String output = LsCommand.execute(new String[]{"ls", "nonexistentDir"}).trim();
        assertTrue(output.contains("Error"));
    }



    @Test
    void testEmptyDirectory() {
        String emptyDir = "emptyTestDir";
        new File(emptyDir).mkdir();
        try {
            String output = LsCommand.execute(new String[]{"ls", emptyDir}).trim();
            assertEquals("", output);
        } finally {
            new File(emptyDir).delete();
        }
    }
}
