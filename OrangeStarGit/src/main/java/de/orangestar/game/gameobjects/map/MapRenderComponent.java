package de.orangestar.game.gameobjects.map;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.render.GLWindow;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.actor.Actor;
import de.orangestar.engine.render.actor.Actor.AnchorType;
import de.orangestar.engine.render.actor.ModernTileMap;
import de.orangestar.engine.render.actor.ModernTileMap.Surface;
import de.orangestar.engine.render.component.UnitRenderComponent;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Quaternion4f;
import de.orangestar.engine.values.Transform;
import de.orangestar.engine.values.Vector3f;

/**
 * The {@link de.orangestar.engine.render.component.RenderComponent} implementation of the {@link Map}.
 * 
 * @author Basti
 */
public class MapRenderComponent extends UnitRenderComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public MapRenderComponent(GameObject obj, MapLogicComponent logic) {
        super(obj);
        _logic = logic;
        
        _window = RenderManager.Get().getMainWindow();
        _tmpRenderWidth = -1;
        _tmpRenderHeight = -1;

        setLayer(0);
    }

    public void onRender() {
        // Check if render window has changed in size
        if (_tmpRenderWidth != _window.getRenderWidth() || _tmpRenderHeight != _window.getRenderHeight()) {
            _tmpRenderWidth = _window.getRenderWidth();
            _tmpRenderHeight = _window.getRenderHeight();

            updateData();
        }
        
        // If a chunk went out of sight
        if (_tmpChunkScrollX != _logic.globalChunkScrollX || _tmpChunkScrollY != _logic.globalChunkScrollY) {
            _tmpChunkScrollX = _logic.globalChunkScrollX;
            _tmpChunkScrollY = _logic.globalChunkScrollY;
            
            updateData();
        }
        
        int chunksX = (int) Math.ceil(_tmpRenderWidth  / (MapChunk.TILE_SIZE * MapChunk.CHUNK_SIZE)) + 1;
        int chunksY = (int) Math.ceil(_tmpRenderHeight / (MapChunk.TILE_SIZE * MapChunk.CHUNK_SIZE)) + 1;
        // System.out.println( chunksX + "/" + chunksY);

        super.onRender();
    } 

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private void updateData() {
        // CLEAR DATA
//        if (_tilemaps != null && _tilemaps.length > 0 && _tilemaps[0].length > 0) {
//            for(int x = 0; x < _tilemaps.length; x++) {
//                for(int y = 0; y < _tilemaps[0].length; y++) {
//                    _tilemaps[x][y].onDestroy();
//                }
//            }
//        }
        //
        
        if (_tilemaps != null) {
            for(ModernTileMap[] maps : _tilemaps) {
                for(ModernTileMap map : maps) {
                    if (map != null) {
                        map.onDestroy();
                    }
                }
            }
        }
        
        // CREATE DATA
        Vector3f unitScale = getGameObject().getLocalTransform().scale;
        
        float scaleX = unitScale.x;
        float scaleY = unitScale.y;
        
        float pxTilemapWidth  = MapChunk.TILE_SIZE * scaleX * MapChunk.CHUNK_SIZE;
        float pxTilemapHeight = MapChunk.TILE_SIZE * scaleY * MapChunk.CHUNK_SIZE;
        
        int x = 0;
        int y = 0;
        
        _tilemaps = new ModernTileMap[1][1];
        Actor proxyActor = new Actor() {
            @Override
            public void onRender() { }

            @Override
            public void onDestroy() { }
        };
        
        MapChunk chunk = _logic.getChunk(x, y);
        
        if (chunk != null) {
            ModernTileMap tilemap = new ModernTileMap(TILESET, 16, 16, MapChunk.TILE_SIZE, MapChunk.TILE_SIZE);
            tilemap.setAnchorType(AnchorType.MID);
            tilemap.setTransform(
                    new Transform(
                            new Vector3f(x * pxTilemapWidth, y * pxTilemapHeight),
                            Vector3f.one(),
                            Quaternion4f.identity()
                            )
                    );
            
            tilemap.setData(readChunk(chunk));
            proxyActor.addChild(tilemap);
        }

        setActor(proxyActor);
    }
    
    /**
     * Reads a map chunk and converts it into renderable data.
     * @param map The map
     * @param x The x coordinate of the chunk
     * @param y The y coordinate of the chunk
     * @return A 2D ModernTileSet.Surface array
     */
    private Surface[][] readChunk(MapChunk chunk) {
        MapSurface[][] data = chunk.getData();
        
        Surface[][] result = new Surface[data.length][data[0].length];
        
        for(int xi = 0; xi < data.length; xi++) {
            for(int yi = 0; yi < data[0].length; yi++) {
                MapSurface current = data[xi][yi];
                
                // Convert MapSurface -> ModernTileMapSurface
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
    
    private GLWindow            _window;
    private ModernTileMap[][]   _tilemaps;    
    
    private int                 _tmpRenderWidth,  _tmpRenderHeight;
    private int                 _tmpChunkScrollX, _tmpChunkScrollY;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
//    private static Texture TILESET = new Texture("textures/prisonarchitect.png");
    private static Texture TILESET = new Texture("textures/WorldTileSetDummy_16x16.png");
    
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
