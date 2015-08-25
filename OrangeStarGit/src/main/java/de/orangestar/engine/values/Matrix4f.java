package de.orangestar.engine.values;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import de.orangestar.engine.values.pools.Matrix4fPool;

/**
 * Represents a 4x4 matrix.
 * 
 * @author Oliver &amp; Basti
 */
public final class Matrix4f {
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                      Public Static                      */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    /**
     * The order for storing the matrices values in a buffer.<br>
     * This engine uses internally Row-Major-Order but is setup to use Column-Major-Order for buffer writing as default.<br>
     * <ul>
     * <li>DirectX : RowMajor
     * <li>OpenGL  : ColumnMajor
     * </ul>
     * @author Basti
     *
     */
    public static enum Order {
        ROW_MAJOR, COLUMN_MAJOR
    }
    
    public static final int             NUM_COMPONENTS  = 16;
    public static final int             BYTE_SIZE       = 16 * Float.BYTES;
    public static final Matrix4fPool    POOL            = new Matrix4fPool();

    /**
     * Tests if all values of the given instance are zero.
     * @param m The instance to test
     * @return If all values of the given instance are zero
     */
    public static final boolean isZero(final Matrix4f m) {
        return m.m00 == 0f && m.m01 == 0f && m.m02 == 0f && m.m03 == 0f 
            && m.m10 == 0f && m.m11 == 0f && m.m12 == 0f && m.m13 == 0f 
            && m.m20 == 0f && m.m21 == 0f && m.m22 == 0f && m.m23 == 0f
            && m.m30 == 0f && m.m31 == 0f && m.m32 == 0f && m.m33 == 0f;
    }
    
    /**
     * Tests if all values of the given instance are zero.
     * @param m The instance to test
     * @param delta The allowed variance of the float values of the matrix
     * @return If the given instance is an identity matrix
     */
    public static final boolean isZero(final Matrix4f m, final float delta) {
        return Math.abs(0.0f - m.m00) <= delta
                && Math.abs(0.0f - m.m01) <= delta
                && Math.abs(0.0f - m.m02) <= delta
                && Math.abs(0.0f - m.m03) <= delta
                && Math.abs(0.0f - m.m10) <= delta
                && Math.abs(0.0f - m.m11) <= delta
                && Math.abs(0.0f - m.m12) <= delta
                && Math.abs(0.0f - m.m13) <= delta
                && Math.abs(0.0f - m.m20) <= delta
                && Math.abs(0.0f - m.m21) <= delta
                && Math.abs(0.0f - m.m22) <= delta
                && Math.abs(0.0f - m.m23) <= delta
                && Math.abs(0.0f - m.m30) <= delta
                && Math.abs(0.0f - m.m31) <= delta
                && Math.abs(0.0f - m.m32) <= delta
                && Math.abs(0.0f - m.m33) <= delta;
    }
    
    /**
     * Tests if the given instance is an identity matrix.
     * @param m The instance to test
     * @return If the given instance is an identity matrix
     */
    public static final boolean isIdentity(final Matrix4f m) {
        return m.m00 == 1f && m.m01 == 0f && m.m02 == 0f && m.m03 == 0f 
            && m.m10 == 0f && m.m11 == 1f && m.m12 == 0f && m.m13 == 0f 
            && m.m20 == 0f && m.m21 == 0f && m.m22 == 1f && m.m23 == 0f
            && m.m30 == 0f && m.m31 == 0f && m.m32 == 0f && m.m33 == 1f;
    }
    
    /**
     * Tests if the given instance is an identity matrix.
     * @param m The instance to test
     * @param delta The allowed variance of the float values of the matrix
     * @return If the given instance is an identity matrix
     */
    public static final boolean isIdentity(final Matrix4f m, final float delta) {
        return Math.abs(1.0f - m.m00) <= delta
                && Math.abs(0.0f - m.m01) <= delta
                && Math.abs(0.0f - m.m02) <= delta
                && Math.abs(0.0f - m.m03) <= delta
                && Math.abs(0.0f - m.m10) <= delta
                && Math.abs(1.0f - m.m11) <= delta
                && Math.abs(0.0f - m.m12) <= delta
                && Math.abs(0.0f - m.m13) <= delta
                && Math.abs(0.0f - m.m20) <= delta
                && Math.abs(0.0f - m.m21) <= delta
                && Math.abs(1.0f - m.m22) <= delta
                && Math.abs(0.0f - m.m23) <= delta
                && Math.abs(0.0f - m.m30) <= delta
                && Math.abs(0.0f - m.m31) <= delta
                && Math.abs(0.0f - m.m32) <= delta
                && Math.abs(1.0f - m.m33) <= delta;
    }

