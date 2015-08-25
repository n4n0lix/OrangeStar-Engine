package de.orangestar.engine.utils;

import java.util.Iterator;

import org.apache.commons.collections4.list.NodeCachingLinkedList;

/**
 * Provides a basic {@link Pool} implementation.
 * 
 * @author Oliver &amp; Basti
 *
 * @param <T> The instance type
 */
public abstract class SimplePool<T> extends Pool<T> {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
    public T get() {
        if (_pool.isEmpty()) {
            return newInstance();
        } else {
            return _pool.removeFirst();
        }
    }

    @Override
    public void release(T t) {
        resetInstance(t);
        _pool.addLast(t);
    }

    @Override
    public int size() {
        return _pool.size();
    }
    
    @Override
    public void clear() {
        _pool.clear();
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                             Protected                              */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
    protected abstract T newInstance();
    
    @Override
    protected abstract void resetInstance(T t);
    
    @Override
    protected abstract void cleanUp(T t);
    
    @Override
    protected Iterator<T> getIterator() {
        return _pool.iterator();
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private NodeCachingLinkedList<T> _pool = new NodeCachingLinkedList<>();
    
}
