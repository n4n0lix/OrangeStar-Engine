package de.orangestar.engine.render.actor;

import java.util.Arrays;

import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.batch.StreamingBatch;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Vector2f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vertex;

public class Image extends Actor {

    public Image(Texture tex) {
        Material material = new Material.Builder()
                    .texture(tex)
                    .shader(Shader.StreamingBatchShader)
                    .build();
        
        _batch = new StreamingBatch.Builder()
                                        .material(material)
                                        //.vertices(6)
                                        .build();
        
        _batch.addVertexData(generateQuad(0, 0, tex.getWidth(), tex.getHeight(), 0, 0, 1, 1));

    }
    
    @Override
    public void onRender() {
        _batch.render(PrimitiveType.TRIANGLES);
    }

    private StreamingBatch  _batch;

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
