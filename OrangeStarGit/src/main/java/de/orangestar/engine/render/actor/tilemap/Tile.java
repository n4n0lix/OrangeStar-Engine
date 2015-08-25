package de.orangestar.engine.render.actor.tilemap;

/**
 * The logic instance of a tile, displayed by a {@link TileMap}.
 * 
 * @author Oliver &amp; Basti
 */
public class Tile {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Returns the tileset tile id
     * @return The tileset tile id
     */
    public int getId() {
        return _id;
    }
    
    /**
     * Sets the tileset tile id
     * @param id The tileset tile id
     */
    public void setId(int id) {
        _id = id;
    }
    
    /**
     * Returns the surface
     * @return A surface
     */
    public Surface getSurface() {
        return _surface;
    }
    
    /**
     * Sets the surface
     * @param surface The surface
     */
    public void setSurface(Surface surface) {
        _surface = surface;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                             Package                                */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    int     _id;

    Surface _surface;
    
}