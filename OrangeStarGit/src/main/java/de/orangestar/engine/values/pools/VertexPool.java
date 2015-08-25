package de.orangestar.engine.values.pools;

import de.orangestar.engine.utils.SimplePool;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Vector2f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vertex;

/**
 * Pool implementation for Quaternion4f. Returns Quaternion4f.identity().
 * 
 * @author Oliver &amp; Basti
 */
public final class VertexPool extends SimplePool<Vertex> {

    @Override
    protected final Vertex newInstance() {
        return new Vertex( new Vector3f(), Color4f.white() , new Vector2f());
    }

    @Override
    protected final void resetInstance(Vertex t) {
        Vector3f.zero(t.position);
        Vector2f.zero(t.texCoord);
        Color4f.white(t.color);
    }

    @Override
    protected void cleanUp(Vertex t) { }

}
