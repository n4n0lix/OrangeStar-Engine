package de.orangestar.engine.values;

import de.orangestar.engine.values.pools.Vector4fPool;

public final class Vector4f {

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                      Public Static                      */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    
	public static final int             NUM_COMPONENTS  = 4;
    public static final int             BYTE_SIZE       = 4 * Float.BYTES;
    public static final Vector4fPool    POOL            = new Vector4fPool();
    
    public static final Vector4f X_AXIS = new Vector4f(1f, 0f, 0f, 0f);
    public static final Vector4f Y_AXIS = new Vector4f(0f, 1f, 0f, 0f);
    public static final Vector4f Z_AXIS = new Vector4f(0f, 0f, 1f, 0f);
    public static final Vector4f W_AXIS = new Vector4f(0f, 0f, 0f, 1f);
    
    /**
     * Returns a vector with all values initialized to zero.
     * @return The vector
     */
    public static final Vector4f zero() {
        return new Vector4f(0f, 0f, 0f, 0f);
    }
    
    /**
     * Sets all values of the vector to zero.
     * @param out The vector
     */
    public static final void zero(Vector4f out) {
        out.x = 0f;
        out.y = 0f;
        out.z = 0f;
        out.w = 0f;
    }
    
    /**
     * Checks if the given vector is a null vector.
     * @param v The vector
     * @return If the given vector is a null vector
     */
    public static boolean isZero(Vector4f v) {
        return v.x == 0f && v.y == 0f && v.z == 0f && v.w == 0f;
    }
    
    /**
     * Returns a vector with all values initialized to one.
     * @return The vector
     */
    public static final Vector4f one() {
        return new Vector4f(1f, 1f, 1f, 1f);
    }
    
    /**
     * Sets all values of the vector to one.
     * @param out The vector
     */
    public static final void one(Vector4f out) {
        out.x = 1f;
        out.y = 1f;
        out.z = 1f;
        out.w = 1f;
    }
    
    /**
     * Checks if the given vector is a unit vector.
     * @param v The vector
     * @return If the given vector is a unit vector
     */
    public static boolean isUnit(Vector4f v) {
        return v.x == 1f && v.y == 1f && v.z == 1f && v.w == 1f;
    }
    
    /**
     * 2-address implementation of the = operator.<br>
     * <i>target</i> = <i>source</i>
     * @param target The target vector
     * @param source The source vector
     */
    public static final void set(Vector4f target, final Vector4f source) {
        target.x = source.x;
        target.y = source.y;
        target.z = source.z;
        target.w = source.w;
    }
    
    /**
     * 2-address implementation of the = operator.<br>
     * <i>target</i> = <i>source</i>
     * @param target The target vector
     * @param x The x value
     * @param y The y value
     * @param z The z value
     * @param w The w value
     */
    public static final void set(Vector4f target, final float x, final float y, final float z, final float w) {
        target.x = x;
        target.y = y;
        target.z = z;
        target.w = w;
    }

    /**
     * Negates the given vector.
     * @param out The vector
     */
    public static final void neg(Vector4f out) {
        out.x = -out.x;
        out.y = -out.y;
        out.z = -out.z;
        out.w = -out.w;
    }

    /**
     * 2-address implementation of the + operator.<br>
     * <i>target</i> = <i>target</i> + <i>add</i>
     * @param target The target vector
     * @param add The added vector
     */
    public static final void add(Vector4f target, final Vector4f add) {
        target.x += add.x;
        target.y += add.y;
        target.z += add.z;
        target.w += add.w;
    }

    /**
     * 2-address implementation of the - operator.<br>
     * <i>target</i> = <i>target</i> - <i>sub</i>
     * @param target The target vector
     * @param sub The subtracted vector
     */
    public static final void sub(Vector4f target, final Vector4f sub) {
        target.x -= sub.x;
        target.y -= sub.y;
        target.z -= sub.z;
        target.w -= sub.w;
    }

    /**
     * 2-address implementation of the * operator.<br>
     * <i>target</i> = <i>target</i> * <i>mul</i>
     * @param target The target vector
     * @param mul The multiplied vector
     */
    public static final void mul(Vector4f target, final Vector4f mul) {
        target.x *= mul.x;
        target.y *= mul.y;
        target.z *= mul.z;
        target.w *= mul.w;
    }

