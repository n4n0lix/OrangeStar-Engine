package de.orangestar.engine.render.actor;

import de.orangestar.engine.render.Batch;
import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vertex;

/**
 * A debug marker. (Three colored lines on each axis)
 * 
 * @author Oliver &amp; Basti
 */
public class DebugMarker extends Actor {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor
     */
    public DebugMarker() {
        this(1f);
    }
    
    /**
     * Public-Constructor.
     * @param length The length of the color lines
     */
    public DebugMarker(float length) {
        Vertex xAxis1 = Vertex.POOL.get();
        Vertex xAxis2 = Vertex.POOL.get();
        
        Vertex yAxis1 = Vertex.POOL.get();
        Vertex yAxis2 = Vertex.POOL.get();
        
        Vertex zAxis1 = Vertex.POOL.get();
        Vertex zAxis2 = Vertex.POOL.get();
        
        Vector3f.set(xAxis1.position, 0f, 0f, 0f);
        Vector3f.set(yAxis1.position, 0f, 0f, 0f);
        Vector3f.set(zAxis1.position, 0f, 0f, 0f);
        Vector3f.set(xAxis2.position, length, 0f, 0f);
        Vector3f.set(yAxis2.position, 0f, length, 0f);
        Vector3f.set(zAxis1.position, 0f, 0f, length);
        
        Color4f.set(xAxis1.color, 1f, 0f, 0f, 1f);
        Color4f.set(xAxis2.color, 1f, 0f, 0f, 1f);
        Color4f.set(yAxis1.color, 0f, 1f, 0f, 1f);
        Color4f.set(yAxis2.color, 0f, 1f, 0f, 1f);
        Color4f.set(zAxis1.color, 0f, 0f, 1f, 1f);
        Color4f.set(zAxis2.color, 0f, 0f, 1f, 1f);
        
        _batch.addVertexData(xAxis1, xAxis2, yAxis1, yAxis2, zAxis1, zAxis2);
    }

    @Override
    public void onRelease() {
        _batch.onDeinitialize();
    }

    @Override
    public void onRender(IRenderEngine engine) {
        _batch.render(engine, PrimitiveType.LINES);
    }
    
    @Override
    public float getWidth() {
        return 0.0f;
    }

    @Override
    public float getHeight() {
        return 0.0f;
    }

    @Override
    public float getDepth() {
        return 0.0f;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Batch _batch = new Batch.Builder()
                                        .maxVertices(6)
                                        .maxIndices(0)
                                        .material(
                                                new Material.Builder()
                                                                .shader(Shader.ColorShader)
                                                                .build())
                                        .build();

}
