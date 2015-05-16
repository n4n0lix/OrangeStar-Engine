package de.orangestar.engine.values;

public class Quaternion4f {
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                      Public Static                      */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    public static final int ComponentsCount = 4;
    public static final int ByteSize = 4 * Float.BYTES;
    
    public static final Quaternion4f   Identity = new Quaternion4f(0.0f, 0.0f, 0.0f, 1.0f);


    public static Quaternion4f   lerp(Quaternion4f q1, Quaternion4f q2, float amount) {
        return (q1.mul(1.0f - amount).add(q2.mul(amount)) ).Normalized();
    }

    public static Quaternion4f   duplicate(Quaternion4f q) {
        return new Quaternion4f(q.x, q.y, q.z, q.w);
    }

    public static Quaternion4f   RotationAxis(Vector3f axis, float angle)
    {
        float s = (float) Math.sin(angle/2);

        return new Quaternion4f(
                    axis.x * s,
                    axis.y * s,
                    axis.z * s,
                    (float) Math.cos(angle/2)
                    );
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                         Public                          */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    public Quaternion4f()
    {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public Quaternion4f(float x1, float y1, float z1, float w1)
    {
        x = x1;
        y = y1;
        z = z1;
        w = w1;
    }


    public Quaternion4f add(Quaternion4f q)
    {
        return new Quaternion4f(
                        x + q.x,
                        y + q.y,
                        z + q.z,
                        w + q.w
                    );
    }

    public Quaternion4f mul(float scalar)
    {
        return new Quaternion4f(
                        x * scalar,
                        y * scalar,
                        z * scalar,
                        w * scalar
                    );
    }

    public Quaternion4f mul(Quaternion4f q)
    {
        return new Quaternion4f(
                        x * q.w + y * q.z - z * q.y + w * q.x,
                       -x * q.z + y * q.w + z * q.x + w * q.y,
                        x * q.y - y * q.x + z * q.w + w * q.z,
                       -x * q.x - y * q.y - z * q.z + w * q.w
                    );
    }

    public Quaternion4f  Normalized() {
        float n = (float) Math.sqrt(x*x + y*y + z*z + w*w);

        if (n == 0.0f) {
            return new Quaternion4f(x,y,z,w);
        }

        return new Quaternion4f( x / n, y / n, z / n, w / n );
    }

    public Matrix4f    toMatrix4f() {
        float xx      = x * x;
        float xy      = x * y;
        float xz      = x * z;
        float xw      = x * w;

        float yy      = y * y;
        float yz      = y * z;
        float yw      = y * w;

        float zz      = z * z;
        float zw      = z * w;

        return new Matrix4f( 1.0f - 2.0f * ( yy + zz ),        2.0f * ( xy - zw ),        2.0f * ( xz + yw ), 0.0f,
                                    2.0f * ( xy + zw ), 1.0f - 2.0f * ( xx + zz ),        2.0f * ( yz - xw ), 0.0f,
                                    2.0f * ( xz - yw ),        2.0f * ( yz + xw ), 1.0f - 2.0f * ( xx + yy ), 0.0f,
                                                  0.0f,                      0.0f,                      0.0f, 1.0f );
    }

    public float x;
    public float y;
    public float z;
    public float w;


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(w);
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
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
    
    
}
