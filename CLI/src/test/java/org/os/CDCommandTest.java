package org.os;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
class CDCommandTest {

    private Path tempDir;
    private String originalDir;

    @BeforeEach
    void setUp() throws Exception {
        tempDir = Files.createTempDirectory("testDir");
        originalDir = System.getProperty("user.dir");
    }

    @AfterEach
    void tearDown() {
        System.setProperty("user.dir", originalDir); // Restore original directory

        // Cleanup t
        try {
            Files.deleteIfExists(tempDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void testCDCommand()throws Exception {
        String tempDirPath = tempDir.toString();

        CDCommand.execute(tempDirPath);

        String expectedDir = System.getProperty("user.dir"); //current working directory
        assertEquals(expectedDir, tempDirPath);

        CDCommand.execute("..");
        expectedDir = tempDir.getParent().toString();
        assertEquals(expectedDir, System.getProperty("user.dir"));

    }
    @Test
    void testCdCommandToNonExistentDirectory() {
        CDCommand.execute("nonExistentDir");

        assertEquals(originalDir, System.getProperty("user.dir"));

    }
    @Test
    void testCdCommandToFile() throws Exception {
        Path tempFile = Files.createTempFile("testFile", ".txt");
        String tempFilePath = tempFile.toString();
        CDCommand.execute(tempFilePath);

        assertEquals(originalDir, System.getProperty("user.dir"));

        try {
            Files.delete(tempFile);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}