    /**
     * Creates a new instance with all values initialized to zero.
     * @return A new instance with all values initialized to zero
     */
    public static final Matrix4f zero() {
        return new Matrix4f(0f);
    }
    
    /**
     * Sets all values of a given instance to zero.
     * @param out The given instance
     */
    public static final void zero(Matrix4f out) {
        out.m00 = 0.0f; out.m01 = 0.0f; out.m02 = 0.0f; out.m03 = 0.0f;
        out.m10 = 0.0f; out.m11 = 0.0f; out.m12 = 0.0f; out.m13 = 0.0f;
        out.m20 = 0.0f; out.m21 = 0.0f; out.m22 = 0.0f; out.m23 = 0.0f;
        out.m30 = 0.0f; out.m31 = 0.0f; out.m32 = 0.0f; out.m33 = 0.0f;
    }
    
    /**
     * Creates a new identity instance.
     * @return A new identity instance
     */
    public static final Matrix4f identity() {
        Matrix4f result = new Matrix4f();
        Matrix4f.identity( result );
        return result;
    }
    
    /**
     * Sets the values of the given instance to become an identity instance.
     * @param out The given instance
     */
    public static final void identity(Matrix4f out) {
        out.m00 = 1.0f; out.m01 = 0.0f; out.m02 = 0.0f; out.m03 = 0.0f;
        out.m10 = 0.0f; out.m11 = 1.0f; out.m12 = 0.0f; out.m13 = 0.0f;
        out.m20 = 0.0f; out.m21 = 0.0f; out.m22 = 1.0f; out.m23 = 0.0f;
        out.m30 = 0.0f; out.m31 = 0.0f; out.m32 = 0.0f; out.m33 = 1.0f;
    }

    /**
     * 2-address implementation of the = operator.<br>
     * <i>target</i> = <i>source</i>
     * 
     * @param target The to set matrix
     * @param source The source matrix
     */
    public static final void set(Matrix4f target, final Matrix4f source) {
        target.m00 = source.m00; target.m01 = source.m01; target.m02 = source.m02; target.m03 = source.m03;
        target.m10 = source.m10; target.m11 = source.m11; target.m12 = source.m12; target.m13 = source.m13;
        target.m20 = source.m20; target.m21 = source.m21; target.m22 = source.m22; target.m23 = source.m23;
        target.m30 = source.m30; target.m31 = source.m31; target.m32 = source.m32; target.m33 = source.m33;
    }
    
