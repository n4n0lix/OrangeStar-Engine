package de.orangestar.game.gameobjects.map;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.render.GLWindow;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.actor.ModernTileMap;
import de.orangestar.engine.render.actor.ModernTileMap.Surface;
import de.orangestar.engine.render.component.UnitRenderComponent;
import de.orangestar.engine.values.Vector3f;

public class MapRenderComponent extends UnitRenderComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public MapRenderComponent(GameObject obj, MapLogicComponent logic) {
        super(obj);
        _logic = logic;
        
        _window = RenderManager.Get().getMainWindow();
        _renderWidth = -1;
        _renderHeight = -1;

//        _actorGround = new ModernTileMap(new Texture("textures/WorldTileSetDummy_16x16.png"), 16, 16);
        _actorGround = new ModernTileMap(new Texture("textures/prisonarchitect.png"), 50, 50);
        
        setLayer(0);
        setActor(_actorGround);
    }

    public void onRender() {
        // Check if render window has changed in size
        if (_renderWidth != _window.getRenderWidth() || _renderHeight != _window.getRenderHeight()) {
            _renderWidth = _window.getRenderWidth();
            _renderHeight = _window.getRenderHeight();

            updateSize();
        }

        super.onRender();
    } 

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private void updateSize() {
        Vector3f unitScale = getGameObject().getLocalTransform().scale;
        
        float scaleX = unitScale.x;
        float scaleY = unitScale.y;
        
        int mapWidth =  (int)(_renderWidth  / scaleX / _actorGround.getTileWidth()) + 1;
        int mapHeight = (int)(_renderHeight / scaleY / _actorGround.getTileHeight()) + 1;

        Map world = (Map) getGameObject();
        _actorGround.setData(readChunk(world, 0, 0));
    }
    
    private Surface[][] readChunk(Map world, int x, int y) {
        MapChunk chunk = _logic.getChunk(x, y);
        MapSurface[][] data = chunk.getData();
        
        Surface[][] result = new Surface[data.length][data[0].length];
        
        for(int xi = 0; xi < data.length; xi++) {
            for(int yi = 0; yi < data[0].length; yi++) {
                MapSurface current = data[xi][yi];
                
                if (current == MapSurface.DIRT) {
                    result[xi][yi] = DIRT;
                } else if (current == MapSurface.GRASS) {
                    result[xi][yi] = GRASS;
                }else if (current == MapSurface.WATER) {
                    result[xi][yi] = WATER;
                }
                
            }
        }
        
        return result;
    }

    private MapLogicComponent _logic;
    
    private GLWindow        _window;
    private ModernTileMap   _actorGround;
    private int             _renderWidth, _renderHeight;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private static Surface WATER = new Surface()
                                        .nonsolid()
                                        .layer(2)                                        
                                        .tileIds( 16, 17, 18, 19, 24, 25, 26, 27 );
    
    private static Surface DIRT = new Surface()
                                        .nonsolid()
                                        .layer(0)                                        
                                        .tileIds( 0, 1, 2, 3, 8, 9, 10, 11 );
    
    private static Surface GRASS = new Surface()
                                        .nonsolid()
                                        .layer(1)                                        
                                        .tileIds( 4, 5, 6, 7, 12, 13, 14, 15 );
            
}
