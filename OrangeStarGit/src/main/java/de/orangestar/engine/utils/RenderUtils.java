package de.orangestar.engine.utils;

import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Vertex;

public class RenderUtils {

    public static Vertex[] generate2DQuadVertices(float x, float y, float width, float height, float uvLeft, float uvTop, float uvRight, float uvBottom) {
        Vertex v1 = Vertex.POOL.get();
        Vertex v2 = Vertex.POOL.get();
        Vertex v3 = Vertex.POOL.get();
        Vertex v4 = Vertex.POOL.get();
        
        // Vertex #0
        v1.position.x = x;
        v1.position.y = y;
        
        v1.texCoord.x = uvLeft;
        v1.texCoord.y = uvTop;
        
        Color4f.set(v1.color, 0.5f, 0.5f, 0.5f, 1f);
        
        // Vertex #1
        v2.position.x = x + width;
        v2.position.y = y;
        
        v2.texCoord.x = uvRight;
        v2.texCoord.y = uvTop;
        
        Color4f.set(v2.color, 0.5f, 0.5f, 0.5f, 1f);
        
        // Vertex #2
        v3.position.x = x;
        v3.position.y = y + height;
        
        v3.texCoord.x = uvLeft;
        v3.texCoord.y = uvBottom;
        
        Color4f.set(v3.color, 0.5f, 0.5f, 0.5f, 1f);
        
        // Vertex #3
        v4.position.x = x + width;
        v4.position.y = y + height;
        
        v4.texCoord.x = uvRight;
        v4.texCoord.y = uvBottom;
        
        Color4f.set(v4.color, 0.5f, 0.5f, 0.5f, 1f);
        
        return new Vertex[] { v1, v2, v3, v4 };
    }
    
    public static int[] generate2DQuadIndices() {
        return new int[] { 0, 2, 1, 1, 2, 3 };
    }
    
    public static int[] generate2DQuadIndices(int offset) {
        return new int[] { offset, offset+2, offset+1, offset+1, offset+2, offset+3 };
    }
    
}
