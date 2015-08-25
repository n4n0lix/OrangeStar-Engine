package de.orangestar.engine.values;

import java.util.Random;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Vector4fTest {
    
    @Before
    public void setup() {
        rnd = new Random();
    }
    
    @Test
    public void constructors() {
        Vector4f subject;
        
        subject = new Vector4f();
        assertTrue( Vector4f.isZero( subject ));
        
        subject = new Vector4f(2812f, 12E-8f, 92.01f, -12.97f);
        assertEquals(2812f, subject.x, 0.0f);
        assertEquals(12E-8f, subject.y, 0.0f);
    }
    
    @Test
    public void isZero() {
        Vector4f subject;
        
        subject = new Vector4f( 0f, 0f, 0f, 0f );
        assertEquals( subject.x == 0f && subject.y == 0f, Vector4f.isZero( subject ));
        
        subject = new Vector4f( 57f, 2.42f, 182.56f, 283.2f );
        assertEquals( subject.x == 0f && subject.y == 0f, Vector4f.isZero( subject ));
        
        subject = new Vector4f( 3E-5f, 14E-4f, 18.35f, 54.98f );
        assertEquals( subject.x == 0f && subject.y == 0f, Vector4f.isZero( subject ));
        
        subject = new Vector4f( 4.1f, -124E12f, 8901.2f, 94.412f );
        assertEquals( subject.x == 0f && subject.y == 0f, Vector4f.isZero( subject ));
    }
    
    @Test
    public void zero() {
        assertTrue( Vector4f.isZero( Vector4f.zero() ));
    }
    
    @Test
    public void zero2() {
        Vector4f subject;
        
        subject = new Vector4f( 0f, 0f, 0f, 0f );
        Vector4f.zero(subject);
        assertTrue(Vector4f.isZero( subject ));
        
        subject = new Vector4f( -374.12f, 126f, -124E12f, 8901.2f );
        Vector4f.zero(subject);
        assertTrue(Vector4f.isZero( subject ));
        
        subject = new Vector4f( 651E-2f, 0f, 8901.2f, 94.412f );
        Vector4f.zero(subject);
        assertTrue(Vector4f.isZero( subject ));
        
        subject = new Vector4f( 0f, -12.823f, 4.1f, -124E12f );
        Vector4f.zero(subject);
        assertTrue(Vector4f.isZero( subject ));
    }
    
    @Test
    public void isUnit() {
        Vector4f subject;
        
        subject = new Vector4f( 1f, 1f, 1f, 1f );
        assertEquals( subject.x == 1f && subject.y == 1f, Vector4f.isUnit( subject ));
        
        subject = new Vector4f( 57f, 2.42f, 126f, -124E12f );
        assertEquals( subject.x == 1f && subject.y == 1f, Vector4f.isUnit( subject ));
        
        subject = new Vector4f( 3E-5f, 0f, 57f, 2.42f );
        assertEquals( subject.x == 1f && subject.y == 1f, Vector4f.isUnit( subject ));
        
        subject = new Vector4f( 0f, -124E12f, 92.01f, -12.97f );
        assertEquals( subject.x == 1f && subject.y == 1f, Vector4f.isUnit( subject ));
    }
    
    @Test
    public void one() {
        assertTrue( Vector4f.isUnit( Vector4f.one() ));
    }
    
    @Test
    public void one2() {
        Vector4f subject;
        
        subject = new Vector4f( 1f, 1f, 1f, 1f );
        Vector4f.one(subject);
        assertTrue(Vector4f.isUnit( subject ));
        
        subject = new Vector4f( -374.12f, 126f, 92.01f, -12.97f );
        Vector4f.one(subject);
        assertTrue(Vector4f.isUnit( subject ));
        
        subject = new Vector4f( 651E-2f, 0f, 92.01f, -12.97f );
        Vector4f.one(subject);
        assertTrue(Vector4f.isUnit( subject ));
        
        subject = new Vector4f( 0f, -12.823f, 57f, 2.42f );
        Vector4f.one(subject);
        assertTrue(Vector4f.isUnit( subject ));
    }
    
    @Test
    public void set() {
        Vector4f subject;
        Vector4f source;
        
        subject = new Vector4f(412.23f, 12.79f, 74.06f, 19.52f );
        source  = new Vector4f(31.7f, 152.4f, -163E5f, 57f);
        
        Vector4f.set(subject, source);
        assertEquals(subject, source);
        
        subject = new Vector4f(819f, -163E5f, 57f, 2.42f);
        source  = new Vector4f(84.732f, 0.02f, 82f, 23.5f);
        
        Vector4f.set(subject, source);
        assertEquals(subject, source);
        
        subject = new Vector4f(-24.72f, -283.3f, 92.01f, -12.97f);
        source  = new Vector4f(12.9f, 3E4f, 0.02f, 82f);
        
        Vector4f.set(subject, 12.9f, 3E4f, 0.02f, 82f);
        assertEquals(subject, source);
        
        subject = new Vector4f(82f, 23.5f, 107.54f, 13.67f);
        source  = new Vector4f(82f, 23.5f, 107.54f, 13.67f);
        
        Vector4f.set(subject, source);
        assertEquals(subject, source);
    }
    
    @Test
    public void neg() {
        float valX;
        float valY;
        float valZ;
        float valW;
        Vector4f subject;
        
        valX = 84.732f;
        valY = 12.79f;
        valZ = 23.5f;
        valW = 92.01f;
        subject = new Vector4f(valX, valY, valZ, valW);        
        Vector4f.neg(subject);
        assertEquals( subject.x, -valX, 0.0f );
        assertEquals( subject.y, -valY, 0.0f );
        assertEquals( subject.z, -valZ, 0.0f );
        assertEquals( subject.w, -valW, 0.0f );
        
        valX = 7E3f;
        valY = -73.5f;
        valZ = -24.72f;
        valW = -374.12f;
        subject = new Vector4f(valX, valY, valZ, valW);        
        Vector4f.neg(subject);
        assertEquals( subject.x, -valX, 0.0f );
        assertEquals( subject.y, -valY, 0.0f );
        assertEquals( subject.z, -valZ, 0.0f );
        assertEquals( subject.w, -valW, 0.0f );
        
        subject = new Vector4f(0f, 0f, 0f, 0f);        
        Vector4f.neg(subject);
        assertEquals( subject.x, 0f, 0.0f );
        assertEquals( subject.y, 0f, 0.0f );
        assertEquals( subject.x, 0f, 0.0f );
        assertEquals( subject.y, 0f, 0.0f );
        
        valX = -127.92f;
        valY = -32.53f;
        valZ = -512.72f;
        valW = -8E-8f;
        subject = new Vector4f(valX, valY, valZ, valW);        
        Vector4f.neg(subject);
        assertEquals( subject.x, -valX, 0.0f );
        assertEquals( subject.y, -valY, 0.0f );
        assertEquals( subject.z, -valZ, 0.0f );
        assertEquals( subject.w, -valW, 0.0f );
    }
    
    @Test
    public void add() {
        Vector4f source;
        Vector4f add;
        
        Vector4f result1 = new Vector4f();
        Vector4f result2 = new Vector4f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomVector4f();
            add    = provideRandomVector4f();
            
            Vector4f.set(result2, source);
            Vector4f.add(result2, add);
            
            result1.x = source.x + add.x;
            result1.y = source.y + add.y;
            result1.z = source.z + add.z;
            result1.w = source.w + add.w;
            
            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void sub() {
        Vector4f source;
        Vector4f sub;
        
        Vector4f result1 = new Vector4f();
        Vector4f result2 = new Vector4f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomVector4f();
            sub    = provideRandomVector4f();
            
            Vector4f.set(result2, source);
            Vector4f.sub(result2, sub);
            
            result1.x = source.x - sub.x;
            result1.y = source.y - sub.y;
            result1.z = source.z - sub.z;
            result1.w = source.w - sub.w;
            
            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void mul() {
        Vector4f source;
        Vector4f mul;
        
        Vector4f result1 = new Vector4f();
        Vector4f result2 = new Vector4f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomVector4f();
            mul    = provideRandomVector4f();
            
            Vector4f.set(result2, source);
            Vector4f.mul(result2, mul);
            
            result1.x = source.x * mul.x;
            result1.y = source.y * mul.y;
            result1.z = source.z * mul.z;
            result1.w = source.w * mul.w;
            
            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void mulScalar() {
        Vector4f source;
        float scalar;
        
        Vector4f result1 = new Vector4f();
        Vector4f result2 = new Vector4f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomVector4f();
            scalar = rnd.nextFloat();
            
            Vector4f.set(result2, source);
            Vector4f.mul(result2, scalar);
            
            result1.x = source.x * scalar;
            result1.y = source.y * scalar;
            result1.z = source.z * scalar;
            result1.w = source.w * scalar;
            
            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void divScalar() {
        Vector4f source;
        float scalar;
        
        Vector4f result1 = new Vector4f();
        Vector4f result2 = new Vector4f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomVector4f();
            scalar = rnd.nextFloat();
            
            Vector4f.set(result2, source);
            Vector4f.div(result2, scalar);
            
            result1.x = source.x / scalar;
            result1.y = source.y / scalar;
            result1.z = source.z / scalar;
            result1.w = source.w / scalar;
            
            assertEquals(result1, result2);
        }
    }
        
    @Test
    public void duplicate() {
        Vector4f subject;
        Vector4f expected;
        for(int i = 0; i < 1000; i++) {
            expected = provideRandomVector4f();
            
            subject = Vector4f.duplicate(expected);

            assertEquals(expected, subject);
        }
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Random rnd;
    
    private Vector4f provideRandomVector4f() {
        return new Vector4f( rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat() );
    }
        
}
