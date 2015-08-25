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
	 * Implement component initialization here. (Called upon everytime the component is assigned to a gameobject)
	 */
	public void onInitialize() { }
    
    /**
     * Implement component deinitialization here. (Called upon everytime the component is removed from a gameobject)
     */
    public void onDeinitialize() { }
    
    /**
     * Returns the associated gameobject.
     * @return A gameobject
     */
    public GameObject getGameObject() {
        return _gameObject;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    void setGameObject(GameObject object) {
        _gameObject = object;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private GameObject _gameObject;
    
}
