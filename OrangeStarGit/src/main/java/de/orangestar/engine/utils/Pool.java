package de.orangestar.engine.utils;

import java.util.Iterator;

/**
 * The abse class for all instance pools.
 * 
 * @author Oliver &amp; Basti
 *
 * @param <T> The instance type
 */
public abstract class Pool<T> {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Returns an instance from the pool or generates a new one if the pool is empty.
     * @return An instance
     */
    public abstract T get();
    
    /**
     * Releases an instance back into the pool. Make sure the instance is no longer used ANYWHERE!
     * @param t The instance
     */
    public abstract void release(T t);
    
    /**
     * Returns the amount of instances living in the pool.
     * @return The amount of instances living in the pool
     */
    public abstract int size();
    
    /**
     * Removes all instances from the pool.
     */
    public abstract void clear();
    
    /**
     * Does cleanup logic for all instances.
     */
    public void cleanUp() {
        Iterator<T> iterator = getIterator();
        while( iterator.hasNext() ) {
            T t = iterator.next();
            cleanUp(t);
        }
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                             Protected                              */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Implement new instance generation.
     * @return A new instance
     */
    protected abstract T    newInstance();
    
    /**
     * Implement the resetting of an instance so it can be used by others.
     * @param t The instance to reset
     */
    protected abstract void resetInstance(T t);
    
    /**
     * Do cleanup logic here.
     */
    protected abstract void cleanUp(T t);
    
    /**
     * Provide a iterator for all objects in the pool.
     * @return A iterator for all objects in the pool
     */
    protected abstract Iterator<T> getIterator();
}
