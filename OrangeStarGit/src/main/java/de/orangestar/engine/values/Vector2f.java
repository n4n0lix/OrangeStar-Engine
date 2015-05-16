package de.orangestar.engine.values;

public class Vector2f {
    
    public static final int ComponentsCount = 2;
    public static final int ByteSize = 2 * Float.BYTES;
    
    public static final Vector2f Zero = new Vector2f(0.0f, 0.0f);
    public static final Vector2f One  = new Vector2f(1.0f, 1.0f);


    public static float    Distance(Vector2f v1, Vector2f v2)
    {
        return (v1.sub(v2)).Length();
    }

    public static float    Angle(Vector2f v1, Vector2f v2)
    {
        float dls = v1.dot(v2) / (v1.Length() * v2.Length());

        if (dls < -1.0f) {
            dls = -1.0f;
        } else if (dls > 1.0f) {
            dls = 1.0f;
        }

        return (float) Math.acos(dls);
    }

    public static Vector2f Lerp(Vector2f v1, Vector2f v2, float amount)
    {
        return (v1.mul(1.0f - amount)).add(v2.mul(amount));
    }

    public static Vector2f MoveTowards( Vector2f start, Vector2f dest, float stepDistance)
    {
        Vector2f dist = start.sub(dest);
        float length  = dist.Length();

        if (length <= stepDistance || length == 0.0f)
        {
            return dest;
        }
        return start.add((dist.div(length)).mul(stepDistance));
    }

    public static boolean     IsLongerAs(Vector2f v1, Vector2f v2)
    {
        return (v1.x*v1.x + v1.y*v1.y) > (v2.x*v2.x + v2.y*v2.y);
    }

    public static boolean     IsShorterAs(Vector2f v1, Vector2f v2)
    {
        return (v1.x*v1.x + v1.y*v1.y) < (v2.x*v2.x + v2.y*v2.y);
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                         Public                          */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public Vector2f()
    {
        this(0.0f, 0.0f);
    }

    public Vector2f(float x1, float y1)
    {
        x = x1;
        y = y1;
    }

    public Vector2f    neg() {
        return new Vector2f(-x,-y);
    }

    public Vector2f    add(Vector2f vec)
    {
        return new Vector2f(x + vec.x, y + vec.y);
    }

    public Vector2f    sub(Vector2f vec)
    {
        return new Vector2f(x - vec.x, y - vec.y);
    }

    public Vector2f    mul(Vector2f vec)
    {
        return new Vector2f(x * vec.x, y * vec.y);
    }

    public Vector2f    mul(float scalar)
    {
        return new Vector2f(x * scalar, y * scalar);
    }

    public Vector2f    div(float divisor) {
        return new Vector2f(x / divisor, y / divisor);
    }
    
    public float dot(Vector2f vec) {
        return (x * vec.x) + (y * vec.y);
    }

    public float       Length() {
        return (float) Math.sqrt(x*x + y*y);
    }

    public Vector2f    Normalized() {
        float len = Length();

        if (len != 0.0f) {
            return new Vector2f(x / len, y / len);
        }

        return new Vector2f(x,y);
    }

    public Vector2f duplicate() {
        return new Vector2f( x, y );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
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
