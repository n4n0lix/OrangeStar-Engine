package de.orangestar.engine.values;

import de.orangestar.engine.values.pools.Vector2fPool;

/**
 * Represents a two component vector.
 * 
 * @author Oliver &amp; Basti
 */
public final class Vector2f {
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                      Public Static                      */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    public static final int             NUM_COMPONENTS  = 2;
    public static final int             BYTE_SIZE       = 2 * Float.BYTES;
    public static final Vector2fPool    POOL            = new Vector2fPool();
    
    /**
     * Returns a new instance with all values initialized to zero.
     * @return A new instance with all values initialized to zero
     */
    public static final Vector2f zero() {
        return new Vector2f(0f, 0f);
    }
    
    /**
     * Sets all values of the given vector to zero.
     * @param v The modified vector
     */
    public static final void zero(Vector2f v) {
        v.x = 0.0f;
        v.y = 0.0f;
    }
    
    /**
     * Checks if the given vector is a null vector.
     * @param v The vector
     * @return If the given vector is a null vector
     */
    public static boolean isZero(Vector2f v) {
        return v.x == 0f && v.y == 0f;
    }
    
    /**
     * Returns a new instance with all values initialized to one.
     * @return A new instance with all values initialized to one
     */
    public static final Vector2f one() {
        return new Vector2f(1f, 1f);
    }
    
    /**
     * Sets all values of the given vector to one.
     * @param v The modified vector
     */
    public static final void one(Vector2f v) {
        v.x = 1.0f;
        v.y = 1.0f;
    }
    
    /**
     * Checks if the given vector is a unit vector.
     * @param v The vector
     * @return If the given vector is a unit vector
     */
    public static boolean isUnit(Vector2f v) {
        return v.x == 1f && v.y == 1f;
    }
    
    /**
     * 2-address implementation of the = operator.<br>
     * <i>target</i> = <i>(x,y)</i>
     * 
     * @param target The to set matrix
     * @param x The x value
     * @param y The y value
     */
    public static final void set(Vector2f target, final float x, final float y) {
        target.x = x;
        target.y = y;
    }
    
    /**
     * 2-address implementation of the = operator.<br>
     * <i>target</i> = <i>(x,y)</i>
     * 
     * @param target The to set matrix
     * @param source The source matrix
     */
    public static final void set(Vector2f target, final Vector2f source) {
        target.x = source.x;
        target.y = source.y;
    }
    
    /**
     * Negates the vector.
     * @param vec The vector
     */
    public static final void neg(Vector2f vec) {
        vec.x = -vec.x;
        vec.y = -vec.y;
    }
    
    /**
     * 2-address implementation of the + operator.<br>
     * <i>target</i> = <i>target</i> + <i>add</i>
     * @param target The target vector
     * @param add The added vector
     */
    public static final void add(Vector2f target, final Vector2f add) {
        target.x += add.x;
        target.y += add.y;
    }
    
    /**
     * 2-address implementation of the - operator.<br>
     * <i>target</i> = <i>target</i> - <i>sub</i>
     * @param target The target vector
     * @param sub The subtracted vector
     */
    public static final void sub(Vector2f target, final Vector2f sub) {
        target.x -= sub.x;
        target.y -= sub.y;
    }
    
    /**
     * 2-address implementation of the * operator.<br>
     * <i>target</i> = <i>target</i> * <i>mul</i>
     * @param target The target vector
     * @param mul The multiplied vector
     */
    public static final void mul(Vector2f target, final Vector2f mul) {
        target.x *= mul.x;
        target.y *= mul.y;
    }
    
    /**
     * 2-address implementation of the scalar multiplication.<br>
     * <i>target</i> = <i>target</i> * <i>scalar</i>
     * @param target The target vector
     * @param scalar The multiplied scalar
     */
    public static final void mul(Vector2f target, final float scalar) {
        target.x *= scalar;
        target.y *= scalar;
    }
    
    /**
     * 2-address implementation of the scalar division.<br>
     * <i>target</i> = <i>target</i> / <i>divisor</i>
     * @param target The target vector
     * @param divisor The divided scalar
     */
    public static final void div(Vector2f target, final float divisor) {
        target.x /= divisor;
        target.y /= divisor;
    }
    
    /**
     * Calculates the dot product of two vectors.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return The dot product of the given vectors
     */
    public static final float dot(final Vector2f v1, final Vector2f v2) {
        return (v1.x * v2.x) + (v1.y * v2.y);
    }
    
