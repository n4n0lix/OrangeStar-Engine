package de.orangestar.engine.render.actor.tilemap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.orangestar.engine.render.Texture;

/**
 * A tileset consists of a texture with subimages, each equal in size. Used by {@link TileMap} for rendering.
 * 
 * @author Oliver &amp; Basti
 */
public class TileSet {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Tileset-Builder class.
     * 
     * @author Basti
     */
    public static class Builder {
        
        public Builder texture(Texture texture) {
            _texture = texture;
            return this;
        }
        
        public Builder tilesize(int width, int height) {
            _tileWidth = width;
            _tileHeight = height;
            return this;
        }
        
        public Builder surfaces(Surface... surfaces) {
            _surfaces.addAll(Arrays.asList(surfaces));
            return this;
        }
        
        /**
         * Builds a tileset instance by the set builder configuration.<br>
         * Preconditions: 
         * <ul>
         *  <li><code>texture != null</code>
         *  <li><code>tileWidth &gt; 0</code>
         *  <li><code>tileHeight &gt;= 0</code>
         *  <li><code>!surfaces.isEmpty()</code>
         * </ul>
         * @return A tileset instance
         */
        public TileSet build() {
            // Preconditions:
            assert _texture != null;
            assert _tileWidth  > 0;
            assert _tileHeight > 0;
            assert !_surfaces.isEmpty();

            // Code:
            return new TileSet(_texture, _surfaces, _tileWidth, _tileHeight);
        }
        
        private List<Surface> _surfaces = new ArrayList<>();
        
        private Texture _texture;
        private int     _tileWidth;
        private int     _tileHeight;
        
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Returns the texture.
     * @return The texture
     */
    public Texture getTexture() {
        return _texture;
    }
    
    /**
     * Returns all surfaces of this tileset sorted by its layer.
     * @return All surfaces of this tileset sorted by its layer
     */
    public List<Surface> getSurfaces() {
        return _readOnlySurfaces;
    }

    /**
     * Returns the tile width in pixels.
     * @return The tile width in pixels
     */
    public int getTileWidth() {
        return _tileWidth;
    }

    /**
     * Returns the tile height in pixels.
     * @return The tile height in pixels
     */
    public int getTileHeight() {
        return _tileHeight;
    }
    
    /**
     * Returns the amount of tiles per column
     * @return The amount of tiles per column
     */
    public int getTilesPerColumn() {
        return _tilesPerColumn;
    }

    /**
     * Returns the amount of tiles per row
     * @return The amount of tiles per row
     */
    public int getTilesPerRow() {
        return _tilesPerRow;
    }
    
    /**
     * Returns the texcoord width of a tile.
     * @return the texcoord width of a tile
     */
    public float getTileTexcoordWidth() {
        return _tileTexcoordWidth;
    }
    
    /**
     * Returns the texcoord height of a tile.
     * @return the texcoord height of a tile
     */
    public float getTileTexcoordHeight() {
        return _tileTexcoordHeight;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Private Constructor
     * @param texture The texture
     * @param surfaces All surfaces associated with this surface
     * @param tileWidth The width of a tile in pixels
     * @param tileHeight The height of a tile in pixels
     */
    private TileSet(Texture texture, List<Surface> surfaces, int tileWidth, int tileHeight) {
        _texture    = texture;
        _tileWidth  = tileWidth;
        _tileHeight = tileHeight;
        
        _tileTexcoordWidth  = 1f / _texture.getWidth()  * _tileWidth;
        _tileTexcoordHeight = 1f / _texture.getHeight() * _tileHeight;
        
        _tilesPerColumn = _texture.getHeight() / _tileHeight;
        _tilesPerRow    = _texture.getWidth()  / _tileWidth;
        
        Collections.sort(surfaces, SurfaceLayerSorter);
        _readOnlySurfaces = Collections.unmodifiableList(new ArrayList<>(surfaces));
    }

    private Texture _texture;
    
    private List<Surface> _readOnlySurfaces;
    
    private int     _tileWidth;
    private int     _tileHeight;
    
    private int     _tilesPerColumn;
    private int     _tilesPerRow;
    
    private float   _tileTexcoordWidth;
    private float   _tileTexcoordHeight;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Sorts surfaces according to their layers.
     */
    private static Comparator<Surface> SurfaceLayerSorter = new Comparator<Surface>() {
        @Override
        public int compare(Surface o1, Surface o2) {
            return Integer.compare(o1.getLayer(), o2.getLayer());
        }
    };
    
}
