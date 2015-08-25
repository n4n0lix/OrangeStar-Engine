package de.orangestar.game.gameobjects.player.action;

import java.util.List;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.engine.render.actor.tilemap.Surface;
import de.orangestar.game.gameobjects.map.Map;

/**
 * Action for changing the surface
 * 
 * @author Oliver &amp; Basti
 */
public class SurfaceChangeAction extends Action {

    public SurfaceChangeAction(int destTileX, int destTileY, Surface surface) {
        _destTileX = destTileX;
        _destTileY = destTileY;
        _destSurface = surface;
    }
    
    @Override
    public void onStart() {

    }

    @Override
    public void onUpdate() {        
        // 1# Get Map Object
        List<GameObject> gameObjects = GameObject.getByTagsAny("map");
        if (gameObjects == null || gameObjects.isEmpty() || !(gameObjects.get(0) instanceof Map)) {
            return;
        }
        
        Map map = (Map) gameObjects.get(0);  
        
        // 2# Change surface
        map.getLogicComponent().setTileSurface(_destTileX, _destTileY, _destSurface);

        finish();
    }

    @Override
    public void onEnd() {
        // TODO Auto-generated method stub
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private final int _destTileX;
    private final int _destTileY;
    private final Surface _destSurface;
    
}
