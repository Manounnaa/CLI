package org.os;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
class RmdirCommandTest {
    private static final String TEST_DIR = "testDir";

    @BeforeEach
    void setUp() {
        File currentDir = new File(DirectoryUtil.getCurrentDirectory());
        File testDir = new File(currentDir, TEST_DIR);
        if (!testDir.exists()) {
            testDir.mkdir();
        }
    }
    @AfterEach
    void tearDown() {
        // Clean up after each test
        File currentDir = new File(DirectoryUtil.getCurrentDirectory());
        File testDir = new File(currentDir, TEST_DIR);
        if (testDir.exists()) {
            for (File file : testDir.listFiles()) {
                file.delete();
            }
            testDir.delete();
        }
    }
    @Test
    void testRemoveEmptyDirectory() {
        RmdirCommand.execute(TEST_DIR);
        File currentDir = new File(DirectoryUtil.getCurrentDirectory());
        File testDir = new File(currentDir, TEST_DIR);
        assertFalse(testDir.exists(), "Directory should be deleted if it's empty.");
    }
    @Test
    void testRemoveNonEmptyDirectory() throws Exception {
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream()) {
            @Override
            public void flush() {
                super.flush();
                assertEquals("rmdir: directory not empty: " + TEST_DIR.toString() + "\n", this.toString());
            }
        });

        File currentDir = new File(DirectoryUtil.getCurrentDirectory());
        File testDir = new File(currentDir, TEST_DIR);
        File fileInDir = new File(testDir, "dummyFile.txt");
        fileInDir.createNewFile();  // Create a file inside the directory to make it non-empty

        RmdirCommand.execute(TEST_DIR);

        assertTrue(testDir.exists(), "Directory should not be deleted if it's not empty.");
    }
    @Test
    void testRemoveNonExistentDirectory() {
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream()) {
            @Override
            public void flush() {
                super.flush();
                assertEquals("rmdir: no such directory: " + "nonExistentDir" + "\n", this.toString());
            }
        });

        RmdirCommand.execute("nonExistentDir");
        assertFalse(Files.exists(Paths.get("nonExistentDir")));
    }
}