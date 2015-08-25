package de.orangestar.game.gameobjects.map;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.render.OrthographicCamera;
import de.orangestar.engine.render.RenderEngine;

/**
 * The Map gamobject.
 * 
 * @author Oliver &amp; Basti
 */
public class Map extends GameObject {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
	/**
     * Creates the map
     */
    public Map() {  
        setName("Map#"+getUID());
        
        // Initialize Test Data
        addTags("map");

        // Components
        setLogicComponent( new MapLogicComponent() );
        setRenderComponent( new MapRenderComponent() );
    }

    @Override
    public MapLogicComponent getLogicComponent() {
        return (MapLogicComponent) super.getLogicComponent();
    }

}
