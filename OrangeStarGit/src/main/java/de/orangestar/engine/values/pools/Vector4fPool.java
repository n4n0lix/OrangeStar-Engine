package de.orangestar.engine.values.pools;

import de.orangestar.engine.utils.SimplePool;
import de.orangestar.engine.values.Vector4f;

/**
 * Pool implementation for Vector4f. Returns Vector4f.zero().
 * 
 * @author Oliver &amp; Basti
 */
public final class Vector4fPool extends SimplePool<Vector4f> {

    @Override
    protected final Vector4f newInstance() {
        return Vector4f.zero();
    }

    @Override
    protected final void resetInstance(Vector4f t) {
        Vector4f.zero(t);
    }

    @Override
    protected void cleanUp(Vector4f t) { }

}
