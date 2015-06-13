package de.orangestar.engine.render.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * A modern tileset with alpha-blending usage to create transitions between
 * surfaces.
 * 
 * @author Basti
 *
 */
public class ModernTileMap extends Actor {

    public static class Surface {
        public int           layer = 0;
        public List<Integer> subtextureIds = new ArrayList<Integer>();
        
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public ModernTileMap(Texture texture, int tileWidth, int tileHeight) {
        Material material = new Material.Builder()
                                            .texture(texture)
                                            .shader(Shader.StreamingBatchShader)
                                            .build();
        
        
        
        _batch = new StreamingBatch.Builder().material(material).build();

        _textureWidth  = _batch.getMaterial().getTexture().getWidth();
        _textureHeight = _batch.getMaterial().getTexture().getHeight();
        
        setTileSize(tileWidth, tileHeight);
    }
    
    public void setTileSize(int width, int height) {
        _tileWidth = width;
        _tileHeight = height;
        
        // Calculate number of tiles per row and per column
        _tilesPerRow    = _textureWidth  / _tileWidth;
        _tilesPerColumn = _textureHeight / _tileHeight;
    }
    
    public int getTileWidth() {
       return _tileWidth;
    }
    
    public int getTileHeight() {
        return _tileHeight;
    }
    
    public void setData(Surface[][] data) {

        // Setup test data
        Surface dirt = new Surface();
        dirt.layer = 0;
        dirt.subtextureIds.addAll(Arrays.asList( 0, 1, 2, 3, 8, 9, 10, 11));
        
        Surface grass = new Surface();
        grass.layer = 1;
        grass.subtextureIds.addAll(Arrays.asList( 4, 5, 6, 7, 12, 13, 14, 15));
        
        data = new Surface[][] {
                new Surface[] { dirt,  dirt,  dirt, },
                new Surface[] { dirt,  grass, dirt, },
                new Surface[] { dirt,  dirt,  dirt, }
        };
        
        // Render Ground Layer
        _batch.clear();
        
        float texcoordWidth    = 1f / _textureWidth  * _tileWidth;
        float texcoordHeight    = 1f / _textureHeight * _tileHeight;
        
        List<Vertex> vertices = new ArrayList<>(data.length * data[0].length * 4);
        
        for(int x = 0; x < data.length; x++) {
            for(int y = 0; y < data[0].length; y++) {
                
                int subimageID  = data[x][y].subtextureIds.get(0);
                int texcoordX   = subimageID % _tilesPerRow;
                int texcoordY   = subimageID / _tilesPerColumn;

                vertices.addAll(
                        Arrays.asList(generate4Quad(
                                     x * _tileWidth,
                                     y * _tileHeight,
                                     _tileWidth,
                                     _tileHeight, 
                                     texcoordX * texcoordWidth, 
                                     texcoordY * texcoordHeight, 
                                     texcoordWidth, 
                                     texcoordHeight)));
                
                if(x == 0) {
                	if(y == 0){ // top left corner
                		
                	} else if(y == data[0].length -1) { // bottom left corner
                		
                	} else { // left
                		
                	}
                } else if(y == 0) {
                	if(x == data.length -1) { // top right corner
                		
                	} else { // top
                		
                	}
                	
                } else if(x == data.length -1) {
                	if(y == data[x].length -1) { // bottom right corner
                		
                	} else { // right
                		
                	}
                } else if(y == data[x].length -1) { // bottom
                	
                } else { // everything in the middle
                	
                }
            }
        }
        
        _batch.addVertexData(vertices);
        
        // Add alpha tiles

        generateAlphaTiles(texcoordWidth, texcoordHeight);
    }

