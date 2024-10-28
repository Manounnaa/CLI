package org.os;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RmCommandTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture output in outputStream
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        // Restore original System.out to avoid affecting other tests
        System.setOut(originalOut);
        // Clear the output stream buffer to start fresh for the next test
        outputStream.reset();
    }

    @Test
    public void testDeleteExistingFile() {
        // Create a temporary file to delete
        File tempFile = new File("testFile.txt");
        try {
            if (tempFile.createNewFile()) {
                RmCommand.execute("testFile.txt");
                assertEquals("File 'testFile.txt' deleted".trim(), outputStream.toString().trim());
            }
        } catch (Exception e) {
            System.err.println("Could not create test file: " + e.getMessage());
        } finally {
            if (tempFile.exists()) {
                tempFile.delete(); // Clean up if the file was not deleted by the command
            }
        }
    }

    @Test
    public void testDeleteNonexistentFile() {
        RmCommand.execute("FileNotFound.txt");
        assertEquals("rm: cannot remove 'FileNotFound.txt': No such file or directory".trim(), outputStream.toString().trim());
    }

    @Test
    public void testDeleteDirectory() {
        // Create a temporary directory to test
        File tempDir = new File("testDir");
        try {
            if (tempDir.mkdir()) {
                RmCommand.execute("testDir");
                assertEquals("rm: cannot remove 'testDir': Is a directory".trim(), outputStream.toString().trim());
            }
        } catch (Exception e) {
            System.err.println("Could not create test directory: " + e.getMessage());
        } finally {
            if (tempDir.exists()) {
                tempDir.delete(); // Clean up the directory if it was not deleted by the command
            }
        }
    }
}
