package de.orangestar.engine.values.pools;

import de.orangestar.engine.utils.SimplePool;
import de.orangestar.engine.values.Quaternion4f;

/**
 * Pool implementation for Quaternion4f. Returns Quaternion4f.identity().
 * 
 * @author Oliver &amp; Basti
 */
public final class Quaternion4fPool extends SimplePool<Quaternion4f> {

    @Override
    protected final Quaternion4f newInstance() {
        return Quaternion4f.identity();
    }

    @Override
    protected final void resetInstance(Quaternion4f t) {
        Quaternion4f.identity(t);
    }

    @Override
    protected void cleanUp(Quaternion4f t) { }
    
}
