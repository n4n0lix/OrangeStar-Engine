package de.orangestar.engine.values.pools;

import de.orangestar.engine.utils.SimplePool;
import de.orangestar.engine.values.Vector2f;

/**
 * Pool implementation for Vector3f. Returns Vector3f.zero().
 * 
 * @author Oliver &amp; Basti
 */
public final class Vector2fPool extends SimplePool<Vector2f> {

    @Override
    protected final Vector2f newInstance() {
        return Vector2f.zero();
    }

    @Override
    protected final void resetInstance(Vector2f t) {
        Vector2f.zero(t);
    }

    @Override
    protected void cleanUp(Vector2f t) { }

}
