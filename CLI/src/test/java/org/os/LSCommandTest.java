package org.os;

import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LSCommandTest {
        private static final String testDir = "testDir";
        @BeforeAll
        static void setUp() {
            new File(testDir).mkdir();
        }
        @AfterAll
        static void tearDown() {
            for (File file : new File(testDir).listFiles()) {
                file.delete();
            }
            new File(testDir).delete();
        }
        // LsRCommand Tests (reverse order listing)
        @Test
        void testListInReverse() {
            TouchCommand.execute(testDir + "/file1.txt");
            TouchCommand.execute(testDir + "/file2.txt");
            List<String> files = LsRCommand.execute(testDir);
            assertEquals(2, files.size());
            assertTrue(files.contains("file1.txt") && files.contains("file2.txt"));
        }
        @Test
        void testEmptyDirectoryInReverse() {
            String emptyDir = "emptyTestDir";
            new File(emptyDir).mkdir();
            List<String> files = LsRCommand.execute(emptyDir);
            assertTrue(files.isEmpty());
            new File(emptyDir).delete();
        }

        @Test
        void testInvalidDirectoryInReverse() {
            List<String> files = LsRCommand.execute("invalidDir");
            assertTrue(files.isEmpty());
        }

        @Test
        void testReverseOrderListing() {
            TouchCommand.execute(testDir + "/b.txt");
            TouchCommand.execute(testDir + "/a.txt");
            TouchCommand.execute(testDir + "/c.txt");
            List<String> files = LsRCommand.execute(testDir);
            assertEquals(3, files.size());
            assertEquals("c.txt", files.get(0));
            assertEquals("b.txt", files.get(1));
            assertEquals("a.txt", files.get(2));
        }

        @Test
        void testFileWithDuplicateNameInReverse() {
            TouchCommand.execute(testDir + "/duplicate.txt");
            TouchCommand.execute(testDir + "/duplicate.txt");
            List<String> files = LsRCommand.execute(testDir);
            assertEquals(1, files.size());
            assertTrue(files.contains("duplicate.txt"));
        }

        // LsCommand Tests
        @Test
        public void testLsAllFiles() {
            LsCommand.execute(new String[]{"ls", "*"});
        }

        @Test
        public void testLsXmlFiles() {
            LsCommand.execute(new String[]{"ls", "*.xml"});
        }

        @Test
        public void testLsRelativePath() {
            LsCommand.execute(new String[]{"ls", "../"});
        }

        @Test
        public void testLsRelativePath2() {
            LsCommand.execute(new String[]{"ls", "../../"});
        }

        @Test
        public void testLsRelativePath3() {
            LsCommand.execute(new String[]{"ls", "../"});
        }
    }

