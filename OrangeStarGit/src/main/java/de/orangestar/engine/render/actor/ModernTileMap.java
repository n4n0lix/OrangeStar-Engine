package de.orangestar.engine.render.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.batch.StreamingBatch;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.utils.Pair;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Vector2f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vertex;

/**
 * A modern tileset with alpha-blending usage to create transitions between
 * different surfaces.
 * 
 * @author Basti
 *
 */
public class ModernTileMap extends Actor {

    /**
     * Used to sort surfaces by its layer.
     * 
     * @author Basti
     */
    public static class SurfaceComparator implements Comparator<Surface> {
        @Override
        public int compare(Surface o1, Surface o2) {
            return Integer.compare(o1._layer, o2._layer);
        }
    }
    
    public static class Surface {

        private static Random RND = new Random();
        
        public Surface() {
            _solid = true;
            _layer = 0;
            _tileIds = new ArrayList<Integer>();
        }

        public boolean isSolid() {
            return _solid;
        }

        public Surface solid() {
            this._solid = true;
            return this;
        }
        
        public Surface nonsolid() {
            this._solid = false;
            return this;
        }

        public int getLayer() {
            return _layer;
        }
        
        public Surface layer(int layer) {
            this._layer = layer;
            return this;
        }
        
        public Integer getRandomTileId() {
            return _tileIds.get(RND.nextInt(_tileIds.size()-1));
        }
        
        public List<Integer> getTileIds() {
            return Collections.unmodifiableList(_tileIds);
        }

        public Surface tileIds(Integer... tileIds) {
            this._tileIds.addAll(Arrays.asList(tileIds));
            return this;
        }

        private boolean       _solid;
        private int           _layer;
        private List<Integer> _tileIds;
        
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public ModernTileMap(Texture texture, int textureTileWidth, int textureTileHeight) {
        this(texture, textureTileWidth, textureTileHeight, textureTileWidth, textureTileHeight);
    }
    
    public ModernTileMap(Texture texture, int textureTileWidth, int textureTileHeight, float tileWidth, float tileHeight) {
        Material material = new Material.Builder()
                                            .texture(texture)
                                            .shader(Shader.StreamingBatchShader)
                                            .build();
        
        _batch = new StreamingBatch.Builder().material(material).build();

        _textureWidth  = _batch.getMaterial().getTexture().getWidth();
        _textureHeight = _batch.getMaterial().getTexture().getHeight();
        
        _tileWidth = tileWidth;
        _tileHeight = tileHeight;
        
        setTextureTileSize(textureTileWidth, textureTileHeight);
    }
    
    public void setTextureTileSize(int width, int height) {
        _textureTileWidth = width;
        _textureTileHeight = height;
        
        // Calculate number of tiles per row and per column
        _tilesPerRow    = _textureWidth  / _textureTileWidth;
        _tilesPerColumn = _textureHeight / _textureTileWidth;
        
        // Calculate the texcoord size per tile
        _texcoordWidth = 1f / _textureWidth  * _textureTileWidth;
        _texcoordHeight = 1f / _textureHeight * _textureTileWidth;
        
        // Update geometry
        setData(_data);
    }
    
    public int getTextureTileWidth() {
       return _textureTileWidth;
    }
    
    public int getTextureTileHeight() {
        return _textureTileHeight;
    }
    
    public void setAnchorType(AnchorType anchor) {
        super.setAnchorType(anchor);
        setData(_data); // Re-Generate vertices
    }
    
    public void setData(Surface[][] data) {
        _batch.clear();
        _data = data;
        
        if (_data == null || _data.length == 0 || _data[0].length == 0) {
            return;
        }
        
        // #1 Generate Ground Layer
        List<Vertex> solidGroundTiles = generateSolidGround(_data);

        // 2# Generate transition tiles
        List<Vertex> transitionTiles = generateTransitionTiles(_data);
        
        // 3# Add vertices to batch
        _batch.addVertexData(solidGroundTiles);
        _batch.addVertexData(transitionTiles);
        
    }

    @Override
    public void onRender() {        
        _batch.render(PrimitiveType.TRIANGLES);
    }
    
