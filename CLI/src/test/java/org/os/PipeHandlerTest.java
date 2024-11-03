package org.os;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class PipeHandlerTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStream)); // Redirect output
    }

    @After
    public void tearDown() {
        System.setOut(originalOut); // Restore original output
    }

    @Test
    public void testPipeWithInvalidCommand() {
        String command = "invalidCommand | sort";
        PipeHandler.handlePipe(command);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Unknown command: invalidCommand"));
    }




    @Test
    public void testEmptyInput() {
        String command = "";
        PipeHandler.handlePipe(command);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Empty command"));
    }
}
