package de.orangestar.engine.render.actor;

import de.orangestar.engine.render.Batch;
import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.utils.RenderUtils;
import de.orangestar.engine.values.Anchor;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Vector2f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vertex;

/**
 * Renders a simple image. (If the same image is used multiple times it is recommended to use {@link InstancedImage})
 * 
 * @author Oliver &amp; Basti
 */
public class Image extends Actor {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * The builder class of {@link Image}
     * 
     * @author Basti
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
         * The anchor of this image.
         * @param anchor The anchor
         * @return <i>this</i>
         */
        public Builder anchor(Anchor anchor) {
            _anchor = anchor;
            return this;
        }
        
        /**
         * Builds the image. Throws exceptions if a parameter has invalid input.<br>
         * Preconditions: 
         * <ul>
         *  <li><code>texture != null</code>
         *  <li><code>anchor != null</code>
         *  <li><code>width &gt; 0</code>
         *  <li><code>height &gt;= 0</code>
         * </ul>
         * @return The Image instance
         */
        public Image build() {
            // Preconditions:
            assert _texture != null;
            assert _anchor != null;
            assert _width > 0;
            assert _height > 0;
            
            // Code:
            Image result = new Image(_texture, _width, _height);
            result.setAnchor(_anchor);
            
            return result;
        }
        
        private Texture     _texture;
        private float       _width;
        private float       _height;
        private Anchor      _anchor = Anchor.MID;

    }
  
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Public-Constructor
     * @param tex The texture to display
     */
    public Image(Texture tex) {
        this(tex, tex.getWidth(), tex.getHeight());
    }

    /**
     * Public-Constructor
     * @param tex The texture to display
     * @param width The width of the image in world-units
     * @param height The height of the image in world-units
     */
    public Image(Texture tex, float width, float height) {
        Material material = new Material.Builder()
                    .texture(tex)
                    .shader(Shader.GodShader)
                    .build();

        _batch = new Batch.Builder()
                              .material(material)
                              .maxVertices(4)
                              .maxIndices(6)
                              .build();

        _width = width;
        _height = height;
        
        refreshRenderData();
    }

    @Override
    public void onRender(IRenderEngine engine) {
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

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Protected                             */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    

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
    
    private Batch  _batch;
    private float  _width;
    private float  _height;
    
    /**
     * Refreshes the rendering data.
     */
    private void refreshRenderData() {
        _batch.addVertexData(RenderUtils.generate2DQuadVertices(0, 0, _width, _height, 0, 0, 1, 1));
        _batch.addIndicesData(RenderUtils.generate2DQuadIndices());
    }

}