    public void onDestroy() {
        _batch.destroy();
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Creates a list that contains all Surface instances that occur in the input array.
     * @param data The input array of surfaces
     * @return A list of surfaces
     */
    private List<Surface> getUsedSurfaces(Surface[][] data) {
        List<Surface> surfaces = new ArrayList<>();
        
        for(int x = 0; x < data.length; x++) {
            for(int y = 0; y < data[0].length; y++) {
                
                // #1.1 Collect all Surface-instances that are present in 'data'
                if (!surfaces.contains(data[x][y])) {
                    surfaces.add(data[x][y]);
                }
            }
        }
        
        return surfaces;
    }
    
    /**
     * Generates the vertices of a tilemap with their surfaces, but no smooth alpha-transitions.
     * @param data The input array of surfaces
     * @return A list of vertices
     */
    private List<Vertex>  generateSolidGround(Surface[][] data) {
        
        List<Vertex>  vertices = new ArrayList<>();
        for(int x = 0; x < data.length; x++) {
            for(int y = 0; y < data[0].length; y++) {

                // #1.2 Generate solid tile
                int tileId  = data[x][y].getRandomTileId();

                // Calculate X-/Y-Offset to enable different anchor types
                Pair<Float, Float> anchorOffsets = getAnchorOffsets();
                
                vertices.addAll(Arrays.asList(generate4Quad(tileId, x * _tileWidth + anchorOffsets.x, y * _tileHeight + anchorOffsets.y)));
            }
        }
        
        return vertices;
    }
    
    /**
     * Generates transition tiles to create a smooth transitions between all different surfaces.
     * @param data The input array of surfaces
     * @return
     */
    private List<Vertex>  generateTransitionTiles(Surface[][] data) {
        
        // Get all used surfaces and sort them for rendering order
        List<Surface> usedSurfaces = getUsedSurfaces(data);
        Collections.sort(usedSurfaces, new SurfaceComparator());
        
        // Generate transition tiles
        List<Vertex> vertices = new ArrayList<>();

        for(Surface surface : usedSurfaces) {
            
            Quad9AlphaCache[][] alphaCache = generateAlphaValues_9Quad(data, surface);

            
            for(int x = 0; x < data.length; x++) {
                for(int y = 0; y < data[0].length; y++) {
                    if (alphaCache[x][y] == null) {
                        continue;
                    }
                    
                    boolean[] av = alphaCache[x][y].getAlphaValues();

//                    smoothAlpha_5Quad(av);
                    smoothAlpha_9Quad(av);

                    // Calculate X-/Y-Offset to enable different anchor types
                    Pair<Float, Float> anchorOffsets = getAnchorOffsets();
                    
                    // Generate vertices
                    Vertex[] genVertices = generate9Quad(
                            surface.getRandomTileId(),
                            x * _tileWidth + anchorOffsets.x,
                            y * _tileHeight + anchorOffsets.y, 
                            alphaCache[x][y].getTileAlphaValues());
                    
//                    Vertex[] genVertices = generate5Quad(
//                            surface.getRandomTileId(),
//                            x * _tileWidth + anchorOffsets.x,
//                            y * _tileHeight + anchorOffsets.y, 
//                            alphaCache[x][y].getTileAlphaValues());
                    
                    vertices.addAll(Arrays.asList(genVertices));
                }
            }
            
        }
        
        return vertices;
    }
    
    /**
     * Returns an array that contains all neighbor surfaces
     * @param data The input array of surfaces
     * @param x The x index of the pivot
     * @param y The y index of the pivot
     * @return An 3x3 array of surfaces
     */
    private Surface[][]   getNeighborSurfaces(Surface[][] data, int x, int y) {
        
        Surface[][] surfaces = new Surface[3][3];
                
        // TOP-LEFT
        if (inBounds(data, x-1, y-1)) {
            surfaces[0][0] = data[x-1][y-1];
        } else {
            surfaces[0][0] = data[x][y];
        }
        
        // TOP
        if (inBounds(data, x, y-1)) {
            surfaces[1][0] = data[x][y-1];
        } else {
            surfaces[1][0] = data[x][y];
        }
        
        // TOP-RIGHT
        if (inBounds(data, x+1, y-1)) {
            surfaces[2][0] = data[x+1][y-1];
        } else {
            surfaces[2][0] = data[x][y];
        }
        
        // MID-LEFT
        if (inBounds(data, x-1, y)) {
            surfaces[0][1] = data[x-1][y];
        } else {
            surfaces[0][1] = data[x][y];
        }
        
        surfaces[1][1] = data[x][y];
                            
        // MID-RIGHT
        if (inBounds(data, x+1, y)) {
            surfaces[2][1] = data[x+1][y];
        } else {
            surfaces[2][1] = data[x][y];
        }
        
        // BOT-LEFT
        if (inBounds(data, x-1, y+1)) {
            surfaces[0][2] = data[x-1][y+1];
        } else {
            surfaces[0][2] = data[x][y];
        }
        
        // BOT
        if (inBounds(data, x, y+1)) {
            surfaces[1][2] = data[x][y+1];
        } else {
            surfaces[1][2] = data[x][y];
        }
        
        // BOT-RIGHT
        if (inBounds(data, x+1, y+1)) {
            surfaces[2][2] = data[x+1][y+1];
        } else {
            surfaces[2][2] = data[x][y];
        }
        
        return surfaces;
    }
            
    
    private Quad5AlphaCache[][] generateAlphaValues_5Quad(Surface[][] data, Surface surface) {
        Quad5AlphaCache[][] alphaCache = new Quad5AlphaCache[data.length][data[0].length];
        
        for(int x = 0; x < data.length; x++) {
            for(int y = 0; y < data[0].length; y++) {
                
                Surface curSurface = data[x][y];
                if (surface != curSurface) {
                    continue;
                }
                                    
                Surface[][] neighbors = getNeighborSurfaces(data, x, y);
                
                // Top-Left
                if (neighbors[0][0] != curSurface && neighbors[0][0]._layer < curSurface._layer) {
                    
                    if (alphaCache[x-1][y-1] == null) {
                        alphaCache[x-1][y-1] = new Quad5AlphaCache();
                    }
                    
                    alphaCache[x-1][y-1].setAlphaValues(
                            new boolean[] {
                                           false,        false,
                                                  false,      
                                           false,        true
                                          });
                }
                
                // Top
                if (neighbors[1][0] != curSurface && neighbors[1][0]._layer < curSurface._layer) {
                    
                    if (alphaCache[x][y-1] == null) {
                        alphaCache[x][y-1] = new Quad5AlphaCache();
                    }
                    
                    alphaCache[x][y-1].setAlphaValues(
                            new boolean[] { 
                                           false,        false,
                                                  false,       
                                           true,         true
                                          });
                }
                
                // Top-Right
                if (neighbors[2][0] != curSurface && neighbors[2][0]._layer < curSurface._layer) {
                    
                    if (alphaCache[x+1][y-1] == null) {
                        alphaCache[x+1][y-1] = new Quad5AlphaCache();
                    }
                    
                    alphaCache[x+1][y-1].setAlphaValues(
                            new boolean[] { 
                                           false,        false,
                                                  false,       
                                           true,         false
                                          });
                }
                
                // Mid-Left
                if (neighbors[0][1] != curSurface && neighbors[0][1]._layer < curSurface._layer) {
                    
                    if (alphaCache[x-1][y] == null) {
                        alphaCache[x-1][y] = new Quad5AlphaCache();
                    }
                    
                    alphaCache[x-1][y].setAlphaValues(
                            new boolean[] { 
                                           false,        true,
                                                  false,      
                                           false,        true
                                          });
                }
                                    
                // Mid-Right
                if (neighbors[2][1] != curSurface && neighbors[2][1]._layer < curSurface._layer) {
                    
                    if (alphaCache[x+1][y] == null) {
                        alphaCache[x+1][y] = new Quad5AlphaCache();
                    }
                    
                    alphaCache[x+1][y].setAlphaValues(
                            new boolean[] { 
                                           true,        false,
                                                 false,       
                                           true,        false
                                          });
                }
                
                // Bot-Left
                if (neighbors[0][2] != curSurface && neighbors[0][2]._layer < curSurface._layer) {
                    
                    if (alphaCache[x-1][y+1] == null) {
                        alphaCache[x-1][y+1] = new Quad5AlphaCache();
                    }
                    
                    alphaCache[x-1][y+1].setAlphaValues(
                            new boolean[] { 
                                           false,        true,
                                                  false,       
                                           false,        false
                                          });
                }
                
                // Bot
                if (neighbors[1][2] != curSurface && neighbors[1][2]._layer < curSurface._layer) {
                    
                    if (alphaCache[x][y+1] == null) {
                        alphaCache[x][y+1] = new Quad5AlphaCache();
                    }
                    
                    alphaCache[x][y+1].setAlphaValues(
                            new boolean[] { 
                                           true,         true,
                                                  false,
                                           false,        false
                                          });
                }
                
                // Bot-Right
                if (neighbors[2][2] != curSurface && neighbors[2][2]._layer < curSurface._layer) {
                    
                    if (alphaCache[x+1][y+1] == null) {
                        alphaCache[x+1][y+1] = new Quad5AlphaCache();
                    }
                    
                    alphaCache[x+1][y+1].setAlphaValues(
                            new boolean[] { 
                                           true,         false,
                                                  false,       
                                           false,        false
                                          });
                }
            }
        }
        
        return alphaCache;
    }
    
    private Quad9AlphaCache[][] generateAlphaValues_9Quad(Surface[][] data, Surface surface) {
        Quad9AlphaCache[][] alphaCache = new Quad9AlphaCache[data.length][data[0].length];
        
        for(int x = 0; x < data.length; x++) {
            for(int y = 0; y < data[0].length; y++) {
                
                Surface curSurface = data[x][y];
                if (surface != curSurface) {
                    continue;
                }
                                    
                Surface[][] neighbors = getNeighborSurfaces(data, x, y);
                
                // Top-Left
                if (neighbors[0][0] != curSurface && neighbors[0][0]._layer < curSurface._layer) {
                    
                    if (alphaCache[x-1][y-1] == null) {
                        alphaCache[x-1][y-1] = new Quad9AlphaCache();
                    }
                    
                    alphaCache[x-1][y-1].setAlphaValues(
                            new boolean[] {
                                           false, false, false,
                                           false, false, false,
                                           false, false,  true
                                          });
                }
                
                // Top
                if (neighbors[1][0] != curSurface && neighbors[1][0]._layer < curSurface._layer) {
                    
                    if (alphaCache[x][y-1] == null) {
                        alphaCache[x][y-1] = new Quad9AlphaCache();
                    }
                    
                    alphaCache[x][y-1].setAlphaValues(
                            new boolean[] { 
                                           false, false, false,
                                           false, false, false,
                                           true,  true,  true
                                          });
                }
                
                // Top-Right
                if (neighbors[2][0] != curSurface && neighbors[2][0]._layer < curSurface._layer) {
                    
                    if (alphaCache[x+1][y-1] == null) {
                        alphaCache[x+1][y-1] = new Quad9AlphaCache();
                    }
                    
                    alphaCache[x+1][y-1].setAlphaValues(
                            new boolean[] { 
                                           false, false, false,
                                           false, false, false,
                                           true,  false, false
                                          });
                }
                
                // Mid-Left
                if (neighbors[0][1] != curSurface && neighbors[0][1]._layer < curSurface._layer) {
                    
                    if (alphaCache[x-1][y] == null) {
                        alphaCache[x-1][y] = new Quad9AlphaCache();
                    }
                    
                    alphaCache[x-1][y].setAlphaValues(
                            new boolean[] { 
                                           false, false, true,
                                           false, false, true,
                                           false, false, true
                                          });
                }
                                    
                // Mid-Right
                if (neighbors[2][1] != curSurface && neighbors[2][1]._layer < curSurface._layer) {
                    
                    if (alphaCache[x+1][y] == null) {
                        alphaCache[x+1][y] = new Quad9AlphaCache();
                    }
                    
                    alphaCache[x+1][y].setAlphaValues(
                            new boolean[] { 
                                           true, false, false,
                                           true, false, false,
                                           true, false, false
                                          });
                }
                
                // Bot-Left
                if (neighbors[0][2] != curSurface && neighbors[0][2]._layer < curSurface._layer) {
                    
                    if (alphaCache[x-1][y+1] == null) {
                        alphaCache[x-1][y+1] = new Quad9AlphaCache();
                    }
                    
                    alphaCache[x-1][y+1].setAlphaValues(
                            new boolean[] { 
                                           false, false, true,
                                           false, false, false, 
                                           false, false, false
                                          });
                }
                
                // Bot
                if (neighbors[1][2] != curSurface && neighbors[1][2]._layer < curSurface._layer) {
                    
                    if (alphaCache[x][y+1] == null) {
                        alphaCache[x][y+1] = new Quad9AlphaCache();
                    }
                    
                    alphaCache[x][y+1].setAlphaValues(
                            new boolean[] { 
                                           true,  true,  true,
                                           false, false, false,
                                           false, false, false
                                          });
                }
                
                // Bot-Right
                if (neighbors[2][2] != curSurface && neighbors[2][2]._layer < curSurface._layer) {
                    
                    if (alphaCache[x+1][y+1] == null) {
                        alphaCache[x+1][y+1] = new Quad9AlphaCache();
                    }
                    
                    alphaCache[x+1][y+1].setAlphaValues(
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
     * Generates a tile/quad by a given tile id, that consists of 5 vertices
     * @param tileId The tile id
     * @param x The x component of the upper left corner
     * @param y Thex y component of the upper left corner
     * @param alphaValues The alpha values for all vertices (float[5])
     * @return An array that contains the quad as vertices
     */
    private Vertex[] generate5Quad(int tileId, float x, float y, float[] alphaValues) {

        int tileIdX = tileId % _tilesPerColumn;
        int tileIdY = tileId / _tilesPerRow;
        
        float texcoordX = tileIdX * _texcoordWidth;
        float texcoordY = tileIdY * _texcoordHeight;
        
        // Setup positions
        float width_2   = _tileWidth * 0.5f;
        float height_2  = _tileHeight * 0.5f;

        float xTopLeft  = x,                yTopLeft    = y;
        float xTopRight = x + _tileWidth,   yTopRight   = y;

        float xMid      = x + width_2,      yMid        = y + height_2;

        float xBotLeft  = x,                yBotLeft    = y + _tileHeight;
        float xBotRight = x + _tileWidth,   yBotRight   = y + _tileHeight;

        // Setup uv
        float texcoordWidth_2    = _texcoordWidth * 0.5f;
        float texcoordHeight_2   = _texcoordHeight * 0.5f;

        Vector2f texcoordTopLeft  = new Vector2f(texcoordX,                   texcoordY);
        Vector2f texcoordTopRight = new Vector2f(texcoordX + _texcoordWidth,  texcoordY);
        
        Vector2f texcoordMid      = new Vector2f(texcoordX + texcoordWidth_2, texcoordY + texcoordHeight_2);
        
        Vector2f texcoordBotLeft  = new Vector2f(texcoordX,                   texcoordY + _texcoordHeight);
        Vector2f texcoordBotRight = new Vector2f(texcoordX + _texcoordWidth,  texcoordY + _texcoordHeight);
        
        Vertex vTopLeft = new Vertex(
                    new Vector3f(xTopLeft, yTopLeft),
                    new Color4f(0.5f, alphaValues[0]), 
                    texcoordTopLeft
                );
        
        Vertex vTopRight = new Vertex(
                    new Vector3f(xTopRight, yTopRight),
                    new Color4f(0.5f, alphaValues[1]),
                    texcoordTopRight
                );

        Vertex vMid = new Vertex(
                    new Vector3f(xMid, yMid),
                    new Color4f(0.5f, alphaValues[2]),
                    texcoordMid
                );

        Vertex vBotLeft = new Vertex(
                    new Vector3f(xBotLeft, yBotLeft),
                    new Color4f(0.5f, alphaValues[3]),
                    texcoordBotLeft
                );
        
        Vertex vBotRight = new Vertex(
                    new Vector3f(xBotRight, yBotRight), 
                    new Color4f(0.5f, alphaValues[4]),
                    texcoordBotRight
                );

        return new Vertex[] {
                // Top Triangle
                vTopLeft, vMid, vTopRight,
                
                // Left Triangle
                vTopLeft, vBotLeft, vMid, 

                // Bottom Triangle
                vBotLeft, vBotRight, vMid,
                
                // Right Triangle
                vBotRight, vTopRight, vMid
            };
    }
    

    private Vertex[] generate4Quad(int tileId, float x, float y) {
        int tileIdX = tileId % _tilesPerColumn;
        int tileIdY = tileId / _tilesPerRow;
        
        // Resize the texcoord rectangle to prevent texture/tile clamp issues
        float fixedTexCoordWidth  = _texcoordWidth - 0.002f;
        float fixedTexCoordHeight = _texcoordHeight - 0.002f;
        
        float fixedTexcoordX      = tileIdX * _texcoordWidth + 0.001f;
        float fixedTexcoordY      = tileIdY * _texcoordHeight + 0.001f;

        return new Vertex[] {
                new Vertex(new Vector3f(               x,               y, 0.0f), new Color4f(0.5f), new Vector2f(fixedTexcoordX,                  fixedTexcoordY)),
                new Vertex(new Vector3f(  x + _tileWidth,               y, 0.0f), new Color4f(0.5f), new Vector2f(fixedTexcoordX + fixedTexCoordWidth, fixedTexcoordY)),  
                new Vertex(new Vector3f(               x, y + _tileHeight, 0.0f), new Color4f(0.5f), new Vector2f(fixedTexcoordX,                  fixedTexcoordY + fixedTexCoordHeight)),
                new Vertex(new Vector3f(  x + _tileWidth,               y, 0.0f), new Color4f(0.5f), new Vector2f(fixedTexcoordX + fixedTexCoordWidth, fixedTexcoordY)),  
                new Vertex(new Vector3f(               x, y + _tileHeight, 0.0f), new Color4f(0.5f), new Vector2f(fixedTexcoordX,                  fixedTexcoordY + fixedTexCoordHeight)),
                new Vertex(new Vector3f(  x + _tileWidth, y + _tileHeight, 0.0f), new Color4f(0.5f), new Vector2f(fixedTexcoordX + fixedTexCoordWidth, fixedTexcoordY + fixedTexCoordHeight)),
          };
    }  
    /**
     * Generates a tile/quad by a given tile id, that consists of 9 vertices and alpha values
     * @param tileId The tile id
     * @param x The x component of the upper left corner
     * @param y Thex y component of the upper left corner
     * @param alphaValues The alpha values for all vertices (float[9])
     * @return An array that contains the quad as vertices
     */
    private Vertex[] generate9Quad(int tileId, float x, float y, float[] alphaValues) {

        int tileIdX = tileId % _tilesPerColumn;
        int tileIdY = tileId / _tilesPerRow;
        
        float texcoordX = tileIdX * _texcoordWidth;
        float texcoordY = tileIdY * _texcoordHeight;
        
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
        float texcoordWidth_2    = _texcoordWidth * 0.5f;
        float texcoordHeight_2   = _texcoordHeight * 0.5f;

        Vector2f texcoordTopLeft  = new Vector2f(texcoordX,                   texcoordY);
        Vector2f texcoordTop      = new Vector2f(texcoordX + texcoordWidth_2, texcoordY);
        Vector2f texcoordTopRight = new Vector2f(texcoordX + _texcoordWidth,  texcoordY);
        
        Vector2f texcoordMidLeft  = new Vector2f(texcoordX,                   texcoordY + texcoordHeight_2);
        Vector2f texcoordMid      = new Vector2f(texcoordX + texcoordWidth_2, texcoordY + texcoordHeight_2);
        Vector2f texcoordMidRight = new Vector2f(texcoordX + _texcoordWidth,  texcoordY + texcoordHeight_2);
        
        Vector2f texcoordBotLeft  = new Vector2f(texcoordX,                   texcoordY + _texcoordHeight);
        Vector2f texcoordBot      = new Vector2f(texcoordX + texcoordWidth_2, texcoordY + _texcoordHeight);
        Vector2f texcoordBotRight = new Vector2f(texcoordX + _texcoordWidth,  texcoordY + _texcoordHeight);
        
        Vertex vTopLeft = new Vertex(
                    new Vector3f(xTopLeft, yTopLeft),
                    new Color4f(0.5f, alphaValues[0]), 
                    texcoordTopLeft
                );
        
        Vertex vTop = new Vertex(
                    new Vector3f(xTop, yTop),
                    new Color4f(0.5f,alphaValues[1]),
                    texcoordTop
                );
        
        Vertex vTopRight = new Vertex(
                    new Vector3f(xTopRight, yTopRight),
                    new Color4f(0.5f, alphaValues[2]),
                    texcoordTopRight
                );

        Vertex vMidLeft = new Vertex(
                    new Vector3f(xMidLeft, yMidLeft),
                    new Color4f(0.5f, alphaValues[3]),
                    texcoordMidLeft
                );
        
        Vertex vMid = new Vertex(
                    new Vector3f(xMid, yMid),
                    new Color4f(0.5f, alphaValues[4]),
                    texcoordMid
                );
        
        Vertex vMidRight = new Vertex(
                    new Vector3f(xMidRight, yMidRight),
                    new Color4f(0.5f, alphaValues[5]),
                    texcoordMidRight
                );

        Vertex vBotLeft = new Vertex(
                    new Vector3f(xBotLeft, yBotLeft),
                    new Color4f(0.5f, alphaValues[6]),
                    texcoordBotLeft
                );
        
        Vertex vBot = new Vertex(
                    new Vector3f(xBot, yBot),
                    new Color4f(0.5f, alphaValues[7]),
                    texcoordBot
                );
        
        Vertex vBotRight = new Vertex(
                    new Vector3f(xBotRight, yBotRight), 
                    new Color4f(0.5f, alphaValues[8]),
                    texcoordBotRight
                );

//        return new Vertex[] {
//                // Upper Top Left
//                vTopLeft, vTop, vMid,
//                // Lower Top Left
//                vMidLeft, vTopLeft, vMid, 
//
//                // Upper Top Right
//                vMid, vMidRight, vTopRight, 
//                // Lower Top Right
//                vMid, vTop, vTopRight,
//
//                // Upper Bot Left
//                vMid, vBotLeft, vMidLeft,
//                // Lower Bot Left
//                vMid, vBot, vBotLeft,
//
//                // Upper Bot Right
//                vMid, vBot,  vBotRight,
//                // Lower Bot Right
//                vMid , vBotRight, vMidRight};
        
        return new Vertex[] {
                // Upper Top Left
                vTopLeft, vTop, vMidLeft,
                // Lower Top Left
                vTop, vMidLeft, vMid, 

                // Upper Top Right
                vMid, vMidRight, vTop, 
                // Lower Top Right
                vTop, vMidRight, vTopRight,

                // Upper Bot Left
                vMid, vBot, vMidLeft,
                // Lower Bot Left
                vMidLeft, vBotLeft, vBot,

                // Upper Bot Right
                vMid, vBot,  vMidRight,
                // Lower Bot Right
                vBot, vMidRight, vBotRight};
    }
    
    private boolean[] smoothAlpha_5Quad(boolean[] av) {
        // Smoothing Top-Left Corner
        if (av[0] && av[1] && av[3]) {
            av[2] = true;
        }
        
        // Smoothing Top-Right Corner
        if (av[0] && av[1] && av[4]) {
            av[2] = true;
        }
        
        // Smoothing Bot-Left Corner
        if (av[1] && av[3] && av[4]) {
            av[2] = true;
        }
        
        // Smoothing Bot-Right Corner
        if (av[0] && av[3] && av[4]) {
            av[2] = true;
        }
        
        return av;
    }
    
    private boolean[] smoothAlpha_9Quad(boolean[] av) {        
        // Smoothing Top-Left Corner
        if (av[0] && av[1] && av[2] && av[3] && av[6]) {
            av[4] = true;
        }
        
        // Smoothing Top-Right Corner
        if (av[0] && av[1] && av[2] && av[5] && av[8]) {
            av[4] = true;
        }
        
        // Smoothing Bot-Left Corner
        if (av[0] && av[3] && av[6] && av[7] && av[8]) {
            av[4] = true;
        }
        
        // Smoothing Bot-Right Corner
        if (av[2] && av[5] && av[8] && av[7] && av[6] ) {
            av[4] = true;
        }
        
        return av;
    }
    
    private Pair<Float,Float> getAnchorOffsets() {
        // Calculate X-/Y-Offset to enable different anchor types
        AnchorType anchor = getAnchorType();

        float tilemapWidth  = _tileWidth * _data.length;
        float tilemapHeight = _tileHeight * _data[0].length;
        float anchoredX = 0;
        float anchoredY = 0;
        
        if (anchor == AnchorType.TOP_RIGHT || anchor == AnchorType.MID_RIGHT || anchor == AnchorType.BOT_RIGHT) {
            anchoredX = -tilemapWidth;
        }
        else if (anchor == AnchorType.TOP || anchor == AnchorType.MID || anchor == AnchorType.BOT) {
            anchoredX = -tilemapWidth/2;
        }
        
        if (anchor == AnchorType.MID_LEFT || anchor == AnchorType.MID || anchor == AnchorType.MID_RIGHT) {
            anchoredY = -tilemapHeight/2;
        }
        
        else if (anchor == AnchorType.BOT_LEFT || anchor == AnchorType.BOT || anchor == AnchorType.BOT_RIGHT) {
            anchoredY = -tilemapHeight;
        }
        
        return Pair.New(anchoredX, anchoredY);
    }
    
    private StreamingBatch  _batch;
    private Surface[][]     _data;
    
    private float _tileWidth;
    private float _tileHeight;
    
    private int _textureTileWidth;
    private int _textureTileHeight;
    
    private int _tilesPerColumn;
    private int _tilesPerRow;
    
    private int _textureWidth;
    private int _textureHeight;
    
    private float _texcoordWidth;
    private float _texcoordHeight;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private static <T> boolean inBounds(T[][] arr, int x, int y) {
        return !(x < 0 || x >= arr.length || y < 0 || y >= arr[0].length);
    }
    
    private static class Quad5AlphaCache {
        
        private boolean [] _alphaValues = new boolean[] { false,        false,
                                                                 false,        
                                                          false,        false };
        
        public void setAlphaValues(boolean[] values) {
            _alphaValues[0] = _alphaValues[0] || values[0]; // Top Left
            _alphaValues[1] = _alphaValues[1] || values[1]; // Top Right
            _alphaValues[2] = _alphaValues[2] || values[2]; // Mid
            _alphaValues[3] = _alphaValues[3] || values[3]; // Bot Left
            _alphaValues[4] = _alphaValues[4] || values[4]; // Bot Right
        }
        
        public boolean[] getAlphaValues() {
            return _alphaValues;
        }
        
        public float[] getTileAlphaValues() {
            return new float[] {
                    _alphaValues[0] ? 1.0f : 0.0f, // Top Left
                    _alphaValues[1] ? 1.0f : 0.0f, // Top Right
                    _alphaValues[2] ? 1.0f : 0.0f, // Mid
                    _alphaValues[3] ? 1.0f : 0.0f, // Bot Left
                    _alphaValues[4] ? 1.0f : 0.0f, // Bot Right
            };
        }
    }
    
    private static class Quad9AlphaCache {
        
        private boolean [] _alphaValues = new boolean[] { false, false, false,
                                                          false, false, false, 
                                                          false, false, false };
        
        public void setAlphaValues(boolean[] values) {
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
        
        public boolean[] getAlphaValues() {
            return _alphaValues;
        }
        
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
    }

}
