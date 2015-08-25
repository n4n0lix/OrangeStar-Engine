package de.orangestar.engine.values;

import de.orangestar.engine.values.pools.Color4fPool;

/**
 * Represents a 4-component color in RGBA-Mode.
 * 
 * @author Oliver, Basti
 */
public final class Color4f {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public static final int         NUM_COMPONENTS = 4;
    public static final int         BYTE_SIZE      = 4 * Float.BYTES;
    public static final Color4fPool POOL           = new Color4fPool();
    
    /**
     * Sets all values of the color to 0.
     * @param color The instance that is modified
     */
    public final static void zero(Color4f color) {
        color.setRed(0.0f);
        color.setGreen(0.0f);
        color.setBlue(0.0f);
        color.setAlpha(0.0f);
    }
    
    /**
     * Sets all values of the color to 1.
     * @param color The instance that is modified
     */
    public final static void one(Color4f color) {
        color.setRed(1.0f);
        color.setGreen(1.0f);
        color.setBlue(1.0f);
        color.setAlpha(1.0f);
    }
    
    /**
     * Returns an instance that represents white.
     * @return An instance that represents white
     */
    public final static Color4f white() {
        return new Color4f(1f, 1f, 1f, 1f);
    }
    
    /**
     * Sets all values of the color to represent the color white.
     * @param color The instance that is modified
     */
    public final static void white(Color4f color) {
        Color4f.one(color);
    }
    
    /**
     * Returns an instance that represents black.
     * @return An instance that represents black
     */
    public final static Color4f black() {
        return new Color4f(0f, 0f, 0f, 1f);
    }
    
    /**
     * Sets all values of the color to represent the color black.
     * @param color The instance that is modified
     */
    public final static void black(Color4f color) {
        color.setRed(0.0f);
        color.setGreen(0.0f);
        color.setBlue(0.0f);
        color.setAlpha(1.0f);
    }
    
    /**
     * Sets all values of the color to represent the color red.
     * @param color The instance that is modified
     */
    public final static void red(Color4f color) {
        color.setRed(1.0f);
        color.setGreen(0.0f);
        color.setBlue(0.0f);
        color.setAlpha(1.0f);
    }
    
    /**
     * Sets all values of the color to represent the color green.
     * @param color The instance that is modified
     */
    public final static void green(Color4f color) {
        color.setRed(0.0f);
        color.setGreen(1.0f);
        color.setBlue(0.0f);
        color.setAlpha(1.0f);
    }
    
    /**
     * Sets all values of the color to represent the color blue.
     * @param color The instance that is modified
     */
    public final static void blue(Color4f color) {
        color.setRed(0.0f);
        color.setGreen(0.0f);
        color.setBlue(1.0f);
        color.setAlpha(1.0f);
    }
    
    /**
     * Sets the values of the color explicit. All given values are clamped to  the range from 0.0f to 1.0f.
     * @param color The instance that is modified
     * @param r The new red amount
     * @param g The new red amount
     * @param b The new red amount
     * @param a The new red amount
     */
    public final static void set(Color4f color, float r, float g, float b, float a) {
        color.setRed(r);
        color.setGreen(g);
        color.setBlue(b);
        color.setAlpha(a);
    }
    
    /**
     * Inverts only the color values, the alpha value stays the same.
     * @param color The color to invert
     */
    public final static void invertColor(Color4f color) {
        color.setRed(1.0f - color.getRed());
        color.setGreen(1.0f - color.getGreen());
        color.setBlue(1.0f - color.getBlue());
    }
    
    /**
     * Inverts all values (including the alpha value).
     * @param color The color to invert
     */
    public final static void invert(Color4f color) {
        color.setRed(1.0f - color.getRed());
        color.setGreen(1.0f - color.getGreen());
        color.setBlue(1.0f - color.getBlue());
        color.setAlpha(1.0f - color.getAlpha());
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor. All values are initialized to zero.
     */
    public Color4f() {
        this(0.0f,0.0f,0.0f);
    }
    
    /**
     * Public-Constructor. 
     * @param rgb All RGB-values are initialized to <i>rgb</i>
     */
    public Color4f(final float rgb) {
        this(rgb,rgb,rgb);
    }
    
    /**
     * Public-Constructor.
     * @param rgb All RGB-values are initialized to <i>rgb</i>
     * @param a The alpha values
     */
    public Color4f(final float rgb, final float a) {
        this(rgb,rgb,rgb, a);
    }
    
    /**
     * Public-Constructor.
     * @param r The red value
     * @param g The green value
     * @param b The blue value
     */
    public Color4f(final float r, final float g, final float b) {
        this(r,g,b,1.0f);
    }    
    
    /**
     * Public-Constructor.
     * @param r The red value
     * @param g The green value
     * @param b The blue value
     * @param a The alpha value
     */
    public Color4f(final float r, final float g, final float b, final float a) {
        setAlpha(a);
        setRed(r);
        setGreen(g);
        setBlue(b);
    }
        
    /**
     * Returns the alpha value.
     * @return The alpha value
     */
    public final float getAlpha() {
        return _a;
    }

    /**
     * Sets the alpha value.
     * @param a The alpha value
     */
    public final void setAlpha(final float a) {
        this._a = Math.max(0.0f, Math.min(1.0f, a));
    }

    /**
     * Returns the red value.
     * @return The red value
     */
    public final float getRed() {
        return _r;
    }

    /**
     * Sets the red value.
     * @param r The red value
     */
    public final void setRed(final float r) {
        this._r = Math.max(0.0f, Math.min(1.0f, r));
    }

    /**
     * Returns the green value.
     * @return The green value
     */
    public final float getGreen() {
        return _g;
    }

    /**
     * Sets the green value.
     * @param g The green value
     */
    public final void setGreen(final float g) {
        this._g = Math.max(0.0f, Math.min(1.0f, g));
    }

    /**
     * Returns the blue value.
     * @return The blue value
     */
    public final float getBlue() {
        return _b;
    }

    /**
     * Sets the blue value.
     * @param b The blue value
     */
    public final void setBlue(final float b) {
        this._b = Math.max(0.0f, Math.min(1.0f, b));
    }

    /**
     * Duplicates this color.
     * @return A new {@link Color4f} instance representing the same value as <i>this</i>.
     */
    public final Color4f duplicate() {
        return new Color4f( _r, _g, _b, _a );
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(_a);
        result = prime * result + Float.floatToIntBits(_b);
        result = prime * result + Float.floatToIntBits(_g);
        result = prime * result + Float.floatToIntBits(_r);
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
        Color4f other = (Color4f) obj;
        if (Float.floatToIntBits(_a) != Float.floatToIntBits(other._a))
            return false;
        if (Float.floatToIntBits(_b) != Float.floatToIntBits(other._b))
            return false;
        if (Float.floatToIntBits(_g) != Float.floatToIntBits(other._g))
            return false;
        if (Float.floatToIntBits(_r) != Float.floatToIntBits(other._r))
            return false;
        return true;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private float _a;
    private float _r;
    private float _g;
    private float _b;
    
}
