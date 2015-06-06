package de.orangestar.engine.values;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Vertex {

    public static final int PositionComponentsCount  = Vector3f.ComponentsCount;
    public static final int ColorComponentsCount     = Color4f.ComponentsCount;
    public static final int UVComponentsCount        = Vector2f.ComponentsCount;

    public static final int ComponentsCount          = PositionComponentsCount + ColorComponentsCount + UVComponentsCount;
    
    public static final int PositionByteOffset       = 0;
    public static final int ColorByteOffset          = PositionByteOffset + Vector3f.ByteSize;
    public static final int UVByteOffset             = ColorByteOffset    + Color4f.ByteSize;
    
    public static final int ByteSize                 = Vector3f.ByteSize + Color4f.ByteSize + Vector2f.ByteSize;
        
    public Vector3f position;
    public Color4f  color;
    public Vector2f texCoord;

    public Vertex(Vector3f pos, Color4f col, Vector2f tex) {
        position = pos;
        color = col;
        texCoord = tex;
    }
    
    public void writeToFloatBuffer(FloatBuffer buffer) {
        buffer.put( new float[] {
                        position.x, position.y, position.z,
                        color.r, color.g, color.b, color.a,
                        texCoord.x, texCoord.y
                    } );
    }

}
