package org.os;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
class MkdirCommandTest {
    String dirName = "testDir";

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(dirName));
    }
    @Test
    void testMkdirCommand() throws IOException {
        Path path = Path.of(dirName);
        File file = path.toFile();
        MkdirCommand.execute(dirName);
        assertTrue(file.exists());

    }
    @Test
    void testMkdirAlreadyExists() {
        MkdirCommand.execute(dirName);

        System.setOut(new PrintStream(new ByteArrayOutputStream()) {
            @Override
            public void flush() {
                super.flush();
                assertEquals("mkdir: cannot create directory '" + dirName + "': File exists\n", this.toString());
            }
        });

        MkdirCommand.execute(dirName);

        // Restore the original System.out
        System.setOut(System.out);
    }
    @Test
    void testMkdirMultipleDirectories() throws Exception {
        String dir1 = "testDir1";
        String dir2 = "testDir2";
        try {
            MkdirCommand.execute(dir1, dir2);
            assertTrue(Files.exists(Path.of(dir1)));
            assertTrue(Files.exists(Path.of(dir2)));
        }
        finally {
            Files.deleteIfExists(Path.of(dir1));
            Files.deleteIfExists(Path.of(dir2));
        }
    }
    @Test
    void testMkdirNestedDirectories() throws Exception {
        String parentDir = "parentDir";
        String childDir = "parentDir/childDir";

        try{
            MkdirCommand.execute(parentDir);

            MkdirCommand.execute(childDir);

            assertTrue(Files.exists(Path.of(parentDir)), "Parent directory should exist");
            assertTrue(Files.exists(Path.of(childDir)), "Child directory should exist");
        }
        finally {
            Files.deleteIfExists(Path.of(childDir));
            Files.deleteIfExists(Path.of(parentDir));
        }
    }

}