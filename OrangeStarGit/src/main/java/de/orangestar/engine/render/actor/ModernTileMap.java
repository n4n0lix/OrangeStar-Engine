package de.orangestar.engine.render.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import de.orangestar.engine.debug.EngineException;
import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.batch.StreamingBatch;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.utils.Pair;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Vector2f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vertex;

/**
 * A modern tileset with alpha-blending usage to create transitions between surfaces.
 * @author Basti
 *
 */
public class ModernTileMap extends Actor {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Inner Classes                            */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public static class Surface {
        public Surface(String name) {
            _name = name;
            _tileIndices = new ArrayList<>();
        }
        
        public Surface nonsolid() {
            _isSolid = false;
            return this;
        }
        
        public Surface solid() {
            _isSolid = true;
            return this;
        }
        
        public Surface tile(int index) {
            _tileIndices.add(index);
            return this;
        }
        
        public Surface tiles(Integer... indices) {
            _tileIndices.addAll(Arrays.asList(indices));
            return this;
        }
        
        public Surface tiles(Collection<Integer> indices) {
            _tileIndices.addAll(indices);
            return this;
        }
        
        public boolean isSolid() {
            return _isSolid;
        }
        
        public String getName() {
            return _name;
        }
        
        public List<Integer> getTiles() {
            return new ArrayList<Integer>(_tileIndices);
        }
        
        private String        _name;
        private boolean       _isSolid;
        private List<Integer> _tileIndices;
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public ModernTileMap(Texture texture, int tileWidth, int tileHeight, Surface... surfaces) {
        this(texture, tileWidth, tileHeight, Arrays.asList(surfaces));
    }
    
    public ModernTileMap(Texture texture, int tileWidth, int tileHeight, Collection<Surface> surfaces) {
        if (texture == null)
            throw new EngineException("Texture is null!");
        
        if (tileWidth > texture.getWidth() || tileHeight > texture.getHeight())
            throw new EngineException("Texture width/height is smaller than tile width/height! (texture: " + texture.getWidth() + "/" + texture.getHeight() + ") (tile: " + tileWidth + "/" + tileHeight+")");
                
        Material material = new Material.Builder()
                                            .texture(texture)
                                            .shader(Shader.StreamingBatchShader)
                                            .build();
        
        _batch = new StreamingBatch.Builder()
                                       .material(material)
                                       .build();
        
        _textureWidth = texture.getWidth();
        _textureHeight = texture.getHeight();

        setTileSize(tileWidth, tileHeight);
    }
    
    public void setTileSize(int width, int height) {
        _tileWidth = width;
        _tileHeight = height;
        
        // Calculate number of tiles per row and per column
        _tilesPerRow    = _textureWidth  / _tileWidth;
        _tilesPerColumn = _textureHeight / _tileHeight;
    }
    
    public void setData(Surface[][] data) {
        _batch.clear();
        
        List<Vertex> vertices = new ArrayList<>();
        
        for(int i = 0; i < data.length; i++) {
            for (int p = 0; p < data[i].length; p++) {
                
                Surface surface = data[i][p];
                int subimageID  = surface.getTiles().get(new Random().nextInt(surface.getTiles().size()));
                int subimageX   = subimageID % _tilesPerRow;
                int subimageY   = subimageID / _tilesPerColumn;
                float uvPerX    = 1f / _textureWidth  * _tileWidth;
                float uvPerY    = 1f / _textureHeight * _tileHeight;
                
                boolean[] alphaValues = getAlphaValues(data, i, p);
                
                vertices.addAll( Arrays.asList(generateQuad( i * _tileWidth, p * _tileHeight, _tileWidth, _tileHeight, 
                                                             subimageX * uvPerX, subimageY * uvPerY, uvPerX, uvPerY,
                                                             alphaValues[0], alphaValues[1], alphaValues[2],
                                                             alphaValues[3], alphaValues[4], alphaValues[5],
                                                             alphaValues[6], alphaValues[7], alphaValues[8])));
            }
        }
        
        _batch.addVertexData(vertices);
    }
    
