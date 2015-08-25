package de.orangestar.engine.values;

import java.util.Random;

import de.orangestar.engine.values.pools.Vector3fPool;

/**
 * Represents a three component vector.
 * 
 * @author Oliver &amp; Basti
 */
public final class Vector3f {
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                      Public Static                      */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    public static final int             NUM_COMPONENTS  = 3;
    public static final int             BYTE_SIZE       = 3 * Float.BYTES;
    public static final Vector3fPool    POOL            = new Vector3fPool();
    
    public static final Vector3f X_AXIS = new Vector3f(1f, 0f, 0f);
    public static final Vector3f Y_AXIS = new Vector3f(0f, 1f, 0f);
    public static final Vector3f Z_AXIS = new Vector3f(0f, 0f, 1f);
    
    /**
     * Returns a vector with all values set to zero.
     * @return The vector
     */
    public static final Vector3f zero() {
        return new Vector3f(0f, 0f, 0f);
    }
    
    /**
     * Checks if the given vector is a null vector.
     * @param v The vector
     * @return If the given vector is a null vector
     */
    public static boolean isZero(Vector3f v) {
    	 return v.x == 0f && v.y == 0f && v.z == 0f;
    }
     
    /**
     * Checks if the given vector is a unit vector.
     * @param v The vector
     * @return If the given vector is a unit vector
     */
    public static boolean isUnit(Vector3f v) {
    	 return v.x == 1f && v.y == 1f && v.z == 1f;
    }
    
    /**
     * Sets all values of the given vector to zero.
     * @param out The vector
     */
    public static final void zero(Vector3f out) {
        out.x = 0f;
        out.y = 0f;
        out.z = 0f;
    }
    
    /**
     * Returns a vector with all values set to one.
     * @return The vector
     */
    public static final Vector3f one() {
        return new Vector3f(1f, 1f, 1f);
    }
    
    /**
     * Sets all values of the given vector to one.
     * @param out The vector
     */
    public static final void one(Vector3f out) {
        out.x = 1f;
        out.y = 1f;
        out.z = 1f;
    }
    
    /**
     * Returns a random vector between the <i>min</i> and <i>max</i> vector.
     * @param min The minimum vector
     * @param max The maximum vector
     * @return The random vector
     */
    public static final Vector3f random(final Vector3f min, final Vector3f max) {
        Vector3f result = new Vector3f();
        Vector3f.random(min, max, result);
        return result;
    }
    
    /**
     * Returns a random vector between the <i>min</i> and <i>max</i> vector.
     * @param min The minimum vector
     * @param max The maximum vector
     * @param out The instance in which the result is stored
     */
    public static final void random(final Vector3f min, final Vector3f max, Vector3f out) {
        out.x = Rnd.nextFloat() * (max.x - min.x) + min.x;
        out.y = Rnd.nextFloat() * (max.y - min.y) + min.y;
        out.z = Rnd.nextFloat() * (max.z - min.z) + min.z;
    }

    /**
     * Returns the distance between two vectors.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return The distance
     */
    public static final float distance(final Vector3f v1, final Vector3f v2) {
        float result;
        Vector3f tmp = Vector3f.POOL.get();
        Vector3f.set(tmp, v1);
        Vector3f.sub(tmp, v2);
        
        result = tmp.length();
        Vector3f.POOL.release(tmp);
        
        return result;
    }

    /**
     * Calculates the angle between to vectors in radian.
     * @param v1 Vector one
     * @param v2 Vector two
     * @return The angle between to vectors in radian
     */
    public static final float angle(final Vector3f v1, final Vector3f v2) {
        Vector3f tmp1 = Vector3f.POOL.get();
        Vector3f tmp2 = Vector3f.POOL.get();
        
        Vector3f.set(tmp1, v1);
        Vector3f.set(tmp2, v2);
        
        float dls = Vector3f.dot(tmp1, tmp2) / (tmp1.length() * tmp2.length());

        if (dls < -1.0f) {
            dls = -1.0f;
        } else if (dls > 1.0f) {
            dls = 1.0f;
        }
        
        Vector3f.POOL.release(tmp1);
        Vector3f.POOL.release(tmp2);

        return (float) Math.acos(dls);
    }

