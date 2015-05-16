package de.orangestar.engine.render;

import org.lwjgl.opengl.GL11;

public enum PrimitiveType {
    POINTS(GL11.GL_POINTS),
    LINES(GL11.GL_LINES),
    LINE_STRIP(GL11.GL_LINE_STRIP),
    TRIANGLES(GL11.GL_TRIANGLES),
    TRIANGLE_STRIP(GL11.GL_TRIANGLE_STRIP),
    QUADS(GL11.GL_QUADS),
    QUAD_STRIP(GL11.GL_QUAD_STRIP);
    
    private PrimitiveType(int id) {
        _primitiveType = id;
    }
    
    public int getGLId() {
        return _primitiveType;
    }
    
    private final int _primitiveType;
}
