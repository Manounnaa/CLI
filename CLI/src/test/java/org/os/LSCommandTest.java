package org.os;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
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

//        @Test
//        public void testLsXmlFiles() {
//            LsCommand.execute(new String[]{"ls", "*.xml"});
//        }
//
//        @Test
//        public void testLsRelativePath() {
//            LsCommand.execute(new String[]{"ls", "../"});
//        }
//
//        @Test
//        public void testLsRelativePath2() {
//            LsCommand.execute(new String[]{"ls", "../../"});
//        }
//
//        @Test
//        public void testLsRelativePath3() {
//            LsCommand.execute(new String[]{"ls", "../"});
//        }

    //    captures the result of the command in a variable instead of printing into the console
    private String captureOutput(Runnable command) {
//    the variable that stores the result of the output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));
        command.run();
        System.setOut(originalOut);
        return out.toString();
    }

    @Test
    public void ls() throws IOException, InterruptedException {
//        LsCommand.execute(new String[]{"ls", "*"});
        String output = captureOutput(() -> LsCommand.execute(new String[]{"ls", "*"}));
//        assertTrue(output.contains("OS_ASSIG.iml"));
//        assertTrue(output.contains("pom.xml"));
//        assertTrue(output.contains("src"));
       // assertTrue(output.equals(runCommand("ls *")));
    }

    @Test
    public void lsMagicCard() throws IOException, InterruptedException {
//        LsCommand.execute(new String[]{"ls", "*.xml"});
        String output = captureOutput(() -> LsCommand.execute(new String[]{"ls", "*.xml"}));
//        assertTrue(output.contains(".xml")); // Example XML file in the directory
        assertTrue(!output.contains(".txt")); // Ensure it excludes non-XML files
        System.out.println(output);
        //assertTrue(output.equals(runCommand("ls *.xml"))); // Ensure it excludes non-XML files
    }

    @Test
    public void lsRelativePath() throws IOException, InterruptedException {
//        LsCommand.execute(new String[]{"ls", "../"});
        String output = captureOutput(() -> LsCommand.execute(new String[]{"ls", "../"}));
        System.out.println(output);
     //   assertTrue(output.equals(runCommand("ls ../../../")));
       // System.out.println(runCommand("ls ../../../"));
    }

    @Test
    public void lsRelativePath2() {
//        LsCommand.execute(new String[]{"ls", "../../"});
        String output = captureOutput(() -> LsCommand.execute(new String[]{"ls", "../../"}));
        assertTrue(output.contains("rootFile.txt"));
    }

    @Test
    public void lsRelativePath3() {
        LsCommand.execute(new String[]{"ls", "../"});


    }
    }