    @Override
    public void onRender() {
        _batch.render(PrimitiveType.TRIANGLES);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private StreamingBatch _batch;

    private int _textureWidth;
    private int _textureHeight;
    
    private int _tileWidth;
    private int _tileHeight;
    
    private int _tilesPerRow;
    private int _tilesPerColumn;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private static boolean[] getAlphaValues(Surface[][] data, int x, int y) {
        Surface pivot = data[x][y];
        
        // If is solid, no alpha
        if ( pivot.isSolid() ) {
            return new boolean[] { false, false, false, false, false, false, false, false, false };
        }
        
        boolean topLeft = false;
        boolean top = false;
        boolean topRight = false;
        
        boolean midLeft = false;
        boolean mid = false;
        boolean midRight = false;
        
        boolean botLeft = false;
        boolean bot = false;
        boolean botRight = false;
              
//        // TOP LEFT
//        if (inBounds(data, x-1, y-1)) {
//            topLeft = !data[x-1][y-1].isSolid() && pivot != data[x-1][y-1];
//        } else {
//            topLeft = false;
//        }
        topLeft = top || midLeft;
        
        // TOP
        if (inBounds(data, x, y-1)) {
            top = !data[x][y-1].isSolid() && pivot != data[x][y-1];
        } else {
            top = false;
        }
        
//        // TOP RIGHT
//        if (inBounds(data, x+1, y-1)) {
//            topRight = !data[x+1][y-1].isSolid() && pivot != data[x+1][y-1];
//        } else {
//            topRight = false;
//        }
        topRight = top || midRight;
        
        // MID LEFT
        if (inBounds(data, x-1, y)) {
            midLeft = !data[x-1][y].isSolid() && pivot != data[x-1][y];
        } else {
            midLeft = false;
        }
        
        // MID RIGHT
        if (inBounds(data, x+1, y)) {
            midRight = !data[x+1][y].isSolid() && pivot != data[x+1][y];
        } else {
            midRight = false;
        }
        
//        // BOT LEFT
//        if (inBounds(data, x-1, y+1)) {
//            botLeft = !data[x-1][y+1].isSolid() && pivot != data[x-1][y+1];
//        } else {
//            botLeft = false;
//        }
        botLeft = bot || midLeft;
        
        // BOT
        if (inBounds(data, x, y+1)) {
            bot = !data[x][y+1].isSolid() && pivot != data[x][y+1];
        } else {
            bot = false;
        }
        
//        // BOT RIGHT
//        if (inBounds(data, x+1, y+1)) {
//            botRight = !data[x+1][y+1].isSolid() && pivot != data[x+1][y+1];
//        } else {
//            botRight = false;
//        }
        botRight = bot || midRight;
        
        return new boolean[] { topLeft, top, topRight, midLeft, mid, midRight, botLeft, bot, botRight };
    }
    
    private static boolean inBounds(Object[][] input, int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }
        
        if (x >= input.length || y >= input[0].length) {
            return false;
        }
        
        return true;
    }
    
