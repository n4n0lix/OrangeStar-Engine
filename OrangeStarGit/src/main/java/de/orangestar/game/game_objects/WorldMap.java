package de.orangestar.game.game_objects;

import de.orangestar.engine.logic.GameObject;
import de.orangestar.engine.logic.modules.RenderModule;
import de.orangestar.engine.render.actor.TileMap;
import de.orangestar.engine.resources.ResourceManager;

public class WorldMap extends GameObject {

    public WorldMap() {
        // Setup rendering
        _actorGround = new TileMap(ResourceManager.Get().getTexture("textures/WorldTileSetDummy_16x16.png"), 16, 16);
        _actorGround.setData(generateTileMapDummyData());
        
        _moduleRender = new RenderModule(this, _actorGround);
        this._moduleRender.setLayer(0);
        
        // Scale x2
        this.setTransform(this.getTransform().scale(2f));
    }
 
    private TileMap _actorGround;
    
    private static int[][] generateTileMapDummyData() {
        return new int[][] {
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0, },
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0, },
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0, },
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0, },
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0, },
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0, },
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0, },
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0, },
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0, },
                
        };
    }
}
