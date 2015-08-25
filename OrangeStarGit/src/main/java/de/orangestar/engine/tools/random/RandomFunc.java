package de.orangestar.engine.tools.random;

/**
 * The basic interface for random number generation functions.
 * 
 * @author Oliver &amp; Basti
 */
public interface RandomFunc {

    /**
     * A one-dimensional random function.
     * @param x The one dimensional parameter
     * @param seed The seed
     * @return The random value
     */
    public double get(double x, long seed);
    
    /**
     * A two-dimensional random function.
     * @param x The first random function parameter
     * @param y The second random function parameter
     * @param seed The seed
     * @return The random value
     */
    public double get(double x, double y, long seed);
    
    /**
     * A three-dimensional random function.
     * @param x The first random function parameter
     * @param y The second random function parameter
     * @param z The second random function parameter
     * @param seed The seed
     * @return The random value
     */
    public double get(double x, double y, double z, long seed);
    
}
