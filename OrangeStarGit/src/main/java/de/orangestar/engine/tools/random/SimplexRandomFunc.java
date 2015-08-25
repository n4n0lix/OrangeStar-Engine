package de.orangestar.engine.tools.random;

import de.orangestar.engine.utils.MathUtils;

/**
 * Modified version of the java simplex implementation of Kurt Spencer (<a href="https://gist.github.com/KdotJPG/9bbab7d3655b82811b24">Github</a>).
 * 
 * Public domain
 * 
 * @author Oliver &amp; Basti
 */
public class SimplexRandomFunc implements RandomFunc {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    @Override
    public double get(double x, long seed) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public double get(double x, double y, long seed) {

        if (_seed != seed) {
            _permutations = generatePermutations(seed);
            _seed = seed;
        }

        //Place input coordinates on triangular grid.
        double squishOffset = (x + y) * SQUISH_CONSTANT;
        double xs = x + squishOffset;
        double ys = y + squishOffset;
        
        //Floor to get base coordinate of containing square/rhombus.
        int xsb = MathUtils.fastFloor(xs);
        int ysb = MathUtils.fastFloor(ys);
        
        //Skew out to get actual coordinates of rhombus origin. We'll need these later.
        double stretchOffset = (xsb + ysb) * STRETCH_CONSTANT;
        double xb = xsb + stretchOffset;
        double yb = ysb + stretchOffset;
 
        //Positions relative to origin point.
        double dx = x - xb;
        double dy = y - yb;
        
        //Compute grid coordinates relative to rhombus origin.
        double xins = xs - xsb;
        double yins = ys - ysb;
        
        double value;
        if (xins > yins) { //We're inside the x>y triangle of the rhombus
        
            //Get our 12 surrounding vertex values
            //Using type "byte" works here because type "byte" in Java is signed
            short yp;
            yp = _permutations[(ysb - 1) & 0xFF];
            byte h1 = (byte)_permutations[(yp + xsb - 1) & 0xFF]; //(-1,-1)
            byte h2 = (byte)_permutations[(yp + xsb + 0) & 0xFF]; //( 0,-1)
            byte h3 = (byte)_permutations[(yp + xsb + 1) & 0xFF]; //( 1,-1)
            yp = _permutations[(ysb + 0) & 0xFF];
            byte h4 = (byte)_permutations[(yp + xsb - 1) & 0xFF]; //(-1, 0)
            byte h5 = (byte)_permutations[(yp + xsb + 0) & 0xFF]; //( 0, 0)
            byte h6 = (byte)_permutations[(yp + xsb + 1) & 0xFF]; //( 1, 0)
            byte h7 = (byte)_permutations[(yp + xsb + 2) & 0xFF]; //( 2, 0)
            yp = _permutations[(ysb + 1) & 0xFF];
            byte h8 = (byte)_permutations[(yp + xsb + 0) & 0xFF]; //( 0, 1)
            byte h9 = (byte)_permutations[(yp + xsb + 1) & 0xFF]; //( 1, 1)
            byte h10 = (byte)_permutations[(yp + xsb + 2) & 0xFF];//( 2, 1)
            yp = _permutations[(ysb + 2) & 0xFF];
            byte h11 = (byte)_permutations[(yp + xsb + 1) & 0xFF];//( 1, 2)
            byte h12 = (byte)_permutations[(yp + xsb + 2) & 0xFF];//( 2, 2)
            
            value = kernels(dx, dy, h1, h2, h3,
                h4, h5, h6, h7, h8, h9, h10, h11, h12);
        } else { //We're inside the y>x triangle of the rhombus
        
            //Get our 12 surrounding vertex values
            //Using type "byte" works here because type "byte" in Java is signed
            short yp;
            yp = _permutations[(ysb - 1) & 0xFF];
            byte h1 = (byte)_permutations[(yp + xsb - 1) & 0xFF]; //(-1,-1)
            byte h4 = (byte)_permutations[(yp + xsb + 0) & 0xFF]; //( 0,-1)
            yp = _permutations[(ysb + 0) & 0xFF];
            byte h2 = (byte)_permutations[(yp + xsb - 1) & 0xFF]; //(-1, 0)
            byte h5 = (byte)_permutations[(yp + xsb + 0) & 0xFF]; //( 0, 0)
            byte h8 = (byte)_permutations[(yp + xsb + 1) & 0xFF]; //( 1, 0)
            yp = _permutations[(ysb + 1) & 0xFF];
            byte h3 = (byte)_permutations[(yp + xsb - 1) & 0xFF]; //(-1, 1)
            byte h6 = (byte)_permutations[(yp + xsb + 0) & 0xFF]; //( 0, 1)
            byte h9 = (byte)_permutations[(yp + xsb + 1) & 0xFF]; //( 1, 1)
            byte h11 = (byte)_permutations[(yp + xsb + 2) & 0xFF];//( 2, 1)
            yp = _permutations[(ysb + 2) & 0xFF];
            byte h7 = (byte)_permutations[(yp + xsb + 0) & 0xFF]; //( 0, 2)
            byte h10 = (byte)_permutations[(yp + xsb + 1) & 0xFF];//( 1, 2)
            byte h12 = (byte)_permutations[(yp + xsb + 2) & 0xFF];//( 2, 2)
            
            value = kernels(dy, dx, h1, h2, h3,
                h4, h5, h6, h7, h8, h9, h10, h11, h12);
        }
        return value / NORM_CONSTANT;
    }
    


