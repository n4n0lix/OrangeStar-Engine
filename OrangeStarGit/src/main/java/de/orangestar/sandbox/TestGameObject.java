package de.orangestar.sandbox;

import de.orangestar.engine.logic.GameObject;

public class TestGameObject extends GameObject {

    public TestGameObject() {
        _moduleLogic  = new TestModuleLogic(this);
        _moduleRender = new TestModuleRender(this);
    }

}
