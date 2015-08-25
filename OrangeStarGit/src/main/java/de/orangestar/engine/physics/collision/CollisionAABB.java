package de.orangestar.engine.physics.collision;

import de.orangestar.engine.values.Ray;
import de.orangestar.engine.values.Vector3f;

/**
 * A so called Axis-Aligned-Bounding-Box, used for fast collision detection.
 * 
 * @author Oliver &amp; Basti
 */
public class CollisionAABB implements CollisionModel {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor
     * @param lowerEdge The edge that is closest to the world origin
     * @param higherEdge The edge that is the farest away from the world origin
     */
    public CollisionAABB( Vector3f lowerEdge, Vector3f higherEdge) {
        _lowerEdge  = Vector3f.zero();
        _higherEdge = Vector3f.zero();
        
        _lowerEdge.x = Math.min(lowerEdge.x, higherEdge.x);
        _lowerEdge.y = Math.min(lowerEdge.y, higherEdge.y);
        _lowerEdge.z = Math.min(lowerEdge.z, higherEdge.z);
        
        _higherEdge.x = Math.max(lowerEdge.x, higherEdge.x);
        _higherEdge.x = Math.max(lowerEdge.y, higherEdge.y);
        _higherEdge.x = Math.max(lowerEdge.z, higherEdge.z);
    }

    // Implementation taken from  http://gamedev.stackexchange.com/a/18459/26334 
    @Override
    public boolean intersects(Ray ray) {
        boolean intersect;
        
        Vector3f dirFraction = Vector3f.POOL.get();
        Vector3f rayOrigin   = Vector3f.POOL.get();
        
        // rayOrigin = ray.origin
        Vector3f.set(rayOrigin, ray.direction);
        
        // To prevent Division-By-Zero
        if (rayOrigin.x == 0.0f) {
            rayOrigin.x = Float.MIN_VALUE; // Float.MinValue = Smallest positive non-zero number
        }
        if (rayOrigin.y == 0.0f) {
            rayOrigin.y = Float.MIN_VALUE; // Float.MinValue = Smallest positive non-zero number
        }
        if (rayOrigin.z == 0.0f) {
            rayOrigin.z = Float.MIN_VALUE; // Float.MinValue = Smallest positive non-zero number
        }
        
        // dirFraction = 1.0f / ray.direction
        Vector3f.one(dirFraction);
        Vector3f.div(dirFraction, ray.direction);
                

        float t1 = (_lowerEdge.x - rayOrigin.x) * dirFraction.x;
        float t2 = (_higherEdge.x  - rayOrigin.x) * dirFraction.x;
        float t3 = (_lowerEdge.y - rayOrigin.y) * dirFraction.y;
        float t4 = (_higherEdge.y  - rayOrigin.y) * dirFraction.y;
        float t5 = (_lowerEdge.z - rayOrigin.z) * dirFraction.z;
        float t6 = (_higherEdge.z  - rayOrigin.z) * dirFraction.z;

        float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
        float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

        if (tmax < 0)
        {
            intersect = false;
        }
        else if (tmin > tmax)
        {
            intersect = false;
        }
        else {
            intersect = true;
        }

        Vector3f.POOL.release(dirFraction);
        Vector3f.POOL.release(rayOrigin);
        
        return intersect;
    }

    @Override
    public boolean intersects(Vector3f point) {

        if (point.x < _lowerEdge.x || point.x > _higherEdge.x) {
            return false;
        }
        
        if (point.y < _lowerEdge.y || point.y > _higherEdge.y) {
            return false;
        }
        
        if (point.z < _lowerEdge.z || point.z > _higherEdge.z) {
            return false;
        }
        
        return true;
    }

    @Override
    public boolean intersects(CollisionModel shape) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Vector3f _lowerEdge;
    private Vector3f _higherEdge;
            
}
