package de.orangestar.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The world in which all active gameobjects are living.
 * 
 * @author Oliver &amp; Basti
 */
public class World {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public static List<World> getAllWorlds() {
        return READ_ONLY_ALL_WORLDS;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Public-Constructor.
     */
    public World() {
        ALL_WORLDS.add(this);
        
        _gameObjects         = new ArrayList<>();
        _readOnlyGameObjects = Collections.unmodifiableList(_gameObjects);
    }
    
    /**
     * Adds the given gameobject into the world. This causes all components of the
     * gameobject to be updated.<br>
     * Preconditions: 
     * <ul>
     *  <li><code>object != null</code>
     *  <li><code>!contains( object )</code>
     * </ul>
     * 
     * @param object A gameobject
     */
    public void addGameObject(GameObject object) {
        // Preconditions:
        assert object != null;
        assert !contains(object);
        
        // Code:
        if (object.getWorld() != null)  {
            object.getWorld().removeGameObject(object);
        }
            
        _gameObjects.add(object);
        object.setWorld(this);
    }

    /**
     * Removes the given gameobject from the world. This disables the component updates.<br>
     * Preconditions: 
     * <ul>
     *  <li><code>object != null</code>
     *  <li><code>contains( object )</code>
     * </ul>
     * @param object A gameobject
     */
    public void removeGameObject(GameObject object) {  
        // Preconditions:
        assert object != null;
        assert contains( object );
        
        // Code:
        _gameObjects.remove(object);
        object.setWorld(null);
    }
    
    /**
     * Removes all gameobjects.
     */
    public void clear() {
        List<GameObject> gameobjects = new ArrayList<>( _gameObjects );
        
        for(GameObject obj : gameobjects) {
            removeGameObject(obj);
        }
    }
    
    /**
     * Returns a readonly list of all gameobjects.
     * @return A readonly list of all gameobjects
     */
    public List<GameObject> getGameObjects() {
        return _readOnlyGameObjects;
    }

    /**
     * If the gameobject is in this world.
     * @param object A gameobject
     * @return If the gameobject is in this world
     */
    public boolean contains(GameObject object) {
        return _gameObjects.contains(object);
    }

    /**
     * Destroys all gameobjects and clears them from the world.
     */
    public void destroyAndClear() {
        for(GameObject obj : _gameObjects) {
            obj.destroy();
        }
        clear();
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private List<GameObject>                  _gameObjects;
    private List<GameObject>                  _readOnlyGameObjects;
        
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private static List<World> ALL_WORLDS           = new ArrayList<>();
    private static List<World> READ_ONLY_ALL_WORLDS = Collections.unmodifiableList(ALL_WORLDS);
    
}
