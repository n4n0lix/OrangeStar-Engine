package de.orangestar.engine.values;

/**
 * Describes a basic transformation in 3D space.
 * 
 * @author Basti
 */
public class Transform {
    
    public static final int ComponentsCount = Vector3f.ComponentsCount + Vector3f.ComponentsCount + Quaternion4f.ComponentsCount;
    public static final int ByteSize        = Vector3f.ByteSize + Vector3f.ByteSize + Quaternion4f.ByteSize;
    
    public static Transform interpolate(Transform t1, Transform t2, float amount)
    {
        return new Transform (
            Vector3f.lerp(t1.position, t2.position, amount),
            Vector3f.lerp(t1.scale, t2.scale, amount),
            Quaternion4f.lerp(t1.rotation, t2.rotation, amount)
        );
    }
    
    /**
     * Combines this transform with another transform. Positions will be added,
     *  scaling and rotation will be multiplied.
     * @param transform
     * @return
     */
    public Transform combine(Transform transform) {
        return new Transform(
                    position.add(transform.position),
                    scale.mul(transform.scale),
                    rotation.mul(transform.rotation)
                );
    }
    
    public static Transform duplicate(Transform trans) {
        if (trans == null) {
            return null;
        }
        
        return new Transform(
                    trans.position,
                    trans.scale,
                    trans.rotation
                );
    }
    
    public Transform() {
        this(Vector3f.Zero, Vector3f.One, Quaternion4f.Identity);
    }
    
    public Transform(Vector3f pos, Vector3f sca, Quaternion4f rot) {
        position = pos;
        scale    = sca;
        rotation = rot;
    }
    
    /**
     * Sets the position to the given Vector3f and returns this instance.
     * @param pos The new position
     * @return this
     */
    public Transform position(Vector3f pos) {
        this.position = pos;
        return this;
    }
    
   /**
    * Sets the scale to the given Vector3f and returns this instance.
    * @param scale The new scale
    * @return this
    */
    public Transform scale(Vector3f scale) {
        this.scale = scale;
        return this;
    }
    
    /**
     * Sets the rotation to the given Quaternion4f and returns this instance.
     * @param rotation The new rotation
     * @return this
     */
    public Transform rotate(Quaternion4f rotation) {
        this.rotation = rotation;
        return this;
    }
    
    /**
     * Shortcut for scale(new Vector3f(val, val, val))
     */
    public Transform scale(float val) {
        this.scale = new Vector3f(val, val, val);
        return this;
    }
    
    /**
     * Converts the transform in a matrix (4x4) representation.
     * @return A transformation matrix
     */
    public Matrix4f toMatrix4f() {
        Matrix4f translateMatrix = new Matrix4f(
                1, 0, 0, position.x,
                0, 1, 0, position.y,
                0, 0, 1, position.z,
                0, 0, 0, 1
        );
        
        Matrix4f scalingMatrix = new Matrix4f(
            scale.x,       0,       0, 0,
                  0, scale.y,       0, 0,
                  0,       0, scale.z, 0,
                  0,       0,       0, 1
        );
        
        Matrix4f rotationMatrix = rotation.toMatrix4f();
        
        return scalingMatrix.mul(rotationMatrix).mul(translateMatrix);
    }
    
    public Vector3f position;
    public Vector3f scale;
    public Quaternion4f rotation;
    
}
