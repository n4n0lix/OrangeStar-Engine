package de.orangestar.engine.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.apache.commons.collections4.list.NodeCachingLinkedList;
import org.lwjgl.BufferUtils;

/**
 * A {@link java.nio.Buffer} pool for instance reusing.
 * 
 * @author Oliver &amp; Basti
 */
public class BufferPool {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Returns a {@link IntBuffer} with capacity 1.
     * @return A {@link IntBuffer}
     */
    public static IntBuffer getInt() {
        if (POOL_1_INT.isEmpty()) {
            return BufferUtils.createIntBuffer(1);
        } else {
            return POOL_1_INT.removeLast();
        }
    }
    
    /**
     * Releases a {@link IntBuffer} with capacity 1 back into the pool.<br>
     * Preconditions:
     * <ul>
     *  <li><code>buffer.capacity() == 1</code>
     * </ul>
     * @param buffer the buffer
     */
    public static void releaseInt(IntBuffer buffer) {
        // Preconditions:
        assert buffer.capacity() == 1;

        // Code:
        buffer.clear();
        POOL_1_INT.add(buffer);
    }
    
    /**
     * Returns a {@link FloatBuffer} with capacity 1.
     * @return A {@link FloatBuffer}
     */
    public static FloatBuffer getFloat() {
        if (POOL_1_FLOAT.isEmpty()) {
            return BufferUtils.createFloatBuffer(1);
        } else {
            return POOL_1_FLOAT.removeLast();
        }
    }

    /**
     * Returns a {@link FloatBuffer} with capacity 16.
     * @return A {@link FloatBuffer}
     */
    public static FloatBuffer getMatrix4f() {
        if (POOL_16_FLOAT.isEmpty()) {
            return BufferUtils.createFloatBuffer(16);
        } else {
            return POOL_16_FLOAT.removeLast();
        }
    }
    
    /**
     * Releases a {@link FloatBuffer} with capacity 1 or 16 back into the pool.
     * @param buffer the buffer
     */
    public static void releaseFloat(FloatBuffer buffer) {
        buffer.clear();
        
        switch (buffer.capacity()) {
            case 1: POOL_1_FLOAT.add(buffer);
                    break;
            case 16: POOL_16_FLOAT.add(buffer);
                    break;
            default:
                    break;
        }
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Private-Constructor.
     */
    private BufferPool() { }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Private Static                          */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private static NodeCachingLinkedList<IntBuffer>   POOL_1_INT = new NodeCachingLinkedList<>();
    
    private static NodeCachingLinkedList<FloatBuffer> POOL_1_FLOAT = new NodeCachingLinkedList<>();
    
    private static NodeCachingLinkedList<FloatBuffer> POOL_16_FLOAT = new NodeCachingLinkedList<>();
}
