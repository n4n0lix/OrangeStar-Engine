package de.orangestar.engine.render.batch;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import de.orangestar.engine.debug.DebugManager;
import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.values.Vertex;

/**
 * A batch to bundle draw calls with the same material.
 * @author Basti
 */
public class StreamingBatch extends Batch {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Whether this batch has space to store the given vertices.
     * @param vertices A list of vertices
     */
    public boolean hasEnoughSpace(List<Vertex> vertices) {
        return _numVertices + vertices.size() <= _maxNumVertices;
    }
    
    @Override
    public boolean isEmpty() {
        return _numVertices == 0;
    }
    
    @Override
    public boolean isFull() {
        return _numVertices >= _maxNumVertices;
    }
    
    /**
     * Adds the given vertices to this batch.
     * @param vertices A list of vertices
     * 
     * @require hasEnoughSpace(vertices)
     */
    public void addVertexData(List<Vertex> vertices) {
        if(!hasEnoughSpace(vertices)) {
            DebugManager.Get().info(StreamingBatch.class, "Vertex data is too large for batch. Data is discarded.");
        }
        
        // 1# Create buffer
        FloatBuffer buffer = BufferUtils.createByteBuffer(vertices.size() * Vertex.ByteSize).asFloatBuffer();
        for(Vertex vertex : vertices) {
            vertex.writeToFloatBuffer(buffer);
        }
        buffer.flip();

        // 2# Write data
        GL30.glBindVertexArray(_idVAO);
        
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, _idVertexBuffer);
            GL15.glBufferSubData( GL15.GL_ARRAY_BUFFER, _numVertices * Vertex.ByteSize, buffer);
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0);
            
        GL30.glBindVertexArray(0);
        _numVertices  += vertices.size();
    }
    
    /**
     * Returns the material of this batch.
     */
    public Material getMaterial() {
        return _material;
    }
    
    
    public void setMaterial(Material mat) {
        _material = mat;
    }
    
    @Override
    public void destroy() {
        super.destroy();
        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0 );
        GL15.glDeleteBuffers( _idVertexBuffer );
        _idVertexBuffer = 0;
        
        GL30.glBindVertexArray( 0 );
        GL30.glDeleteVertexArrays( _idVAO );
        _idVAO = 0;
    }
    
    @Override
    public void render(PrimitiveType type) {  
        _material.getShader().bind();
        GL30.glBindVertexArray( _idVAO );

        GL11.glDrawArrays( type.getGLId(), 0, _numVertices );
        
        GL30.glBindVertexArray( 0 );
        _material.getShader().unbind();
    }
    
    @Override
    public void clear() {
        _numVertices  = 0;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Package-Private Constructor
     * @param mat The material for which vertices will be bundled
     * @param maxSize The maximum amount of vertices that can be stored by this batch
     */
    StreamingBatch(Material mat, int maxSize) {
        _material = mat;
        _maxNumVertices = maxSize;
        
        // 1# Setup OpenGL objects
        // VAO
        _idVAO = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(_idVAO);
        
        // VBO
        _idVertexBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, _idVertexBuffer);
        GL15.glBufferData( GL15.GL_ARRAY_BUFFER, _maxNumVertices * Vertex.ByteSize, null, GL15.GL_STREAM_DRAW );
            
        _material.getShader().layoutVBO();
        
        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, _idVertexBuffer);
            
        GL30.glBindVertexArray(0);   

        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    
    private int _idVAO;
    private int _idVertexBuffer;

    private Material _material;
    
    private int _numVertices;
    private int _maxNumVertices;
        
}
