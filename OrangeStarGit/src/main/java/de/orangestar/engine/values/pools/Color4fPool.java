package de.orangestar.engine.values.pools;

import de.orangestar.engine.utils.SimplePool;
import de.orangestar.engine.values.Color4f;

/**
 * Pool implementation for Color4f. Returns new Color4f.white().
 * 
 * @author Oliver &amp; Basti
 */
public final class Color4fPool extends SimplePool<Color4f> {

    @Override
    protected final Color4f newInstance() {
        return new Color4f(1f, 1f, 1f, 1f);
    }

    @Override
    protected final void resetInstance(Color4f t) {
        Color4f.white(t);
    }

    @Override
    protected void cleanUp(Color4f t) { }

}
