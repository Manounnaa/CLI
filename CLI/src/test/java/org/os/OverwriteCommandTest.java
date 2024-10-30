package org.os;
import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
//testing > >>writes content to files.
class OverwriteCommandTest {
    private static final String testfilename = "overwriteFile.txt";
    private static final String  initialcontent = "Hello, World!";
    private static final String newcontent = "New Content!";
    @BeforeEach
    void creation() {  // clean up before each test to ensure fresh start
        new File(testfilename).delete();}
    @Test
    void testWriting() {//testWriteToFile
        TouchCommand.execute(testfilename);// create new one
        // Write initial content tofile
        OverwriteCommand.execute(testfilename, initialcontent);
        try (BufferedReader reader = new BufferedReader(new FileReader(testfilename))) {
            String line = reader.readLine();
            // assert >>content matches past wrote
            assertEquals(initialcontent, line);
        } catch (IOException e) {
            fail("IOException occurred while reading the file: " + e.getMessage());
        }
    }
    @Test
    void testEditing() {//testOverwriteFileContent
        // creat new file , write initial content
        TouchCommand.execute(testfilename);
        OverwriteCommand.execute(testfilename, initialcontent);
        OverwriteCommand.execute(testfilename, newcontent);
        //  verifying updated content
        try (BufferedReader reader = new BufferedReader(new FileReader(testfilename))) {
            String line = reader.readLine();
            // content updated?
            assertEquals(newcontent, line); //The file content should reflect the new content after overwriting
        } catch (IOException e) {
            fail("IOException occurred while reading the file: " + e.getMessage());
        }
    }
    @Test
    void testEditNoContent() {//testOverwriteFileWithEmptyContent
        TouchCommand.execute(testfilename);// create newone and write content
        OverwriteCommand.execute(testfilename, "Some Content");
        OverwriteCommand.execute(testfilename, "");
        // assert >>empty file  after writing no content
        assertEquals(0, new File(testfilename).length(), "File should be empty after writing empty content");
    }
    @Test
    void testValidation() {//testOverwriteInvalidFileName
        OverwriteCommand.execute("invalid<>file.txt", initialcontent);
        assertFalse(new File("invalid<>file.txt").exists(), "Invalid file name shouldn't create a file");
    }
}
