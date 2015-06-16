package de.orangestar.engine.values;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Matrix4f {
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                      Public Static                      */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    public static enum Order {
        ROW_MAJOR, COLUMN_MAJOR
    }
    
    public static final int ComponentsCount = 16;
    public static final int ByteSize = 16 * Float.BYTES;
        
    public static final Matrix4f zero() {
        return new Matrix4f(
            0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f
            );
    }
    
    public static final Matrix4f one() {
        return new Matrix4f(
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f
            );
    }


    public void writeTo(ByteBuffer buffer) {
        writeTo(buffer, Order.COLUMN_MAJOR);
    } 
    
    public void writeTo(ByteBuffer buffer, Order order) {
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
   
    public void writeTo(FloatBuffer buffer) {
        writeTo(buffer, Order.COLUMN_MAJOR);
    }
    
    public void writeTo(FloatBuffer buffer, Order order) {
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
    
    public static Matrix4f rotationAxis(float angle, Vector3f axis) {

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

        return new Matrix4f(
                     f00,  f01,  f02,  0.0f,
                     f10,  f11,  f12,  0.0f,
                     f20,  f21,  f22,  0.0f,
                    0.0f, 0.0f, 0.0f,  0.0f
                    );
    }

    public static Matrix4f frustum(float left, float right, float bottom, float top, float znear, float zfar ) {
        return new Matrix4f(
                    (2.0f * znear) / (right - left),
                    0.0f,
                    0.0f,
                    0.0f,

                    0.0f,
                    (2.0f * znear) / (top - bottom),
                    0.0f,
                    0.0f,

                    (right + left) / (right - left),
                    (top + bottom) / (top - bottom),
                    -(zfar + znear) / (zfar - znear),
                    -1.0f,

                    0.0f,
                    0.0f,
                    -(2.0f * zfar * znear) / (zfar - znear),
                    0.0f
                    );
    }

    public static Matrix4f perspective(float fov, float ratio, float znear, float zfar) {
        float xmin, xmax, ymin, ymax;

        ymax = znear * (float) Math.tan(Math.toRadians(fov * 0.5f));
        ymin = -ymax;

        xmin = ymin * ratio;
        xmax = ymax * ratio;

        return frustum(xmin, xmax, ymin, ymax, znear, zfar);
    }

    public static Matrix4f ortho2D(float left, float right, float bottom, float top) {
        float zNear = -1.0f;
        float zFar = 1.0f;
        float invZ = 1.0f / (zFar - zNear);
        float invY = 1.0f / (top - bottom);
        float invX = 1.0f / (right - left);

        Matrix4f result = new Matrix4f();

        result.m00 = 2.0f * invX;
//        result.m10 = 0.0f;
//        result.m20 = 0.0f;
//        result.m30 = 0.0f;

//        result.m01 = 0.0f;
        result.m11 = 2.0f * invY;
//        result.m21 = 0.0f;
//        result.m31 = 0.0f;

//        result.m02 = 0.0f);
//        result.m12 = 0.0f);
        result.m22 = -2.0f * invZ;
//        result.m32 = 0.0f;

        result.m03 = -(right + left) * invX;
        result.m13 = -(top  + bottom) * invY;
        result.m23 = -(zFar + zNear) * invZ;
        result.m33 = 1.0f;

        return result;
    }
    
    public static Matrix4f ortho(float left, float right, float bottom, float top, float znear, float zfar) {
        if (left == right || bottom == top || znear == zfar)
            return one();

        float width = right - left;
        float invheight = top - bottom;
        float clip = zfar - znear;

        return new Matrix4f(
                    2.0f / width,   0.0f,               0.0f,               -(left + right) / width,
                    0.0f,           2.0f / invheight,   0.0f,               -(top + bottom) / invheight,
                    0.0f,           0.0f,               -2.0f / clip,       -(znear + zfar) / clip,
                    0.0f,           0.0f,               0.0f,               1.0f
                    );
    }

    public static Matrix4f orthoLHCentered(float left, float right, float bottom, float top, float znear, float zfar) {
        float zRange = 1.0f / (zfar - znear);
        
        Matrix4f result = new Matrix4f(Matrix4f.one());
        result.m00 = 2.0f / (right - left);
        result.m11 = 2.0f / (top - bottom);
        result.m22 = zRange;
        result.m30 = (left + right) / (left - right);
        result.m31 = (top + bottom) / (bottom - top);
        result.m32 = -znear * zRange;
        
        return result;
    }
    
    public static Matrix4f orthoLH(float width, float height, float znear, float zfar) {
        float halfWidth =  width * 0.5f;
        float halfHeight = height * 0.5f;

        return orthoLHCentered(-halfWidth, halfWidth, -halfHeight, halfHeight, znear, zfar);
    }
    
    public static Matrix4f lookAtLH(Vector3f eye, Vector3f target, Vector3f up ) {
        Matrix4f result;
        
        Vector3f xaxis;
        Vector3f yaxis;
        Vector3f zaxis;
        
        zaxis = target.sub(eye).normalized();
        xaxis = up.cross(zaxis).normalized();
        yaxis = zaxis.cross(xaxis);
        
        result = Matrix4f.one();
        result.m00 = xaxis.x; result.m01 = yaxis.x; result.m02 = zaxis.x; 
        result.m10 = xaxis.y; result.m11 = yaxis.y; result.m12 = zaxis.y;
        result.m20 = xaxis.z; result.m21 = yaxis.z; result.m22 = zaxis.z;

        result.m30 = -xaxis.dot(eye);
        result.m31 = -yaxis.dot(eye);
        result.m32 = -zaxis.dot(eye);
        
        return result;
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                         Public                          */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    
    public Matrix4f()
    {
        this( 0.0f, 0.0f, 0.0f, 0.0f,
              0.0f, 0.0f, 0.0f, 0.0f,
              0.0f, 0.0f, 0.0f, 0.0f,
              0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Matrix4f(float val)
    {
        this( val, 0.0f, 0.0f, 0.0f,
             0.0f,  val, 0.0f, 0.0f,
             0.0f, 0.0f,  val, 0.0f,
             0.0f, 0.0f, 0.0f,  val);
    }

    public Matrix4f( float v00, float v01, float v02, float v03,
                     float v10, float v11, float v12, float v13,
                     float v20, float v21, float v22, float v23,
                     float v30, float v31, float v32, float v33 )
    {
        m00 = v00; m01 = v01; m02 = v02; m03 = v03;
        m10 = v10; m11 = v11; m12 = v12; m13 = v13;
        m20 = v20; m21 = v21; m22 = v22; m23 = v23;
        m30 = v30; m31 = v31; m32 = v32; m33 = v33;
    }
    
    public Matrix4f(Matrix4f copy) {
        m00 = copy.m00; m01 = copy.m01; m02 = copy.m02; m03 = copy.m03;
        m10 = copy.m10; m11 = copy.m11; m12 = copy.m12; m13 = copy.m13;
        m20 = copy.m20; m21 = copy.m21; m22 = copy.m22; m23 = copy.m23;
        m30 = copy.m30; m31 = copy.m31; m32 = copy.m32; m33 = copy.m33;
    }

    public Matrix4f add(Matrix4f x) {
        return new Matrix4f(
                    m00 + x.m00, m01 + x.m01, m02 + x.m02, m03 + x.m03,
                    m10 + x.m10, m11 + x.m11, m12 + x.m12, m13 + x.m13,
                    m20 + x.m20, m21 + x.m21, m22 + x.m22, m23 + x.m23,
                    m30 + x.m30, m31 + x.m31, m32 + x.m32, m33 + x.m33
                    );
    }

    public Matrix4f sub(Matrix4f x) {
        return new Matrix4f(
                    m00 - x.m00, m01 - x.m01, m02 - x.m02, m03 - x.m03,
                    m10 - x.m10, m11 - x.m11, m12 - x.m12, m13 - x.m13,
                    m20 - x.m20, m21 - x.m21, m22 - x.m22, m23 - x.m23,
                    m30 - x.m30, m31 - x.m31, m32 - x.m32, m33 - x.m33
                    );
    }

    public Matrix4f mul(Matrix4f x) {
        return new Matrix4f(
                    m00 * x.m00 + m10 * x.m01 + m20 * x.m02 + m30 * x.m03,
                    m01 * x.m00 + m11 * x.m01 + m21 * x.m02 + m31 * x.m03,
                    m02 * x.m00 + m12 * x.m01 + m22 * x.m02 + m32 * x.m03,
                    m03 * x.m00 + m13 * x.m01 + m23 * x.m02 + m33 * x.m03,
                    m00 * x.m10 + m10 * x.m11 + m20 * x.m12 + m30 * x.m13,
                    m01 * x.m10 + m11 * x.m11 + m21 * x.m12 + m31 * x.m13,
                    m02 * x.m10 + m12 * x.m11 + m22 * x.m12 + m32 * x.m13,
                    m03 * x.m10 + m13 * x.m11 + m23 * x.m12 + m33 * x.m13,
                    m00 * x.m20 + m10 * x.m21 + m20 * x.m22 + m30 * x.m23,
                    m01 * x.m20 + m11 * x.m21 + m21 * x.m22 + m31 * x.m23,
                    m02 * x.m20 + m12 * x.m21 + m22 * x.m22 + m32 * x.m23,
                    m03 * x.m20 + m13 * x.m21 + m23 * x.m22 + m33 * x.m23,
                    m00 * x.m30 + m10 * x.m31 + m20 * x.m32 + m30 * x.m33,
                    m01 * x.m30 + m11 * x.m31 + m21 * x.m32 + m31 * x.m33,
                    m02 * x.m30 + m12 * x.m31 + m22 * x.m32 + m32 * x.m33,
                    m03 * x.m30 + m13 * x.m31 + m23 * x.m32 + m33 * x.m33
                    );
    }

    public Quaternion4f ToQuaternion() {
        float x,y,z,w;

        float s;
        float tr = m00 + m11 + m22;
        if (tr >= 0.0) {
            s = (float) Math.sqrt(tr + 1.0);
            w = s * 0.5f;
            s = 0.5f / s;
            x = (m21 - m12) * s;
            y = (m02 - m20) * s;
            z = (m10 - m01) * s;
        } else {
            float max = Math.max(Math.max(m00, m11), m22);
            if (max == m00) {
                s = (float) Math.sqrt(m00 - (m11 + m22) + 1.0);
                x = s * 0.5f;
                s = 0.5f / s;
                y = (m01 + m10) * s;
                z = (m20 + m02) * s;
                w = (m21 - m12) * s;
            } else if (max == m11) {
                s = (float) Math.sqrt(m11 - (m22 + m00) + 1.0);
                y = s * 0.5f;
                s = 0.5f / s;
                z = (m12 + m21) * s;
                x = (m01 + m10) * s;
                w = (m02 - m20) * s;
            } else {
                s = (float) Math.sqrt(m22 - (m00 + m11) + 1.0);
                z = s * 0.5f;
                s = 0.5f / s;
                x = (m20 + m02) * s;
                y = (m12 + m21) * s;
                w = (m10 - m01) * s;
            }
        }

        return new Quaternion4f(x,y,z,w);
    }

    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

    @Override
    public int hashCode() {
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
    public boolean equals(Object obj) {
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
    
    public String toString() {
        return new StringBuilder().append(m00).append("|").append(m01).append("|").append(m02).append("|").append(m03)
                     .append("\n").append(m10).append("|").append(m11).append("|").append(m12).append("|").append(m13)
                     .append("\n").append(m20).append("|").append(m21).append("|").append(m22).append("|").append(m23)
                     .append("\n").append(m30).append("|").append(m31).append("|").append(m32).append("|").append(m33).toString();
    }
    
}
