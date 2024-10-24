package org.os;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PwdCommandTest {

    @Test
    void testPwdCommand() {
        String expectedDir = System.getProperty("user.dir");
        assertEquals(expectedDir, PwdCommand.getCurrentDirectory());
    }
}
