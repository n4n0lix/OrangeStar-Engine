package de.orangestar.engine.render.actor.tilemap;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.list.NodeCachingLinkedList;

import de.orangestar.engine.render.Batch;
import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.render.actor.Actor;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.utils.ArrayUtils;
import de.orangestar.engine.utils.SimplePool;
import de.orangestar.engine.values.Anchor;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Vector2f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vertex;

/**
 * A tilemap with optional alpha-blending usage to create transitions between
 * different surfaces.
 * 
 * @author Oliver &amp; Basti
 */
public class TileMap extends Actor {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Public Constructor
     * @param tileset The tileset used by this tilemap
     * @param mapTileWidth The width of the tilemap in world-units
     * @param mapTileHeight The height of the tilemap in world-units
     */
    public TileMap(TileSet tileset, float mapTileWidth, float mapTileHeight) {
        _tileset = tileset;
        _tileWidth  = mapTileWidth;
        _tileHeight = mapTileHeight;

        Material material = new Material.Builder()
                                            .texture(_tileset.getTexture())
                                            .shader(Shader.GodShader)
                                            .build();
        
        _batch = new Batch.Builder()
                            .material(material)
                            .maxInstances(0)
                            .build();
        
        setAnchor(Anchor.TOP_LEFT);
    }

    /**
     * Sets the tile data of this tilemap.
     * @param data The data
     */
    public void setData(Tile[][] data) {
        generateGeometry(data);
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                             Protected                              */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
    public void onRelease() {
        _batch.onDeinitialize();
    }
    
    @Override
    public void onRender(IRenderEngine engine) {
        _batch.render(engine, PrimitiveType.TRIANGLES);
    }

    @Override
    public float getWidth() {
        return _tileWidth * _genData.length;
    }

    @Override
    public float getHeight() {
        return _tileHeight * _genData[0].length;
    }

    @Override
    public float getDepth() {
        return 0.0f;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Generates the vertices for the given tile array.
     * @param data The tile data
     */
    private void generateGeometry(Tile[][] data) {
        if (!isValidData(data)) {
            return;
        }

        _batch.clear();
        
        // 1# Prepare vertex generation
        _genData          = data;
        _genTransitions   = true;
        _genVertexCounter = 0;
        _genVertices = VertexListPool.get();
        _genIndices  = IndexListPool.get();

        // 2# Generate vertices
        generateSolidTiles();

        if (_genTransitions) {
            generateTransitionTiles();
        }

        // 3# Put vertices into the batch
        _batch.addVertexData(_genVertices);

        for(int i = 0; i < _genIndices.size(); i++) {
            _batch.addIndicesData(_genIndices.get(i));
        }
        IndexListPool.release(_genIndices);
        VertexListPool.release(_genVertices);
    }
    
    /**
     * Check if the given tile data array is valid.
     * @param data The tile data
     * @return If the given tile data array is valid
     */
    private boolean isValidData(Tile[][] data) {
        if (_tileset == null ) {
            return false;
        }
        
        if (data == null) {
            return false;
        }
        
        if (data.length == 0 || data[0].length == 0) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Generates the tiles for the solid ground layer.
     */
    private void generateSolidTiles() {
        int tileId;
        float tileX;
        float tileY;
        
        for (int x = 0; x < _genData.length; x++) {
            for (int y = 0; y < _genData[0].length; y++) {
                tileId = _genData[x][y]._id;
                tileX  = x * _tileWidth;
                tileY  = y * _tileHeight;
                
                generate4Quad(tileId, tileX, tileY);
            }
        }
    }
    
    /**
     * Generates the tiles for the surface transition layer.
     */
    private void generateTransitionTiles() {
        
        // Get all used surfaces and sort them for rendering order
        List<Surface> usedSurfaces = _tileset.getSurfaces();
        
        // Generate transition tiles
        for(Surface surface : usedSurfaces) {
            if (surface.isSolid()) {
                continue;
            }
            
            Quad9AlphaValues[][] alphaCache = generateAlphaValuesForSurface(surface);

            int tileId;
            float tileX;
            float tileY;
            
            for (int x = 0; x < _genData.length; x++) {
                for (int y = 0; y < _genData[0].length; y++) {
                    
                    if (alphaCache[x][y] == null) {
                        continue;
                    }

                    // Generate vertices
                    tileId = surface.getTileIds()[0];
                    tileX  = x * _tileWidth;
                    tileY  = y * _tileHeight;

                    generate9Quad(tileId, tileX, tileY, alphaCache[x][y].getTileAlphaValues());
                }
            }
        }
    }
    
    /**
     * Generates a quad with 4 vertices according to the tileset and tilemap configuration.
     * @param tileId The tileset tile id
     * @param x The top left x position
     * @param y The top left y position
     */
    private void generate4Quad(int tileId, float x, float y) {
        int tileIdX = tileId % _tileset.getTilesPerColumn();
        int tileIdY = tileId / _tileset.getTilesPerRow();
        
        // Resize the texcoord rectangle to prevent texture/tile clamp issues
        float fixedTexCoordWidth  = _tileset.getTileTexcoordWidth() - 0.002f;
        float fixedTexCoordHeight = _tileset.getTileTexcoordHeight() - 0.002f;
        
        float fixedTexcoordX      = tileIdX * _tileset.getTileTexcoordWidth() + 0.001f;
        float fixedTexcoordY      = tileIdY * _tileset.getTileTexcoordHeight() + 0.001f;

        // Vertex - TopLeft
        Vertex topLeft = Vertex.POOL.get();
        Vector3f.set(topLeft.position, x, y, 0.0f);
        Color4f.set(topLeft.color, 0.5f, 0.5f, 0.5f, 1.0f);
        Vector2f.set(topLeft.texCoord, fixedTexcoordX, fixedTexcoordY);
        
        // Vertex - BotLeft
        Vertex botLeft = Vertex.POOL.get();
        Vector3f.set(botLeft.position, x, y + _tileHeight, 0.0f);
        Color4f.set(botLeft.color, 0.5f, 0.5f, 0.5f, 1.0f);
        Vector2f.set(botLeft.texCoord, fixedTexcoordX, fixedTexcoordY + fixedTexCoordHeight);
        
        // Vertex - TopRight
        Vertex topRight = Vertex.POOL.get();
        Vector3f.set(topRight.position, x + _tileWidth, y, 0.0f);
        Color4f.set(topRight.color, 0.5f, 0.5f, 0.5f, 1.0f);
        Vector2f.set(topRight.texCoord, fixedTexcoordX + fixedTexCoordWidth, fixedTexcoordY);
        
        // Vertex - BotRight
        Vertex botRight = Vertex.POOL.get();
        Vector3f.set(botRight.position, x + _tileWidth, y + _tileHeight, 0.0f);
        Color4f.set(botRight.color, 0.5f, 0.5f, 0.5f, 1.0f);
        Vector2f.set(botRight.texCoord, fixedTexcoordX + fixedTexCoordWidth, fixedTexcoordY + fixedTexCoordHeight);
        
        Vertex[] vertices = new Vertex[] {
                topLeft,
                botLeft,
                topRight,  
                botRight,
          };

        int[] indices = new int[] {
                _genVertexCounter + 0, _genVertexCounter + 1, _genVertexCounter + 2, 
                _genVertexCounter + 2, _genVertexCounter + 1, _genVertexCounter + 3
        };

        _genVertexCounter += vertices.length;
        _genVertices.addAll(Arrays.asList(vertices));
        _genIndices.add(indices);
    }  

    /**
     * Generates a quad with 9 vertices and alpha values, according to the tileset and tilemap configuration.
     * @param tileId The tileset tile id
     * @param x The top left x position
     * @param y The top left y position
     * @param alphaValues The alphavalues for each vertex, starting from left to right and from top to bottom
     */
    private void generate9Quad(int tileId, float x, float y, float[] alphaValues) {

        int tileIdX = tileId % _tileset.getTilesPerColumn();
        int tileIdY = tileId / _tileset.getTilesPerRow();
        
        float texcoordWidth = _tileset.getTileTexcoordWidth();
        float texcoordHeight = _tileset.getTileTexcoordHeight();
        float texcoordX = tileIdX * texcoordWidth;
        float texcoordY = tileIdY * texcoordHeight;
        
        // Setup positions
        float width_2   = _tileWidth * 0.5f;
        float height_2  = _tileHeight * 0.5f;

        float xTopLeft  = x,                yTopLeft    = y;
        float xTop      = x + width_2,      yTop        = y;
        float xTopRight = x + _tileWidth,   yTopRight   = y;

        float xMidLeft  = x,                yMidLeft    = y + height_2;
        float xMid      = x + width_2,      yMid        = y + height_2;
        float xMidRight = x + _tileWidth,   yMidRight   = y + height_2;

        float xBotLeft  = x,                yBotLeft    = y + _tileHeight;
        float xBot      = x + width_2,      yBot        = y + _tileHeight;
        float xBotRight = x + _tileWidth,   yBotRight   = y + _tileHeight;

        // Setup uv
        float texcoordWidth_2    = texcoordWidth * 0.5f;
        float texcoordHeight_2   = texcoordHeight * 0.5f;
        
       // TopLeft - Index: 0
        Vertex vTopLeft = Vertex.POOL.get();
        Vector3f.set(vTopLeft.position, xTopLeft, yTopLeft, 0.0f);
        Color4f.set(vTopLeft.color, 0.5f, 0.5f, 0.5f, alphaValues[0]);
        Vector2f.set(vTopLeft.texCoord, texcoordX, texcoordY );

        // Top - Index: 1
        Vertex vTop = Vertex.POOL.get();
        Vector3f.set(vTop.position, xTop, yTop, 0.0f);
        Color4f.set(vTop.color, 0.5f, 0.5f, 0.5f, alphaValues[1]);
        Vector2f.set(vTop.texCoord, texcoordX + texcoordWidth_2, texcoordY );
        
        // TopRight - Index: 2
        Vertex vTopRight = Vertex.POOL.get();
        Vector3f.set(vTopRight.position, xTopRight, yTopRight, 0.0f);
        Color4f.set(vTopRight.color, 0.5f, 0.5f, 0.5f, alphaValues[2]);
        Vector2f.set(vTopRight.texCoord, texcoordX + texcoordWidth,  texcoordY);

        // MidLeft - Index: 3
        Vertex vMidLeft = Vertex.POOL.get();
        Vector3f.set(vMidLeft.position, xMidLeft, yMidLeft, 0.0f);
        Color4f.set(vMidLeft.color, 0.5f, 0.5f, 0.5f, alphaValues[3]);
        Vector2f.set(vMidLeft.texCoord, texcoordX, texcoordY + texcoordHeight_2);
        
        // Mid - Index: 4
        Vertex vMid = Vertex.POOL.get();
        Vector3f.set(vMid.position, xMid, yMid, 0.0f);
        Color4f.set(vMid.color, 0.5f, 0.5f, 0.5f, alphaValues[4]);
        Vector2f.set(vMid.texCoord, texcoordX + texcoordWidth_2, texcoordY + texcoordHeight_2);
        
        // MidRight - Index: 5
        Vertex vMidRight = Vertex.POOL.get();
        Vector3f.set(vMidRight.position, xMidRight, yMidRight, 0.0f);
        Color4f.set(vMidRight.color, 0.5f, 0.5f, 0.5f, alphaValues[5]);
        Vector2f.set(vMidRight.texCoord, texcoordX + texcoordWidth, texcoordY + texcoordHeight_2);

        // BotLeft - Index: 6
        Vertex vBotLeft = Vertex.POOL.get();
        Vector3f.set(vBotLeft.position, xBotLeft, yBotLeft, 0.0f);
        Color4f.set(vBotLeft.color, 0.5f, 0.5f, 0.5f, alphaValues[6]);
        Vector2f.set(vBotLeft.texCoord, texcoordX, texcoordY + texcoordHeight);
        
        // Bot - Index: 7
        Vertex vBot = Vertex.POOL.get();
        Vector3f.set(vBot.position, xBot, yBot, 0.0f);
        Color4f.set(vBot.color, 0.5f, 0.5f, 0.5f, alphaValues[7]);
        Vector2f.set(vBot.texCoord, texcoordX + texcoordWidth_2, texcoordY + texcoordHeight);

        // BotRight - Index: 8
        Vertex vBotRight = Vertex.POOL.get();
        Vector3f.set(vBotRight.position, xBotRight, yBotRight, 0.0f);
        Color4f.set(vBotRight.color, 0.5f, 0.5f, 0.5f, alphaValues[8]);
        Vector2f.set(vBotRight.texCoord, texcoordX + texcoordWidth,  texcoordY + texcoordHeight);

      Vertex[] vertices = new Vertex[] {
                  vTopLeft, vTop, vTopRight,
                  vMidLeft, vMid, vMidRight,
                  vBotLeft, vBot, vBotRight
                };
      
      int[] indices = new int[] {
              _genVertexCounter + 0, _genVertexCounter + 3, _genVertexCounter + 1,
              _genVertexCounter + 1, _genVertexCounter + 3, _genVertexCounter + 4,
              _genVertexCounter + 4, _genVertexCounter + 5, _genVertexCounter + 1,
              _genVertexCounter + 1, _genVertexCounter + 5, _genVertexCounter + 2,
              _genVertexCounter + 4, _genVertexCounter + 3, _genVertexCounter + 7,
              _genVertexCounter + 3, _genVertexCounter + 6, _genVertexCounter + 7,
              _genVertexCounter + 4, _genVertexCounter + 7, _genVertexCounter + 5,
              _genVertexCounter + 7, _genVertexCounter + 8, _genVertexCounter + 5
      };
      
      _genVertexCounter += vertices.length;

      _genVertices.addAll(Arrays.asList(vertices));
      _genIndices.add(indices);
    }
    
    /**
     * Generates an array of alphavalues for quads with 9 vertices. This is used to determine
     * which vertices of a transition tile are transparent or visible.
     * @param surface The surface
     * @return An array of alphavalues
     */
    private Quad9AlphaValues[][] generateAlphaValuesForSurface(Surface surface) {
        Quad9AlphaValues[][] alphaCache = new Quad9AlphaValues[_genData.length][_genData[0].length];
        
        for(int x = 0; x < _genData.length; x++) {
            for(int y = 0; y < _genData[0].length; y++) {

                Surface curSurface = _genData[x][y]._surface;
                if (surface != curSurface) {
                    continue;
                }
          
                Surface[][] neighbors = getNeighborSurfaces(x, y);                      

                // Top-Left
                // Only generate alpha values for other surfaces that lay below the current surface
                if (neighbors[0][0] != curSurface && neighbors[0][0].getLayer() < curSurface.getLayer()) {

                    // If no Quad9AlphaValues instance was created for this tile yet, create one
                    if (alphaCache[x-1][y-1] == null) {
                        alphaCache[x-1][y-1] = new Quad9AlphaValues();
                    }
                    
                    // 'or' the alpha values for the tile top left of the current tile, so that transparency 
                    //  can accumulate and is not overwritten
                    alphaCache[x-1][y-1].orAlphaValues(
                            new boolean[] {
                                           false, false, false,
                                           false, false, false,
                                           false, false,  true
                                          });
                }
                
                // Top
                if (neighbors[1][0] != curSurface && neighbors[1][0].getLayer() < curSurface.getLayer()) {

                    if (alphaCache[x][y-1] == null) {
                        alphaCache[x][y-1] = new Quad9AlphaValues();
                    }
                    
                    alphaCache[x][y-1].orAlphaValues(
                            new boolean[] { 
                                           false, false, false,
                                           false, false, false,
                                           true,  true,  true
                                          });
                }
                
                // Top-Right
                if (neighbors[2][0] != curSurface && neighbors[2][0].getLayer() < curSurface.getLayer()) {

                    if (alphaCache[x+1][y-1] == null) {
                        alphaCache[x+1][y-1] = new Quad9AlphaValues();
                    }
                    
                    alphaCache[x+1][y-1].orAlphaValues(
                            new boolean[] { 
                                           false, false, false,
                                           false, false, false,
                                           true,  false, false
                                          });
                }
                
                // Mid-Left
                if (neighbors[0][1] != curSurface && neighbors[0][1].getLayer() < curSurface.getLayer()) {

                    if (alphaCache[x-1][y] == null) {
                        alphaCache[x-1][y] = new Quad9AlphaValues();
                    }
                    
                    alphaCache[x-1][y].orAlphaValues(
                            new boolean[] { 
                                           false, false, true,
                                           false, false, true,
                                           false, false, true
                                          });
                }
                                    
                // Mid-Right
                if (neighbors[2][1] != curSurface && neighbors[2][1].getLayer() < curSurface.getLayer()) {

                    if (alphaCache[x+1][y] == null) {
                        alphaCache[x+1][y] = new Quad9AlphaValues();
                    }
                    
                    alphaCache[x+1][y].orAlphaValues(
                            new boolean[] { 
                                           true, false, false,
                                           true, false, false,
                                           true, false, false
                                          });
                }
                
                // Bot-Left
                if (neighbors[0][2] != curSurface && neighbors[0][2].getLayer() < curSurface.getLayer()) {

                    if (alphaCache[x-1][y+1] == null) {
                        alphaCache[x-1][y+1] = new Quad9AlphaValues();
                    }
                    
                    alphaCache[x-1][y+1].orAlphaValues(
                            new boolean[] { 
                                           false, false, true,
                                           false, false, false, 
                                           false, false, false
                                          });
                }
                
                // Bot
                if (neighbors[1][2] != curSurface && neighbors[1][2].getLayer() < curSurface.getLayer()) {

                    if (alphaCache[x][y+1] == null) {
                        alphaCache[x][y+1] = new Quad9AlphaValues();
                    }
                    
                    alphaCache[x][y+1].orAlphaValues(
                            new boolean[] { 
                                           true,  true,  true,
                                           false, false, false,
                                           false, false, false
                                          });
                }
                
                // Bot-Right
                if (neighbors[2][2] != curSurface && neighbors[2][2].getLayer() < curSurface.getLayer()) {

                    if (alphaCache[x+1][y+1] == null) {
                        alphaCache[x+1][y+1] = new Quad9AlphaValues();
                    }
                    
                    alphaCache[x+1][y+1].orAlphaValues(
                            new boolean[] { 
                                           true,  false, false,
                                           false, false, false,
                                           false, false, false
                                          });
                }
            }
        }
        
        return alphaCache;
    }
    
    /**
     * Returns the surfaces of the neighboring tiles, of the tile at [x][y] in the array.
     * @param x The x'th Tile
     * @param y The y'th Tile
     * @return A 3x3 array with surfaces. The surface at [1][1] is the surface of the tile itself
     */
    private Surface[][] getNeighborSurfaces(int x, int y) {
        
        Surface[][] surfaces = new Surface[3][3];
                
        // TOP-LEFT
        if (ArrayUtils.inBounds(_genData, x-1, y-1)) {
            surfaces[0][0] = _genData[x-1][y-1]._surface;
        } else {
            surfaces[0][0] = _genData[x][y]._surface;
        }
        
        // TOP
        if (ArrayUtils.inBounds(_genData, x, y-1)) {
            surfaces[1][0] = _genData[x][y-1]._surface;
        } else {
            surfaces[1][0] = _genData[x][y]._surface;
        }
        
        // TOP-RIGHT
        if (ArrayUtils.inBounds(_genData, x+1, y-1)) {
            surfaces[2][0] = _genData[x+1][y-1]._surface;
        } else {
            surfaces[2][0] = _genData[x][y]._surface;
        }
        
        // MID-LEFT
        if (ArrayUtils.inBounds(_genData, x-1, y)) {
            surfaces[0][1] = _genData[x-1][y]._surface;
        } else {
            surfaces[0][1] = _genData[x][y]._surface;
        }
        
        surfaces[1][1] = _genData[x][y]._surface;
                            
        // MID-RIGHT
        if (ArrayUtils.inBounds(_genData, x+1, y)) {
            surfaces[2][1] = _genData[x+1][y]._surface;
        } else {
            surfaces[2][1] = _genData[x][y]._surface;
        }
        
        // BOT-LEFT
        if (ArrayUtils.inBounds(_genData, x-1, y+1)) {
            surfaces[0][2] = _genData[x-1][y+1]._surface;
        } else {
            surfaces[0][2] = _genData[x][y]._surface;
        }
        
        // BOT
        if (ArrayUtils.inBounds(_genData, x, y+1)) {
            surfaces[1][2] = _genData[x][y+1]._surface;
        } else {
            surfaces[1][2] = _genData[x][y]._surface;
        }
        
        // BOT-RIGHT
        if (ArrayUtils.inBounds(_genData, x+1, y+1)) {
            surfaces[2][2] = _genData[x+1][y+1]._surface;
        } else {
            surfaces[2][2] = _genData[x][y]._surface;
        }
        
        return surfaces;
    }
    
    private Batch       _batch;
    
    private TileSet     _tileset;

    private float       _tileWidth;
    private float       _tileHeight;
    
    // Generation-only variables, only used during vertex generation
    private Tile[][]                        _genData;
    private boolean                         _genTransitions;
    private int                             _genVertexCounter;
    private NodeCachingLinkedList<Vertex>   _genVertices;
    private NodeCachingLinkedList<int[]>    _genIndices;

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Represents the alpha values for each vertex in a quad that consists of 9 vertices.
     * 
     * @author Basti
     */
    private static class Quad9AlphaValues {

        /**
         * Or's the current values with the parameter values.
         * @param values A new set of alpha values
         */
        public void orAlphaValues(boolean[] values) {
            _alphaValues[0] = _alphaValues[0] || values[0]; // Top Left
            _alphaValues[1] = _alphaValues[1] || values[1]; // Top 
            _alphaValues[2] = _alphaValues[2] || values[2]; // Top Right
            _alphaValues[3] = _alphaValues[3] || values[3]; // Mid Left
            _alphaValues[4] = _alphaValues[4] || values[4]; // Mid 
            _alphaValues[5] = _alphaValues[5] || values[5]; // Mid Right
            _alphaValues[6] = _alphaValues[6] || values[6]; // Bot Left
            _alphaValues[7] = _alphaValues[7] || values[7]; // Bot 
            _alphaValues[8] = _alphaValues[8] || values[8]; // Bot Right
        }
    
        /**
         * Returns the alpha values.
         * @return An array of 0.0f-1.0f values, representing the alpha values
         */
        public float[] getTileAlphaValues() {
            return new float[] {
                    _alphaValues[0] ? 1.0f : 0.0f, // Top Left
                    _alphaValues[1] ? 1.0f : 0.0f, // Top
                    _alphaValues[2] ? 1.0f : 0.0f, // Top Right
                    _alphaValues[3] ? 1.0f : 0.0f, // Mid Left
                    _alphaValues[4] ? 1.0f : 0.0f, // Mid 
                    _alphaValues[5] ? 1.0f : 0.0f, // Mid Right
                    _alphaValues[6] ? 1.0f : 0.0f, // Bot Left
                    _alphaValues[7] ? 1.0f : 0.0f, // Bot
                    _alphaValues[8] ? 1.0f : 0.0f, // Bot Right
            };
        }
        
        
        private boolean [] _alphaValues = new boolean[] { false, false, false,
                                                          false, false, false, 
                                                          false, false, false };

    }

    /**
     * A pool of 'NodeCachingLinkedList<Vertex>' instances, which are heavily used in the geometry generation for vertices.
     */
    private static SimplePool<NodeCachingLinkedList<Vertex>> VertexListPool = new SimplePool<NodeCachingLinkedList<Vertex>>() {

        @Override
        protected NodeCachingLinkedList<Vertex> newInstance() {
            return new NodeCachingLinkedList<Vertex>();
        }

        @Override
        protected void resetInstance(NodeCachingLinkedList<Vertex> t) {
            t.clear();
        }

        @Override
        protected void cleanUp(NodeCachingLinkedList<Vertex> t) { }

    };
    
    /**
     * A pool of 'NodeCachingLinkedList<int[]>' instances, which are heavily used in the geometry generation for vertex indices.
     */
    private static SimplePool<NodeCachingLinkedList<int[]>> IndexListPool = new SimplePool<NodeCachingLinkedList<int[]>>() {

        @Override
        protected NodeCachingLinkedList<int[]> newInstance() {
            return new NodeCachingLinkedList<int[]>();
        }

        @Override
        protected void resetInstance(NodeCachingLinkedList<int[]> t) {
            t.clear();
        }

        @Override
        protected void cleanUp(NodeCachingLinkedList<int[]> t) { }

    };

}
