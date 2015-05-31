package de.orangestar.engine.tools.random;

import java.util.ArrayList;
import java.util.Random;

public class PerlinNoiseGenerator extends NoiseGenerator {

    @Override
    public double[][] generate2DMap(int width, int height, long seed) {

        int smoothness = 5;
        
        double[][] noisemap;

        noisemap = rndnoise(seed, width, height);
        noisemap = turbulence(noisemap, 5);
        noisemap = smooth(noisemap, 5);

        // Adjust the result (95% of the values will range between -0.3 and 0.3, so we multiply by 3
        noisemap = multiply(noisemap, 3, -1.0d, 1.0d);

        for (int i = 0; i < smoothness; i++) {
            noisemap = smooth(noisemap, 5);
        }

        return noisemap;

    }

    private double[][] rndnoise(final long seed, final int width, final int height) {

        Random rnd = new Random(seed);
        double[][] result = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result[x][y] = rnd.nextDouble() * 2.0d - 1.0d; // Next rnd double in [-1,1]
            }
        }

        return result;

    }

    private double[][] smooth(final double[][] input, final int sharpness) {

        int width = input.length;

        int height = input[0].length;

        double[][] result = new double[width][height];

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                double averageValue = 0;
                double components = 0;

                // Weight diagonal pixels only half
                if (inBounds(x - 1, y - 1, width, height)) {
                    averageValue += input[x - 1][y - 1] * 0.5d;
                }
                else {
                    averageValue += input[x][y] * 0.5d;
                }
                components += 0.5d;

                if (inBounds(x, y - 1, width, height)) {
                    averageValue += input[x][y - 1];
                }
                else {
                    averageValue += input[x][height - 1];
                }
                components += 1d;

                if (inBounds(x + 1, y - 1, width, height)) {
                    averageValue += input[x + 1][y - 1] * 0.5d;
                }
                else {
                    averageValue += input[x][y] * 0.5d;
                }
                components += 0.5d;

                if (inBounds(x - 1, y, width, height)) {
                    averageValue += input[x - 1][y];
                }
                else {
                    averageValue += input[width - 1][y];
                }
                components += 1d;

                averageValue += input[x][y] * sharpness;
                components += 1d * sharpness;

                if (inBounds(x + 1, y, width, height)) {
                    averageValue += input[x + 1][y];
                }
                else {
                    averageValue += input[0][y];
                }
                components += 1d;

                if (inBounds(x - 1, y + 1, width, height)) {
                    averageValue += input[x - 1][y + 1] * 0.5d;
                }
                else {
                    averageValue += input[x][y] * 0.5d;
                }
                components += 0.5d;

                if (inBounds(x, y + 1, width, height)) {
                    averageValue += input[x][y + 1];
                }
                else {
                    averageValue += input[x][0];
                }
                components += 1d;

                if (inBounds(x + 1, y + 1, width, height)) {
                    averageValue += input[x + 1][y + 1] * 0.5d;
                }
                else {
                    averageValue += input[x][y] * 0.5d;
                }
                components += 0.5d;
                
                result[x][y] = averageValue / components;

            }

        }

        return result;

    }

    private double[][] turbulence(final double[][] input, final int levels) {

        int width = input.length;

        int height = input[0].length;

        ArrayList<double[][]> turbulanceMaps = new ArrayList<>();

        for (int i = 1; i <= levels; i++) {

            double[][] turbulanceMap;

            turbulanceMap = subset(input, width / i, height / i);

            turbulanceMap = scale(turbulanceMap, width, height);

            turbulanceMaps.add(turbulanceMap);

        }

        return combine(turbulanceMaps
                .toArray(new double[turbulanceMaps.size()][width][height]));

    }

    private double[][] subset(final double[][] input, final int width,
            final int height) {

        double[][] subset = new double[width][height];

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                subset[x][y] = input[x][y];

            }

        }

        return subset;

    }

    private double[][] scale(final double[][] input, final int destWidth,
            final int destHeight) {

        int width = input.length;

        int height = input[0].length;

        double[][] scaled = new double[destWidth][destHeight];

        double xFactor = 1d * width / destWidth;

        double yFactor = 1d * width / destWidth;

        for (int xi = 0; xi < destWidth; xi++) {

            for (int yi = 0; yi < destHeight; yi++) {

                int x = (int) Math.min(xi * xFactor, width - 1);

                int y = (int) Math.min(yi * yFactor, height - 1);

                scaled[xi][yi] = input[x][y];

            }

        }

        return scaled;

    }

    private double[][] combine(final double[][]... maps) {

        int components = maps.length;

        int width = maps[0].length;

        int height = maps[0][0].length;

        double[][] result = new double[width][height];

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                double average = 0.0d;

                for (int c = 0; c < components; c++) {

                    average += maps[c][x][y];

                }

                result[x][y] = average / components;

            }

        }

        return result;

    }

    private double[][] multiply(final double[][] input, final double factor,
            final double min, final double max) {

        int width = input.length;

        int height = input[0].length;

        double[][] result = new double[width][height];

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                result[x][y] = Math.min(Math.max(min, input[x][y] * factor),
                        max);

            }

        }

        return result;

    }

    private static boolean inBounds(final int x, final int y, final int width,
            final int height) {

        return !(x < 0 || x > width - 1 || y < 0 || y > height - 1);

    }

}
