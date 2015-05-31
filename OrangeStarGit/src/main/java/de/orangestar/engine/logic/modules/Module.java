package de.orangestar.engine.logic.modules;

import de.orangestar.engine.logic.GameObject;

/**
 * Base class for gameobject modules.
 * 
 * @author Basti
 */
public abstract class Module {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor.
     * @param parent The associated gameobject
     */
    public Module(GameObject parent) {
        _parent = parent;
    }
    
    /**
     * Returns the associated gameobject.
     * @return A gameobject
     */
    public GameObject getGameObject() {
        return _parent;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private GameObject _parent;
    
}
