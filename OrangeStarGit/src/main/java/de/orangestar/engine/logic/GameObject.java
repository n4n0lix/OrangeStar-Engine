package de.orangestar.engine.logic;

import de.orangestar.engine.logic.modules.LogicModule;
import de.orangestar.engine.logic.modules.RenderModule;
import de.orangestar.engine.values.Transform;

public class GameObject {

    public GameObject() {
        _transform = new Transform();
    }
    
    public Transform getTransform() {
        return _transform;
    }
    
    public void setTransform(Transform transform) {
        _transform = transform;
    }
    
    public Transform getLastTransform() {
        return _lastTransform;
    }
    
    void setLastTransform(Transform lastTransform) {
        _lastTransform = lastTransform;
    }
    
    public LogicModule     _moduleLogic;
    public RenderModule    _moduleRender;
    
    private Transform _transform;
    private Transform _lastTransform;
    
    
}