    /**
     * 2-address implementation of the scalar multiplication.<br>
     * <i>target</i> = <i>target</i> * <i>scalar</i>
     * @param target The target vector
     * @param scalar The scalar
     */
    public static final void mul(Vector4f target, final float scalar) {
        target.x *= scalar;
        target.y *= scalar;
        target.z *= scalar;
        target.w *= scalar;
    }
    
    /**
     * 2-address implementation of the scalar division.<br>
     * <i>target</i> = <i>target</i> / <i>scalar</i>
     * @param target The target vector
     * @param scalar The scalar
     */
    public static final void div(Vector4f target, final float scalar) {
        target.x /= scalar;
        target.y /= scalar;
        target.z /= scalar;
        target.w /= scalar;
    }

    /**
     * Duplicates the given instance.
     * @param model The model vector
     * @return A new instance representing the same vector as <i>model</i>
     */
    public static final Vector4f duplicate(final Vector4f model) {
        return new Vector4f( model.x, model.y, model.z, model.w );
    }
    
    /**
     * Linear-interpolation between two vectors by a given amount.
     * @param v1 The first vector
     * @param v2 The second vector
     * @param amount The amount of interpolation
     * @param out The instance in which the result is stored
     */
    public static final void lerp(final Vector4f v1, final Vector4f v2, final float amount, Vector4f out) {
        // out = (v1 * (1.0f - amount)) + (v2 * amount)
        
        Vector4f tmpV1  = Vector4f.POOL.get();
        Vector4f tmpV2  = Vector4f.POOL.get();
        
        Vector4f.set(tmpV1, v1);                // tmpV1 = v1
        Vector4f.set(tmpV2, v2);                // tmpV2 = v2

        Vector4f.mul(tmpV1, 1.0f - amount);     // tmpV1 = tmpV1 * (1.0f - amount)
        Vector4f.mul(tmpV2, amount);            // tmpV2 = tmpV2 * amount

        Vector4f.set(out, tmpV1);               // result = tmpV1 + tmpV2
        Vector4f.add(out, tmpV2);

        Vector4f.POOL.release(tmpV1);
        Vector4f.POOL.release(tmpV2);
    }

    /**
     * Checks if <i>v1</i> is longer as <i>v2</i>.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return  If <i>v1</i> is longer as <i>v2</i>
     */
    public static final boolean isLongerAs(final Vector4f v1, final Vector4f v2) {
        return (v1.x * v1.x + v1.y * v1.y + v1.z * v1.z + v1.w * v1.w) > (v2.x * v2.x + v2.y * v2.y + v2.z * v2.z+ v2.w * v2.w);
    }
    
    /**
     * Checks if <i>v1</i> is shorter as <i>v2</i>.
     * @param v1 The first vector
     * @param v2 The second vector
     * @return  If <i>v1</i> is shorter as <i>v2</i>
     */
    public static final boolean isShorterAs(final Vector4f v1, final Vector4f v2) {
        return (v1.x * v1.x + v1.y * v1.y + v1.z * v1.z + v1.w * v1.w) < (v2.x * v2.x + v2.y * v2.y + v2.z * v2.z + v2.w * v2.w);
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                         Public                          */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    
    /**
     * Public-Constructor. Initializes all values to zero.
     */
    public Vector4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    /**
     * Public-Constructor.
     * @param x1 The x value
     * @param y1 The y value
     * @param z1 The z value
     * @param w1 The w value
     */
    public Vector4f(final float x1, final float y1, final float z1, final float w1) {
        x = x1;
        y = y1;
        z = z1;
        w = w1;
    }

    /**
     * Converts this 4 component vector into a 3 component vector by omitting the <i>w</i> value.
     * @return A 3 componen vector
     */
    public final Vector3f toVector3f() {
    	return new Vector3f(x, y, z);
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
		Vector4f other = (Vector4f) obj;
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
        return "[" + x + ", " + y + ", " + z + ", " + w + " ]";
    }

    public float x;
    public float y;
    public float z;
    public float w;
    
}
