package de.orangestar.sandbox;

import de.orangestar.engine.logic.GameObject;
import de.orangestar.engine.values.Vector3f;

public class TestGameObject extends GameObject {

    public TestGameObject() {
        _moduleLogic  = new TestModuleLogic(this);
        _moduleRender = new TestModuleRender(this);
        
        getTransform().scale = new Vector3f(2f, 2f, 0f);
    }

}
