package de.orangestar.engine.values.pools;

import de.orangestar.engine.utils.SimplePool;
import de.orangestar.engine.values.Vector3f;

/**
 * Pool implementation for Vector3f. Returns Vector3f.zero().
 * 
 * @author Oliver &amp; Basti
 */
public final class Vector3fPool extends SimplePool<Vector3f> {

    @Override
    protected final Vector3f newInstance() {
        return Vector3f.zero();
    }

    @Override
    protected final void resetInstance(Vector3f t) {
        Vector3f.zero(t);
    }

    @Override
    protected void cleanUp(Vector3f t) { }

}
