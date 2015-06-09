package de.orangestar.engine.values;

public class Vector3f {
    
    public static final int ComponentsCount = 3;
    public static final int ByteSize = 3 * Float.BYTES;
    
    public static Vector3f zero() {
        return new Vector3f(0f, 0f, 0f);
    }
    
    public static Vector3f one() {
        return new Vector3f(1f, 1f, 1f);
    }

    public static float    distance(Vector3f v1, Vector3f v2)
    {
        return (v1.sub(v2)).length();
    }

    public static float    angle(Vector3f v1, Vector3f v2)
    {
        float dls = v1.dot(v2) / (v1.length() * v2.length());

        if (dls < -1.0f) {
            dls = -1.0f;
        } else if (dls > 1.0f) {
            dls = 1.0f;
        }

        return (float) Math.acos(dls);
    }

    public static Vector3f lerp(Vector3f v1, Vector3f v2, float amount)
    {
        return (v1.mul(1.0f - amount)).add(v2.mul(amount));
    }

    public static Vector3f moveTowards( Vector3f start, Vector3f dest, float stepDistance)
    {
        Vector3f dist = start.sub(dest);
        float length  = dist.length();

        if (length <= stepDistance || length == 0.0f)
        {
            return dest;
        }
        return start.add((dist.div(length)).mul(stepDistance));
    }

    public static boolean     isLongerAs(Vector3f v1, Vector3f v2)
    {
        return (v1.x*v1.x + v1.y*v1.y + v1.z*v1.z) > (v2.x*v2.x + v2.y*v2.y + v2.z*v2.z);
    }

    public static boolean     isShorterAs(Vector3f v1, Vector3f v2)
    {
        return (v1.x*v1.x + v1.y*v1.y + v1.z*v1.z) < (v2.x*v2.x + v2.y*v2.y + v2.z*v2.z);
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                         Public                          */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public Vector3f()
    {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3f(float x1, float y1) {
        this(x1, y1, 0);
    }
    
    public Vector3f(float x1, float y1, float z1) {
        x = x1;
        y = y1;
        z = z1;
    }

    public Vector3f    neg() {
        return new Vector3f(-x,-y,-z);
    }

    public Vector3f    add(Vector3f vec)
    {
        return new Vector3f(x + vec.x, y + vec.y, z + vec.z);
    }

    public Vector3f    sub(Vector3f vec)
    {
        return new Vector3f(x - vec.x, y - vec.y, z - vec.z);
    }

    public Vector3f    mul(Vector3f vec)
    {
        return new Vector3f(x * vec.x, y * vec.y, z * vec.z);
    }

    public Vector3f    mul(float scalar)
    {
        return new Vector3f(x * scalar, y * scalar, z * scalar);
    }

    public Vector3f    div(float divisor) {
        return new Vector3f(x / divisor, y / divisor, z / divisor);
    }

    public Vector3f    cross(Vector3f vec) {
        return new Vector3f(y * vec.z - z * vec.y,
                        z * vec.x - x * vec.z,
                        x * vec.y - y * vec.x);
    }

    public float       dot(Vector3f vec) {
        return x * vec.x + y * vec.y + z * vec.z;
    }

    public float       length() {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3f    normalized() {
        float len = length();

        if (len != 0.0f) {
            return new Vector3f(x / len, y / len, z / len);
        }

        return new Vector3f(x,y,z);
    }
    
    public Vector3f duplicate() {
        return new Vector3f( x, y, z );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
    public String toString() {
        return super.toString() + "[" + x + ", " + y + ", " + z + " ]";
    }

    public float x;
    public float y;
    public float z;
    
}
