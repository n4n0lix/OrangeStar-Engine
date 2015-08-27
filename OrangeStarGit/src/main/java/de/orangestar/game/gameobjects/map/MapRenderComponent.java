package de.orangestar.game.gameobjects.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.orangestar.engine.debug.Logger;
import de.orangestar.engine.render.Camera;
import de.orangestar.engine.render.OrthographicCamera;
import de.orangestar.engine.render.RenderEngine;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.Camera.ViewChangedListener;
import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.actor.tilemap.TileMap;
import de.orangestar.engine.render.actor.tilemap.TileSet;
import de.orangestar.engine.render.component.UnitRenderComponent;
import de.orangestar.engine.utils.Pair;
import de.orangestar.engine.utils.SimplePool;
import de.orangestar.engine.values.Viewport4f;
import de.orangestar.engine.values.Viewport4i;
import de.orangestar.engine.values.Transform;
import de.orangestar.game.MainGameState;
import de.orangestar.game.gameobjects.map.surfaces.DeepWater;
import de.orangestar.game.gameobjects.map.surfaces.Grass;
import de.orangestar.game.gameobjects.map.surfaces.Sand;
import de.orangestar.game.gameobjects.map.surfaces.Water;

/**
 * The {@link de.orangestar.engine.render.RenderComponent} implementation of the {@link Map}.
 * 
 * @author Oliver &amp; Basti
 */
public class MapRenderComponent extends UnitRenderComponent {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
    protected void onInitialize() {
        super.onInitialize();
        
        _cache       = new HashMap<>();
        _tilemapPool = new TilemapPool();

        // Get maps logic component
        _logic = getGameObject().getComponent( MapLogicComponent.class );
                
        // Setup camera listener
        _viewUpdateRequested = true;
        _cameraViewUpdatedListener = (Camera src) -> { _viewUpdateRequested = true; };

        // Setup chunk updating
        _chunkUpdateRequested = new ArrayList<Pair<Integer,Integer>>();
        _chunkUpdateListener  = ( (int x, int y) -> _chunkUpdateRequested.add(Pair.New(x, y)) );

        setLayer(MainGameState.LAYER_WORLD);
    }
    
    @Override
    public void onRender(IRenderEngine engine, Camera camera) {
        checkIfCameraChanged(camera);

        if (_orthoCamera == null) {
            return;
        }
        
        reloadVisibleChangedChunks();
        updateVisibleChunks();
        
        super.onRender(engine, camera);
    }

    private void checkIfCameraChanged(Camera camera) {
        if (_orthoCamera != camera) {
            if (_orthoCamera != null) {
                _orthoCamera.removeViewChangedListener(_cameraViewUpdatedListener);
            }
            
            if (!(camera instanceof OrthographicCamera)) {
                _orthoCamera = null;
                return;
            }
            
            _orthoCamera = (OrthographicCamera) camera;
            
            if (_orthoCamera != null) {
                _orthoCamera.addViewChangedListener(_cameraViewUpdatedListener);
            }
        }
    }