    /**
     * Normalizes a vector.
     * @param vec The vector
     */
    public static final void normalize(Vector2f vec) {
        float len = vec.length();
        
        if (len != 0.0f) {
            vec.x /= len;
            vec.y /= len;
        }
    }
    
    /**
     * Duplicates the given vector.
     * @param model The vector
     * @return A new instance, representing the same vector as <i>model</i>
     */
    public static final Vector2f duplicate(final Vector2f model) {
        return new Vector2f( model.x, model.y );
    }
    
    /**
     * Calculates the distance between two vectors.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return The distance
     */
    public static final float distance(final Vector2f v1, final Vector2f v2) {
        Vector2f tmpV1 = Vector2f.POOL.get();
        float length;
        
        Vector2f.set(tmpV1, v1);
        Vector2f.sub(tmpV1, v2);

        length = tmpV1.length();
        
        Vector2f.POOL.release(tmpV1);
        
        return length;
    }

    /**
     * Calculates the angle between two vectors.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return The angle between the two vectors
     */
    public static final float angle(final Vector2f v1, final Vector2f v2) {
        float dls = Vector2f.dot(v1, v2) / (v1.length() * v2.length());

        if (dls < -1.0f) {
            dls = -1.0f;
        } else if (dls > 1.0f) {
            dls = 1.0f;
        }

        return (float) Math.acos(dls);
    }

    /**
     * Linear-interpolates between two vectors.
     * @param v1 The first vector
     * @param v2 The second vector
     * @param amount The amount of interpolation
     * @param out The instance in which the result is stored
     */
    public static final void lerp(final Vector2f v1, final Vector2f v2, final float amount, Vector2f out) {
        Vector2f tmpV2 = Vector2f.POOL.get();
        
        // out = (v1 * (1.0f - amount)) + (v2 * (amount))
        Vector2f.set(out, v1);
        Vector2f.set(tmpV2, v2);
        Vector2f.mul(out, 1.0f - amount);
        Vector2f.mul(tmpV2, amount);
        Vector2f.add(out, tmpV2);
        
        Vector2f.POOL.release(tmpV2);
    }

    /**
     * Moves a vector by a given amount in the direction of the destination.
     * @param start The start vector
     * @param dest The destination vector
     * @param stepDistance The amount of movement
     * @param out The instance in which the result is stored
     */
    public static final void moveTowards( final Vector2f start, final Vector2f dest, final float stepDistance, Vector2f out) {

        Vector2f tmpDist  = Vector2f.POOL.get();
        
        // dist = start - dest
        Vector2f.set(tmpDist, start);
        Vector2f.sub(tmpDist, dest);
        
        float length  = tmpDist.length();

        if (length <= stepDistance || length == 0.0f) {
            Vector2f.set(out, dest);
            return;
        }
        
        // out = start + (( dist / length ) * stepDistance)
        Vector2f.set(out, start);
        Vector2f.div(tmpDist, length);
        Vector2f.mul(tmpDist, stepDistance);
        Vector2f.add(out, tmpDist);
    }

    /**
     * If the first vector is longer than the second vector.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return If the first vector is longer than the second vector
     */
    public static final boolean isLongerAs(final Vector2f v1, final Vector2f v2) {
        return (v1.x * v1.x + v1.y * v1.y) > (v2.x * v2.x + v2.y * v2.y);
    }
    
    /**
     * If the first vector is shorter than the second vector.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return If the first vector is shorter than the second vector
     */
    public static final boolean isShorterAs(final Vector2f v1, final Vector2f v2) {
        return (v1.x * v1.x + v1.y * v1.y) < (v2.x * v2.x + v2.y * v2.y);
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                         Public                          */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    
    /**
     * Public-Constructor.
     */
    public Vector2f() {
        this(0.0f, 0.0f);
    }
    
    /**
     * Public-Constructor.
     * @param x1 The x value
     * @param y1 The y value
     */
    public Vector2f(final float x1, final float y1) {
        x = x1;
        y = y1;
    }


    /**
     * Returns the length.
     * @return The length
     */
    public final float length() {
        return (float) Math.sqrt(x*x + y*y);
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
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
        Vector2f other = (Vector2f) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        return true;
    }
    
    public float x;
    public float y;

}
