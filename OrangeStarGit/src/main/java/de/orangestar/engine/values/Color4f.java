package de.orangestar.engine.values;

public class Color4f {

    public static final int ComponentsCount = 4;
    public static final int ByteSize = 4 * Float.BYTES;
    
    public Color4f() {
        this(0.0f,0.0f,0.0f);
    }
    
    public Color4f(float rgb) {
        this(rgb,rgb,rgb);
    }
    
    public Color4f(float r1, float g1, float b1) {
        this(r1,g1,b1,1.0f);
    }    
    
    public Color4f(float r1, float g1, float b1, float a1) {
        a = Math.max(0.0f, Math.min(1.0f, a1));
        r = Math.max(0.0f, Math.min(1.0f, r1));
        g = Math.max(0.0f, Math.min(1.0f, g1));
        b = Math.max(0.0f, Math.min(1.0f, b1));
    }
    
    public Color4f invertColor() {
        return new Color4f(1.0f - r, 1.0f - g, 1.0f - b, a);
    }
    
    public Color4f invert() {
        return new Color4f(1.0f - r, 1.0f - g, 1.0f - b, 1.0f - a);
    }
    
    public Color4f duplicate() {
        return new Color4f( r, g, b, a );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(a);
        result = prime * result + Float.floatToIntBits(b);
        result = prime * result + Float.floatToIntBits(g);
        result = prime * result + Float.floatToIntBits(r);
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
        Color4f other = (Color4f) obj;
        if (Float.floatToIntBits(a) != Float.floatToIntBits(other.a))
            return false;
        if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b))
            return false;
        if (Float.floatToIntBits(g) != Float.floatToIntBits(other.g))
            return false;
        if (Float.floatToIntBits(r) != Float.floatToIntBits(other.r))
            return false;
        return true;
    }
    
    public float a;
    public float r;
    public float g;
    public float b;
    
}
