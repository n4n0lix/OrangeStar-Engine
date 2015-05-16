package de.orangestar.engine.render.batch;

import de.orangestar.engine.render.PrimitiveType;

/**
 * A batch is a collection of 'drawcalls' (typically 1..n vertices with the same shader and texture). 
 * Draw calls with e.g. the same material or even the same topology are gathered in one batch, to 
 * bundle them all in one draw call. This improves the rendering performance, because calls from CPU 
 * to GPU are somewhat expensive.
 * 
 * @author Basti
 */
public abstract class Batch {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Executes all draw calls of this batch.
     */
    public abstract void render(PrimitiveType type);
    
    /**
     * Clears all bundled vertices/instances etc.
     * 
     * @ensures isEmpty();
     */
    public abstract void clear();
    
    /**
     * Whether this batch is full or not. Full batches shouldn't be able to store further vertices/instances/... .
     */
    public abstract boolean isFull();
    
    /**
     * Whether this batch is empty or not. An empty batch shouldn't do any draw calls if {@link render()} is invoked.
     */
    public abstract boolean isEmpty();
    
    /**
     * Destroys this batch and therefore make it unusable.
     * 
     * @ensures isDestroyed()
     */
    public void destroy() {
        _isDestroyed = true;
    }
    
    /**
     * Whether this batch was destroyed or not.
     */
    public final boolean isDestroyed() {
        return _isDestroyed;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private boolean _isDestroyed = false;
    
}
