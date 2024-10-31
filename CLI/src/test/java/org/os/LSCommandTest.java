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
        void testList() {  // ereate tests >>testListFilesInDirectory
            TouchCommand.execute(testDir + "/file1.txt");
            TouchCommand.execute(testDir + "/file2.txt");
            String output = LsCommand.execute(new String[]{testDir, "-r"});
            assertTrue(output.contains("file1.txt"));
            assertTrue(output.contains("file2.txt"));
        }
        @Test
        void testEmpty() {// create empty test dir>>testEmptyDirectory
            String emptyDir = "emptyTestDir";
            new File(emptyDir).mkdir();
            String output = LsCommand.execute(new String[]{emptyDir, "-r"});
            assertTrue(output.isEmpty());
            new File(emptyDir).delete();
        }

        @Test
    void testValidation() {//testInvalidDirectory
            // execute Ls-r on an invalid dir
        String output = LsCommand.execute(new String[]{"invalidDir", "-r"});
        assertTrue(output.isEmpty());
    }
        @Test
        void testReversing() {//testReverseOrderListing
            // create multiple test files
            TouchCommand.execute(testDir + "/b.txt");
            TouchCommand.execute(testDir + "/a.txt");
            TouchCommand.execute(testDir + "/c.txt");
            String output = LsCommand.execute(new String[]{testDir, "-r"});
            assertTrue(output.startsWith("c.txt"));
        }
        @Test
        void testSimilarity() {//testFilesWithSameName
            // file with a duplicate name
            TouchCommand.execute(testDir + "/duplicate.txt");
            TouchCommand.execute(testDir + "/duplicate.txt");
            String output = LsCommand.execute(new String[]{testDir, "-r"});
            assertTrue(output.contains("duplicate.txt"));
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

