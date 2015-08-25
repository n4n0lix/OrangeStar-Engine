package de.orangestar.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;

import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.utils.Deinitializable;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Vertex;

/**
 * A batch to bundle draw calls with the same material.
 * The batch renders every given vertex. If at least one index is given via {@link addIndicesData} this batch switches
 * to indexed rendering.
 * 
 * Note: All vertices given to this batch are released to the vertex pool {@linkplain Vertex#POOL} after being send to the GPU.
 * 
 * @author Oliver &amp; Basti
 */
public class Batch implements Deinitializable {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Inner Classes                            */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Creates a batch used for bundling vertices that use the same material for rendering.<br>
     * (Indexed &amp; instanced rendering is optional)<br>
     * <br>
     * (Default) Max Vertices:   116508 (4 megabytes of vertex data)<br>
     * (Default) Max Indices:    262144 (1 megabyte of index data)<br>
     * (Default) Max Instances:   32768 (2 megabyte of instance data)<br>
     */
    public static class Builder {
        
        public Builder material(Material material) {
            _material = material;
            return this;
        }
        
        public Builder material(Texture texture, Shader shader, Color4f color) {
            _material = new Material(texture, shader, color);
            return this;
        }
        
        public Builder maxVertices(int maxVertices) {
            _maxVertices = maxVertices;
            return this;
        }
        
        public Builder noIndices() {
            _maxIndices = 0;
            return this;
        }
        
        public Builder maxIndices(int maxIndices) {
            _maxIndices = maxIndices;
            return this;
        }
        
        public Builder noInstances() {
            _maxInstances = 0;
            return this;
        }
        
        public Builder maxInstances(int maxInstances) {
            _maxInstances = maxInstances;
            return this;
        }
        
        /**
         * Builds a batch instance by its configuration.<br>
         * Preconditions: 
         * <ul>
         *  <li><code>material != null</code>
         *  <li><code>material.getShader() != null</code>
         *  <li><code>maxVertices &gt; 0</code>
         *  <li><code>maxIndices &gt;= 0</code>
         *  <li><code>maxInstances &gt;= 0</code>
         * </ul>
         * @return A batch instance
         */
        public Batch build() {
            // Preconditions:
            assert _material != null;
            assert _material.getShader() != null;
            assert _maxVertices > 0;
            assert _maxIndices >= 0;
            assert _maxInstances >= 0;
            
            // Code:
            return new Batch(_material, _maxVertices, _maxIndices, _maxInstances);
        }
        
        private Material _material;
        private int      _maxVertices  = 116508;
        private int      _maxIndices   = 262144;
        private int      _maxInstances =  32768;
        
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * If this batch has space to store the given vertices.
     * @param vertices A collection of vertices
     * @return If this batch has space to store the given vertices
     */
    public boolean hasEnoughSpaceVertices(Collection<Vertex> vertices) {
        return _vertices.size() + vertices.size() <= _maxNumVertices;
    }
    
    /**
     * If this batch has space to store the given indices.
     * @param indices An array of indices
     * @return If this batch has space to store the given indices
     */
    public boolean hasEnoughSpaceIndices(int[] indices) {
        return _curNumIndices + indices.length <= _maxNumIndices;
    }
    
    /**
     * If this batch has space to store the given indices.
     * @param instances A collection of instances
     * @return If this batch has space to store the given indices
     */
    public boolean hasEnoughSpaceInstances(Collection<Matrix4f> instances) {
        return _curNumInstances + instances.size() <= _maxNumInstances;
    }
    
    /**
     * Adds the given vertices to this batch. !All given vertices must be allocated via Vertex.Pool!
     * @param vertices A list of vertices
     */
    public void addVertexData(Vertex... vertices) {
        addVertexData(Arrays.asList(vertices));
    }
    
    /**
     * Adds the given vertices to this batch. !All given vertices must be allocated via Vertex.Pool!<br>
     * Preconditions: 
     * <ul>
     *  <li><code>hasEnoughSpaceVertices( vertices )</code>
     * </ul>
     * @param vertices A list of vertices
     */
    public void addVertexData(Collection<Vertex> vertices) {
        assert hasEnoughSpaceVertices(vertices);
        
        _vertices.addAll(vertices);
        _hasChangedVertices = true;
    }
        
    /**
     * Adds indices to the indexbuffer.<br>
     * Preconditions: 
     * <ul>
     *  <li><code>hasEnoughSpaceIndices( indices )</code>
     * </ul>
     * @param indices The indices
     */
    public void addIndicesData(int[] indices) {
        assert hasEnoughSpaceIndices( indices );

        _indices.add(indices);
        _curNumIndices += indices.length;
        _hasChangedIndices = true;
    }
    