    public void generateAlphaTiles(float texcoordWidth, float texcoordHeight) {
    	
    	_batch.addVertexData(
    			generate9Quad(
    					0, 
    					0, 
    					_tileWidth, 
    					_tileHeight, 
    					4 * texcoordWidth, 
    					0 * texcoordHeight, 
    					texcoordWidth, 
    					texcoordHeight, 
    					new float[] { 0, 0, 0, 0, 0, 0, 0, 0, 1 }));
    	
    	_batch.addVertexData(
    			generate9Quad(
    					16, 
    					0, 
    					_tileWidth, 
    					_tileHeight, 
    					4 * texcoordWidth, 
    					0 * texcoordHeight, 
    					texcoordWidth, 
    					texcoordHeight, 
    					new float[] { 0, 0, 0, 0, 0, 0, 1, 1, 1 }));
    	
    	_batch.addVertexData(
    			generate9Quad(
    					32, 
    					0, 
    					_tileWidth, 
    					_tileHeight, 
    					4 * texcoordWidth, 
    					0 * texcoordHeight, 
    					texcoordWidth, 
    					texcoordHeight, 
    					new float[] { 0, 0, 0, 0, 0, 0, 1, 0, 0 }));
    	
    	_batch.addVertexData(
    			generate9Quad(
    					32, 
    					16, 
    					_tileWidth, 
    					_tileHeight, 
    					4 * texcoordWidth, 
    					0 * texcoordHeight, 
    					texcoordWidth, 
    					texcoordHeight, 
    					new float[] { 1, 0, 0, 1, 0, 0, 1, 0, 0 }));
    	
    	_batch.addVertexData(
    			generate9Quad(
    					32, 
    					32, 
    					_tileWidth, 
    					_tileHeight, 
    					4 * texcoordWidth, 
    					0 * texcoordHeight, 
    					texcoordWidth, 
    					texcoordHeight, 
    					new float[] { 1, 0, 0, 0, 0, 0, 0, 0, 0 }));
    	
    	_batch.addVertexData(
    			generate9Quad(
    					16, 
    					32, 
    					_tileWidth, 
    					_tileHeight, 
    					4 * texcoordWidth, 
    					0 * texcoordHeight, 
    					texcoordWidth, 
    					texcoordHeight, 
    					new float[] { 1, 1, 1, 0, 0, 0, 0, 0, 0 }));
    	
    	_batch.addVertexData(
    			generate9Quad(
    					0, 
    					32, 
    					_tileWidth, 
    					_tileHeight, 
    					4 * texcoordWidth, 
    					0 * texcoordHeight, 
    					texcoordWidth, 
    					texcoordHeight, 
    					new float[] { 0, 0, 1, 0, 0, 0, 0, 0, 0 }));
    	
    	_batch.addVertexData(
    			generate9Quad(
    					0, 
    					16, 
    					_tileWidth, 
    					_tileHeight, 
    					4 * texcoordWidth, 
    					0 * texcoordHeight, 
    					texcoordWidth, 
    					texcoordHeight, 
    					new float[] { 0, 0, 1, 0, 0, 1, 0, 0, 1 }));
    }
    
//    _batch.addVertexData(generate4Quad(
//                    0, 
//                    0, 
//                    _tileWidth, 
//                    _tileHeight, 
//                    4 * texcoordWidth, 
//                    0 * texcoordHeight, 
//                    texcoordWidth, 
//                    texcoordHeight, 
//                    alphaValues));
    

