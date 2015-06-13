package de.orangestar.engine.tools.random;

import java.util.Random;

public abstract class NoiseGenerator {
    
    public static double[][] translate(double[][] input, double inputMin, double inputMax, double outputMin, double outputMax) {
        double[][] result = new double[input.length][input[0].length];
        
        double inputSpan  = inputMax - inputMin;
        double outputSpan = outputMax - outputMin;
        
        for(int x = 0; x < input.length; x++ ) {
            for(int y = 0; y < input[0].length; y++ ) {
                double val = input[x][y];
                
                result[x][y] = outputMin + ((val - inputMin) / inputSpan) * outputSpan;
            }
        }

        return result;
    }
    
    public abstract double[][] generate2DMap(int width, int height, long seed);
    
    public double[][] generate2DMap(int width, int height) {
        return generate2DMap(width, height, new Random().nextLong());
    }
}
