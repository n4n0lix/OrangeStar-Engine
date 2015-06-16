package de.orangestar.engine.render.batch;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;

import de.orangestar.engine.debug.DebugManager;
import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.values.Vertex;

/**
 * A batch to bundle draw calls with the same material.
 * The batch renders every given vertex. If at least one index is given via {@link addIndicesData} this batch switches
 * to indexed rendering.
 * @author Basti
 */
public class StreamingBatch extends Batch {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Inner Classes                            */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Creates a batch used for bundling vertices that use the same material for rendering.
     * (Indexed rendering is optional)
     * 
     * (Default) Max Vertices: 116508 (4 megabytes of vertex data)
     * (Default) Max Indices:  262144 (1 megabyte of index data)
     *
     */
    public static class Builder {
        
        public Builder material(Material material) {
            _material = material;
            return this;
        }
        
        public Builder vertices(int maxVertices) {
            _maxVertices = maxVertices;
            return this;
        }
        
        public Builder indices(int maxIndices) {
            _maxIndices = maxIndices;
            return this;
        }
        
        public StreamingBatch build() {
            if (!(_maxVertices > 0))
                throw new IllegalArgumentException("Cannot create empty batch.");
            
            if (!(_maxIndices >= 0))
                throw new IllegalArgumentException("Cannot create batch with negative index amount.");

            if (_material == null) 
                throw new IllegalArgumentException("Material is null.");
            
            if (_material.getShader() == null) 
                throw new IllegalArgumentException("Shader is null.");
            
            return new StreamingBatch(_material, _maxVertices, _maxIndices);
        }
        
        private Material _material;
        private int      _maxVertices = 116508;
        private int      _maxIndices  = 262144;
        
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * If this batch has space to store the given vertices.
     * @param vertices A list of vertices
     */
    public boolean hasEnoughSpaceVertices(List<Vertex> vertices) {
        return _vertices.size() + vertices.size() <= _maxNumVertices;
    }
    
    /**
     * If this batch has space to store the given indices.
     * @param vertices A list of vertices
     */
    public boolean hasEnoughSpaceIndices(List<Integer> indices) {
        return _indices.size() + indices.size() <= _maxNumIndices;
    }
    
    @Override
    public boolean isEmpty() {
        return _vertices.isEmpty();
    }
    
    @Override
    public boolean isFull() {
        return _vertices.size() >= _maxNumVertices;
    }
    
    /**
     * Adds the given vertices to this batch.
     * @param vertices A list of vertices
     * 
     * @require hasEnoughSpace(vertices)
     */
    public void addVertexData(Vertex... vertices) {
        addVertexData(Arrays.asList(vertices));
    }
    
    /**
     * Adds the given vertices to this batch.
     * @param vertices A list of vertices
     * 
     * @require hasEnoughSpace(vertices)
     */
    public void addVertexData(List<Vertex> vertices) {
        if(!hasEnoughSpaceVertices(vertices)) {
            throw new IllegalArgumentException("Vertex data is too large for batch.");
        }
        
        _vertices.addAll(vertices);
        _hasChanged = true;
    }
    
    /**
     * Adds indices to the indexbuffer.
     * @param indices
     */
    public void addIndicesData(List<Integer> indices) {
        if(!hasEnoughSpaceIndices(indices)) {
            throw new IllegalArgumentException("Index data is too large for batch.");
        }
        
        _indices.addAll(_indices);
    }
    
    /**
     * Returns the material of this batch.
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * Sets the material used by this batch.
     * @param mat
     */
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
        if (_hasChanged) {
            sendVerticesToGPU();
            _hasChanged = false;
        }
        
        boolean isIndexedRendering = !_indices.isEmpty();

        _material.getShader().bind();
        _material.getTexture().setAsActiveTexture(_material.getShader());
        
        GL30.glBindVertexArray( _idVAO );

        if (isIndexedRendering) {
            IntBuffer intBuffer = BufferUtils.createIntBuffer(_indices.size());
            for(Integer i : _indices) {
                intBuffer.put(i);
            }
            intBuffer.flip();
            
            GL11.glDrawElements(type.glConst(), intBuffer);
        } else {
            GL11.glDrawArrays( type.glConst(), 0, _vertices.size() );
        }

        GL30.glBindVertexArray( 0 );
        _material.getShader().unbind();
    }
    
    @Override
    public void clear() {
        _vertices.clear();
        _indices.clear();
    }
    
    @Override
    public void onDestroy() {
        GL15.glDeleteBuffers(_idVertexBuffer);
        GL30.glDeleteVertexArrays(_idVAO);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Package-Private Constructor
     * @param mat The material for which vertices will be bundled
     * @param maxVertices The maximum amount of vertices that can be stored by this batch
     */
    StreamingBatch(Material mat, int maxVertices, int maxIndices) {
        _material = mat;
        _maxNumVertices = maxVertices;
        _maxNumIndices  = maxIndices;
        _vertices = new ArrayList<>();
        _indices = new ArrayList<>();
        
        // Setup OpenGL objects
        // VAO
        _idVAO = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(_idVAO);
        
            // VBO
            _idVertexBuffer = GL15.glGenBuffers();
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, _idVertexBuffer);
            GL15.glBufferData( GL15.GL_ARRAY_BUFFER, _maxNumVertices * Vertex.ByteSize, null, GL15.GL_STREAM_DRAW );
                
            _material.getShader().layoutVBO();
            
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0);
            
        GL30.glBindVertexArray(0);   
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private void sendVerticesToGPU() {
        // 1# Create buffer
        FloatBuffer floatBuffer = BufferUtils.createByteBuffer(_vertices.size() * Vertex.ByteSize).asFloatBuffer();
        for(Vertex vertex : _vertices) {
            vertex.writeToFloatBuffer(floatBuffer);
        }
        floatBuffer.flip();      

        // 2# Send data to GPU
        GL30.glBindVertexArray(_idVAO);
        
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, _idVertexBuffer);
            GL15.glBufferSubData( GL15.GL_ARRAY_BUFFER, 0, floatBuffer);
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0);
        
        GL30.glBindVertexArray(0);
    }
    
    private List<Vertex> _vertices;
    private List<Integer> _indices;
    
    private boolean _hasChanged;
    
    private int _idVAO;
    private int _idVertexBuffer;

    private Material _material;
    
    private int _maxNumVertices;
    private int _maxNumIndices;
        
}
