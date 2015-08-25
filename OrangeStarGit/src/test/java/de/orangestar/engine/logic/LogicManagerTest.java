package de.orangestar.engine.logic;

import org.junit.Test;

public class LogicManagerTest {

    @Test
    public void lifecycle() {
        // Just test that no exceptions are thrown
        LogicEngine manager = new LogicEngine();
        manager.onStart();
        manager.onUpdate();
        manager.onShutdown();
    }

}
