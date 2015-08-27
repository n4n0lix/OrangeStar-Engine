package de.orangestar.game.gameobjects.map;

import java.util.HashMap;
import java.util.Map;

import de.orangestar.engine.logic.LogicComponent;
import de.orangestar.engine.render.actor.tilemap.Surface;
import de.orangestar.engine.utils.MathUtils;
import de.orangestar.engine.utils.Pair;
import de.orangestar.game.gameobjects.map.generator.MapChunkGenerator;

/**
 * LogicComponent of the Map.
 * 
 * @author Oliver &amp; Basti
 */
public class MapLogicComponent extends LogicComponent {
    
	/**
	 * Returns the Chunk index at a position in the world.
	 *  
	 * @param worldPositionX x position in the world
	 * @param worldPositionY y position in the world
	 * @return the Chunk index
	 */
    public static Pair<Integer, Integer> getChunkIndexAt(float worldPositionX, float worldPositionY) {
        return Pair.New(MathUtils.fastFloor(worldPositionX / (MapChunk.TILE_SIZE * MapChunk.CHUNK_SIZE)),
                        MathUtils.fastFloor(worldPositionY / (MapChunk.TILE_SIZE * MapChunk.CHUNK_SIZE)));
    }
    
    /**
     * Returns the Chunk index at a position in the world.
     * 
     * @param tileX x position of the tile
     * @param tileY y position of the tile
     * @return the chunk index
     */
    public static Pair<Integer, Integer> getChunkIndexAt(int tileX, int tileY) {
        return Pair.New(MathUtils.fastFloor(tileX / (1d * MapChunk.CHUNK_SIZE)),
                        MathUtils.fastFloor(tileY / (1d * MapChunk.CHUNK_SIZE)));
    }
    
    /**
     * Returns the tile index at a position in the world.
     * 
     * @param worldPositionX x position in the world
	 * @param worldPositionY y position in the world
     * @return the tile index
     */
    public static Pair<Integer, Integer> getTileIndexAt(float worldPositionX, float worldPositionY) {
        return Pair.New(MathUtils.fastFloor(worldPositionX / MapChunk.TILE_SIZE),
                        MathUtils.fastFloor(worldPositionY / MapChunk.TILE_SIZE));
    }
    
    /**
     * Returns the local tile index on a Chunk.
     * 
     * @param tileX x position of the tile
     * @param tileY y position of the tile
     * @return the local tile
     */
    public static Pair<Integer, Integer> getLocalTileIndex(int tileX, int tileY) {
        int localTileY = Math.abs(tileY % MapChunk.CHUNK_SIZE);
        int localTileX = Math.abs(tileX % MapChunk.CHUNK_SIZE);
        
        if (tileX < 0) {
            localTileX = MapChunk.CHUNK_SIZE - localTileX;
        }
        
        if (tileY < 0) {
            localTileY = MapChunk.CHUNK_SIZE - localTileY;
        }
        
        return Pair.New(localTileX, localTileY);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
    protected void onInitialize() {        
        _chunks            = new HashMap<>();
        _mapChunkGenerator = new MapChunkGenerator();
    }

    @Override
    protected void onDeinitialize() {

    }
    
	/**
	 * Returns a chunk for a given chunk x/y position. If no chunk was found a new is generated.
	 * @param x The chunk's x position
	 * @param y The chunk's y position
	 * @return A map chunk
	 */
    public MapChunk getChunk(int x, int y) {
        MapChunk chunk = _chunks.get(Pair.New(x,y));
        
        if (chunk == null) {
            chunk = _mapChunkGenerator.generateMapChunk(x, y);
            _chunks.put(Pair.New(x, y), chunk);
        }
        
        return chunk;
    } 
    
    /**
     * Returns a chunk for a given tile x/y position. If no chunk was found a new is generated.
     * @param x The tile's x position
     * @param y The tile's y position
     * @return A map chunk
     */
    public MapChunk getChunkByTile(int x, int y) {
        Pair<Integer, Integer> chunkIndex = getChunkIndexAt(x, y);
        
        return getChunk( chunkIndex.x, chunkIndex.y );
    }
    
    /**
     * Returns a chunk for a given world x/y position. If no chunk was found a new is generated.
     * @param x The world's x position
     * @param y The world's y position
     * @return A map chunk
     */
    public MapChunk getChunkByWorldPosition(float x, float y) {
        Pair<Integer, Integer> chunkIndex = getChunkIndexAt(x, y);
        
        return getChunk( chunkIndex.x, chunkIndex.y );
    }

    
    /**
     * Sets the surface of the tile with position x/y.
     * @param tileX The tile's x position
     * @param tileY The tile's y position
     * @param surface The new surface
     */
    public void setTileSurface(int tileX, int tileY, Surface surface) {       
        Pair<Integer, Integer> localTileIndex = getLocalTileIndex(tileX, tileY);

        getChunkByTile(tileX, tileY).setTileSurface( localTileIndex.x, localTileIndex.y, surface);
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private MapChunkGenerator _mapChunkGenerator;
    private Map<Pair<Integer,Integer>, MapChunk> _chunks;

}