    @Override
    protected void onDeinitialize() {
        super.onDeinitialize();
        
        _orthoCamera.removeViewChangedListener( _cameraViewUpdatedListener );
        _tilemapPool.cleanUp();
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    

    private void updateVisibleChunks() {
        if (_viewUpdateRequested) {
            updateData();
            _viewUpdateRequested = false;
        }
    }

    private void reloadVisibleChangedChunks() {
        if (!_chunkUpdateRequested.isEmpty()) {
            for(int i = 0; i < _chunkUpdateRequested.size(); i++) {
                Pair<Integer, Integer> chunkLocation = _chunkUpdateRequested.get(i);
                updateChunk(chunkLocation.x, chunkLocation.y);
            }
            
            _chunkUpdateRequested.clear();
        }
    }
    
    private void updateData() {
        // 1# Calculate visible chunks 
        Viewport4i visibleChunks = calcVisibleChunks();
        
        if (visibleChunks.equals(_lastVisibleChunks)) {
            return; // Visible chunks haven't changed
        }
        
        _lastVisibleChunks = visibleChunks;
        
        // 2# Load visible chunks
        List<List<MapChunk>> chunks = loadMapChunks(_lastVisibleChunks);
        
        if (chunks.isEmpty() || chunks.get(0).isEmpty()) {
            Logger.info(MapRenderComponent.class, "No visible chunks!");
            return;
        }
        
        // 3# Reset active status of all tilemaps
        for(Pair<TileMap, Boolean> pair : _cache.values()) {
            pair.y = false;
        }
        
        // 4# Display loaded chunks  
        displayChunks(chunks);
        
        // 5# Remove inactive tilemaps and put them into the pool
        removeInvisibleChunks();
    }
    
    private void removeInvisibleChunks() {
        List<Pair<Integer, Integer>> unusedTilemaps = new ArrayList<>();
        _cache.forEach( ( key, value) ->
        {
            if (!value.y) {
                unusedTilemaps.add(key);
                _tilemapPool.release(value.x);
            }
        });
        
        for(Pair<Integer, Integer> pair : unusedTilemaps) {
            _cache.remove(pair);
            _logic.getChunk(pair.x, pair.y).removeChangeListener(_chunkUpdateListener);
        }
    }

    private void displayChunks(List<List<MapChunk>> chunks) {
        Transform transform = Transform.POOL.get();
        
        for(List<MapChunk> list : chunks) {
            for(MapChunk chunk : list) {
                Pair<Integer, Integer> cacheKey    = Pair.New(chunk.getX(), chunk.getY());
                Pair<TileMap, Boolean> cacheValue  = _cache.get(cacheKey);
                
                TileMap tilemap;
                
                if (cacheValue == null) {                        
                    float chunkWidth  = MapChunk.CHUNK_SIZE * MapChunk.TILE_SIZE;
                    float chunkHeight = MapChunk.CHUNK_SIZE * MapChunk.TILE_SIZE;
                    
                    tilemap = _tilemapPool.get();

                    transform.position.x = chunk.getX() * chunkWidth;
                    transform.position.y = chunk.getY() * chunkHeight;
                    
                    tilemap.setTransform(transform);
                    tilemap.setData(chunk.getData());

                    // Add mapdata change listener
                    chunk.addChangeListener(_chunkUpdateListener);
                    
                    // Add data to cache
                    cacheValue = Pair.New(tilemap, true);
                    _cache.put(Pair.New(chunk.getX(), chunk.getY()), cacheValue);         
                } else {
                    tilemap = cacheValue.x;
                }
                
                cacheValue.y = true; // Activly used
                
                if (!getActors().contains(tilemap)) {
                    addActor(tilemap);
                }
            }
        }
        
        Transform.POOL.release(transform);
    }
        
    private void updateChunk(int x, int y) {
        Pair<TileMap, Boolean> tileMap = _cache.get(Pair.New(x, y));
        
        // Chunk not visible, no need to update
        if (tileMap == null) {
            return;
        }
        
        MapChunk chunk = _logic.getChunk(x, y);
        tileMap.x.setData(chunk.getData());
    }

    private Viewport4i calcVisibleChunks() {     
        Viewport4f viewport = _orthoCamera.getWorldViewport();
        
        MapChunk chunkTopLeft  = _logic.getChunkByWorldPosition(viewport.left, viewport.top);
        MapChunk chunkTopRight = _logic.getChunkByWorldPosition(viewport.right, viewport.top);
        MapChunk chunkBotLeft  = _logic.getChunkByWorldPosition(viewport.left, viewport.bottom);
                
        int start_ix = chunkTopLeft.getX();
        int end_ix   = chunkTopRight.getX();
        int start_iy = chunkTopLeft.getY();
        int end_iy   = chunkBotLeft.getY();
        
        Logger.debug(MapRenderComponent.class, "Visible chunks X: [" + start_ix + " - " + end_ix + "] Y: [" + start_iy + " - " + end_iy + "]");
        
        return new Viewport4i(start_ix, start_iy, end_ix,  end_iy);
    }
    
    private List<List<MapChunk>> loadMapChunks(Viewport4i visibleChunks) {
        List<List<MapChunk>> chunks = new ArrayList<>();
        
        // Load the map chunks for every visible 'map chunk column' ...
        for(int ix = visibleChunks._xLeft; ix <= visibleChunks._xRight; ix++) {
            List<MapChunk> curList = new ArrayList<>();
            
            // ... and for every 'map chunk row'
            for(int iy = visibleChunks._yTop; iy <= visibleChunks._yBottom; iy++) {
                curList.add(_logic.getChunk(ix, iy));
            }
            
            chunks.add(curList);
        }
        
        return chunks;
    }

    private MapLogicComponent _logic;
    
    private OrthographicCamera  _orthoCamera;
    private ViewChangedListener _cameraViewUpdatedListener;
    private Map<Pair<Integer, Integer>, Pair<TileMap, Boolean>> _cache;
    
    private TilemapPool _tilemapPool;
    
    /**
     * Set to true if the view has changed and we need to check for new visible chunks.
     */
    private boolean     _viewUpdateRequested;
    
    private Viewport4i  _lastVisibleChunks;
    
    /**
     * Contains chunk positions of chunks with changed data, so we need to update the tilemaps.
     */
    private List<Pair<Integer,Integer>> _chunkUpdateRequested;
    private MapChunk.ChangeListener     _chunkUpdateListener;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private static class TilemapPool extends SimplePool<TileMap> {

        @Override
        protected TileMap newInstance() {
            return new TileMap(Tileset, MapChunk.TILE_SIZE, MapChunk.TILE_SIZE);
        }

        @Override
        protected void resetInstance(TileMap t) { }

        @Override
        protected void cleanUp(TileMap t) {
            t.onDeinitialize();
        }

    }

//    private static Tileset Tileset = new Tileset.Builder()
//                                                    .texture(new Texture("textures/debug/tileset.png"))
//                                                    .surfaces(DeepWater.Instance, Water.Instance, Sand.Instance, Grass.Instance)
//                                                    .tilesize(16, 16)
//                                                    .build();
    
    private static TileSet Tileset = new TileSet.Builder()
                                                    .texture(new Texture("textures/tileset.png"))
                                                    .surfaces(DeepWater.Instance, Water.Instance, Sand.Instance, Grass.Instance)
                                                    .tilesize(128, 128)
                                                    .build();

}
