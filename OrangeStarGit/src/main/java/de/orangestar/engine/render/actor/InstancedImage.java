package de.orangestar.engine.render.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import de.orangestar.engine.render.Batch;
import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.utils.RenderUtils;
import de.orangestar.engine.values.Anchor;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Transform;
import de.orangestar.engine.values.Vector2f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vertex;

/**
 * The instanced-rendering supported version of {@link Image}.
 * 
 * @author Oliver &amp; Basti
 */
public class InstancedImage extends Actor {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * The InstancedImage-Builder class.
     * 
     * @author Oliver &amp; Basti
     */
    public static class Builder {

        /**
         * The texture to use.
         * @param texture A texture
         * @return <i>this</i>
         */
        public Builder texture(Texture texture) {
            _texture = texture;
            return this;
        }

        /**
         * The anchor of this image.
         * @param anchor The anchor
         * @return <i>this</i>
         */
        public Builder anchor(Anchor anchor) {
            _anchor = anchor;
            return this;
        }

        /**
         * The size of the image in the world.
         * @param width The width of the image in the world
         * @param height The width of the image in the world
         * @return <i>this</i>
         */
        public Builder size(float width, float height) {
            _width = width;
            _height = height;
            return this;
        }

        /**
         * Sets the sorter for the rendering order of the instances.
         * @param sorter A transform comparator
         * @return <i>this</i>
         */
        public Builder sorted(Comparator<Transform> sorter) {
            _sorter = sorter;
            return this;
        }
        
        /**
         * Builds the instance.<br>
         * Preconditions: 
         * <ul>
         *  <li><code>texture != null</code>
         *  <li><code>anchor != null</code>
         * </ul>
         * @return The build instance
         */
        public InstancedImage build() {
            // Preconditions:
            assert _texture != null;
            assert _anchor != null;
            
            // Code:
            if (_width < 0 || _height < 0) {
                _width = _texture.getWidth();
                _height = _texture.getHeight();
            }
            
            if (_sorter == null) {
                _sorter = (Transform o1, Transform o2) -> { return 0; };
            }

            return construct();
        }
        
        private InstancedImage construct() {
            InstancedImage result = new InstancedImage();
            

            Material material = new Material.Builder()
                                            .texture(_texture)
                                            .shader(Shader.GodShader)
                                            .build();
            
            
            result._batch = new Batch.Builder()
                                        .material(material)
                                        .maxVertices(4)
                                        .maxIndices(6)
                                        .build();
            
            result._instances = new ArrayList<>();
            result._instancesSorter = _sorter;
            result._width  = _width;
            result._height = _height;
            
            result.refreshRenderData();

            result.setAnchor(_anchor);
            
            return result;
        }
        
        private Comparator<Transform> _sorter = (Transform o1, Transform o2) -> { return 0; };

        private Texture               _texture;
        private Anchor                _anchor = Anchor.MID;
        private float                 _width  = -1;
        private float                 _height = -1;
        
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Adds an instance to the image
     * @param transform The transform of the instance
     */
    public void addInstance(Transform transform) {
        _instances.add(transform);
        _instancesChanged = true;
    }
    
    /**
     * Removes an instance from the image
     * @param transform The transform of the instance
     */
    public void removeInstance(Transform transform) {
        _instances.remove(transform);
        _instancesChanged = true;
    }
    
    @Override
    public void onRender(IRenderEngine engine) {
        // 1# Check if instances changed
        if ( _instancesChanged) {
            // 1.1# Clear all instances
            _batch.clearInstances();
            
            // 1.2# Fill with instances
            Transform[] instances = _instances.toArray(new Transform[_instances.size()]);

            Arrays.sort(instances, _instancesSorter);                
            
            for(Transform transform : instances) {
                Matrix4f mat4 = Matrix4f.POOL.get();
                Transform.toMatrix4f(transform, mat4);
                _batch.addInstancesData(mat4);
            }
            
            _instancesChanged = false;
        }
        
        // 2# Render
        _batch.render(engine, PrimitiveType.TRIANGLES);
    }

    @Override
    public void setAnchor(Anchor anchor) {
        super.setAnchor(anchor);

        _batch.clear();
        refreshRenderData();
    }
    
    @Override
    public void onRelease() {
        _batch.onDeinitialize();
    }
    
    @Override
    public float getWidth() {
        return _width;
    }

    @Override
    public float getHeight() {
        return _height;
    }

    @Override
    public float getDepth() {
        return 0.0f;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Private-Constructor
     */
    private InstancedImage() { }
    
    /**
     * Refreshes the rendering data.
     */
    private void refreshRenderData() {
        _batch.addVertexData(RenderUtils.generate2DQuadVertices(0, 0, _width, _height, 0, 0, 1, 1));
        _batch.addIndicesData(RenderUtils.generate2DQuadIndices());
    }
    
    private ArrayList<Transform>  _instances;
    private boolean               _instancesChanged;
    private Comparator<Transform> _instancesSorter;
    
    private Batch           _batch;
    private float           _width;
    private float           _height;

}
