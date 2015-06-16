package de.orangestar.game.gameobjects.map;

import de.orangestar.engine.debug.DebugManager;

/**
 * Represents a "chunk" of the map.
 * 
 * @author Basti
 */
public class MapChunk {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * The chunk size (width and height)
     */
    public static final int CHUNK_SIZE = 32;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor
     */
    public MapChunk(int x, int y) {
        _x = x;
        _y = y;
        _data = new MapSurface[CHUNK_SIZE][CHUNK_SIZE];
    }
    
    /**
     * The chunks x position.
     */
    public int getX() {
        return _x;
    }

    /**
     * The chunks y position.
     */
    public int getY() {
        return _y;
    }
    
    /**
     * Returns the map chunk's data.
     */
    public MapSurface[][] getData() {
        return _data;
    }
    
    /**
     * Sets the data of this chunk.
     * @param data The data
     */
    public void setData(MapSurface[][] data) {
        // 
        if (data.length != CHUNK_SIZE || data[0].length != CHUNK_SIZE) {
            DebugManager.Get().info(MapSurface.class, "Chunk data has wrong dimensions!");
            return;
        }
        
        _data = data;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private int _x;
    private int _y;
    
    private MapSurface[][] _data;
    
}