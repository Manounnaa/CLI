package org.os;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CDCommandTest {

    @Test
    void testCDCommand()throws Exception {

        Path tempDir = Files.createTempDirectory("testDir");
        String tempDirPath = tempDir.toString();

        CDCommand.execute(tempDirPath);
        String expectedDir = System.getProperty("user.dir"); //current working directory
        assertEquals(expectedDir, tempDirPath);

        CDCommand.execute("..");
        expectedDir = tempDir.getParent().toString();
        assertEquals(expectedDir, System.getProperty("user.dir"));
        try {
            Files.delete(tempDir);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void testCdCommandToNonExistentDirectory() {
        CDCommand.execute("nonExistentDir");
    }

    @Test
    void testCdCommandToFile() throws Exception {

        Path tempFile = Files.createTempFile("testFile", ".txt");
        String tempFilePath = tempFile.toString();

        CDCommand.execute(tempFilePath);
        try {
            Files.delete(tempFile);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}