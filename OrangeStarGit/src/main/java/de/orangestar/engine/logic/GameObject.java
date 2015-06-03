package de.orangestar.engine.logic;

import de.orangestar.engine.logic.modules.LogicModule;
import de.orangestar.engine.logic.modules.RenderModule;
import de.orangestar.engine.values.Transform;

/**
 * Represents an object in the game world. This can be a player, an enemy, 
 * a tree, the worldmap or even the complete UI.
 * 
 * @author Basti
 */
public class GameObject {

    /**
     * Public-Constructor
     */
    public GameObject() {
        _transform = new Transform();
    }
    
    /**
     * Returns the current transform.
     * @return A transform
     */
    public Transform getTransform() {
        return _transform;
    }
    
    /**
     * Sets the current transform.
     * @param transform A transform
     */
    public void setTransform(Transform transform) {
        _transform = transform;
    }
    
    /**
     * Returns the transform of the last logic tick.
     * @return The last transform
     */
    public Transform getLastTransform() {
        return _lastTransform;
    }
        
    public LogicModule     _moduleLogic;
    public RenderModule    _moduleRender;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Sets the last transform. Only called by {@link GameManager}!
     * @param lastTransform
     */
    void setLastTransform(Transform lastTransform) {
        _lastTransform = lastTransform;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Transform _transform;
    private Transform _lastTransform;

}
