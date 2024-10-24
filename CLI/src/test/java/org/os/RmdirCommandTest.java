package org.os;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RmdirCommandTest {
    private Path tempDir;
    private Path nonEmptyDir;

    @BeforeEach
    void setUp() throws Exception {
        tempDir = Files.createTempDirectory("testEmptyDir");

        nonEmptyDir = Files.createTempDirectory("testNonEmptyDir");
        Files.createFile(nonEmptyDir.resolve("testFile.txt"));
    }
    //tests bokra :)
}