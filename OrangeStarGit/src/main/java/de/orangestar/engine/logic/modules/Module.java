package de.orangestar.engine.logic.modules;

import de.orangestar.engine.logic.GameObject;

public abstract class Module {

    public Module(GameObject parent) {
        _parent = parent;
    }
    
    public GameObject getGameObject() {
        return _parent;
    }
    
    private GameObject _parent;
    
}