    @Override
    public void onRender() {
        _batch.render(PrimitiveType.TRIANGLES);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private StreamingBatch  _batch;
    
    private int _tileWidth;
    private int _tileHeight;
    
    private int _tilesPerColumn;
    private int _tilesPerRow;
    
    private int _textureWidth;
    private int _textureHeight;

/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
/*                              Private                               */
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private static Vertex[] generate9Quad(
            final float x,    final float y,    final float width,    final float height,
            final float uv_x, final float uv_y, final float uv_width, final float uv_height,
            final float[] alphaValues) {

        // Setup positions
        float width_2   = width * 0.5f;
        float height_2  = height * 0.5f;

        float xTopLeft  = x,            yTopLeft    = y;
        float xTop      = x + width_2,  yTop        = y;
        float xTopRight = x + width,    yTopRight   = y;

        float xMidLeft  = x,            yMidLeft    = y + height_2;
        float xMid      = x + width_2,  yMid        = y + height_2;
        float xMidRight = x + width,    yMidRight   = y + height_2;

        float xBotLeft  = x,            yBotLeft    = y + height;
        float xBot      = x + width_2,  yBot        = y + height;
        float xBotRight = x + width,    yBotRight   = y + height;

        // Setup uv
        float uv_width_2    = uv_width * 0.5f;
        float uv_height_2   = uv_height * 0.5f;

        float uv_xTopLeft   = uv_x,                 uv_yTopLeft     = uv_y;
        float uv_xTop       = uv_x + uv_width_2,    uv_yTop         = uv_y;
        float uv_xTopRight  = uv_x + uv_width,      uv_yTopRight    = uv_y;

        float uv_xMidLeft   = uv_x,                 uv_yMidLeft     = uv_y + uv_height_2;
        float uv_xMid       = uv_x + uv_width_2,    uv_yMid         = uv_y + uv_height_2;
        float uv_xMidRight  = uv_x + uv_width,      uv_yMidRight    = uv_y + uv_height_2;

        float uv_xBotLeft   = uv_x,                 uv_yBotLeft     = uv_y + uv_height;
        float uv_xBot       = uv_x + uv_width_2,    uv_yBot         = uv_y + uv_height;
        float uv_xBotRight  = uv_x + uv_width,      uv_yBotRight    = uv_y + uv_height;

        Vertex vTopLeft = new Vertex(
                    new Vector3f(xTopLeft, yTopLeft),
                    new Color4f(0.5f, alphaValues[0]), 
                    new Vector2f(uv_xTopLeft, uv_yTopLeft)
                );
        
        Vertex vTop = new Vertex(
                    new Vector3f(xTop, yTop),
                    new Color4f(0.5f,alphaValues[1]),
                    new Vector2f(uv_xTop, uv_yTop)
                );
        
        Vertex vTopRight = new Vertex(
                    new Vector3f(xTopRight, yTopRight),
                    new Color4f(0.5f, alphaValues[2]),
                    new Vector2f(uv_xTopRight, uv_yTopRight)
                );

        Vertex vMidLeft = new Vertex(
                    new Vector3f(xMidLeft, yMidLeft),
                    new Color4f(0.5f, alphaValues[3]),
                    new Vector2f(uv_xMidLeft, uv_yMidLeft)
                );
        
        Vertex vMid = new Vertex(
                        new Vector3f(xMid, yMid),
                        new Color4f(0.5f, alphaValues[4]),
                        new Vector2f(uv_xMid, uv_yMid)
                    );
        
        Vertex vMidRight = new Vertex(
                    new Vector3f(xMidRight, yMidRight),
                    new Color4f(0.5f, alphaValues[5]),
                    new Vector2f(uv_xMidRight, uv_yMidRight)
                );

        Vertex vBotLeft = new Vertex(
                    new Vector3f(xBotLeft, yBotLeft),
                    new Color4f(0.5f, alphaValues[6]),
                    new Vector2f(uv_xBotLeft, uv_yBotLeft)
                );
        
        Vertex vBot = new Vertex(
                    new Vector3f(xBot, yBot),
                    new Color4f(0.5f, alphaValues[7]),
                    new Vector2f(uv_xBot, uv_yBot)
                );
        
        Vertex vBotRight = new Vertex(
                    new Vector3f(xBotRight, yBotRight), 
                    new Color4f(0.5f, alphaValues[8]),
                    new Vector2f(uv_xBotRight, uv_yBotRight)
                );

        return new Vertex[] {
                // Upper Top Left
                vTopLeft, vTop, vMidLeft,
                // Lower Top Left
                vMidLeft, vTop, vMid, 

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
                vBot, vBotRight, vMidRight };
    }
    
    private static Vertex[] generate4Quad(float x, float y, float width, float height, float uv_x, float uv_y, float uv_width, float uv_height) {
        return new Vertex[] {
                new Vertex(new Vector3f(          x,          y, 0.0f), new Color4f(0.5f), new Vector2f(uv_x,            uv_y)),
                new Vertex(new Vector3f(  x + width,          y, 0.0f), new Color4f(0.5f), new Vector2f(uv_x + uv_width, uv_y)),  
                new Vertex(new Vector3f(          x, y + height, 0.0f), new Color4f(0.5f), new Vector2f(uv_x,            uv_y + uv_height)),
                new Vertex(new Vector3f(  x + width,          y, 0.0f), new Color4f(0.5f), new Vector2f(uv_x + uv_width, uv_y)),  
                new Vertex(new Vector3f(          x, y + height, 0.0f), new Color4f(0.5f), new Vector2f(uv_x,            uv_y + uv_height)),
                new Vertex(new Vector3f(  x + width, y + height, 0.0f), new Color4f(0.5f), new Vector2f(uv_x + uv_width, uv_y + uv_height)),
          };
    }  
    
    private static Vertex[] generate4Quad(float x, float y, float width, float height, float uv_x, float uv_y, float uv_width, float uv_height, float[] alphaValues) {
        return new Vertex[] {
                new Vertex(new Vector3f(          x,          y, 0.0f), new Color4f(0.5f, alphaValues[0]), new Vector2f(uv_x,            uv_y)),
                new Vertex(new Vector3f(  x + width,          y, 0.0f), new Color4f(0.5f, alphaValues[2]), new Vector2f(uv_x + uv_width, uv_y)),  
                new Vertex(new Vector3f(          x, y + height, 0.0f), new Color4f(0.5f, alphaValues[6]), new Vector2f(uv_x,            uv_y + uv_height)),
                new Vertex(new Vector3f(  x + width,          y, 0.0f), new Color4f(0.5f, alphaValues[2]), new Vector2f(uv_x + uv_width, uv_y)),  
                new Vertex(new Vector3f(          x, y + height, 0.0f), new Color4f(0.5f, alphaValues[6]), new Vector2f(uv_x,            uv_y + uv_height)),
                new Vertex(new Vector3f(  x + width, y + height, 0.0f), new Color4f(0.5f, alphaValues[8]), new Vector2f(uv_x + uv_width, uv_y + uv_height)),
          };
    }  

}
