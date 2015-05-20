package de.orangestar.engine.render.batch;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL33;

import de.orangestar.engine.debug.DebugManager;
import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Matrix4f.Order;
import de.orangestar.engine.values.Transform;
import de.orangestar.engine.values.Vertex;

/**
 * A batch to bundle draw calls with the same material and topology.
 * @author Basti
 */
public class InstancingBatch extends Batch {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Adds the given instances to this batch.
     * @param transforms A list of transformations that each represents an instance
     */
    public void addInstances(List<Transform> transforms) {
        // 1# Create buffer
        FloatBuffer buffer = BufferUtils.createFloatBuffer(_shader.getMaxInstances() * Matrix4f.ComponentsCount);
        for(Transform transform : transforms) {
            transform.toMatrix4f().writeTo(buffer, Order.COLUMN_MAJOR);
        }
        buffer.flip();

        // 2# Write data
        GL30.glBindVertexArray(_vaoId);
        
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, _iboId);
            GL15.glBufferSubData( GL15.GL_ARRAY_BUFFER, _iboNumInstances * Matrix4f.ByteSize, buffer);
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0);
            
        GL30.glBindVertexArray(0);
        _iboNumInstances  += transforms.size();
    }
    
    @Override
    public boolean isFull() {
        return _iboNumInstances >= _material.getShader().getMaxInstances();
    }
    
    @Override
    public void destroy() {
        super.destroy();
        
        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0 );
        GL15.glDeleteBuffers( _vboId );
        _vboId = 0;
        
        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0 );
        GL15.glDeleteBuffers( _iboId );
        _iboId = 0;
        
        GL30.glBindVertexArray( 0 );
        GL30.glDeleteVertexArrays( _vaoId );
        _vaoId = 0;
    }
    
    @Override
    public void render(PrimitiveType type) {
        DebugManager.Get().glClearError();
        Shader shader = _material.getShader();
        Texture texture = _material.getTexture();
        
        shader.bind();
        GL30.glBindVertexArray( _vaoId );
        
        if (shader.isTextured() && texture != null) {
            texture.setAsActiveTexture(shader);
        }
        GL31.glDrawArraysInstanced(type.getGLId(), 0, _vboNumVertices, _iboNumInstances);

        GL30.glBindVertexArray( 0 );
        _material.getShader().unbind();
        DebugManager.Get().glCheckError("InstancingBatch2 - render");
    }
    
    @Override
    public void clear() {
        _iboNumInstances = 0;
    }
    
    @Override
    public boolean isEmpty() {
        return _iboNumInstances == 0;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    InstancingBatch(List<Vertex> vertices, Material mat) {
        _material = mat;
        _shader   = _material.getShader();
        
        _vboNumVertices = vertices.size();
        
        // 1# Write the vertices to a java buffer
        FloatBuffer buffer = BufferUtils.createByteBuffer(_vboNumVertices * Vertex.ByteSize).asFloatBuffer();
        for(Vertex vertex : vertices) {
            vertex.writeToFloatBuffer(buffer);
        }
        buffer.flip();
        
        // 2# Setup OpenGL objects and fill the VBO with the vertexbuffer
        // VAO
        _vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(_vaoId);
        
        // VBO
        _vboId = GL15.glGenBuffers();
        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, _vboId);
        GL15.glBufferData( GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW );
            
        _shader.layoutVBO();
        
        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0);
        
        // Instance Buffer
        _iboId = GL15.glGenBuffers();
        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, _iboId);
        GL15.glBufferData( GL15.GL_ARRAY_BUFFER, _material.getShader().getMaxInstances() * Matrix4f.ByteSize, null, GL15.GL_STREAM_DRAW );
        
        _shader.layoutIBO();
        
        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Material _material;
    private Shader   _shader;

    private int _vaoId;
    
    private int _vboId;
    private int _vboNumVertices;

    private int _iboId;
    private int _iboNumInstances;
    
}
