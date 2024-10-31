package org.os;

import org.junit.jupiter.api.*;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

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

    // Test for reverse order listing
    @Test
    void testList() {
        TouchCommand.execute(testDir + "/file1.txt");
        TouchCommand.execute(testDir + "/file2.txt");
        String output = LsCommand.execute(new String[]{testDir, "-r"});
        assertTrue(output.contains("file1.txt"));
        assertTrue(output.contains("file2.txt"));
    }

    // Test for empty directory
    @Test
    void testEmpty() {
        String emptyDir = "emptyTestDir";
        new File(emptyDir).mkdir();
        String output = LsCommand.execute(new String[]{emptyDir, "-r"});
        assertTrue(output.isEmpty());
        new File(emptyDir).delete();
    }

    // Test for invalid directory
    @Test
    void testValidation() {
        String output = LsCommand.execute(new String[]{"invalidDir", "-r"});
        assertTrue(output.contains("Error: 'invalidDir' is not a valid directory.")); // Assuming error handling
    }

    // Test for reverse order listing with multiple files
    @Test
    void testReversing() {
        TouchCommand.execute(testDir + "/b.txt");
        TouchCommand.execute(testDir + "/a.txt");
        TouchCommand.execute(testDir + "/c.txt");
        String output = LsCommand.execute(new String[]{testDir, "-r"});
        assertTrue(output.startsWith("c.txt"));
    }

    // Test for files with the same name
    @Test
    void testSimilarity() {
        TouchCommand.execute(testDir + "/duplicate.txt");
        TouchCommand.execute(testDir + "/duplicate.txt");
        String output = LsCommand.execute(new String[]{testDir, "-r"});
        assertTrue(output.contains("duplicate.txt"));
    }

    // Test for listing all files
    @Test
    void testLsAllFiles() {
        TouchCommand.execute(testDir + "/file3.txt");
        String output = LsCommand.execute(new String[]{testDir, "*"});
        assertTrue(output.contains("file3.txt"));
    }

    // Test for listing XML files
    @Test
    void testLsXmlFiles() {
        TouchCommand.execute(testDir + "/file1.xml");
        TouchCommand.execute(testDir + "/file2.txt");
        String output = LsCommand.execute(new String[]{testDir, "*.xml"});
        assertTrue(output.contains("file1.xml"));
        assertFalse(output.contains("file2.txt"));
    }

    // Test for relative path
    @Test
    void testLsRelativePath() {
        String output = LsCommand.execute(new String[]{"ls", "../"}); // Assuming valid parent directory
        assertNotNull(output); // Check that the command runs without error
    }

    // Test for multiple levels of relative path
    @Test
    void testLsRelativePath2() {
        String output = LsCommand.execute(new String[]{"ls", "../../"}); // Adjust based on your structure
        assertNotNull(output); // Check that the command runs without error
    }

    // Another relative path test
    @Test
    void testLsRelativePath3() {
        String output = LsCommand.execute(new String[]{"ls", "../"});
        assertNotNull(output); // Check that the command runs without error
    }
}
