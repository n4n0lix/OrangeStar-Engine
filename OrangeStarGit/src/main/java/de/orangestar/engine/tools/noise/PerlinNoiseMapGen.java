package de.orangestar.engine.tools.noise;

import java.util.ArrayList;

import de.orangestar.engine.tools.random.RandomFunc;
import de.orangestar.engine.utils.ArrayUtils;

/**
 * A perlin-noise based implementation of {@link NoiseMapGen}.
 * 
 * @author Oliver &amp; Basti
 */
public class PerlinNoiseMapGen implements NoiseMapGen {

    @Override
    public double[][] get(int width, int height, RandomFunc noise, double rndX, double rndY, double rndWidth, double rndHeight, long seed) {

        double[][] noisemap;

        noisemap = rndnoise(width, height, noise, rndX, rndY, rndWidth, rndHeight, seed);

        if (_turbulance > 0) {
            noisemap = turbulence(noisemap, _turbulance);
        }

        for (int i = 0; i < _smoothness; i++) {
            noisemap = smooth(noisemap, 5);
        }

        return noisemap;
    }
    
    /**
     * Sets the smoothness of the generated noise maps.
     * @param smoothness <code> &gt;= 0 </code>
     */
    public void setSmoothness(int smoothness) {
        _smoothness = Math.max(0, smoothness);
    }
    
    /**
     * Sets the turbulance of the generated noise maps.
     * @param turbulance <code> &gt;= 0 </code>
     */
    public void setTurbulance(int turbulance) {
        _turbulance = Math.max(0, turbulance);
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Generates the random noise.
     * @see NoiseMapGen.get
     */
    private double[][] rndnoise(final int width, final int height, RandomFunc func, double rndX, double rndY, double rndWidth, double rndHeight, final long seed) {

        double[][] result = new double[width][height];

        double xFrac = rndWidth / width;
        double yFrac = rndHeight / height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result[x][y] = func.get(x*xFrac+rndX, y*yFrac+rndY, seed);
            }
        }

        return result;

    }

    /**
     * Smoothens a 2D-double array.
     * @param input The array
     * @param sharpness The sharpness(!), lower values make the array smoother, larger values (bigger than ~7) make the array sharper
     * @return The smoothened input array
     */
    private double[][] smooth(final double[][] input, final int sharpness) {

        int width = input.length;
        int height = input[0].length;

        double[][] result = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                double averageValue = 0;
                double components = 0;

                // Top-Left
                if (ArrayUtils.inBounds(width, height, x - 1, y - 1)) {
                    averageValue += input[x - 1][y - 1] * 0.5d;
                }
                else {
                    averageValue += input[x][y] * 0.5d;
                }
                components += 0.5d; // Weight diagonal pixels only half

                // Top
                if (ArrayUtils.inBounds(width, height, x, y - 1)) {
                    averageValue += input[x][y - 1];
                }
                else {
                    averageValue += input[x][height - 1];
                }
                components += 1d; // Weight adjacent pixels full

                // Top Right
                if (ArrayUtils.inBounds(width, height, x + 1, y - 1)) {
                    averageValue += input[x + 1][y - 1] * 0.5d;
                }
                else {
                    averageValue += input[x][y] * 0.5d;
                }
                components += 0.5d; // Weight diagonal pixels only half

                // Mid-Left
                if (ArrayUtils.inBounds(width, height, x - 1, y)) {
                    averageValue += input[x - 1][y];
                }
                else {
                    averageValue += input[width - 1][y];
                }
                components += 1d; // Weight adjacent pixels full

                // Self
                averageValue += input[x][y] * sharpness;
                components += 1d * sharpness;

                // Mid-Right
                if (ArrayUtils.inBounds(width, height, x + 1, y)) {
                    averageValue += input[x + 1][y];
                }
                else {
                    averageValue += input[0][y];
                }
                components += 1d; // Weight adjacent pixels full

                // Bottom-Left
                if (ArrayUtils.inBounds(width, height, x - 1, y + 1)) {
                    averageValue += input[x - 1][y + 1] * 0.5d;
                }
                else {
                    averageValue += input[x][y] * 0.5d;
                }
                components += 0.5d; // Weight diagonal pixels only half

                // Bottom
                if (ArrayUtils.inBounds(width, height, x, y + 1)) {
                    averageValue += input[x][y + 1];
                }
                else {
                    averageValue += input[x][0];
                }
                components += 1d; // Weight adjacent pixels full

                // Bottom-Right
                if (ArrayUtils.inBounds(width, height, x + 1, y + 1)) {
                    averageValue += input[x + 1][y + 1] * 0.5d;
                }
                else {
                    averageValue += input[x][y] * 0.5d;
                }
                components += 0.5d; // Weight diagonal pixels only half
                
                // Calculate average value
                result[x][y] = averageValue / components;
            }
        }

        return result;
    }

    /**
     * Adds turbulence to a 2D-double array.
     * @param input The array
     * @param sharpness The amount of turbulence levels
     * @return The turbulance'd input array
     */
    private double[][] turbulence(final double[][] input, final int levels) {

        int width = input.length;
        int height = input[0].length;

        ArrayList<double[][]> turbulanceMaps = new ArrayList<>();

        for (int i = 1; i <= levels; i++) {
            double[][] turbulanceMap = new double[width / i][height / i];

            ArrayUtils.subset(input, turbulanceMap);
            turbulanceMap = ArrayUtils.scale(turbulanceMap, width, height);
            turbulanceMaps.add(turbulanceMap);

        }

        return ArrayUtils.average(turbulanceMaps.toArray(new double[turbulanceMaps.size()][width][height]));
    }

    private int _smoothness = 0;
    private int _turbulance = 0;
    
}