    /**
     * Inverts the given matrix.
     * @param out The matrix
     */
    public static final void invert(Matrix4f out) {
        double b0 = (out.m20 * out.m31) - (out.m21 * out.m30);
        double b1 = (out.m20 * out.m32) - (out.m22 * out.m30);
        double b2 = (out.m23 * out.m30) - (out.m21 * out.m33);
        double b3 = (out.m21 * out.m32) - (out.m22 * out.m31);
        double b4 = (out.m23 * out.m31) - (out.m21 * out.m33);
        double b5 = (out.m22 * out.m33) - (out.m23 * out.m32);
        
        double d11 = out.m11 *  b5 + out.m12 *  b4 + out.m13 * b3;
        double d12 = out.m10 *  b5 + out.m12 *  b2 + out.m13 * b1;
        double d13 = out.m10 * -b4 + out.m11 *  b2 + out.m13 * b0;
        double d14 = out.m10 *  b3 + out.m11 * -b1 + out.m12 * b0;
        
        double det = out.m00 * d11 - out.m01 * d12 + out.m02 * d13 - out.m03 * d14;
        if (Math.abs(det) == 0.0d) {
            Matrix4f.zero(out);
            return;
        }
        
        det = 1f / det;
        
        double a0 = (out.m00 * out.m11) - (out.m01 * out.m10);
        double a1 = (out.m00 * out.m12) - (out.m02 * out.m10);
        double a2 = (out.m03 * out.m10) - (out.m00 * out.m13);
        double a3 = (out.m01 * out.m12) - (out.m02 * out.m11);
        double a4 = (out.m03 * out.m11) - (out.m01 * out.m13);
        double a5 = (out.m02 * out.m13) - (out.m03 * out.m12);
        
        double d21 = out.m01 *  b5 + out.m02 *  b4 + out.m03 * b3;
        double d22 = out.m00 *  b5 + out.m02 *  b2 + out.m03 * b1;
        double d23 = out.m00 * -b4 + out.m01 *  b2 + out.m03 * b0;
        double d24 = out.m00 *  b3 + out.m01 * -b1 + out.m02 * b0;
        
        double d31 = out.m31 *  a5 + out.m32 *  a4 + out.m33 * a3;
        double d32 = out.m30 *  a5 + out.m32 *  a2 + out.m33 * a1;
        double d33 = out.m30 * -a4 + out.m31 *  a2 + out.m33 * a0;
        double d34 = out.m30 *  a3 + out.m31 * -a1 + out.m32 * a0;
        
        double d41 = out.m21 *  a5 + out.m22 *  a4 + out.m23 * a3;
        double d42 = out.m20 *  a5 + out.m22 *  a2 + out.m23 * a1;
        double d43 = out.m20 * -a4 + out.m21 *  a2 + out.m23 * a0;
        double d44 = out.m20 *  a3 + out.m21 * -a1 + out.m22 * a0;
        
        out.m00 = (float) (+d11 * det);
        out.m10 = (float) (-d12 * det);
        out.m20 = (float) (+d13 * det);
        out.m30 = (float) (-d14 * det);
        
        out.m01 = (float) (-d21 * det);
        out.m11 = (float) (+d22 * det);
        out.m21 = (float) (-d23 * det);
        out.m31 = (float) (+d24 * det);
        
        out.m02 = (float) (+d31 * det);
        out.m12 = (float) (-d32 * det);
        out.m22 = (float) (+d33 * det);
        out.m32 = (float) (-d34 * det);
        
        out.m03 = (float) (-d41 * det);
        out.m13 = (float) (+d42 * det);
        out.m23 = (float) (-d43 * det);
        out.m33 = (float) (+d44 * det);
    }

    /**
     * 2-address implementation of the + operator.<br>
     * <i>out</i> = <i>out</i> + <i>add</i>
     * @param out The matrix that is been added to
     * @param add The added matrix
     */
    public static final void add(Matrix4f out, final Matrix4f add) {
        out.m00 = out.m00 + add.m00;
        out.m01 = out.m01 + add.m01;
        out.m02 = out.m02 + add.m02;
        out.m03 = out.m03 + add.m03;
        
        out.m10 = out.m10 + add.m10;
        out.m11 = out.m11 + add.m11;
        out.m12 = out.m12 + add.m12;
        out.m13 = out.m13 + add.m13;
        
        out.m20 = out.m20 + add.m20;
        out.m21 = out.m21 + add.m21;
        out.m22 = out.m22 + add.m22;
        out.m23 = out.m23 + add.m23;
        
        out.m30 = out.m30 + add.m30;
        out.m31 = out.m31 + add.m31;
        out.m32 = out.m32 + add.m32;
        out.m33 = out.m33 + add.m33;
    }
    
    /**
     * 2-address implementation of the - operator.<br>
     * <i>out</i> = <i>out</i> - <i>sub</i>
     * @param out The matrix that is been subtracted from
     * @param sub The subtracted matrix
     */
    public static final void sub(Matrix4f out, final Matrix4f sub) {
        out.m00 = out.m00 - sub.m00;
        out.m01 = out.m01 - sub.m01;
        out.m02 = out.m02 - sub.m02;
        out.m03 = out.m03 - sub.m03;
        
        out.m10 = out.m10 - sub.m10;
        out.m11 = out.m11 - sub.m11;
        out.m12 = out.m12 - sub.m12;
        out.m13 = out.m13 - sub.m13;
        
        out.m20 = out.m20 - sub.m20;
        out.m21 = out.m21 - sub.m21;
        out.m22 = out.m22 - sub.m22;
        out.m23 = out.m23 - sub.m23;
        
        out.m30 = out.m30 - sub.m30;
        out.m31 = out.m31 - sub.m31;
        out.m32 = out.m32 - sub.m32;
        out.m33 = out.m33 - sub.m33;
    }
    
