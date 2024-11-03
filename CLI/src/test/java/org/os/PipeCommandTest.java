package org.os;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class PipeCommandTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // Redirect System.out to capture outputs
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(originalOut);
    }

    @Test
    public void testHandlePipeWithInvalidCommand() {
        String input = "unknownCommand";
        String output = PipeHandler.handlePipe(input);
        assertEquals("Unknown command: unknownCommand", output.trim());
    }

    @Test
    public void testHandlePipeWithEmptyInput() {
        String input = "";
        String output = PipeHandler.handlePipe(input);
        assertEquals("Empty command", output.trim());
    }

    @Test
    public void testHandlePipeWithNullInput() {
        String output = PipeHandler.handlePipe(null);
        assertEquals("Empty command", output.trim());
    }

    @Test
    public void testHandlePipeWithUnsupportedCommand() {
        String input = "touch text.txt | ls";
        String output = PipeHandler.handlePipe(input);

        // Expect "Unknown command" output
        assertTrue(output.contains("Unknown command: touch"));
    }
}
