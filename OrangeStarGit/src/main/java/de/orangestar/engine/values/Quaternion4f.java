package de.orangestar.engine.values;

import de.orangestar.engine.values.pools.Quaternion4fPool;

/**
 * Represents a quaternion.
 * 
 * @author Oliver &amp; Basti
 */
public class Quaternion4f {
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                      Public Static                      */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    public static final int                 NUM_COMPONENTS  = 4;
    public static final int                 BYTE_SIZE       = 4 * Float.BYTES;
    public static final Quaternion4fPool    POOL            = new Quaternion4fPool();
    
    /**
     * Returns an identity quaternion.
     * @return An identity quaternion
     */
    public static final Quaternion4f identity() {
        Quaternion4f result = new Quaternion4f();
        Quaternion4f.identity( result );
        return result;
    }
    
    /**
     * Sets the values of the quaternion so it becomes an identity quaternion.
     * @param out The quaternion
     */
    public static final void identity(Quaternion4f out) {
        out.x = 0.0f;
        out.y = 0.0f;
        out.z = 0.0f;
        out.w = 1.0f;
    }
    
    /**
     * Linear-interpolation between two quaternions by a given amount.
     * @param q1 The first quaternion
     * @param q2 The second quaternion
     * @param amount The amount of interpolation
     * @param out The instance in which the result is stored in
     */
    public static final void lerp(final Quaternion4f q1, final Quaternion4f q2, final float amount, Quaternion4f out) {
        
        Quaternion4f tmpQ1 = Quaternion4f.POOL.get();
        Quaternion4f tmpQ2 = Quaternion4f.POOL.get();
        
        Quaternion4f.set(tmpQ1, q1);
        Quaternion4f.set(tmpQ2, q2);
        
        // (q1 * (1f - amount) + q2 * amount).normalized
        Quaternion4f.mul(tmpQ1, 1f - amount);
        Quaternion4f.mul(tmpQ2, amount);
        Quaternion4f.add(tmpQ1, tmpQ2);
        Quaternion4f.set(out, tmpQ1);
        Quaternion4f.normalize(out);
        
        Quaternion4f.POOL.release(tmpQ1);
        Quaternion4f.POOL.release(tmpQ2);
    }

    /**
     * Duplicates the given quaternion.
     * @param model The quaternion
     * @return A new instance that represents the same quaternion as <i>model</i>
     */
    public static final Quaternion4f duplicate(final Quaternion4f model) {
        return new Quaternion4f(model.x, model.y, model.z, model.w);
    }

    /**
     * Creates a rotation quaternion that describes the rotation around a given axis by a given angle.
     * @param axis The rotation axis
     * @param angle The angle
     * @param out The instance in which the result is stored in
     */
    public static final void rotationAxis(final Vector3f axis, final float angle, Quaternion4f out) {
        float s = (float) Math.sin(angle/2);

        out.x = axis.x * s;
        out.y = axis.y * s;
        out.z = axis.z * s;
        out.w = (float) Math.cos(angle/2);
    }
    
    /**
     * 2-address implementation of the = operator.<br>
     * <i>target</i> = <i>source</i>
     * @param target The target quaternion
     * @param source The source quaternion
     */
    public static final void set(final Quaternion4f target, Quaternion4f source) {
        target.x = source.x;
        target.y = source.y;
        target.z = source.z;
        target.w = source.w;
    }
    
    /**
     * 2-address implementation of the + operator.<br>
     * <i>target</i> = <i>target</i> + <i>source</i>
     * @param target The target quaternion
     * @param source The source quaternion
     */
    public static final void add(final Quaternion4f target, Quaternion4f source) {
        target.x = target.x +  source.x;
        target.y = target.y +  source.y;
        target.z = target.z +  source.z;
        target.w = target.w +  source.w;
    }

