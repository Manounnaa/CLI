package org.os;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class LsCommandTest {
    @TempDir
    Path tempDir;

    private static final String testDir = "testDir";
    private File file1;
    private File file2;
    private File hiddenFile;
    private File subDir;

    @BeforeAll
    static void setUpClass() {
        new File(testDir).mkdir();
    }

    @AfterAll
    static void tearDownClass() {
        File dir = new File(testDir);
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
            dir.delete();
        }
    }

    @BeforeEach
    void setUp() throws IOException {
        // Create test files and directories in temp directory
        file1 = Files.createFile(tempDir.resolve("test1.txt")).toFile();
        file2 = Files.createFile(tempDir.resolve("test2.txt")).toFile();
        hiddenFile = Files.createFile(tempDir.resolve(".hidden")).toFile();
        subDir = Files.createDirectory(tempDir.resolve("subdir")).toFile();

        // Mock DirectoryUtil to return our temp directory
        DirectoryUtil.setCurrentDirectory(tempDir.toString());
    }



    @Test
    void testLsWithReverseFlag() {
        String[] tokens = {"ls", "-r"};
        String result = LsCommand.execute(tokens);

        // In reverse order, test2.txt should come before test1.txt
        int pos1 = result.indexOf("test1.txt");
        int pos2 = result.indexOf("test2.txt");
        assertTrue(pos2 < pos1);
    }

    @Test
    void testLsWithShowAllFlag() {
        String[] tokens = {"ls", "-a"};
        String result = LsCommand.execute(tokens);

        assertTrue(result.contains(".hidden"));
        assertTrue(result.contains("test1.txt"));
        assertTrue(result.contains("test2.txt"));
    }

    @Test
    void testLsWithWildcard() {
        String[] tokens = {"ls", "*.txt"};
        String result = LsCommand.execute(tokens);

        assertTrue(result.contains("test1.txt"));
        assertTrue(result.contains("test2.txt"));
        assertFalse(result.contains("subdir"));
        assertFalse(result.contains(".hidden"));
    }


    @Test
    void testLsWithWildcardAndReverseFlag() {
        String[] tokens = {"ls", "-r", "*.txt"};
        String result = LsCommand.execute(tokens);

        assertTrue(result.contains("test1.txt"));
        assertTrue(result.contains("test2.txt"));
        int pos1 = result.indexOf("test1.txt");
        int pos2 = result.indexOf("test2.txt");
        assertTrue(pos2 < pos1);
    }

    // Added tests from LSCommandTest class
    @Test
    void testLsRelativePath() {
        String output = LsCommand.execute(new String[]{"ls", "../"});
        assertNotNull(output);
        assertFalse(output.trim().isEmpty());
    }

    @Test
    void testLsRelativePath2() {
        String output = LsCommand.execute(new String[]{"ls", "../../"});
        assertNotNull(output);
        assertFalse(output.trim().isEmpty());
    }

    @Test
    void testLsRelativePath3() {
        String output = LsCommand.execute(new String[]{"ls", "../"});
        assertNotNull(output);
        assertFalse(output.trim().isEmpty());
    }


    // Additional new test cases


    @Test
    void testLsWithComplexWildcard() throws IOException {
        // Create files with different extensions
        Files.createFile(tempDir.resolve("test.jpg"));
        Files.createFile(tempDir.resolve("test.png"));

        String[] tokens = {"ls", "*.{txt,jpg}"};
        String result = LsCommand.execute(tokens);

        assertTrue(result.contains("test1.txt"));
        assertTrue(result.contains("test.jpg"));
        assertFalse(result.contains("test.png"));
    }
}