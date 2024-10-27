package org.os;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
class RmdirCommandTest {
    private Path tempDir;
    private Path nonEmptyDir;
    //create temp directory empty and no
    @BeforeEach
    void setUp() throws Exception {
        tempDir = Files.createTempDirectory("testEmptyDir");
        nonEmptyDir = Files.createTempDirectory("testNonEmptyDir");
        Files.createFile(nonEmptyDir.resolve("testFile.txt"));
    }
    @Test
    void testRemoveEmptyDirectory() {
        RmdirCommand.execute(tempDir.toString());
        assertFalse(Files.exists(tempDir));
    }
    @Test
    void testRemoveNonEmptyDirectory() {
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream()) {
            @Override
            public void flush() {
                super.flush();
                assertEquals("rmdir: directory not empty: " + nonEmptyDir.toString() + "\n", this.toString());
            }
        });
        RmdirCommand.execute(nonEmptyDir.toString());
        assertTrue(Files.exists(nonEmptyDir));
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