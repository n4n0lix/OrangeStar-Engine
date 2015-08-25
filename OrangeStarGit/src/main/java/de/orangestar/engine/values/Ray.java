package de.orangestar.engine.values;

/**
 * Represents a ray.
 * 
 * @author Oliver, Basti
 */
public final class Ray {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * 2-address implementation of the = operator.<br>
     * <i>target</i> = <i>source</i>
     * @param target The target value
     * @param source The source value
     */
    public static final void set(Ray target, final Ray source) {
        Vector3f.set(target.origin,    source.origin);
        Vector3f.set(target.direction, source.direction);
    }

    /**
     * 2-address implementation of the vector subtraction.<br>
     * <i>target</i> = <i>target</i> - <i>vec3</i>
     * @param target The ray
     * @param vec3 The vector
     */
    public static final void sub(Ray target, final Vector3f vec3) {
        Vector3f.sub(target.origin, vec3);
        Vector3f.sub(target.direction, vec3);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public Vector3f origin;
    public Vector3f direction;

}
