package de.orangestar.engine.tools.noise;

import de.orangestar.engine.tools.random.RandomFunc;

/**
 * A basic interface for noisemap generators.
 * 
 * @author Oliver &amp; Basti
 */
public interface NoiseMapGen {

    /**
     * Generates a noisemap with a given random function, seed and dimension. The random function implementation
     * must support 2D-Random value generation f(x,y) = r.<br>
     * <code><br>
     * result[0][0] = rndFunc.get(rndX, rndY)<br>
     * ...<br>
     * result[n][m] = rndFunc.get(rndX + n *(rndWidth / width), rndY + m *(rndHeight / height))<br>
     * ...<br>
     * result[width][height] = rndFunc.get(rndWidth, rndHeight)<br>
     * </code><br>
     * This enables squeezing/stretching the random area into/to the desired noise map dimension.
     * 
     * @param width The width of the noisemap
     * @param height The height of the noisemap
     * @param rndFunc The random function
     * @param rndX The random function x offset
     * @param rndY The random function y offset
     * @param rndWidth The random function width
     * @param rndHeight The random function height
     * @param seed The seed for the random function
     * @return The noise map
     */
    public double[][] get(int width, int height, RandomFunc rndFunc, double rndX, double rndY, double rndWidth, double rndHeight, long seed);
    
}
