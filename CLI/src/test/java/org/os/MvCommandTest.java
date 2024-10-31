package org.os;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class MvCommandTest {

    private static final String testDir = "testDir";
    private static final String source = testDir+ "/source";
    private static final String destintion = testDir + "/dest";

    @BeforeAll
    public static void setup() throws IOException {
        Files.createDirectories(Paths.get(source));
        Files.createDirectories(Paths.get(destintion));
        // Create test files
        Files.createFile(Paths.get(source, "file1.txt"));
        Files.createFile(Paths.get(source, "file2.txt"));
        Files.createFile(Paths.get(source, "file3.txt"));
    }

    @AfterAll
    public static void delete() throws IOException {
        // Clean up test directories
        Files.walkFileTree(Paths.get(testDir), new SimpleFileVisitor<Path>() {
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

    @Test
    public void testsinglemove() throws IOException {
        String[] args = {"mv", "file1.txt", destintion};
        MvCommand.execute(args);
        assertFalse(Files.exists(Paths.get(source, "file1.txt")));
        assertTrue(Files.exists(Paths.get(destintion, "file1.txt")));
    }

    @Test
    public void testmultymoves() throws IOException {
        String[] args = {"mv", "file2.txt", "file3.txt", destintion};
        MvCommand.execute(args);

        assertFalse(Files.exists(Paths.get(source, "file2.txt")));
        assertFalse(Files.exists(Paths.get(source, "file3.txt")));
        assertTrue(Files.exists(Paths.get(destintion, "file2.txt")));
        assertTrue(Files.exists(Paths.get(destintion, "file3.txt")));
    }

    @Test
    public void testnonexist() {
        String[] args = {"mv", "file1.txt", "nonExistentDir"};
        MvCommand.execute(args);

        assertTrue(Files.exists(Paths.get(source, "file1.txt"))); // Should still exist
    }

    @Test
    public void testrenaming() throws IOException {
        String[] args = {"mv", "file1.txt", destintion + "/renamedFile.txt"};
        MvCommand.execute(args);

        assertFalse(Files.exists(Paths.get(source, "file1.txt")));
        assertTrue(Files.exists(Paths.get(destintion, "renamedFile.txt")));
    }

    @Test
    public void test_samenaming_difdir() throws IOException {
        String[] args = {"mv", "file2.txt", destintion + "/file2.txt"};
        MvCommand.execute(args);
        assertFalse(Files.exists(Paths.get(source, "file2.txt")));
        assertTrue(Files.exists(Paths.get(destintion, "file2.txt")));
    }

    @Test
    public void testsamesame() throws IOException {
        String[] args = {"mv", "file1.txt", "file1.txt"};
        MvCommand.execute(args);

        assertTrue(Files.exists(Paths.get(destintion, "renamedFile.txt"))); // Should still exist
    }
}