    /**
     * Interpolates the two vectors by a given amount.
     * @param out The initial vector and the instance where the result is stored in 
     * @param v2 The target vector
     * @param amount The amount
     */
    public static final void lerp(Vector3f out, final Vector3f v2, final float amount)
    {
        float _1_amount = 1.0f - amount;    
        out.x = out.x * _1_amount + v2.x * amount;
        out.y = out.y * _1_amount + v2.y * amount;
        out.z = out.z * _1_amount + v2.z * amount;
    }

    /**
     * Moves a vector into towards a destination by a given amount.
     * @param start The start vector
     * @param dest The destination vector
     * @param stepDistance The amount of movement
     * @param out The instance where the result is stored in
     */
    public static final void moveTowards( final Vector3f start, final Vector3f dest, final float stepDistance, Vector3f out) {
        Vector3f tmpStart = Vector3f.POOL.get();
        Vector3f tmpDest  = Vector3f.POOL.get();
        Vector3f tmpDist  = Vector3f.POOL.get();
        
        Vector3f.set(tmpStart, start);
        Vector3f.set(tmpDest, dest);
        Vector3f.set(tmpDist, start);
        
        Vector3f.sub(tmpDist, dest);
        
        float length  = tmpDist.length();

        if (length <= stepDistance || length == 0.0f)
        {            
            Vector3f.set(out, tmpDest);
            Vector3f.POOL.release(tmpStart);
            Vector3f.POOL.release(tmpDest);
            Vector3f.POOL.release(tmpDist);
            return;
        }
        
        Vector3f.div(tmpDist, length);
        Vector3f.mul(tmpDist, stepDistance);
        Vector3f.add(tmpStart, tmpDist);
        
        Vector3f.set(out, tmpDest);
        Vector3f.POOL.release(tmpStart);
        Vector3f.POOL.release(tmpDest);
        Vector3f.POOL.release(tmpDist);
        return;
    }

    /**
     * Checks if <i>v1</i> is longer than <i>v2</i>.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return If the <i>v1</i> is longer than <i>v2</i>
     */
    public static final boolean isLongerAs(final Vector3f v1, final Vector3f v2) {
        return (v1.x * v1.x + v1.y * v1.y + v1.z * v1.z) > (v2.x * v2.x + v2.y * v2.y + v2.z * v2.z);
    }

    /**
     * Checks if <i>v1</i> is shorter than <i>v2</i>.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return If the <i>v1</i> is shorter than <i>v2</i>
     */
    public static final boolean isShorterAs(final Vector3f v1, final Vector3f v2) {
        return (v1.x*v1.x + v1.y*v1.y + v1.z*v1.z) < (v2.x*v2.x + v2.y*v2.y + v2.z*v2.z);
    }
    
    /**
     * 2-address implementation of the = operator.<br>
     * <i>target</i> = <i>(x,y,z)</i>
     * @param target The vector
     * @param x The x value
     * @param y The y value
     * @param z The z value
     */
    public static final void set(Vector3f target, final float x, final float y, final float z) {
        target.x = x;
        target.y = y;
        target.z = z;
    }
    
    /**
     * 2-address implementation of the = operator.<br>
     * <i>target</i> = <i>source</i>
     * @param target The target vector
     * @param source The source vector
     */
    public static final void set(Vector3f target, final Vector3f source) {
        target.x = source.x;
        target.y = source.y;
        target.z = source.z;
    }
    
    /**
     * Negates the given vector.
     * @param out The vector
     */
    public static final void neg(Vector3f out) {
        out.x = -out.x;
        out.y = -out.y;
        out.z = -out.z;
    }
    
    /**
     * Normalizes the given vector.
     * @param out The vector
     */
    public static final void normalize(Vector3f out) {
        float len = out.length();

        if (len != 0.0f) {
            out.x = out.x / len;
            out.y = out.y / len;
            out.z = out.z / len;
            return;
        }

        out.x = out.x;
        out.y = out.y;
        out.z = out.z;
        return;
    }
    
