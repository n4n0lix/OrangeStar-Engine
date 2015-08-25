package de.orangestar.game.gameobjects.map;

import java.util.ArrayList;
import java.util.Random;

import de.orangestar.engine.debug.Logger;
import de.orangestar.engine.render.actor.tilemap.Surface;
import de.orangestar.engine.render.actor.tilemap.Tile;

/**
 * Represents a "chunk" of the map.
 * 
 * @author Oliver &amp; Basti
 */
public class MapChunk {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
	/**
	 * A listener for chunk data changes.
	 * 
	 * @author Oliver &amp; Basti
	 */
    public static interface ChangeListener {
        void onChunkDataChanged(int srcX, int srcY);
    }
    
    /**
     * The chunk size (width and height)
     */
    public static final int     CHUNK_SIZE = 16;
    
    public static final float   TILE_SIZE  = 16;

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor.
     * @param x The x of the chunk
     * @param y The y of the chunk
     */
    public MapChunk(int x, int y) {
        _x = x;
        _y = y;
        _data = new Tile[CHUNK_SIZE][CHUNK_SIZE];
        _listener = new ArrayList<>();
    }
    
    /**
     * Returns the x of the map chunk. (The x'th chunk)
     * @return The x of this chunk
     */
    public int getX() {
        return _x;
    }
    
    /**
     * Returns the y of the map chunk. (The y'th chunk)
     * @return The y of this chunk
     */
    public int getY() {
        return _y;
    }

    /**
     * Returns the map chunk's data.
     * @return The tile data
     */
    public Tile[][] getData() {
        return _data;
    }
    
    /**
     * Sets the data of this chunk.
     * @param data The data
     */
    public void setTileData(Tile[][] data) {
        if (data.length != CHUNK_SIZE || data[0].length != CHUNK_SIZE) {
            Logger.warn(MapChunk.class, "Chunk data has wrong dimensions!");
            return;
        }
        
        _data = data;
        notifyMapSurfaceChange();
    }
    
    /**
     * Changes the MapSurface of a single tile.
     * @param x The x'th tile of the chunk.
     * @param y The y'th tile of the chunk.
     * @param surface The surface
     */
    public void setTileSurface(int x, int y, Surface surface) {
        if (x < 0 || x >= MapChunk.CHUNK_SIZE || y < 0 || y >= MapChunk.CHUNK_SIZE) {
            Logger.info(MapChunk.class, "Tile surface couldn't be set, tile coords are wrong (" + x + "/" + y + ")!");
            return;
        }
        
        _data[x][y] = generateRandomTile(surface);
        notifyMapSurfaceChange();
    }
        
    /**
     * Returns the pathfinding data.
     * @return The pathfinding data
     */
    public boolean[][] getPathfindingData() {
    	boolean[][] scannedArea = new boolean[_data.length][_data[0].length];
    	
    	for(int i = 0; i < _data.length; i++) {
    		for(int j = 0; j < _data[i].length; j++) {
    		    scannedArea[i][j] = _data[i][j].getSurface().isTraversable();
    		}
    	}
    	
    	return scannedArea;
    }
    
    /**
     * Adds a {@link ChangeListener} to this map chunk.
     * @param listener The listener
     */
    public void addChangeListener(ChangeListener listener) {
        _listener.add(listener);
    }
    
    /**
     * Adds a {@link ChangeListener} to this map chunk.
     * @param listener The listener
     */
    public void removeChangeListener(ChangeListener listener) {
        _listener.remove(listener);
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Notifies all {@link ChangeListener}
     */
    private void notifyMapSurfaceChange() {
        for(ChangeListener listener : _listener) {
            listener.onChunkDataChanged(getX(), getY());
        }
    }
    
    /** This is the x'th chunk. */
    private int _x;
    
    /** This is the y'th chunk. */
    private int _y;
    
    private Tile[][] _data;
    private ArrayList<ChangeListener> _listener;
       
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Generates a random Tile.
     * 
     * @param surface Surface the Tile should have
     * @return the Tile
     */
    public static Tile generateRandomTile(Surface surface) {
        Tile result = new Tile();
        
        int rndId = Rnd.nextInt(surface.getTileIds().length);
        
        result.setId(surface.getTileIds()[rndId]);
        result.setSurface(surface);
        
        return result;
    }
    
    private static Random Rnd = new Random();
}