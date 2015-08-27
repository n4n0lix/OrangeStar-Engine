package de.orangestar.engine;

/**
 * Base class for Components.
 * The gameobject has to be set in the constructor and cannot be changed afterwards.
 * 
 * @author Oliver &amp; Basti
 */
public abstract class Component {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Returns the associated gameobject.
     * @return A gameobject
     */
    public GameObject getGameObject() {
        return _gameObject;
    } 
    
    public void initialize(GameObject gameObject) {
        _gameObject = gameObject;
        onInitialize();

        _isInitialized = true;
    }

    public void deinitialize() {
        onDeinitialize();
        _gameObject = null;
        
        _isInitialized = false;
    }
    
    public boolean isInitialized() {
        return _isInitialized;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Protected                             */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Implement component initialization here. (Called upon everytime the component is assigned to a gameobject)
     */
    protected abstract void onInitialize();
    
    /**
     * Implement component deinitialization here. (Called upon everytime the component is removed from a gameobject)
     */
    protected abstract void onDeinitialize();

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private boolean    _isInitialized;
    private GameObject _gameObject;
    
}
