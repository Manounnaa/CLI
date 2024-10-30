package org.os;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
// testing Ls-r >> lists files in reverse order.
class LsRCommandTest {
    private static final String testdir = "testDir";
    @BeforeAll
    static void creation() {// ereate test dir before running any tests
        new File(testdir).mkdir();
    }
    @AfterAll
    static void delete() {  // delete created files ,dirs after tests
        for (File file : new File(testdir).listFiles()) {
            file.delete();
        }
        new File(testdir).delete();
    }
    @Test
    void testList() {  // ereate tests >>testListFilesInDirectory
        TouchCommand.execute(testdir + "/file1.txt");
        TouchCommand.execute(testdir + "/file2.txt");
//execute Ls-r
        List<String> files = LsRCommand.execute(testdir);
        // ensure 2files are listed
        assertEquals(2, files.size(), "Expected two files in the directory ");
        // ensure both files are in the listing
        assertTrue(files.contains("file1.txt") && files.contains("file2.txt"),
                "Expected file1.txt and file2.txt in the listing ");
    }
    @Test
    void testEmpty() {// create empty test dir>>testEmptyDirectory
        String emptyDir = "emptyTestDir";
        new File(emptyDir).mkdir();
        // execute Ls-r on empty dir
        List<String> files = LsRCommand.execute(emptyDir);
        //  result >> is empty list
        assertTrue(files.isEmpty(), "Expected no files in the empty directory ");
        new File(emptyDir).delete();
    }
    @Test
    void testValidation() {//testInvalidDirectory
        // execute Ls-r on an invalid dir
        List<String> files = LsRCommand.execute("invalidDir");
        // ensuring result >>still empty list
        assertTrue(files.isEmpty(), "Expected no files for an invalid directory ");
    }
    @Test
    void testReversing() {//testReverseOrderListing
        // create multiple test files
        TouchCommand.execute(testdir + "/b.txt");
        TouchCommand.execute(testdir + "/a.txt");
        TouchCommand.execute(testdir + "/c.txt");
        // listing files in reverse order
        List<String> files = LsRCommand.execute(testdir);
        assertEquals(3, files.size(), "Expected three files in the directory !");
        // checking>> reverse alphabetical order
        assertEquals("c.txt", files.get(0), "First file should be c.txt ");
        assertEquals("b.txt", files.get(1), "Second file should be b.txt ");
        assertEquals("a.txt", files.get(2), "Last file should be a.txt ");
    }
    @Test
    void testSimilarity() {//testFilesWithSameName
        // file with a duplicate name
        TouchCommand.execute(testdir + "/duplicate.txt");
        TouchCommand.execute(testdir + "/duplicate.txt"); // try to create it again
        List<String> files = LsRCommand.execute(testdir);
        // only one instance is listed
        assertEquals(1, files.size(), "Expected one instance of duplicate.txt ");
        assertTrue(files.contains("duplicate.txt"),
                "Expected duplicate.txt to be in the list ");
    }
}
