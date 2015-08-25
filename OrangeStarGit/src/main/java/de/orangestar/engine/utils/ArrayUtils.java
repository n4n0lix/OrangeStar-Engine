package de.orangestar.engine.utils;


/**
 * A collection of useful array operations.
 * 
 * @author Oliver &amp; Basti
 */
public class ArrayUtils {
    
    /**
     * Translates a 2D double array from a given range into a destinated range.
     * @param input The array
     * @param inputMin The current min value
     * @param inputMax The current max value
     * @param outputMin The destinated min value
     * @param outputMax The destinated max value
     */
    public static void translate(double[][] input, double inputMin, double inputMax, double outputMin, double outputMax) {

        double inputSpan  = inputMax - inputMin;
        double outputSpan = outputMax - outputMin;
        
        for(int x = 0; x < input.length; x++ ) {
            for(int y = 0; y < input[0].length; y++ ) {
                double val = input[x][y];
                
                input[x][y] = outputMin + ((val - inputMin) / inputSpan) * outputSpan;
            }
        }
    }

    /**
     * Returns a subset of the given array.<br>
     * Preconditions:
     * <ul>
     *  <li><code>dest.length &gt; 0</code>
     *  <li><code>dest[0].length &gt; 0</code>
     * </ul>
     * @param input The input array
     * @param dest The array in which the result is stored. The given dimensions also determine how large the subset is.
     * @param <T> The generic type
     */
    public static <T> void subset(final T[][] input, final T[][] dest) {
        // Preconditions:
        assert dest.length > 0;
        assert dest[0].length > 0;

        // Code:
        for (int x = 0; x < dest.length; x++) {
            for (int y = 0; y < dest[0].length; y++) {
                dest[x][y] = input[x][y];
            }
        }
    }
    
    /**
     * Returns a subset of the given array.<br>
     * Preconditions:
     * <ul>
     *  <li><code>dest.length &gt; 0</code>
     *  <li><code>dest[0].length &gt; 0</code>
     * </ul>
     * @param input The input array
     * @param dest The array in which the result is stored. The given dimensions also determine how large the subset is.
     */
    public static void subset(final double[][] input, final double[][] dest) {
        // Preconditions:
        assert dest.length > 0;
        assert dest[0].length > 0;

        // Code:
        for (int x = 0; x < dest.length; x++) {
            for (int y = 0; y < dest[0].length; y++) {
                dest[x][y] = input[x][y];
            }
        }

    }
    
    /**
     * Scales the input array to a specified width and height.
     * @param input The array
     * @param destWidth The designated width
     * @param destHeight The designated height
     * @return The scaled array
     */
    public static double[][] scale(final double[][] input, final int destWidth, final int destHeight) {

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

    /**
     * Returns an array that contains the average values for each index-pair.
     * @param maps The value maps
     * @return The average map
     */
    public static double[][] average(final double[][]... maps) {
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

    /**
     * Multiplies every value in the array with the factor.
     * @param array The array
     * @param factor The multiplied factor
     */
    public static void multiply(final double[][] array, final double factor) {
        int width = array.length;
        int height = array[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                array[x][y] = array[x][y] * factor;
            }
        }
    }
    
    /**
     * Multiplies every value in the array with the factor and clamps the result between min and max.
     * @param input The input array
     * @param factor The multiplied factor
     * @param min The minimal value allowed in the array after multiplication
     * @param max The maximal value allowed in the array after multiplication
     */
    public static void multiply(final double[][] input, final double factor, final double min, final double max) {
        int width = input.length;
        int height = input[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                input[x][y] = Math.min(Math.max(min, input[x][y] * factor), max);
            }
        }
    }

    /**
     * Copys the content of the source array into the destination array, starting at the index-pair [offsetX][offsetY]
     * @param dest The destination array
     * @param src The source array
     * @param offsetX The index x offset
     * @param offsetY The index y offset
     * @param <T> The generic type
     */
    public static <T> void copyIntoArray(final T[][] dest, final T[][] src, final int offsetX, final int offsetY) {
        if (src.length == 0 || src[0].length == 0) {
            return;
        }
        
        for(int ix = 0; ix < src.length; ix++) {
            if (ix >= dest.length) return;
            
            for(int iy = 0; iy < src[0].length; iy++) {
                if (iy >= dest[0].length) break;
                
                dest[offsetX+ix][offsetY+iy] = src[ix][iy];
            }
        }
    }
    
    /**
     * Copys the content of the source array into the destination array, starting at the index-pair [offsetX][offsetY]
     * @param dest The destination array
     * @param src The source array
     * @param x The index x offset
     * @param y The index y offset
     */
    public static void copyIntoArray(final boolean[][] dest, final boolean[][] src, final int x, final int y) {
        if (src.length == 0 || src[0].length == 0) {
            return;
        }
        
        for(int ix = 0; ix < src.length; ix++) {
            if (ix >= dest.length) return;

            for(int iy = 0; iy < src[0].length; iy++) {
                if (iy >= dest[0].length) break;
                
                dest[x+ix][y+iy] = src[ix][iy];
            }
        }
    }
    
    /**
     * Checks if the given index is in bounds of the given array.
     * @param array The array
     * @param x The index
     * @param <T> The generic type
     * @return If the given index is in bounds of the given array
     */
    public static <T> boolean inBounds(T[] array, final int x) {
        return !(x < 0 || x >= array.length);
    }

    /**
     * Checks if the given index pair is in bounds of the given array.
     * @param array The array
     * @param x The x index
     * @param y The y index
     * @param <T> The generic type
     * @return If the given index pair is in bounds of the given array
     */
    public static <T> boolean inBounds(T[][] array, final int x, final int y) {
        return !(x < 0 || x >= array.length || y < 0 || y >= array[0].length);
    }
    
    /**
     * Checks if the given point is inside the rectangle (0,0,width,height)
     * @param x The x index
     * @param y The y index
     * @param arrWidth The array width
     * @param arrHeight The array height
     * @return If the given point is inside the rectangle (0,0,width,height)
     */
    public static boolean inBounds(final int arrWidth,final int arrHeight, final int x, final int y) {
        return !(x < 0 || x >= arrWidth || y < 0 || y >= arrHeight);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Private-Constructor.
     */
    private ArrayUtils() { }

}