    /**
     * 2-address implementation of the * operator.<br>
     * <i>out</i> = <i>out</i> * <i>mult</i><br>
     * Note: A lot of float multiplications are happening here, so results may be inprecise!
     * @param out The matrix that is been subtracted from
     * @param mult The multiplicated matrix
     */
    public static final void mul(Matrix4f out, final Matrix4f mult) {
        Matrix4f dup = Matrix4f.POOL.get();        
        Matrix4f.set(dup, out);
        
        out.m00 = dup.m00 * mult.m00 + dup.m10 * mult.m01 + dup.m20 * mult.m02 + dup.m30 * mult.m03;
        out.m01 = dup.m01 * mult.m00 + dup.m11 * mult.m01 + dup.m21 * mult.m02 + dup.m31 * mult.m03;
        out.m02 = dup.m02 * mult.m00 + dup.m12 * mult.m01 + dup.m22 * mult.m02 + dup.m32 * mult.m03;
        out.m03 = dup.m03 * mult.m00 + dup.m13 * mult.m01 + dup.m23 * mult.m02 + dup.m33 * mult.m03;
        out.m10 = dup.m00 * mult.m10 + dup.m10 * mult.m11 + dup.m20 * mult.m12 + dup.m30 * mult.m13;
        out.m11 = dup.m01 * mult.m10 + dup.m11 * mult.m11 + dup.m21 * mult.m12 + dup.m31 * mult.m13;
        out.m12 = dup.m02 * mult.m10 + dup.m12 * mult.m11 + dup.m22 * mult.m12 + dup.m32 * mult.m13;
        out.m13 = dup.m03 * mult.m10 + dup.m13 * mult.m11 + dup.m23 * mult.m12 + dup.m33 * mult.m13;
        out.m20 = dup.m00 * mult.m20 + dup.m10 * mult.m21 + dup.m20 * mult.m22 + dup.m30 * mult.m23;
        out.m21 = dup.m01 * mult.m20 + dup.m11 * mult.m21 + dup.m21 * mult.m22 + dup.m31 * mult.m23;
        out.m22 = dup.m02 * mult.m20 + dup.m12 * mult.m21 + dup.m22 * mult.m22 + dup.m32 * mult.m23;
        out.m23 = dup.m03 * mult.m20 + dup.m13 * mult.m21 + dup.m23 * mult.m22 + dup.m33 * mult.m23;
        out.m30 = dup.m00 * mult.m30 + dup.m10 * mult.m31 + dup.m20 * mult.m32 + dup.m30 * mult.m33;
        out.m31 = dup.m01 * mult.m30 + dup.m11 * mult.m31 + dup.m21 * mult.m32 + dup.m31 * mult.m33;
        out.m32 = dup.m02 * mult.m30 + dup.m12 * mult.m31 + dup.m22 * mult.m32 + dup.m32 * mult.m33;
        out.m33 = dup.m03 * mult.m30 + dup.m13 * mult.m31 + dup.m23 * mult.m32 + dup.m33 * mult.m33;
        
        Matrix4f.POOL.release(dup);
    }


    /**
     * Multiplies the matrix with a vector and stores the result in the vector. (<i>this</i>*<i>vec</i>)
     * @param mat The matrix
     * @param out The multiplication vector
     */
    public static final void mul(final Matrix4f mat, Vector4f out) {
        Vector4f copy = Vector4f.POOL.get();
        Vector4f.set(copy, out);
        
        out.x = mat.m00 * copy.x + mat.m01 * copy.y + mat.m02 * copy.z + mat.m03 * copy.w;
        out.y = mat.m10 * copy.x + mat.m11 * copy.y + mat.m12 * copy.z + mat.m13 * copy.w;
        out.z = mat.m20 * copy.x + mat.m21 * copy.y + mat.m22 * copy.z + mat.m23 * copy.w;
        out.w = mat.m30 * copy.x + mat.m31 * copy.y + mat.m32 * copy.z + mat.m33 * copy.w;
        
        Vector4f.POOL.release(copy);
    }
    
    /**
     * Writes the matrix into a buffer in column major order.
     * @param buffer The buffer
     */
    public final void writeTo(ByteBuffer buffer) {
        writeTo(buffer, Order.COLUMN_MAJOR);
    } 
    
