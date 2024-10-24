package org.os;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
class MkdirCommandTest {

    @Test
    void testMkdirCommand() throws IOException {
        String dirName = "testDir";
        Path path = Path.of(dirName);
        File file = path.toFile();


        try {
            MkdirCommand.execute(dirName);
            assertTrue(file.exists());
        }
        finally {
            Files.deleteIfExists(file.toPath());

        }



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

}