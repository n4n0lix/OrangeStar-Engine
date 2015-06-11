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
public class ModernTileMap extends TileMap {

    public ModernTileMap(Texture texture, int tileWidth, int tileHeight) {
		super(texture, tileWidth, tileHeight);
	}
    
    @Override
    public void setData(int[][] data) {
    	
    }

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private static Vertex[] generate9Quad(float x, float y, float width, float height, float uv_x, float uv_y, float uv_width, float uv_height) {
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
