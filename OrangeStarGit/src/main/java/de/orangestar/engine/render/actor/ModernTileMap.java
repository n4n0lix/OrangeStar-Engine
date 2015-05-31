package de.orangestar.engine.render.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import de.orangestar.engine.debug.EngineException;
import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.batch.StreamingBatch;
import de.orangestar.engine.render.shader.Shader;
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
            _isSolid = true;
            return this;
        }
        
        public Surface solid() {
            _isSolid = false;
            return this;
        }
        
        public Surface tile(int index) {
            _tileIndices.add(index);
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
        
        if (tileWidth < texture.getWidth() || tileHeight < texture.getHeight())
            throw new EngineException("Texture width/height is smaller than tile width/height!");
                
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
        _textureWidth = width;
        _textureHeight = height;
    }
    
    public void setData(Surface[][] data) {
        
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
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private static Vertex[] generateQuad(final float x,    final float y,    final float width,    final float height,
                                         final float uv_x, final float uv_y, final float uv_width, final float uv_height) {

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
        
        Vertex vTopLeft  = new Vertex(new Vector3f(xTopLeft,  yTopLeft),    new Color4f(0.5f), new Vector2f(uv_xTopLeft,  uv_yTopLeft));
        Vertex vTop      = new Vertex(new Vector3f(xTop,      yTop),        new Color4f(0.5f), new Vector2f(uv_xTop,      uv_yTop));
        Vertex vTopRight = new Vertex(new Vector3f(xTopRight, yTopRight),   new Color4f(0.5f), new Vector2f(uv_xTopRight, uv_yTopRight));
        
        Vertex vMidLeft  = new Vertex(new Vector3f(xMidLeft,  yMidLeft),    new Color4f(0.5f), new Vector2f(uv_xMidLeft,  uv_yMidLeft));
        Vertex vMid      = new Vertex(new Vector3f(xMid,      yMid),        new Color4f(0.5f), new Vector2f(uv_xMid,      uv_yMid));
        Vertex vMidRight = new Vertex(new Vector3f(xMidRight, yMidRight),   new Color4f(0.5f), new Vector2f(uv_xMidRight, uv_yMidRight));
        
        Vertex vBotLeft  = new Vertex(new Vector3f(xBotLeft,  yBotLeft),    new Color4f(0.5f), new Vector2f(uv_xBotLeft,  uv_yBotLeft));
        Vertex vBot      = new Vertex(new Vector3f(xBot,      yBot),        new Color4f(0.5f), new Vector2f(uv_xBot,      uv_yBot));
        Vertex vBotRight = new Vertex(new Vector3f(xBotRight, yBotRight),   new Color4f(0.5f), new Vector2f(uv_xBotRight, uv_yBotRight));

        return new Vertex[] {
            // Upper Top Left
            vTopLeft, vTop, vMidLeft,
            // Lower Top Left
            vMidLeft, vTop, vMid,
            
            // Upper Top Right
            vMid, vTop, vTopRight,
            // Lower Top Right
            vTopRight, vMidRight, vMid, 
            
            // Upper Bot Left
            vMid, vBotLeft, vMidLeft,
            // Lower Bot Left
            vBotLeft, vMid, vBot,
            
            // Upper Bot Right
            vMid, vMidRight, vBot,
            // Lower Bot Right
            vBot, vMidRight, vBotRight
        };
    }
}