    /**
     * Writes the matrix into a buffer in the given order.
     * @param buffer The buffer
     * @param order The order
     */
    public final void writeTo(ByteBuffer buffer, final Order order) {
        if (order == Order.COLUMN_MAJOR) {
            buffer.putFloat(m00); buffer.putFloat(m10); buffer.putFloat(m20); buffer.putFloat(m30);
            buffer.putFloat(m01); buffer.putFloat(m11); buffer.putFloat(m21); buffer.putFloat(m31);
            buffer.putFloat(m02); buffer.putFloat(m12); buffer.putFloat(m22); buffer.putFloat(m32);
            buffer.putFloat(m03); buffer.putFloat(m13); buffer.putFloat(m23); buffer.putFloat(m33);
        } else {
            buffer.putFloat(m00); buffer.putFloat(m01); buffer.putFloat(m02); buffer.putFloat(m03);
            buffer.putFloat(m10); buffer.putFloat(m11); buffer.putFloat(m12); buffer.putFloat(m13);
            buffer.putFloat(m20); buffer.putFloat(m21); buffer.putFloat(m22); buffer.putFloat(m23);
            buffer.putFloat(m30); buffer.putFloat(m31); buffer.putFloat(m32); buffer.putFloat(m33);
        }
    }
   
    /**
     * Writes the matrix into a float buffer in column major order.
     * @param buffer The buffer
     */
    public final void writeTo(FloatBuffer buffer) {
        writeTo(buffer, Order.COLUMN_MAJOR);
    }
    
    /**
     * Writes the matrix into a float buffer in the given order.
     * @param buffer The buffer
     * @param order The order
     */
    public final void writeTo(FloatBuffer buffer, final Order order) {
        if (order == Order.COLUMN_MAJOR) {
            buffer.put(m00); buffer.put(m10); buffer.put(m20); buffer.put(m30);
            buffer.put(m01); buffer.put(m11); buffer.put(m21); buffer.put(m31);
            buffer.put(m02); buffer.put(m12); buffer.put(m22); buffer.put(m32);
            buffer.put(m03); buffer.put(m13); buffer.put(m23); buffer.put(m33);
        } else {
            buffer.put(m00); buffer.put(m01); buffer.put(m02); buffer.put(m03);
            buffer.put(m10); buffer.put(m11); buffer.put(m12); buffer.put(m13);
            buffer.put(m20); buffer.put(m21); buffer.put(m22); buffer.put(m23);
            buffer.put(m30); buffer.put(m31); buffer.put(m32); buffer.put(m33);
        }
    }
    
    /**
     * Creates a rotation matrix and stores it's values in the <i>out</i> instance.
     * @param angle The describe angle in radiant
     * @param axis The axis of the rotation
     * @param out The instance where the result is stored in
     */
    public static final void rotationAxis(final float angle, final Vector3f axis, Matrix4f out) {
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);
        float oneMinusC = 1.0f - c;
        float xy = axis.x*axis.y;
        float yz = axis.y*axis.z;
        float xz = axis.x*axis.z;
        float xs = axis.x*s;
        float ys = axis.y*s;
        float zs = axis.z*s;

        float f00 = (axis.x * axis.x * oneMinusC) + c;
        float f01 = (xy * oneMinusC) + zs;
        float f02 = (xz * oneMinusC) - ys;

        float f10 = (xy * oneMinusC) - zs;
        float f11 = (axis.y * axis.y * oneMinusC) + c;
        float f12 = (yz * oneMinusC) +xs;

        float f20 = (xz * oneMinusC) + ys;
        float f21 = (yz * oneMinusC) - xs;
        float f22 = (axis.z * axis.z * oneMinusC ) + c;

