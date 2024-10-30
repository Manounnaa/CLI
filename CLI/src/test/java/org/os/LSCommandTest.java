package org.os;

import org.junit.jupiter.api.Test;

public class LSCommandTest {
    @Test
    public void ls() {
        LsCommand.execute(new String[]{"ls", "*"});
    }

    @Test
    public void lsMagicCard() {
        LsCommand.execute(new String[]{"ls", "*.xml"});
    }

    @Test
    public void lsRelativePath() {
        LsCommand.execute(new String[]{"ls", "../"});
    }

    @Test
    public void lsRelativePath2() {
        LsCommand.execute(new String[]{"ls", "../../"});
    }

    @Test
    public void lsRelativePath3() {
        LsCommand.execute(new String[]{"ls", "../"});
    }
}