    /**
     * Adds indices to the indexbuffer. !All given instances must be allocated via Matrix4f.Pool!
     * @param instances The instances
     */
    public void addInstancesData(Matrix4f... instances) {
        addInstancesData(Arrays.asList(instances));
    }
    
    /**
     * Adds indices to the indexbuffer. !All given instances must be allocated via Matrix4f.Pool!<br>
     * Preconditions: 
     * <ul>
     *  <li><code>hasEnoughSpaceInstances(instances)</code>
     * </ul>
     * @param instances The instances
     */
    public void addInstancesData(Collection<Matrix4f> instances) {
        assert hasEnoughSpaceInstances(instances);

        _instances.addAll(instances);
        _hasChangedInstances = true;
    }

    /**
     * Returns the material of this batch.
     * @return The material of this batch
     */
    public Material getMaterial() {
        return _material;
    }

    @Override
    public void onDeinitialize() {
        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0 );
        
        GL15.glDeleteBuffers( _idVertexBuffer );
        _idVertexBuffer = 0;
        
        GL15.glDeleteBuffers( _idInstanceBuffer);
        _idVertexBuffer = 0;
        
        GL30.glBindVertexArray( 0 );
        GL30.glDeleteVertexArrays( _idVAO );
        _idVAO = 0;
    }

    /**
     * Renders the content of this batch.
     * @param type The openGL primitive type
     */
    public void render(IRenderEngine engine, PrimitiveType type) {  
        if (_material == null) {
            return;
        }
        
        updateVertices();
        updateIndices();
        updateInstances();
        
        boolean isIndexedRendering   = _curNumIndices > 0;
        boolean isInstancedRendering = _curNumInstances > 0;

        _material.getShader().updateWVP(engine);
        _material.getShader().bind();
        
        if (_material.getTexture() != null) {
            _material.getTexture().setAsActiveTexture(_material.getShader());
        }

        GL30.glBindVertexArray( _idVAO );

        if(isInstancedRendering && isIndexedRendering) {
            renderIndexedInstanced(type);
        }
        else if(isInstancedRendering) {
            renderInstanced(type);
        } 
        else if (isIndexedRendering) {
            renderIndexed(type);
        } 
        else {
            renderNormal(type);
        }

        GL30.glBindVertexArray( 0 );
    }
        
    /**
     * Clears this batch.
     */
    public void clear() {
        clearVertices();
        clearIndices();
        clearInstances();
    }
    
    /**
     * Clears all vertices of this batch.
     */
    public void clearVertices() {
        _vertices.clear();
        _curNumVertices = 0;
        _hasChangedVertices = true;
    }
    
    /**
     * Clears all indices of this batch.
     */
    public void clearIndices() {
        _indices.clear();
        _curNumIndices = 0;
        _hasChangedIndices = true;
    }

