package de.orangestar.engine.logic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class World implements Iterable<GameObject> {
    
    public void addGameObject(GameObject object) {
        if (object == null) {
            throw new IllegalArgumentException("GameObject is null!");
        }
        
        _gameObjects.add(object);
    }
    
    @Override
    public Iterator<GameObject> iterator() {
        return _gameObjects.iterator();
    }
    
    private List<GameObject> _gameObjects;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              SINGLETON                             */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Returns the active instance of {@link GameManager}
     * @return A game manager
     */
    public static World Get() {
        if (INSTANCE == null) {
            INSTANCE = new World();
        }

        return INSTANCE;
    }


    private World() {
        _gameObjects = new LinkedList<>();
    }
    
    private static World INSTANCE = null;
    
}