    private static Vertex[] generateQuad(final float x,    final float y,    final float width,    final float height,
                                         final float uv_x, final float uv_y, final float uv_width, final float uv_height,
                                         boolean alphaTopLeft, boolean alphaTop, boolean alphaTopRight,
                                         boolean alphaMidLeft, boolean alphaMid, boolean alphaMidRight,
                                         boolean alphaBotLeft, boolean alphaBot, boolean alphaBotRight) {

        // Setup positions
        float width_2   = width * 0.5f;
        float height_2  = height * 0.5f;
        
        float xTopLeft  = x,            yTopLeft  = y;
        float xTop      = x + width_2,  yTop      = y;
        float xTopRight = x + width,    yTopRight = y;
        
        float xMidLeft  = x,            yMidLeft  = y + height_2;
        float xMid      = x + width_2,  yMid      = y + height_2;
        float xMidRight = x + width,    yMidRight = y + height_2;
        
        float xBotLeft  = x,            yBotLeft  = y + height;
        float xBot      = x + width_2,  yBot      = y + height;
        float xBotRight = x + width,    yBotRight = y + height;
        
        // Setup uv
        float uv_width_2    = uv_width * 0.5f;
        float uv_height_2   = uv_height * 0.5f;
        
        float uv_xTopLeft   = uv_x,                 uv_yTopLeft  = uv_y;
        float uv_xTop       = uv_x + uv_width_2,    uv_yTop      = uv_y;
        float uv_xTopRight  = uv_x + uv_width,      uv_yTopRight = uv_y;
        
        float uv_xMidLeft   = uv_x,                 uv_yMidLeft  = uv_y + uv_height_2;
        float uv_xMid       = uv_x + uv_width_2,    uv_yMid      = uv_y + uv_height_2;
        float uv_xMidRight  = uv_x + uv_width,      uv_yMidRight = uv_y + uv_height_2;
        
        float uv_xBotLeft   = uv_x,                 uv_yBotLeft  = y + uv_height;
        float uv_xBot       = uv_x + uv_width_2,    uv_yBot      = y + uv_height;
        float uv_xBotRight  = uv_x + uv_width,      uv_yBotRight = y + uv_height;
        
        Vertex vTopLeft  = new Vertex(new Vector3f(xTopLeft,  yTopLeft),    new Color4f(0.5f, alphaTopLeft  ? 0.0f : 1.0f), new Vector2f(uv_xTopLeft,  uv_yTopLeft));
        Vertex vTop      = new Vertex(new Vector3f(xTop,      yTop),        new Color4f(0.5f, alphaTop      ? 0.0f : 1.0f), new Vector2f(uv_xTop,      uv_yTop));
        Vertex vTopRight = new Vertex(new Vector3f(xTopRight, yTopRight),   new Color4f(0.5f, alphaTopRight ? 0.0f : 1.0f), new Vector2f(uv_xTopRight, uv_yTopRight));
        
        Vertex vMidLeft  = new Vertex(new Vector3f(xMidLeft,  yMidLeft),    new Color4f(0.5f, alphaMidLeft  ? 0.0f : 1.0f), new Vector2f(uv_xMidLeft,  uv_yMidLeft));
        Vertex vMid      = new Vertex(new Vector3f(xMid,      yMid),        new Color4f(0.5f, alphaMid      ? 0.0f : 1.0f), new Vector2f(uv_xMid,      uv_yMid));
        Vertex vMidRight = new Vertex(new Vector3f(xMidRight, yMidRight),   new Color4f(0.5f, alphaMidRight ? 0.0f : 1.0f), new Vector2f(uv_xMidRight, uv_yMidRight));
        
        Vertex vBotLeft  = new Vertex(new Vector3f(xBotLeft,  yBotLeft),    new Color4f(0.5f, alphaBotLeft  ? 0.0f : 1.0f), new Vector2f(uv_xBotLeft,  uv_yBotLeft));
        Vertex vBot      = new Vertex(new Vector3f(xBot,      yBot),        new Color4f(0.5f, alphaBot      ? 0.0f : 1.0f), new Vector2f(uv_xBot,      uv_yBot));
        Vertex vBotRight = new Vertex(new Vector3f(xBotRight, yBotRight),   new Color4f(0.5f, alphaBotRight ? 0.0f : 1.0f), new Vector2f(uv_xBotRight, uv_yBotRight));

        return new Vertex[] {
            // Upper Top Left
            vTopLeft, vTop, vMidLeft,
            // Lower Top Left
            vMidLeft, vMid, vTop,
            
            // Upper Top Right
            vMid, vTopRight, vTop, 
            // Lower Top Right
            vTopRight, vMid, vMidRight, 
            
            // Upper Bot Left
            vMid, vMidLeft, vBotLeft, 
            // Lower Bot Left
            vBotLeft, vBot, vMid, 
            
            // Upper Bot Right
            vMid, vBot, vMidRight, 
            // Lower Bot Right
            vBot, vBotRight, vMidRight
        };
    }
}
