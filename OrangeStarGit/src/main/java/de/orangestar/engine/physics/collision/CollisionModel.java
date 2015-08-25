package de.orangestar.engine.physics.collision;

import de.orangestar.engine.values.Ray;
import de.orangestar.engine.values.Vector3f;

/**
 * The interface for collision models used by the physics engine.
 * 2D-Collision Models are interpreted as 3D-Models with infinite depth.
 * 
 * @author Oliver &amp; Basti
 */
public interface CollisionModel {

    /**
     * Tests if the given point lies inside this collision model.
     * @param point The point
     * @return If the given point lies inside this collision model
     */
    public boolean intersects(Vector3f point);
    
    /**
     * Tests if the given ray is intersecting with this collision model.
     * @param ray The ray
     * @return If the given ray is intersecting with this collision model
     */
    public boolean intersects(Ray ray);
    
    /**
     * Tests if the given collision model is intersecting with this collision model.
     * @param shape The shape
     * @return If the given collision model is intersecting with this collision model
     */
    public boolean intersects(CollisionModel shape);
    
}