    /**
     * Clears all instances of this batch.
     */
    public void clearInstances() {
        _instances.clear();
        _curNumInstances = 0;
        _hasChangedInstances = true;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Package-Private Constructor
     * @param mat The material for which vertices will be bundled
     * @param maxVertices The maximum amount of vertices that can be stored by this batch
     */
    Batch(Material mat, int maxVertices, int maxIndices, int maxInstances) {
        _material = mat;
        
        _maxNumVertices = maxVertices;
        _maxNumIndices  = maxIndices;
        _maxNumInstances = maxInstances;
        
        _vertices  = new ArrayList<>();
        _indices   = new ArrayList<>();
        _instances = new ArrayList<>();
        
        // Setup OpenGL objects
        // VAO
        _idVAO = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(_idVAO);

            // VBO
            _idVertexBuffer = GL15.glGenBuffers();
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, _idVertexBuffer);
            GL15.glBufferData( GL15.GL_ARRAY_BUFFER, _maxNumVertices * Vertex.BYTE_SIZE, null, GL15.GL_STREAM_DRAW );

            _material.getShader().layoutVBO();

            if (_maxNumInstances != 0) {
                // IBO
                _idInstanceBuffer = GL15.glGenBuffers();
                GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, _idInstanceBuffer);
                GL15.glBufferData( GL15.GL_ARRAY_BUFFER, _maxNumInstances * Matrix4f.BYTE_SIZE, null, GL15.GL_STREAM_DRAW );

                _material.getShader().layoutInstancedRendering();
            }

            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0);
            
        GL30.glBindVertexArray(0);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Renders the content of this batch in normal mode.
     * @param type The openGL primitive type
     */
    private void renderNormal(PrimitiveType type) {
        _material.getShader().setInstanced(false);
        GL11.glDrawArrays( type.glConst(), 0, _curNumVertices );
    }
    
    /**
     * Renders the content of this batch in indexed mode.
     * @param type The openGL primitive type
     */
    private void renderIndexed(PrimitiveType type) {
        _material.getShader().setInstanced(false);
        GL11.glDrawElements(type.glConst(), _indicesBuffer);
    }
    
    /**
     * Renders the content of this batch in instanced mode.
     * @param type The openGL primitive type
     */
    private void renderInstanced(PrimitiveType type) {        
        _material.getShader().setInstanced(true);
        GL31.glDrawArraysInstanced(type.glConst(), 0, _curNumVertices, _curNumInstances);
    }
    
    /**
     * Renders the content of this batch in indexed instance mode.
     * @param type The openGL primitive type
     */
    private void renderIndexedInstanced(PrimitiveType type) {        
        _material.getShader().setInstanced(true);
        GL31.glDrawElementsInstanced(type.glConst(), _indicesBuffer, _curNumInstances);
    }
    
    /**
     * Updates the batch if the vertex content of this batch has changed.
     */
    private void updateVertices() {
        if (!_hasChangedVertices) {
            return;
        }

        // 1# Create buffer
        _verticesBuffer = BufferUtils.createByteBuffer(_vertices.size() * Vertex.BYTE_SIZE).asFloatBuffer();
        
        for(Vertex vertex : _vertices) {
            vertex.writeToFloatBuffer(_verticesBuffer);
        }
        _verticesBuffer.flip();      

        // 2# Send data to GPU
        GL30.glBindVertexArray(_idVAO);
        
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, _idVertexBuffer);
            GL15.glBufferSubData( GL15.GL_ARRAY_BUFFER, 0, _verticesBuffer);
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0);
        
        GL30.glBindVertexArray(0);
        
        _curNumVertices = _vertices.size();
        
        // Discard all vertex data
        // Release the vertex instances into the VertexPool
        for(int i = 0; i < _curNumVertices; i++) {
            Vertex.POOL.release(_vertices.get(i));
        }
        _vertices.clear();
        _hasChangedVertices = false;
    }

    /**
     * Updates the batch if the index content of this batch has changed.
     */
    private void updateIndices() {
        if (!_hasChangedIndices) {
            return;
        }
        
        _indicesBuffer = BufferUtils.createIntBuffer(_curNumIndices);
        
        for(int i = 0; i < _indices.size(); i++) {
            _indicesBuffer.put(_indices.get(i));
        }
        
        _indicesBuffer.flip();
        
        // Discard all index data
        _hasChangedIndices = false;
    }

    /**
     * Updates the batch if the instanced content of this batch has changed.
     */
    private void updateInstances() {
        if (!_hasChangedInstances) {
            return;
        }
        
        _curNumInstances = _instances.size();
        
        // 1# Create buffer and put the instances transforms in it
        _instancesBuffer = BufferUtils.createFloatBuffer(_curNumInstances * Matrix4f.NUM_COMPONENTS);
        for(int i = 0; i < _curNumInstances; i++) {
            _instances.get(i).writeTo(_instancesBuffer);
        }
        
        _instancesBuffer.flip();
        
        // 2# Write data the buffer into the opengl buffer
        GL30.glBindVertexArray(_idVAO);
      
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, _idInstanceBuffer);
            GL15.glBufferSubData( GL15.GL_ARRAY_BUFFER, 0, _instancesBuffer);
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0);
          
        GL30.glBindVertexArray(0);
        
        // 3# Discard all cpu data
        for(int i = 0; i < _curNumInstances; i++) {
            Matrix4f.POOL.release(_instances.get(i));
        }
        _instances.clear();
        _hasChangedInstances = false;
    }

    // Vertices
    private List<Vertex> _vertices;
    private FloatBuffer  _verticesBuffer;
    private int          _maxNumVertices;
    private int          _curNumVertices;
    private boolean      _hasChangedVertices;
        
    // Indices
    private List<int[]>  _indices;
    private IntBuffer    _indicesBuffer;
    private int          _maxNumIndices;
    private int          _curNumIndices;
    private boolean      _hasChangedIndices;
    
    // Instances
    private List<Matrix4f>  _instances;
    private FloatBuffer     _instancesBuffer;
    private int             _maxNumInstances;
    private int             _curNumInstances;
    private boolean         _hasChangedInstances;
    

    // Buffers
    private int _idVAO;
    private int _idVertexBuffer;
    private int _idInstanceBuffer;

    // Material
    private Material _material;
    
}