    @Override
    public double get(double x, double y, double z, long seed) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private short[] generatePermutations(long seed) {
        short[] permutations = new short[256];
        short[] source = new short[256];
        
        for (short i = 0; i < 256; i++)
            source[i] = i;
        seed = seed * 6364136223846793005l + 1442695040888963407l;
        seed = seed * 6364136223846793005l + 1442695040888963407l;
        seed = seed * 6364136223846793005l + 1442695040888963407l;
        for (int i = 255; i >= 0; i--) {
            seed = seed * 6364136223846793005l + 1442695040888963407l;
            int r = (int)((seed + 31) % (i + 1));
            if (r < 0)
                r += (i + 1);
            permutations[i] = source[r];
            source[r] = source[i];
        }
        
        return permutations;
    }
    
    private long    _seed;
    private short[] _permutations;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private static double kernels(double dx, double dy,
        byte h1, byte h2, byte h3, byte h4, byte h5, byte h6,
        byte h7, byte h8, byte h9, byte h10, byte h11, byte h12) {
        double value = 0;
        double dxv, dyv, attn;
        
        dxv = dx + 1 + 2 * STRETCH_CONSTANT; dyv = dy + 1 + 2 * STRETCH_CONSTANT;
        attn = KERNEL_RADIUS - dxv * dxv - dyv * dyv;
        if (attn > 0) {
            attn *= attn;
            value += attn * attn * h1;
        }
        
        dxv = dx + 0 + STRETCH_CONSTANT; dyv = dy + 1 + STRETCH_CONSTANT;
        attn = KERNEL_RADIUS - dxv * dxv - dyv * dyv;
        // if (attn > 0) {
            attn *= attn;
            value += attn * attn * h2;
        // }
        
        dxv = dx - 1; dyv = dy + 1;
        attn = KERNEL_RADIUS - dxv * dxv - dyv * dyv;
        if (attn > 0) {
            attn *= attn;
            value += attn * attn * h3;
        }
        
        dxv = dx + 1 + STRETCH_CONSTANT; dyv = dy + 0 + STRETCH_CONSTANT;
        attn = KERNEL_RADIUS - dxv * dxv - dyv * dyv;
        if (attn > 0) {
            attn *= attn;
            value += attn * attn * h4;
        }
        
        dxv = dx + 0; dyv = dy + 0;
        attn = KERNEL_RADIUS - dxv * dxv - dyv * dyv;
        // if (attn > 0) {
            attn *= attn;
            value += attn * attn * h5;
        // }
        
        dxv = dx - 1 - STRETCH_CONSTANT; dyv = dy + 0 - STRETCH_CONSTANT;
        attn = KERNEL_RADIUS - dxv * dxv - dyv * dyv;
        // if (attn > 0) {
            attn *= attn;
            value += attn * attn * h6;
        // }
        
        dxv = dx - 2 - 2 * STRETCH_CONSTANT; dyv = dy + 0 - 2 * STRETCH_CONSTANT;
        attn = KERNEL_RADIUS - dxv * dxv - dyv * dyv;
        if (attn > 0) {
            attn *= attn;
            value += attn * attn * h7;
        }
        
        dxv = dx + 0 - STRETCH_CONSTANT; dyv = dy - 1 - STRETCH_CONSTANT;
        attn = KERNEL_RADIUS - dxv * dxv - dyv * dyv;
        // if (attn > 0) {
            attn *= attn;
            value += attn * attn * h8;
        // }
        
        dxv = dx - 1 - 2 * STRETCH_CONSTANT; dyv = dy - 1 - 2 * STRETCH_CONSTANT;
        attn = KERNEL_RADIUS - dxv * dxv - dyv * dyv;
        // if (attn > 0) {
            attn *= attn;
            value += attn * attn * h9;
        // }
        
        dxv = dx - 2 - 3 * STRETCH_CONSTANT; dyv = dy - 1 - 3 * STRETCH_CONSTANT;
        attn = KERNEL_RADIUS - dxv * dxv - dyv * dyv;
        // if (attn > 0) {
            attn *= attn;
            value += attn * attn * h10;
        // }
        
        dxv = dx - 1 - 3 * STRETCH_CONSTANT; dyv = dy - 2 - 3 * STRETCH_CONSTANT;
        attn = KERNEL_RADIUS - dxv * dxv - dyv * dyv;
        if (attn > 0) {
            attn *= attn;
            value += attn * attn * h11;
        }
        
        dxv = dx - 2 - 4 * STRETCH_CONSTANT; dyv = dy - 2 - 4 * STRETCH_CONSTANT;
        attn = KERNEL_RADIUS - dxv * dxv - dyv * dyv;
        if (attn > 0) {
            attn *= attn;
            value += attn * attn * h12;
        }
        
        return value;
    }
    
    private static final double SQUISH_CONSTANT = 0.366025403784439;    //(Math.sqrt(2+1)-1)/2;
    private static final double STRETCH_CONSTANT = -0.211324865405187;  //(1/Math.sqrt(2+1)-1)/2;
    private static final double NORM_CONSTANT = 2174;
    private static final double KERNEL_RADIUS = Math.sqrt(3);
    


}