package de.orangestar.engine.render.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.render.batch.BatchFactory;
import de.orangestar.engine.render.batch.StreamingBatch;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Vector2f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vertex;

public class ATileMap extends Actor {

    
    public ATileMap(Material material) {
        _batch = BatchFactory.createStreaming(material, 32768);
    }
    
    public void setWidth(int width) {

    }
    
    public void setHeight(int height) {
        
    }
    
    public void setMaterial(Material mat) {
        _batch.setMaterial(mat);
    }
    
    public Material getMaterial() {
        return _batch.getMaterial();
    }
    
    public void setData(int[][] data) {
        _batch.clear();
        
        _data = data;
        List<Vertex> vertices = new ArrayList<>(data.length * data[0].length * 4);
        
        for(int i = 0; i < data.length; i++) {
            for (int p = 0; p < data[i].length; p++) {
                if (data[i][p] == 0) {
                    vertices.addAll( Arrays.asList(generateQuad( i * 32, p * 32, 32, 32, 0f, 0f, 0.5f, 1f)));
                } else {
                    vertices.addAll( Arrays.asList(generateQuad( i * 32, p * 32, 32, 32, 0.5f, 0f, 0.5f, 1f)));
                }
                
            }
        }
        
        _batch.addVertexData(vertices);
    }
    
    @Override
    public void onRender() {
        _batch.render(PrimitiveType.TRIANGLES);
    }

    private int[][]         _data;
    private StreamingBatch  _batch;
    
    private static Vertex[] generateQuad(float x, float y, float width, float height, float uv_x, float uv_y, float uv_width, float uv_height) {
        return new Vertex[] {
                new Vertex(new Vector3f(          x,          y, 0.0f), new Color4f(0.5f, 0.5f, 0.5f, 1f), new Vector2f(uv_x,            uv_y)),
                new Vertex(new Vector3f(  x + width,          y, 0.0f), new Color4f(0.5f, 0.5f, 0.5f, 1f), new Vector2f(uv_x + uv_width, uv_y)),  
                new Vertex(new Vector3f(          x, y + height, 0.0f), new Color4f(0.5f, 0.5f, 0.5f, 1f), new Vector2f(uv_x,            uv_y + uv_height)),
                new Vertex(new Vector3f(  x + width,          y, 0.0f), new Color4f(0.5f, 0.5f, 0.5f, 1f), new Vector2f(uv_x + uv_width, uv_y)),  
                new Vertex(new Vector3f(          x, y + height, 0.0f), new Color4f(0.5f, 0.5f, 0.5f, 1f), new Vector2f(uv_x,            uv_y + uv_height)),
                new Vertex(new Vector3f(  x + width, y + height, 0.0f), new Color4f(0.5f, 0.5f, 0.5f, 1f), new Vector2f(uv_x + uv_width, uv_y + uv_height)),
          };
    }
    
}
