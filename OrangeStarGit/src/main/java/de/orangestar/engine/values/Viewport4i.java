package de.orangestar.engine.values;

/**
 * Represents a view port with 4 view borders (left, top, right, bottom).
 * 
 * @author Basti
 */
public class Viewport4i  {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor.
     * @param leftX The x of the left viewborder
     * @param topY The y of the top viewborder
     * @param rightX The x of the right viewborder
     * @param bottomY The y of the bottom viewborder
     */
    public Viewport4i( int leftX, int topY, int rightX, int bottomY) {

        if (Integer.compare(leftX, rightX) < 0) {
            _xLeft = leftX;
            _xRight = rightX;
        } else {
            _xRight = leftX;
            _xLeft = rightX;
        }

        if (Integer.compare(topY, bottomY) < 0) {
            _yTop = topY;
            _yBottom = bottomY;
        } else {
            _yBottom = topY;
            _yTop = bottomY;
        }

    }
    
    /**
     * Returns the width.
     * @return The width
     */
    public int getWidth() {
        return _xRight - _xLeft;
    }
    
    /**
     * Returns the height.
     * @return The height
     */
    public int getHeight() {
        return _yBottom - _yTop;
    }

    /**
     * Checks if the given point is inside this viewport.
     * @param x The x value of the point
     * @param y The y value of the point
     * @return If the given point is inside this viewport
     */
    public boolean isPointInside(int x, int y) {
        if (Integer.compare(x, _xLeft) <= 0) {
            return false;
        }
        
        if (Integer.compare(x, _xRight) >= 0) {
            return false;
        }
        
        if (Integer.compare(y, _yTop) <= 0) {
            return false;
        }
        
        if (Integer.compare(y, _yBottom) >= 0) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "[ " + _xLeft + ", " + _yTop + ", " + _xRight + ", " + _yBottom + "]";
    }

    public int _xLeft, _yTop, _xRight, _yBottom;

}