    /**
     * 2-address implementation of the * operator.<br>
     * <i>target</i> = <i>target</i> * <i>source</i>
     * @param target The target quaternion
     * @param source The source quaternion
     */
    public static final void mul(final Quaternion4f target, Quaternion4f source) {
        Quaternion4f tmpOut = POOL.get();
        Quaternion4f.set(tmpOut, target);
        
        target.x =  tmpOut.x * source.w + tmpOut.y * source.z - tmpOut.z * source.y + tmpOut.w * source.x;
        target.y = -tmpOut.x * source.z + tmpOut.y * source.w + tmpOut.z * source.x + tmpOut.w * source.y;
        target.z =  tmpOut.x * source.y - tmpOut.y * source.x + tmpOut.z * source.w + tmpOut.w * source.z;
        target.w = -tmpOut.x * source.x - tmpOut.y * source.y - tmpOut.z * source.z + tmpOut.w * source.w;
        
        Quaternion4f.POOL.release(tmpOut);
    }
    
    /**
     * 2-address implementation of the scalar multiplication.<br>
     * <i>target</i> = <i>target</i> + <i>scalar</i>
     * @param target The target quaternion
     * @param scalar The scalar
     */
    public static final void mul(final Quaternion4f target, float scalar) {
        target.x = target.x * scalar;
        target.y = target.y * scalar;
        target.z = target.z * scalar;
        target.w = target.w * scalar;
    }

    /**
     * Normalizes the given quaternion.
     * @param q The quaternion
     */
    public static final void normalize(Quaternion4f q) {
        float n = (float) Math.sqrt(q.x*q.x + q.y*q.y + q.z*q.z + q.w*q.w);

        if (n == 0.0f) {
            return;
        }

        q.x = q.x / n;
        q.y = q.y / n;
        q.z = q.z / n;
        q.w = q.w / n;
    }
    
    /**
     * Converts this quaternion into a rotation matrix.
     * @param q The quaternion
     * @param out The instance in which the result is stored
     */
    public static final void toRotationMatrix4f(final Quaternion4f q, Matrix4f out) {
        float xx      = q.x * q.x;
        float xy      = q.x * q.y;
        float xz      = q.x * q.z;
        float xw      = q.x * q.w;

        float yy      = q.y * q.y;
        float yz      = q.y * q.z;
        float yw      = q.y * q.w;

        float zz      = q.z * q.z;
        float zw      = q.z * q.w;

        out.m00 = 1.0f - 2.0f * ( yy + zz );
        out.m01 = 2.0f * ( xy - zw );
        out.m02 = 2.0f * ( xz + yw );
        out.m03 = 0.0f;
        
        out.m10 = 2.0f * ( xy + zw );
        out.m11 = 1.0f - 2.0f * ( xx + zz );
        out.m12 = 2.0f * ( yz - xw );
        out.m13 = 0.0f;

        out.m20 = 2.0f * ( xz - yw );
        out.m21 = 2.0f * ( yz + xw );
        out.m22 = 1.0f - 2.0f * ( xx + yy );
        out.m23 = 0.0f;
                                                  
        out.m30 = 0.0f;  
        out.m31 = 0.0f;   
        out.m32 = 0.0f;
        out.m33 = 1.0f;
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                         Public                          */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    /**
     * Public-Constructor. Initializes all values to zero.
     */
    public Quaternion4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    /**
     * Public-Constructor.
     * @param x1 The x value
     * @param y1 The y value
     * @param z1 The z value
     * @param w1 The 
     */
    public Quaternion4f(final float x1, final float y1, final float z1, final float w1) {
        x = x1;
        y = y1;
        z = z1;
        w = w1;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(w);
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }
    
    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Quaternion4f other = (Quaternion4f) obj;
        if (Float.floatToIntBits(w) != Float.floatToIntBits(other.w))
            return false;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        return true;
    }
    
    @Override
    public final String toString() {
        return "[ " + x + ", " + y  + ", " + z + ", " + w + " ]";
    }

    public float x;
    public float y;
    public float z;
    public float w;

}
