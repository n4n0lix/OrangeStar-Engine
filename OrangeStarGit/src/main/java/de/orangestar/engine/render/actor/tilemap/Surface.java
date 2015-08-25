package de.orangestar.engine.render.actor.tilemap;

/**
 * A tilemap surface.
 * 
 * @author Oliver &amp; Basti
 */
public interface Surface {

    /**
     * Returns all tileset tile id's of this surface.
     * @return All tileset tile id's of this surface
     */
    public int[]    getTileIds();

    /**
     * Returns the layer of this surface.
     * @return the layer of this surface
     */
    public int      getLayer();

    /**
     * If this surface is traversable for ai units.
     * @return If this surface is traversable for ai units
     */
    public boolean  isTraversable();

    /**
     * If this surface is solid and therefore no transition tiles are rendered.
     * @return If this surface is solid and therefore no transition tiles are rendered
     */
    public boolean  isSolid();

}