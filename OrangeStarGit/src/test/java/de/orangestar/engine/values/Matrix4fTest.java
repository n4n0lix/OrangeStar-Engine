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
        Matrix4f subject;
        
        // Matrix4f()
        subject = new Matrix4f();
        
        assertEquals(0.0f, subject.m00, 0.0f); assertEquals(0.0f, subject.m01, 0.0f); assertEquals(0.0f, subject.m02, 0.0f); assertEquals(0.0f, subject.m03, 0.0f);
        assertEquals(0.0f, subject.m10, 0.0f); assertEquals(0.0f, subject.m11, 0.0f); assertEquals(0.0f, subject.m12, 0.0f); assertEquals(0.0f, subject.m13, 0.0f);
        assertEquals(0.0f, subject.m20, 0.0f); assertEquals(0.0f, subject.m21, 0.0f); assertEquals(0.0f, subject.m22, 0.0f); assertEquals(0.0f, subject.m23, 0.0f);
        assertEquals(0.0f, subject.m30, 0.0f); assertEquals(0.0f, subject.m31, 0.0f); assertEquals(0.0f, subject.m32, 0.0f); assertEquals(0.0f, subject.m33, 0.0f);
        
        // Matrix4f(float val)
        for(int i = 0; i < 1000; i++) {
            float val = rnd.nextFloat();
            subject = new Matrix4f(val,   0,   0,   0,
                                     0, val,   0,   0,
                                     0,   0, val,   0,
                                     0,   0,   0, val);
            
            Matrix4f result = new Matrix4f(val);
            assertEquals(subject, result);
        }
        
        // Matrix4f( ... )
        for(int i = 0; i < 1000; i++) {
            float v00 = rnd.nextFloat(); float v01 = rnd.nextFloat(); float v02 = rnd.nextFloat(); float v03 = rnd.nextFloat();
            float v10 = rnd.nextFloat(); float v11 = rnd.nextFloat(); float v12 = rnd.nextFloat(); float v13 = rnd.nextFloat();
            float v20 = rnd.nextFloat(); float v21 = rnd.nextFloat(); float v22 = rnd.nextFloat(); float v23 = rnd.nextFloat();
            float v30 = rnd.nextFloat(); float v31 = rnd.nextFloat(); float v32 = rnd.nextFloat(); float v33 = rnd.nextFloat();
            
            subject = new Matrix4f(v00, v01, v02, v03,
                                           v10, v11, v12, v13,
                                           v20, v21, v22, v23,
                                           v30, v31, v32, v33);
            
            assertEquals(v00, subject.m00, 0.0f); assertEquals(v01, subject.m01, 0.0f); assertEquals(v02, subject.m02, 0.0f); assertEquals(v03, subject.m03, 0.0f);
            assertEquals(v10, subject.m10, 0.0f); assertEquals(v11, subject.m11, 0.0f); assertEquals(v12, subject.m12, 0.0f); assertEquals(v13, subject.m13, 0.0f);
            assertEquals(v20, subject.m20, 0.0f); assertEquals(v21, subject.m21, 0.0f); assertEquals(v22, subject.m22, 0.0f); assertEquals(v23, subject.m23, 0.0f);
            assertEquals(v30, subject.m30, 0.0f); assertEquals(v31, subject.m31, 0.0f); assertEquals(v32, subject.m32, 0.0f); assertEquals(v33, subject.m33, 0.0f);
        }
    }
    
    @Test
    public void isZero() {
        Matrix4f m;
        
        m = new Matrix4f(1.0f, 8.0f, 12.0f, 3.0f, 14.0f, 2.0f, 44.0f, 51.0f, 84.0f, 25.0f, 83.0f, 57.0f, 25.0f, 12.0f, 73.0f, 88.0f);
        assertFalse(Matrix4f.isZero(m));
        
        m = new Matrix4f(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        assertFalse(Matrix4f.isZero(m));
        
        m = new Matrix4f(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        assertTrue(Matrix4f.isZero(m));
    }
    
    @Test
    public void zero() {
        assertTrue( Matrix4f.isZero( Matrix4f.zero() ));
    }
    
    @Test
    public void zero2() {
        Matrix4f subject = new Matrix4f(32.0f, 16.0f, 8.0f, 4.0f, 32.0f, 16.0f, 8.0f, 4.0f, 32.0f, 16.0f, 8.0f, 4.0f, 32.0f, 16.0f, 8.0f, 4.0f);
        Matrix4f.zero(subject);
        assertTrue( Matrix4f.isZero( subject ));
    }
    
    @Test
    public void isIdentity() {
        Matrix4f m;
        
        m = new Matrix4f(1.0f, 8.0f, 12.0f, 3.0f, 14.0f, 2.0f, 44.0f, 51.0f, 84.0f, 25.0f, 83.0f, 57.0f, 25.0f, 12.0f, 73.0f, 88.0f);
        assertFalse(Matrix4f.isIdentity(m));
        
        m = new Matrix4f(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        assertFalse(Matrix4f.isIdentity(m));
        
        m = new Matrix4f(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
        assertTrue( Matrix4f.isIdentity(m) );
    }
    
    @Test
    public void identity() {
        assertTrue( Matrix4f.isIdentity( Matrix4f.identity() ));
    }
    
    @Test
    public void identity2() {
        Matrix4f subject = new Matrix4f(32.0f, 16.0f, 8.0f, 4.0f, 32.0f, 16.0f, 8.0f, 4.0f, 32.0f, 16.0f, 8.0f, 4.0f, 32.0f, 16.0f, 8.0f, 4.0f);
        Matrix4f.identity(subject);
        assertTrue( Matrix4f.isIdentity( subject ));
    }
    
    @Test
    public void set() {
        Matrix4f target = Matrix4f.zero();
        Matrix4f source;
        
        for(int i = 0; i < 1000; i++) {
            
            source = provideRandomMatrix();
            Matrix4f.set(target, source);

            assertEquals(target, source);
        }
    }
    
    @Test
    public void invert() {
        Matrix4f source = new Matrix4f(3f, 4f, 1f, 3f, 2f, 5f, 8f, 9f, 2f, 9f, 7f, 3f, 1f, 4f, 9f, 1f);
        Matrix4f sourceInv = new Matrix4f();

        Matrix4f.set( sourceInv, source );
        Matrix4f.invert( sourceInv );
        Matrix4f.mul( source, sourceInv );
        
        assertTrue( Matrix4f.isIdentity( source, 0.2f ) );
    }
    
    @Test
    public void add() {
        Matrix4f source;
        Matrix4f add;
        
        Matrix4f result1 = new Matrix4f();
        Matrix4f result2 = new Matrix4f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomMatrix();
            add    = provideRandomMatrix();
            
            Matrix4f.set(result2, source);
            Matrix4f.add(result2, add);
            
            result1.m00 = source.m00 + add.m00;
            result1.m01 = source.m01 + add.m01;
            result1.m02 = source.m02 + add.m02;
            result1.m03 = source.m03 + add.m03;
            
            result1.m10 = source.m10 + add.m10;
            result1.m11 = source.m11 + add.m11;
            result1.m12 = source.m12 + add.m12;
            result1.m13 = source.m13 + add.m13;
            
            result1.m20 = source.m20 + add.m20;
            result1.m21 = source.m21 + add.m21;
            result1.m22 = source.m22 + add.m22;
            result1.m23 = source.m23 + add.m23;
            
            result1.m30 = source.m30 + add.m30;
            result1.m31 = source.m31 + add.m31;
            result1.m32 = source.m32 + add.m32;
            result1.m33 = source.m33 + add.m33;
            
            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void sub() {
        Matrix4f source;
        Matrix4f sub;
        
        Matrix4f result1 = new Matrix4f();
        Matrix4f result2 = new Matrix4f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomMatrix();
            sub    = provideRandomMatrix();
            
            Matrix4f.set(result2, source);
            Matrix4f.sub(result2, sub);
            
            result1.m00 = source.m00 - sub.m00;
            result1.m01 = source.m01 - sub.m01;
            result1.m02 = source.m02 - sub.m02;
            result1.m03 = source.m03 - sub.m03;
            
            result1.m10 = source.m10 - sub.m10;
            result1.m11 = source.m11 - sub.m11;
            result1.m12 = source.m12 - sub.m12;
            result1.m13 = source.m13 - sub.m13;
            
            result1.m20 = source.m20 - sub.m20;
            result1.m21 = source.m21 - sub.m21;
            result1.m22 = source.m22 - sub.m22;
            result1.m23 = source.m23 - sub.m23;
            
            result1.m30 = source.m30 - sub.m30;
            result1.m31 = source.m31 - sub.m31;
            result1.m32 = source.m32 - sub.m32;
            result1.m33 = source.m33 - sub.m33;
            
            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void mulMatrix4f() {
        Matrix4f source;
        Matrix4f mult;
        
        Matrix4f result1 = new Matrix4f();
        Matrix4f result2 = new Matrix4f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomMatrix();
            mult    = provideRandomMatrix();
            
            Matrix4f.set(result2, source);
            Matrix4f.mul(result2, mult);
            
            result1.m00 = source.m00 * mult.m00 + source.m10 * mult.m01 + source.m20 * mult.m02 + source.m30 * mult.m03;
            result1.m01 = source.m01 * mult.m00 + source.m11 * mult.m01 + source.m21 * mult.m02 + source.m31 * mult.m03;
            result1.m02 = source.m02 * mult.m00 + source.m12 * mult.m01 + source.m22 * mult.m02 + source.m32 * mult.m03;
            result1.m03 = source.m03 * mult.m00 + source.m13 * mult.m01 + source.m23 * mult.m02 + source.m33 * mult.m03;
            
            result1.m10 = source.m00 * mult.m10 + source.m10 * mult.m11 + source.m20 * mult.m12 + source.m30 * mult.m13;
            result1.m11 = source.m01 * mult.m10 + source.m11 * mult.m11 + source.m21 * mult.m12 + source.m31 * mult.m13;
            result1.m12 = source.m02 * mult.m10 + source.m12 * mult.m11 + source.m22 * mult.m12 + source.m32 * mult.m13;
            result1.m13 = source.m03 * mult.m10 + source.m13 * mult.m11 + source.m23 * mult.m12 + source.m33 * mult.m13;
            
            result1.m20 = source.m00 * mult.m20 + source.m10 * mult.m21 + source.m20 * mult.m22 + source.m30 * mult.m23;
            result1.m21 = source.m01 * mult.m20 + source.m11 * mult.m21 + source.m21 * mult.m22 + source.m31 * mult.m23;
            result1.m22 = source.m02 * mult.m20 + source.m12 * mult.m21 + source.m22 * mult.m22 + source.m32 * mult.m23;
            result1.m23 = source.m03 * mult.m20 + source.m13 * mult.m21 + source.m23 * mult.m22 + source.m33 * mult.m23;
            
            result1.m30 = source.m00 * mult.m30 + source.m10 * mult.m31 + source.m20 * mult.m32 + source.m30 * mult.m33;
            result1.m31 = source.m01 * mult.m30 + source.m11 * mult.m31 + source.m21 * mult.m32 + source.m31 * mult.m33;
            result1.m32 = source.m02 * mult.m30 + source.m12 * mult.m31 + source.m22 * mult.m32 + source.m32 * mult.m33;
            result1.m33 = source.m03 * mult.m30 + source.m13 * mult.m31 + source.m23 * mult.m32 + source.m33 * mult.m33;

            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void mulVector4f() {
        Matrix4f mat;
        Vector4f vec;
        
        Vector4f result1 = new Vector4f();
        Vector4f result2 = new Vector4f();
        
        for(int i = 0; i < 1000; i++) {
            mat  = provideRandomMatrix();
            vec = provideRandomVector4f();
            
            Vector4f.set(result2, vec);
            Matrix4f.mul(mat, result2);
            
            result1.x = mat.m00 * vec.x + mat.m01 * vec.y + mat.m02 * vec.z + mat.m03 * vec.w;
            result1.y = mat.m10 * vec.x + mat.m11 * vec.y + mat.m12 * vec.z + mat.m13 * vec.w;
            result1.z = mat.m20 * vec.x + mat.m21 * vec.y + mat.m22 * vec.z + mat.m23 * vec.w;
            result1.w = mat.m30 * vec.x + mat.m31 * vec.y + mat.m32 * vec.z + mat.m33 * vec.w;

            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void translation() {
        Matrix4f transMatrix = new Matrix4f();
        
        for(int i = 0; i < 1000; i++) {
            Vector3f transVector = new Vector3f(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
            Vector4f vector      = new Vector4f(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), 1f);
            
            Vector4f result = new Vector4f();
            Vector4f.set(result, vector);
            
            Matrix4f.translation(transVector, transMatrix);
            Matrix4f.mul(transMatrix, result);
            
            assertEquals(vector.x + transVector.x, result.x, 0.5f);
            assertEquals(vector.y + transVector.y, result.y, 0.5f);
            assertEquals(vector.z + transVector.z, result.z, 0.5f);
            assertEquals(vector.w, result.w, 0.5f);
        }
    }
    
    @Test
    public void scaling() {
        Matrix4f transMatrix = new Matrix4f();

        for(int i = 0; i < 1000; i++) {
            Vector3f transVector = new Vector3f(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
            Vector4f vector      = new Vector4f(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), 1f);
            
            Vector4f result = new Vector4f();
            Vector4f.set(result, vector);
            
            Matrix4f.scaling(transVector, transMatrix);
            Matrix4f.mul(transMatrix, result);
            
            assertEquals(vector.x * transVector.x, result.x, 0.5f);
            assertEquals(vector.y * transVector.y, result.y, 0.5f);
            assertEquals(vector.z * transVector.z, result.z, 0.5f);
            assertEquals(vector.w, result.w, 0.5f);
        }
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Random rnd;
    
    private Matrix4f provideRandomMatrix() {
        float v00 = rnd.nextFloat(); float v01 = rnd.nextFloat(); float v02 = rnd.nextFloat(); float v03 = rnd.nextFloat();
        float v10 = rnd.nextFloat(); float v11 = rnd.nextFloat(); float v12 = rnd.nextFloat(); float v13 = rnd.nextFloat();
        float v20 = rnd.nextFloat(); float v21 = rnd.nextFloat(); float v22 = rnd.nextFloat(); float v23 = rnd.nextFloat();
        float v30 = rnd.nextFloat(); float v31 = rnd.nextFloat(); float v32 = rnd.nextFloat(); float v33 = rnd.nextFloat();
        
        return new Matrix4f(v00, v01, v02, v03,
                            v10, v11, v12, v13,
                            v20, v21, v22, v23,
                            v30, v31, v32, v33);
    }
    
    private Vector4f provideRandomVector4f() {
        return new Vector4f( rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat() );
    }
    
}
