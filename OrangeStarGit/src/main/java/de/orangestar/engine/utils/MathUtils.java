package de.orangestar.engine.utils;

/**
 * A collection of useful mathematical operations.
 * 
 * @author Oliver &amp; Basti
 */
public class MathUtils {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    // TODO: Change javadoc to instead tell whats actually happening, not how good the method is
    /**
     * A faster floor implementation that Math.floor.
     * @param x The to floor value
     * @return The floored value
     */
    public static int fastFloor(double x) {
        int xi = (int)x;
        return x < xi ? xi - 1 : xi;
    }
    
    /**
     * Linear interpolation.
     * @param v1 The start value
     * @param v2 The target value
     * @param amount The amount
     * @return The linear interpolated result
     */
    public static double lerp(double v1, double v2, double amount) {
        return (1f - amount) * v1 + amount * v2;
    }
    
    /**
     * Linear interpolation.
     * @param v1 The start value
     * @param v2 The target value
     * @param amount The amount
     * @return The linear interpolated result
     */
    public static float lerp(float v1, float v2, float amount) {
        return (1f - amount) * v1 + amount * v2;
    }
    
    /**
     * Translate a given value from one range into another range.
     * @param value The value
     * @param currentMin The minimum of the current range
     * @param currentMax The maximum of the current range
     * @param targetMin The minimum of the target range
     * @param targetMax The maximum of the target range
     * @return The new value
     */
    public static float translate(float value, float currentMin, float currentMax, float targetMin, float targetMax) {
        float currentSpan = currentMax - currentMin;
        float targetSpan = targetMax - targetMin;

        return targetMin + ((value - currentMin) / currentSpan) * targetSpan;
    }

    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Private-Constructor
     */
    private MathUtils() { }
    
}
