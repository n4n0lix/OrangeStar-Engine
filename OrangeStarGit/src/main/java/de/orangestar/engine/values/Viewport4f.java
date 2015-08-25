package de.orangestar.engine.values;

/**
 * Represents a view port with 4 view borders (left, top, right, bottom).
 * 
 * @author Basti
 */
public class Viewport4f {
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*                      Public Static                      */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    /**
     * 2-address implementation of the = operator.<br>
     * <i>target</i> = <i>(leftX, topY, rightX, bottomY)</i>
     * @param target The target viewport
     * @param leftX The x of the left viewborder
     * @param topY The y of the top viewborder
     * @param rightX The x of the right viewborder
     * @param bottomY The y of the bottom viewborder
     */
	public static void set(Viewport4f target, float leftX, float topY, float rightX, float bottomY) {
		target.left = leftX;
		target.right = rightX;
		target.bottom = bottomY;
		target.top = topY;
	}
	
    /**
     * 2-address implementation of the = operator.<br>
     * <i>target</i> = <i>source</i>
     * @param target The target viewport
     * @param source The source viewport
     */
	public static void set(Viewport4f target, Viewport4f source) {
		target.left   = source.left;
		target.right  = source.right;
		target.bottom = source.bottom;
		target.top    = source.top;
	}

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/**
     * Public-Constructor.
     */
    public Viewport4f() {

    }
	
	/**
	 * Public-Constructor.
     * @param leftX The x of the left viewborder
     * @param topY The y of the top viewborder
     * @param rightX The x of the right viewborder
     * @param bottomY The y of the bottom viewborder
	 */
    public Viewport4f( float leftX, float topY, float rightX, float bottomY) {
        left   = Math.min(leftX, rightX);
        right  = Math.max(leftX, rightX);
        top    = Math.min(topY, bottomY);
        bottom = Math.max(topY, bottomY);
    }

    /**
     * Returns the width of this rectangle.
     * @return _x2 - _x1
     */
    public float getWidth() {
    	return right - left;
    }

    /**
     * Returns the height of this rectangle.
     * @return _y2 - _y1
     */
    public float getHeight() {
    	return bottom - top;
    }

    /**
     * Checks if the given point is inside this viewport.
     * @param x The x value of the point
     * @param y The y value of the point
     * @return If the given point is inside this viewport
     */
    public boolean isPointInside(float x, float y) {
        if (x < left) {
            return false;
        }

        if (x > right) {
            return false;
        }

        if (y < top) {
            return false;
        }

        if (y > bottom) {
            return false;
        }

        return true;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(left);
		result = prime * result + Float.floatToIntBits(right);
		result = prime * result + Float.floatToIntBits(bottom);
		result = prime * result + Float.floatToIntBits(top);
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
		Viewport4f other = (Viewport4f) obj;
		if (Float.floatToIntBits(left) != Float.floatToIntBits(other.left))
			return false;
		if (Float.floatToIntBits(right) != Float
				.floatToIntBits(other.right))
			return false;
		if (Float.floatToIntBits(bottom) != Float
				.floatToIntBits(other.bottom))
			return false;
		if (Float.floatToIntBits(top) != Float.floatToIntBits(other.top))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "[ " + left + ", " + top + ", " + right + ", " + bottom + "]";
    }

    public float left, top, right, bottom;

}
