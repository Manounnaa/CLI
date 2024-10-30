package org.os;
import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
//testing for tiuch >> creating newfile
class TouchCommandTest {
    private static final String testfilename = "testFile.txt";
    @BeforeEach
    void creation() {// clean up before each test to ensure fresh start
        new File(testfilename).delete();}
    @Test
    void testCreation() {// execute to create new one>>testCreateNewFile
        TouchCommand.execute(testfilename);
        assertTrue(new File(testfilename).exists()); // assert >>file now exists
    }
    @Test
    void testDuplicateCreation() {//testCreateDuplicateFile
        TouchCommand.execute(testfilename);// xreating file for 1st time
        TouchCommand.execute(testfilename);// trying to create same file again
        // assert >> file still exists after the duplicate creation attempt
        assertTrue(new File(testfilename).exists());
    }
    @Test
    void testValidation() { // trying to create file with invalid name>>testCreateFileWithInvalidName
        String invalidFileName = "invalid<>file.txt";
        TouchCommand.execute(invalidFileName);
        // assert >>no file is created with invalid name
        assertFalse(new File(invalidFileName).exists(), "Invalid file name shouldn't create a file ");
    }
}
