package de.orangestar.engine.values;

import java.util.Random;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Matrix4fTest {
    
    @Before
    public void setup() {
        rnd = new Random();
    }
    
    @Test
    public void constructors() {
        // Matrix4f(float val)
        for(int i = 0; i < 1000; i++) {
            float val = rnd.nextFloat();
            Matrix4f reference = new Matrix4f(val,   0,   0,   0,
                                                0, val,   0,   0,
                                                0,   0, val,   0,
                                                0,   0,   0, val);
            
            Matrix4f result = new Matrix4f(val);
            assertEquals(reference, result);
        }
        
        // Matrix4f( ... )
        for(int i = 0; i < 1000; i++) {
            float v00 = rnd.nextFloat(); float v01 = rnd.nextFloat(); float v02 = rnd.nextFloat(); float v03 = rnd.nextFloat();
            float v10 = rnd.nextFloat(); float v11 = rnd.nextFloat(); float v12 = rnd.nextFloat(); float v13 = rnd.nextFloat();
            float v20 = rnd.nextFloat(); float v21 = rnd.nextFloat(); float v22 = rnd.nextFloat(); float v23 = rnd.nextFloat();
            float v30 = rnd.nextFloat(); float v31 = rnd.nextFloat(); float v32 = rnd.nextFloat(); float v33 = rnd.nextFloat();
            
            Matrix4f result = new Matrix4f(v00, v01, v02, v03,
                                           v10, v11, v12, v13,
                                           v20, v21, v22, v23,
                                           v30, v31, v32, v33);
            
            assertEquals(v00, result.m00, 0.0f); assertEquals(v01, result.m01, 0.0f); assertEquals(v02, result.m02, 0.0f); assertEquals(v03, result.m03, 0.0f);
            assertEquals(v10, result.m10, 0.0f); assertEquals(v11, result.m11, 0.0f); assertEquals(v12, result.m12, 0.0f); assertEquals(v13, result.m13, 0.0f);
            assertEquals(v20, result.m20, 0.0f); assertEquals(v21, result.m21, 0.0f); assertEquals(v22, result.m22, 0.0f); assertEquals(v23, result.m23, 0.0f);
            assertEquals(v30, result.m30, 0.0f); assertEquals(v31, result.m31, 0.0f); assertEquals(v32, result.m32, 0.0f); assertEquals(v33, result.m33, 0.0f);
        }
    }
    
    private Random rnd;
}
