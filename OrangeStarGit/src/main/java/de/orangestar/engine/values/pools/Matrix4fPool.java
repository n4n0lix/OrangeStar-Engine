package de.orangestar.engine.values.pools;

import de.orangestar.engine.utils.SimplePool;
import de.orangestar.engine.values.Matrix4f;

/**
 * Pool implementation for Matrix4f. Returns Matrix4f.identity().
 * 
 * @author Oliver &amp; Basti
 */
public final class Matrix4fPool extends SimplePool<Matrix4f> {

    @Override
    protected final Matrix4f newInstance() {
        return Matrix4f.identity();
    }

    @Override
    protected final void resetInstance(Matrix4f t) {
        Matrix4f.identity(t);
    }

    @Override
    protected void cleanUp(Matrix4f t) { }

}
