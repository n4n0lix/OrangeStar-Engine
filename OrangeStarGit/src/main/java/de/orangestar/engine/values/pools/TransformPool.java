package de.orangestar.engine.values.pools;

import de.orangestar.engine.utils.SimplePool;
import de.orangestar.engine.values.Transform;

/**
 * Pool implementation for Transform. Returns Transform.identity().
 * 
 * @author Oliver &amp; Basti
 */
public final class TransformPool extends SimplePool<Transform> {

    @Override
    protected final Transform newInstance() {
        return Transform.identity();
    }

    @Override
    protected final void resetInstance(Transform t) {
        Transform.identity(t);
    }

    @Override
    protected void cleanUp(Transform t) { }

}
