package de.orangestar.engine.values;

import java.nio.FloatBuffer;

import de.orangestar.engine.values.pools.VertexPool;

/**
 * Represents a vertex in OpenGL space.
 * 
 * @author Oliver &amp; Basti
 */
public class Vertex {

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                      Public Static                      */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    
    public static final int POSITION_NUM_COMPONENTS  = Vector3f.NUM_COMPONENTS;
    public static final int COLOR_NUM_COMPONENTS     = Color4f.NUM_COMPONENTS;
    public static final int UV_NUM_COMPONENTS        = Vector2f.NUM_COMPONENTS;

    public static final int NUM_COMPONENTS           = POSITION_NUM_COMPONENTS + COLOR_NUM_COMPONENTS + UV_NUM_COMPONENTS;
    
    public static final int POSITION_BYTE_OFFSET     = 0;
    public static final int COLOR_BYTE_OFFSET        = POSITION_BYTE_OFFSET + Vector3f.BYTE_SIZE;
    public static final int UV_BYTE_OFFSET           = COLOR_BYTE_OFFSET    + Color4f.BYTE_SIZE;
    
    public static final int BYTE_SIZE                = Vector3f.BYTE_SIZE + Color4f.BYTE_SIZE + Vector2f.BYTE_SIZE;
        
    public static final VertexPool POOL              = new VertexPool();
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor. Position is (0,0,0), Color is white, TexCoord is (0,0)
     */
    public Vertex() {
        this(Vector3f.zero(), Color4f.white(), Vector2f.zero());
    }
    
    /**
     * Public-Constructor.
     * @param pos The position
     * @param col The color
     * @param tex The texcoords
     */
    public Vertex(Vector3f pos, Color4f col, Vector2f tex) {
        position = pos;
        color = col;
        texCoord = tex;
    }
    
    /**
     * Writes this vertex into a float buffer.
     * @param buffer The float buffer
     */
    public void writeToFloatBuffer(FloatBuffer buffer) {
        buffer.put( new float[] {
                        position.x, position.y, position.z,
                        color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(),
                        texCoord.x, texCoord.y
                    } );
    }

    @Override
    public String toString() {
        return "Vertex [position=" + position + ", color=" + color + ", texCoord=" + texCoord + "]";
    }

    public Vector3f position;
    public Color4f  color;
    public Vector2f texCoord;

}
