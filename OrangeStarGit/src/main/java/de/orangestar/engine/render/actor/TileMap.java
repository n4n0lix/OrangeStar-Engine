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
 * A 'classic' tileset, one image per tile, no alpha-blending.
 * 
 * @author Basti
 */
public class TileMap extends Actor {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public TileMap(Texture texture, int tileWidth, int tileHeight) {
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
    
    public void setData(int[][] data) {
        _batch.clear();
        
        List<Vertex> vertices = new ArrayList<>(data.length * data[0].length * 4);
        List<Integer> indices = new ArrayList<>(data.length * data[0].length * 6);
        
        
        for(int i = 0; i < data.length; i++) {
            for (int p = 0; p < data[i].length; p++) {
                
                int subimageID  = data[i][p];
                int subimageX   = subimageID % _tilesPerRow;
                int subimageY   = subimageID / _tilesPerColumn;
                float uvSubimageWidth  = 1f / _textureWidth  * _tileWidth;
                float uvSubimageHeight = 1f / _textureHeight * _tileHeight;
                
                vertices.addAll( Arrays.asList(generateQuad( i * _tileWidth, 
                                                             p * _tileHeight, 
                                                             _tileWidth, 
                                                             _tileHeight, 
                                                             subimageX * uvSubimageWidth, 
                                                             subimageY * uvSubimageHeight, 
                                                             uvSubimageWidth, 
                                                             uvSubimageHeight)));
            }
        }
        
        _batch.addVertexData(vertices);
        _batch.addIndicesData(indices);
    }
    
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
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private static Vertex[] generateQuad(float x, float y, float width, float height, float uv_x, float uv_y, float uv_width, float uv_height) {
        return new Vertex[] {
                new Vertex(new Vector3f(          x,          y, 0.0f), new Color4f(0.5f), new Vector2f(uv_x,            uv_y)),
                new Vertex(new Vector3f(  x + width,          y, 0.0f), new Color4f(0.5f), new Vector2f(uv_x + uv_width, uv_y)),  
                new Vertex(new Vector3f(          x, y + height, 0.0f), new Color4f(0.5f), new Vector2f(uv_x,            uv_y + uv_height)),
                new Vertex(new Vector3f(  x + width,          y, 0.0f), new Color4f(0.5f), new Vector2f(uv_x + uv_width, uv_y)),  
                new Vertex(new Vector3f(          x, y + height, 0.0f), new Color4f(0.5f), new Vector2f(uv_x,            uv_y + uv_height)),
                new Vertex(new Vector3f(  x + width, y + height, 0.0f), new Color4f(0.5f), new Vector2f(uv_x + uv_width, uv_y + uv_height)),
          };
    }  
    
}