    /**
     * 2-address implementation of the + operator.<br>
     * <i>target</i> = <i>target</i> + <i>add</i>
     * @param target The target vector
     * @param add The added vector
     */
    public static final void add(Vector3f target, final Vector3f add) {
        target.x = target.x + add.x;
        target.y = target.y + add.y;
        target.z = target.z + add.z;
    }
    
    /**
     * 2-address implementation of the - operator.<br>
     * <i>target</i> = <i>target</i> - <i>sub</i>
     * @param target The target vector
     * @param sub The subtracted vector
     */
    public static final void sub(Vector3f target, final Vector3f sub) {
        target.x = target.x - sub.x;
        target.y = target.y - sub.y;
        target.z = target.z - sub.z;
    }
    
    /**
     * 2-address implementation of the * operator.<br>
     * <i>target</i> = <i>target</i> * <i>mul</i>
     * @param target The target vector
     * @param mul The multiplicated vector
     */
    public static final void mul(Vector3f target, final Vector3f mul) {
        target.x = target.x * mul.x;
        target.y = target.y * mul.y;
        target.z = target.z * mul.z;
    }
    
    /**
     * 2-address implementation of the scalar multiplication.<br>
     * <i>target</i> = <i>target</i> * <i>scalar</i>
     * @param target The target vector
     * @param scalar The scalar
     */
    public static final void mul(Vector3f target, final float scalar)
    {
        target.x = target.x * scalar;
        target.y = target.y * scalar;
        target.z = target.z * scalar;
    }
    
    /**
     * 2-address implementation of the scalar division.<br>
     * <i>target</i> = <i>target</i> / <i>divisor</i>
     * @param target The target vector
     * @param divisor The scalar
     */
    public static final void div(Vector3f target, final float divisor) {
        target.x = target.x / divisor;
        target.y = target.y / divisor;
        target.z = target.z / divisor;
    }
    
    /**
     * 2-address implementation of the / operator.<br>
     * <i>target</i> = <i>target</i> / <i>divisor</i>
     * @param target The target vector
     * @param divisor The divided by vector
     */
    public static final void div(Vector3f target, final Vector3f divisor) {
        target.x = target.x / divisor.x;
        target.y = target.y / divisor.y;
        target.z = target.z / divisor.z;
    }
    
    /**
     * Calculates the cross product of two vectors and stores the result in <code>out</code>.
     * @param out The first vector
     * @param vec The second vector
     */
    public static final void cross(Vector3f out, final Vector3f vec) {
        Vector3f tmpOut = Vector3f.POOL.get();
        Vector3f.set(tmpOut, out);
        
        out.x = tmpOut.y * vec.z - tmpOut.z * vec.y;
        out.y = tmpOut.z * vec.x - tmpOut.x * vec.z;
        out.z = tmpOut.x * vec.y - tmpOut.y * vec.x;

        Vector3f.POOL.release(tmpOut);
    }
    
    /**
     * Calculates the dot product of two vectors.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return The dot product
     */
    public static final float dot(final Vector3f v1, final Vector3f v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }
    
    /**
     * Duplicates the given vector.
     * @param model The model vector
     * @return A new instance that represents the <i>model</i> vector.
     */
    public static final Vector3f duplicate(final Vector3f model) {
        return new Vector3f(model.x, model.y, model.z);
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                         Public                          */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    
    /**
     * Public-Constructor. All values initialized to zero.
     */
    public Vector3f() {
        this(0.0f, 0.0f, 0.0f);
    }

    /**
     * Public-Constructor with <code>z = 0.0f</code>.
     * @param x The x value
     * @param y The y value
     */
    public Vector3f(final float x, final float y) {
        this(x, y, 0);
    }
    
    /**
     * Public-Constructor.
     * @param x1 The x value
     * @param y1 The y value
     * @param z1 The z value
     */
    public Vector3f(final float x1, final float y1, final float z1) {
        x = x1;
        y = y1;
        z = z1;
    }

    /**
     * Calculates the length.
     * @return The length
     */
    public final float length() {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
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
        Vector3f other = (Vector3f) obj;
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
        return "[" + x + ", " + y + ", " + z + " ]";
    }

    public float x;
    public float y;
    public float z;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private final static Random Rnd = new Random();

}
