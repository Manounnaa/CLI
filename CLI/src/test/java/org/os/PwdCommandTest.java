package org.os;
import org.junit.jupiter.api.*;
 import static org.junit.jupiter.api.Assertions.*;
//testing for pwd >>it retrieves current working dir
class PwdCommandTest{
    @Test
    void testGetCurrentDirectory() {// get current dir by PwdCommand fun
        String currentDir = PwdCommand.getCurrentDirectory();// assert>>returned dir matches expected property for user dir
        assertEquals(System.getProperty("user.dir"), currentDir, "Current directory should match system property");
        //"The current working directory should align with the system's user directory path
    }
}