        out.m00 = f00; out.m01 = f01; out.m02 = f02; out.m03 = 0.0f;
        out.m10 = f10; out.m11 = f11; out.m12 = f12; out.m13 = 0.0f;
        out.m20 = f20; out.m21 = f21; out.m22 = f22; out.m23 = 0.0f;
        out.m30 = 0.0f; out.m31 = 0.0f; out.m32 = 0.0f; out.m33 = 0.0f;
    }
    
    /**
     * Creates a translation matrix by a given vector.
     * @param pos The translation vector
     * @param out The instance in which the result is stored
     */
    public static final void translation(final Vector3f pos, Matrix4f out) {
        identity(out);
        out.m03 = pos.x;
        out.m13 = pos.y;
        out.m23 = pos.z;
    }

    /**
     * Creates a scaling matrix by a given vector.
     * @param scale The scaling vector
     * @param out The instance in which the result is stored
     */
    public static final void scaling(final Vector3f scale, Matrix4f out) {
        identity(out);
        out.m00 = scale.x;
        out.m11 = scale.y;
        out.m22 = scale.z;
    }


    /**
     * Creates a view frustum matrix.
     * @param left The x value that represents the left view plane
     * @param right The x value that represents the right view plane
     * @param bottom The y value that represents the bottom view plane
     * @param top The y value that represents the top view plane
     * @param znear The z value that represents the near view plane
     * @param zfar The z value that represents the far view plane
     * @param out The instance in which the result is stored
     */
    public static final void frustum(final float left, final float right, final float bottom, final float top, final float znear, final float zfar, Matrix4f out ) {
        out.m00 = (2.0f * znear) / (right - left); 
        out.m01 = 0.0f; 
        out.m02 = 0.0f; 
        out.m03 = 0.0f;
        
        out.m10 = 0.0f; 
        out.m11 = (2.0f * znear) / (top - bottom); 
        out.m12 = 0.0f; 
        out.m13 = 0.0f;
        
        out.m20 = (right + left) / (right - left); 
        out.m21 = (top + bottom) / (top - bottom);
        out.m22 = -(zfar + znear) / (zfar - znear);
        out.m23 = -1.0f;                          
        
        out.m30 = 0.0f;
        out.m31 = 0.0f;
        out.m32 = -(2.0f * zfar * znear) / (zfar - znear);
        out.m33 = 0.0f;
    }

    /**
     * Creates a perspective transformation matrix for simulating a perspective view.
     * This creates a PROJECTION-MATRIX
     * @param fov The field of view in degrees
     * @param ratio The ratio of the frustrum
     * @param znear The near plane
     * @param zfar The far plane
     * @param out The matrix in which the result is stored
     */
    public static final void perspective(final float fov, final float ratio, final float znear, final float zfar, Matrix4f out) {
        float xmin, xmax, ymin, ymax;

        ymax = znear * (float) Math.tan(Math.toRadians(fov * 0.5f));
        ymin = -ymax;

        xmin = ymin * ratio;
        xmax = ymax * ratio;

        frustum(xmin, xmax, ymin, ymax, znear, zfar, out);
    }

    /**
     * Creates an orthographics transformation matrix for simulating an orthographic view.
     * This creates a PROJECTION-MATRIX
     * @param left The x value of the left view clipping plane (what x value shall be mapped to the left of the monitor)
     * @param right The x value of right view clipping  plane (what x value shall be mapped to the right of the monitor)
     * @param bottom The y value of the bottom view clipping plane (what y value shall be mapped to the bottom of the monitor)
     * @param top The y value of the top view clipping plane (what y value shall be mapped to the top of the monitor)
     * @param out The matrix in which the result is stored
     */
    public static final void ortho2D(final float left, final float right, final float bottom, final float top, Matrix4f out) {
        ortho2D(left, right, bottom, top, -1f, 1f, out);
    }
    
    /**
     * Creates an orthographics transformation matrix for simulating an orthographic view.
     * This creates a PROJECTION-MATRIX
     * @param left The x value of the left view clipping plane (what x value shall be mapped to the left of the monitor)
     * @param right The x value of right view clipping  plane (what x value shall be mapped to the right of the monitor)
     * @param bottom The y value of the bottom view clipping plane (what y value shall be mapped to the bottom of the monitor)
     * @param top The y value of the top view clipping plane (what y value shall be mapped to the top of the monitor)
     * @param zNear The z value of the near view clipping plane (how close objects can come to the camera and still be visible)
     * @param zFar The z value of the far view clipping plane (how far objects can go from the camera and still be visible)
     * @param out The matrix to write the result to
     */
    public static void ortho2D(final float left, final float right, final float bottom, final float top, final float zNear, final float zFar, Matrix4f out) {
        if (left == right || bottom == top) {
            identity(out);
            return;
        }

        float invZ = 1.0f / (zFar - zNear);
        float invY = 1.0f / (top - bottom);
        float invX = 1.0f / (right - left);

        out.m00 = 2.0f * invX;
        out.m10 = 0.0f;
        out.m20 = 0.0f;
        out.m30 = 0.0f;

        out.m01 = 0.0f;
        out.m11 = 2.0f * invY;
        out.m21 = 0.0f;
        out.m31 = 0.0f;

        out.m02 = 0.0f;
        out.m12 = 0.0f;
        out.m22 = -2.0f * invZ;
        out.m32 = 0.0f;

        out.m03 = -(right + left) * invX;
        out.m13 = -(top  + bottom) * invY;
        out.m23 = -(zFar + zNear) * invZ;
        out.m33 = 1.0f;
    }

    /**
     * Creates a camera like view transformation matrix with an eye and a target.
     * This creates a VIEW-MATRIX
     * @param eye The eye, where the camera sits
     * @param target The target, where the camera is pointed at
     * @param up The up orientation
     * @param out The matrix to write the result to
     */
    public static final void lookAtLH(final Vector3f eye, final Vector3f target, final Vector3f up, Matrix4f out ) {

        Vector3f xaxis = Vector3f.POOL.get();
        Vector3f yaxis = Vector3f.POOL.get();
        Vector3f zaxis = Vector3f.POOL.get();
        
        // zaxis = (tartget-eye).normalize()
        zaxis.x = target.x - eye.x;
        zaxis.y = target.y - eye.y;
        zaxis.z = target.z - eye.z;
        Vector3f.normalize(zaxis);
        
        // xaxis = up.cross(zaxis).normalize()
        xaxis.x = up.x;
        xaxis.y = up.y;
        xaxis.z = up.z;
        Vector3f.cross(xaxis, zaxis);
        Vector3f.normalize(xaxis);

        // yaxis = zaxis.cross(xaxis)
        yaxis.x = zaxis.x;
        yaxis.y = zaxis.y;
        yaxis.z = zaxis.z;
        Vector3f.cross(yaxis, xaxis);

        Matrix4f.identity(out);
        out.m00 = xaxis.x; out.m10 = yaxis.x; out.m20 = zaxis.x; 
        out.m01 = xaxis.y; out.m11 = yaxis.y; out.m21 = zaxis.y;
        out.m02 = xaxis.z; out.m12 = yaxis.z; out.m22 = zaxis.z;

        out.m03 = -Vector3f.dot(xaxis, eye);
        out.m13 = -Vector3f.dot(yaxis, eye);
        out.m32 = -Vector3f.dot(zaxis, eye);
        
        Vector3f.POOL.release(xaxis);
        Vector3f.POOL.release(yaxis);
        Vector3f.POOL.release(zaxis);
    }
    
    /**
     * Converts a rotation matrix into a quaternion.
     * @param rot The rotation matrix
     * @param out The quaternion in which the result is stored
     */
    public static final void rotationToQuaternion(final Matrix4f rot, Quaternion4f out) {
        float x,y,z,w;

        float s;
        float tr = rot.m00 + rot.m11 + rot.m22;
        if (tr >= 0.0) {
            s = (float) Math.sqrt(tr + 1.0);
            w = s * 0.5f;
            s = 0.5f / s;
            x = (rot.m21 - rot.m12) * s;
            y = (rot.m02 - rot.m20) * s;
            z = (rot.m10 - rot.m01) * s;
        } else {
            float max = Math.max(Math.max(rot.m00, rot.m11), rot.m22);
            if (max == rot.m00) {
                s = (float) Math.sqrt(rot.m00 - (rot.m11 + rot.m22) + 1.0);
                x = s * 0.5f;
                s = 0.5f / s;
                y = (rot.m01 + rot.m10) * s;
                z = (rot.m20 + rot.m02) * s;
                w = (rot.m21 - rot.m12) * s;
            } else if (max == rot.m11) {
                s = (float) Math.sqrt(rot.m11 - (rot.m22 + rot.m00) + 1.0);
                y = s * 0.5f;
                s = 0.5f / s;
                z = (rot.m12 + rot.m21) * s;
                x = (rot.m01 + rot.m10) * s;
                w = (rot.m02 - rot.m20) * s;
            } else {
                s = (float) Math.sqrt(rot.m22 - (rot.m00 + rot.m11) + 1.0);
                z = s * 0.5f;
                s = 0.5f / s;
                x = (rot.m20 + rot.m02) * s;
                y = (rot.m12 + rot.m21) * s;
                w = (rot.m10 - rot.m01) * s;
            }
        }

        out.x = x;
        out.y = y;
        out.z = z;
        out.w = w;
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                         Public                          */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    
    /**
     * Public-Constructor. Initializes all values to zero.
     */
    public Matrix4f() {
        this( 0.0f, 0.0f, 0.0f, 0.0f,
              0.0f, 0.0f, 0.0f, 0.0f,
              0.0f, 0.0f, 0.0f, 0.0f,
              0.0f, 0.0f, 0.0f, 0.0f);
    }

    /**
     * Public-Constructor.
     * @param val Initializes all values of the main diagonal to <i>val</i>
     */
    public Matrix4f(final float val) {
        this( val, 0.0f, 0.0f, 0.0f,
             0.0f,  val, 0.0f, 0.0f,
             0.0f, 0.0f,  val, 0.0f,
             0.0f, 0.0f, 0.0f,  val);
    }

    /**
     * Public-Constructor.
     * @param v00 The 0/0 value
     * @param v01 The 0/1 value
     * @param v02 The 0/2 value
     * @param v03 The 0/3 value
     * @param v10 The 1/0 value
     * @param v11 The 1/1 value
     * @param v12 The 1/2 value
     * @param v13 The 1/3 value
     * @param v20 The 2/0 value
     * @param v21 The 2/1 value
     * @param v22 The 2/2 value
     * @param v23 The 2/3 value
     * @param v30 The 3/0 value
     * @param v31 The 3/1 value
     * @param v32 The 3/2 value
     * @param v33 The 3/3 value
     */
    public Matrix4f( final float v00, final float v01, final float v02, final float v03,
                     final float v10, final float v11, final float v12, final float v13,
                     final float v20, final float v21, final float v22, final float v23,
                     final float v30, final float v31, final float v32, final float v33 )
    {
        m00 = v00; m01 = v01; m02 = v02; m03 = v03;
        m10 = v10; m11 = v11; m12 = v12; m13 = v13;
        m20 = v20; m21 = v21; m22 = v22; m23 = v23;
        m30 = v30; m31 = v31; m32 = v32; m33 = v33;
    }


    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(m00);
        result = prime * result + Float.floatToIntBits(m01);
        result = prime * result + Float.floatToIntBits(m02);
        result = prime * result + Float.floatToIntBits(m03);
        result = prime * result + Float.floatToIntBits(m10);
        result = prime * result + Float.floatToIntBits(m11);
        result = prime * result + Float.floatToIntBits(m12);
        result = prime * result + Float.floatToIntBits(m13);
        result = prime * result + Float.floatToIntBits(m20);
        result = prime * result + Float.floatToIntBits(m21);
        result = prime * result + Float.floatToIntBits(m22);
        result = prime * result + Float.floatToIntBits(m23);
        result = prime * result + Float.floatToIntBits(m30);
        result = prime * result + Float.floatToIntBits(m31);
        result = prime * result + Float.floatToIntBits(m32);
        result = prime * result + Float.floatToIntBits(m33);
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
        Matrix4f other = (Matrix4f) obj;
        if (Float.floatToIntBits(m00) != Float.floatToIntBits(other.m00))
            return false;
        if (Float.floatToIntBits(m01) != Float.floatToIntBits(other.m01))
            return false;
        if (Float.floatToIntBits(m02) != Float.floatToIntBits(other.m02))
            return false;
        if (Float.floatToIntBits(m03) != Float.floatToIntBits(other.m03))
            return false;
        if (Float.floatToIntBits(m10) != Float.floatToIntBits(other.m10))
            return false;
        if (Float.floatToIntBits(m11) != Float.floatToIntBits(other.m11))
            return false;
        if (Float.floatToIntBits(m12) != Float.floatToIntBits(other.m12))
            return false;
        if (Float.floatToIntBits(m13) != Float.floatToIntBits(other.m13))
            return false;
        if (Float.floatToIntBits(m20) != Float.floatToIntBits(other.m20))
            return false;
        if (Float.floatToIntBits(m21) != Float.floatToIntBits(other.m21))
            return false;
        if (Float.floatToIntBits(m22) != Float.floatToIntBits(other.m22))
            return false;
        if (Float.floatToIntBits(m23) != Float.floatToIntBits(other.m23))
            return false;
        if (Float.floatToIntBits(m30) != Float.floatToIntBits(other.m30))
            return false;
        if (Float.floatToIntBits(m31) != Float.floatToIntBits(other.m31))
            return false;
        if (Float.floatToIntBits(m32) != Float.floatToIntBits(other.m32))
            return false;
        if (Float.floatToIntBits(m33) != Float.floatToIntBits(other.m33))
            return false;
        return true;
    }
    
    @Override
    public final String toString() {
        return new StringBuilder().append(m00).append("|").append(m01).append("|").append(m02).append("|").append(m03)
                     .append("\n").append(m10).append("|").append(m11).append("|").append(m12).append("|").append(m13)
                     .append("\n").append(m20).append("|").append(m21).append("|").append(m22).append("|").append(m23)
                     .append("\n").append(m30).append("|").append(m31).append("|").append(m32).append("|").append(m33).toString();
    }

    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

}
