package de.orangestar.engine.values;

import de.orangestar.engine.values.pools.TransformPool;

/**
 * Describes a basic transformation in 3D space. (translation, scaling, rotation)
 * 
 * @author Oliver, Basti
 */
public final class Transform {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public static final int             NUM_COMPONENTS = Vector3f.NUM_COMPONENTS + Vector3f.NUM_COMPONENTS + Quaternion4f.NUM_COMPONENTS;
    public static final int             BYTE_SIZE      = Vector3f.BYTE_SIZE + Vector3f.BYTE_SIZE + Quaternion4f.BYTE_SIZE;
    public static final TransformPool   POOL           = new TransformPool();
    
    /**
     * Interpolates between two transforms.
     * @param out The original transform and the instance in which the result is stored
     * @param t2 The future transform
     * @param amount The amount of interpolation
     */
    public static final void interpolate(Transform out, final Transform t2, final float amount) {
        // Position lerping
        Vector3f.lerp(out.position, t2.position, amount);
    
        // Scaling lerping
        Vector3f.lerp(out.scale, t2.scale, amount);
    
        // Rotation lerping
        Quaternion4f tmpOutRotation = Quaternion4f.POOL.get();
    
        tmpOutRotation.x = out.rotation.x;
        tmpOutRotation.y = out.rotation.y;
        tmpOutRotation.z = out.rotation.z;
        tmpOutRotation.w = out.rotation.w;

        Quaternion4f.lerp(tmpOutRotation, t2.rotation, amount, out.rotation);
        Quaternion4f.POOL.release(tmpOutRotation);
    }
    
    /**
     * Returns a new instance of an identity Transform.
     * @return A new instance of an identity Transform.
     */
    public static final Transform identity() {
        Transform result = new Transform();
        identity( result );
        return result;
    }
    
    /**
     * Sets a Transform to identity.
     * @param out The transform to modify.
     */
    public static final void identity(Transform out) {
        Vector3f.zero(out.position);
        Vector3f.one(out.scale);
        Quaternion4f.identity(out.rotation);
    }
    
    /**
     * Sets the out transform to the values of the transform.
     * @param out The transform to modify.
     * @param transform The transform that provides the values.
     */
    public static final void set(Transform out, final Transform transform) {
        out.position.x = transform.position.x;
        out.position.y = transform.position.y;
        out.position.z = transform.position.z;
        
        out.scale.x = transform.scale.x;
        out.scale.y = transform.scale.y;
        out.scale.z = transform.scale.z;

        out.rotation.x = transform.rotation.x;
        out.rotation.y = transform.rotation.y;
        out.rotation.w = transform.rotation.w;
        out.rotation.z = transform.rotation.z;
    }
    
    /**
     * Combines this transform with another transform. Positions will be added,
     * scaling and rotation will be multiplied. The result is stored in <code>out</code>.
     * @param out The first transform
     * @param transform The second transform
     */
    public static final void combine(Transform out, final Transform transform) {
        Vector3f.add(out.position, transform.position);
        Vector3f.mul(out.scale, transform.scale);
        out.rotation = out.rotation; //TODO: Point around Point rotation
    }
        
    /**
     * Duplicates a transform instance.
     * @param trans The transform that shall be duplicated.
     * @return A transform
     */
    public static final Transform duplicate(final Transform trans) {
        return new Transform(
                    trans.position,
                    trans.scale,
                    trans.rotation
                );
    }
    
    /**
     * Returns a transformation matrix that represents the transformation.
     * @param trans The transform
     * @return A matrix
     */
    public static final Matrix4f toMatrix4f(final Transform trans) {
        Matrix4f result = new Matrix4f();
        toMatrix4f(trans, result);
        return result;
    }
    
    /**
     * Converts the transform into a transformation matrix.
     * @param trans The transform
     * @param out The instance in which the result is stored
     */
    public static final void toMatrix4f(final Transform trans, Matrix4f out) {
        Matrix4f translateMatrix = Matrix4f.POOL.get();
        Matrix4f scalingMatrix   = Matrix4f.POOL.get();
        Matrix4f rotationMatrix  = Matrix4f.POOL.get();
        
        Matrix4f.translation(trans.position, translateMatrix);
        Matrix4f.scaling(trans.scale, scalingMatrix);
        Quaternion4f.toRotationMatrix4f(trans.rotation, rotationMatrix);
                
        Matrix4f.set(out, scalingMatrix);
        Matrix4f.mul(out, rotationMatrix);
        Matrix4f.mul(out, translateMatrix);
        
        Matrix4f.POOL.release(translateMatrix);
        Matrix4f.POOL.release(scalingMatrix);
        Matrix4f.POOL.release(rotationMatrix);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor.
     */
    public Transform() {
        this(
                Vector3f.zero(), 
                Vector3f.one(), 
                Quaternion4f.identity()
            );
    }
    
    /**
     * Public-Constructor
     * @param pos The position represented as a {@link Vector3f}
     * @param sca The scaling represented as a {@link Vector3f}
     * @param rot The rotation represented as a {@link Quaternion4f}
     */
    public Transform(final Vector3f pos, final Vector3f sca, final Quaternion4f rot) {
        position = pos;
        scale    = sca;
        rotation = rot;
    }
    
    /**
     * Public-Constructor.
     * @param pos The position represented as a {@link Vector3f}
     */
    public Transform(final Vector3f pos) {
        this(pos, Vector3f.one(), Quaternion4f.identity());
    }
          
    @Override
    public final String toString() {
        return "{ POS: " + position + " | SCA: " + scale + " | ROT: " + rotation + " }";
    }
    
    public Vector3f     position;
    public Vector3f     scale;
    public Quaternion4f rotation;
        
}
