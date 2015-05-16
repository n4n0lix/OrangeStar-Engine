package de.orangestar.engine.render.batch;

import java.util.List;

import de.orangestar.engine.render.Material;
import de.orangestar.engine.values.Vertex;

/**
 * A factory that produces rendering batches.
 * @see Batch
 * @author Basti
 */
public class BatchFactory {

    /**
     * Creates a batch used for instance bundling.
     * @param vertices A list of vertices / the topology of the rendered instances 
     * @param material The material used to render the instances
     * 
     * @require material != null
     * @require material.getShader() != null
     * @require material.getShader().isInstanced()
     * 
     * @ensures return_value != null 
     */
    public static InstancingBatch createInstancing(List<Vertex> vertices, Material material) {
        if (material == null) {
            throw new IllegalStateException("Material is null!");
        }
        
        if (material.getShader() == null) {
            throw new IllegalStateException("Shader is null!");
        }
        
        if (!material.getShader().isInstanced()) {
            throw new IllegalStateException("Shader doesn't support instancing!");
        }
        
        return new InstancingBatch(vertices, material);
    }
    
    /**
     * Creates a batch used for bundling vertices that use the same material for rendering.
     * @param material The material used for rendering
     * @param maxSize The max amount of vertices that can be stored 
     * 
     * @require maxSize > 0
     * @require material != null
     * @require material.getShader() != null
     * 
     * @ensures return_value != null 
     */
    
    public static StreamingBatch createStreaming(Material material, int maxSize) {
        if (!(maxSize > 0)) {
            throw new IllegalStateException("Cannot create empty batch!");
        }

        if (material == null) {
            throw new IllegalStateException("Material is null!");
        }
        
        if (material.getShader() == null) {
            throw new IllegalStateException("Shader is null!");
        }
        
        return new StreamingBatch(material, maxSize);
    }
    
}
