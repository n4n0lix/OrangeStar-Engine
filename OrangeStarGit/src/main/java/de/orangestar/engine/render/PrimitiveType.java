package de.orangestar.engine.render;

import org.lwjgl.opengl.GL11;

/**
 * Simple mapping of OpenGL primitive rendering type constants to an enum set.
 * 
 * @author Oliver &amp; Basti
 */
public enum PrimitiveType {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    POINTS          (GL11.GL_POINTS),
    LINES           (GL11.GL_LINES),
    LINE_STRIP      (GL11.GL_LINE_STRIP),
    TRIANGLES       (GL11.GL_TRIANGLES),
    TRIANGLE_STRIP  (GL11.GL_TRIANGLE_STRIP),
    QUADS           (GL11.GL_QUADS),
    QUAD_STRIP      (GL11.GL_QUAD_STRIP);
   
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Returns the OpenGL Constant.
     * @return The OpenGL Constant
     */
    public int glConst() {
        return _primitiveType;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private PrimitiveType(int id) {
        _primitiveType = id;
    }

    private final int _primitiveType;
    
}
