package org.os;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.jupiter.api.Assertions.*;

public class MvCommandTest {
    private static final Path TEST_DIR = Paths.get("testDir").toAbsolutePath();
    private static final Path SOURCE_DIR = TEST_DIR.resolve("source");
    private static final Path DEST_DIR = TEST_DIR.resolve("dest");

    @BeforeEach
    public void setup() throws IOException {
        // Create fresh directories for each test
        Files.createDirectories(SOURCE_DIR);
        Files.createDirectories(DEST_DIR);

        // Create test files
        Files.writeString(SOURCE_DIR.resolve("file1.txt"), "test content 1");
        Files.writeString(SOURCE_DIR.resolve("file2.txt"), "test content 2");
        Files.writeString(SOURCE_DIR.resolve("file3.txt"), "test content 3");
    }

    @AfterEach
    public void cleanup() throws IOException {
        // Clean up test directories after each test
        if (Files.exists(TEST_DIR)) {
            Files.walkFileTree(TEST_DIR, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    @Test
    public void testSingleFileMove() throws IOException {
        String sourceFile = SOURCE_DIR.resolve("file1.txt").toString();
        String destDir = DEST_DIR.toString();

        MvCommand.execute(new String[]{"mv", sourceFile, destDir});

        assertFalse(Files.exists(SOURCE_DIR.resolve("file1.txt")));
        assertTrue(Files.exists(DEST_DIR.resolve("file1.txt")));
    }

    @Test
    public void testMultipleFileMove() throws IOException {
        String file1 = SOURCE_DIR.resolve("file1.txt").toString();
        String file2 = SOURCE_DIR.resolve("file2.txt").toString();
        String destDir = DEST_DIR.toString();

        MvCommand.execute(new String[]{"mv", file1, file2, destDir});

        assertFalse(Files.exists(SOURCE_DIR.resolve("file1.txt")));
        assertFalse(Files.exists(SOURCE_DIR.resolve("file2.txt")));
        assertTrue(Files.exists(DEST_DIR.resolve("file1.txt")));
        assertTrue(Files.exists(DEST_DIR.resolve("file2.txt")));
    }

    @Test
    public void testNonExistentSource() {
        String nonExistentFile = SOURCE_DIR.resolve("nonexistent.txt").toString();
        String destDir = DEST_DIR.toString();

        MvCommand.execute(new String[]{"mv", nonExistentFile, destDir});

        assertFalse(Files.exists(DEST_DIR.resolve("nonexistent.txt")));
    }

    @Test
    public void testFileRename() throws IOException {
        String sourceFile = SOURCE_DIR.resolve("file1.txt").toString();
        String destFile = SOURCE_DIR.resolve("renamed.txt").toString();

        MvCommand.execute(new String[]{"mv", sourceFile, destFile});

        assertFalse(Files.exists(SOURCE_DIR.resolve("file1.txt")));
        assertTrue(Files.exists(SOURCE_DIR.resolve("renamed.txt")));
    }

    @Test
    public void testMoveToNonExistentDirectory() {
        String sourceFile = SOURCE_DIR.resolve("file1.txt").toString();
        String nonExistentDir = TEST_DIR.resolve("nonexistent").resolve("file1.txt").toString();

        MvCommand.execute(new String[]{"mv", sourceFile, nonExistentDir});

        assertTrue(Files.exists(SOURCE_DIR.resolve("file1.txt")));
    }
}