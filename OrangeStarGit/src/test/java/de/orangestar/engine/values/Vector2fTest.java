package de.orangestar.engine.values;

import java.util.Random;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Vector2fTest {
    
    @Before
    public void setup() {
        rnd = new Random();
    }
    
    @Test
    public void constructors() {
        Vector2f subject;
        
        subject = new Vector2f();
        assertTrue( Vector2f.isZero( subject ));
        
        subject = new Vector2f(2812f, 12E-8f);
        assertEquals(2812f, subject.x, 0.0f);
        assertEquals(12E-8f, subject.y, 0.0f);
    }
    
    @Test
    public void isZero() {
        Vector2f subject;
        
        subject = new Vector2f( 0f, 0f );
        assertEquals( subject.x == 0f && subject.y == 0f, Vector2f.isZero( subject ));
        
        subject = new Vector2f( 57f, 2.42f );
        assertEquals( subject.x == 0f && subject.y == 0f, Vector2f.isZero( subject ));
        
        subject = new Vector2f( 3E-5f, 0f );
        assertEquals( subject.x == 0f && subject.y == 0f, Vector2f.isZero( subject ));
        
        subject = new Vector2f( 0f, -124E12f );
        assertEquals( subject.x == 0f && subject.y == 0f, Vector2f.isZero( subject ));
    }
    
    @Test
    public void zero() {
        assertTrue( Vector2f.isZero( Vector2f.zero() ));
    }
    
    @Test
    public void zero2() {
        Vector2f subject;
        
        subject = new Vector2f( 0f, 0f );
        Vector2f.zero(subject);
        assertTrue(Vector2f.isZero( subject ));
        
        subject = new Vector2f( -374.12f, 126f );
        Vector2f.zero(subject);
        assertTrue(Vector2f.isZero( subject ));
        
        subject = new Vector2f( 651E-2f, 0f );
        Vector2f.zero(subject);
        assertTrue(Vector2f.isZero( subject ));
        
        subject = new Vector2f( 0f, -12.823f );
        Vector2f.zero(subject);
        assertTrue(Vector2f.isZero( subject ));
    }
    
    @Test
    public void isUnit() {
        Vector2f subject;
        
        subject = new Vector2f( 1f, 1f );
        assertEquals( subject.x == 1f && subject.y == 1f, Vector2f.isUnit( subject ));
        
        subject = new Vector2f( 57f, 2.42f );
        assertEquals( subject.x == 1f && subject.y == 1f, Vector2f.isUnit( subject ));
        
        subject = new Vector2f( 3E-5f, 0f );
        assertEquals( subject.x == 1f && subject.y == 1f, Vector2f.isUnit( subject ));
        
        subject = new Vector2f( 0f, -124E12f );
        assertEquals( subject.x == 1f && subject.y == 1f, Vector2f.isUnit( subject ));
    }
    
    @Test
    public void one() {
        assertTrue( Vector2f.isUnit( Vector2f.one() ));
    }
    
    @Test
    public void one2() {
        Vector2f subject;
        
        subject = new Vector2f( 1f, 1f );
        Vector2f.one(subject);
        assertTrue(Vector2f.isUnit( subject ));
        
        subject = new Vector2f( -374.12f, 126f );
        Vector2f.one(subject);
        assertTrue(Vector2f.isUnit( subject ));
        
        subject = new Vector2f( 651E-2f, 0f );
        Vector2f.one(subject);
        assertTrue(Vector2f.isUnit( subject ));
        
        subject = new Vector2f( 0f, -12.823f );
        Vector2f.one(subject);
        assertTrue(Vector2f.isUnit( subject ));
    }
    
    @Test
    public void set() {
        Vector2f subject;
        Vector2f source;
        
        subject = new Vector2f(412.23f, 12.79f);
        source  = new Vector2f(31.7f, 152.4f);
        
        Vector2f.set(subject, source);
        assertEquals(subject, source);
        
        subject = new Vector2f(819f, -163E5f);
        source  = new Vector2f(84.732f, 0.02f);
        
        Vector2f.set(subject, source);
        assertEquals(subject, source);
        
        subject = new Vector2f(-24.72f, -283.3f);
        source  = new Vector2f(12.9f, 3E4f);
        
        Vector2f.set(subject, 12.9f, 3E4f);
        assertEquals(subject, source);
        
        subject = new Vector2f(82f, 23.5f);
        source  = new Vector2f(82f, 23.5f);
        
        Vector2f.set(subject, source);
        assertEquals(subject, source);
    }
    
    @Test
    public void neg() {
        float valX;
        float valY;
        Vector2f subject;
        
        valX = 84.732f;
        valY = 12.79f;
        subject = new Vector2f(valX, valY);        
        Vector2f.neg(subject);
        assertEquals( subject.x, -valX, 0.0f );
        assertEquals( subject.y, -valY, 0.0f );
        
        valX = 7E3f;
        valY = -73.5f;
        subject = new Vector2f(valX, valY);        
        Vector2f.neg(subject);
        assertEquals( subject.x, -valX, 0.0f );
        assertEquals( subject.y, -valY, 0.0f );
        
        subject = new Vector2f(0f, 0f);        
        Vector2f.neg(subject);
        assertEquals( subject.x, 0f, 0.0f );
        assertEquals( subject.y, 0f, 0.0f );
        
        valX = -127.92f;
        valY = -32.53f;
        subject = new Vector2f(valX, valY);        
        Vector2f.neg(subject);
        assertEquals( subject.x, -valX, 0.0f );
        assertEquals( subject.y, -valY, 0.0f );
    }
    
    @Test
    public void add() {
        Vector2f source;
        Vector2f add;
        
        Vector2f result1 = new Vector2f();
        Vector2f result2 = new Vector2f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomVector2f();
            add    = provideRandomVector2f();
            
            Vector2f.set(result2, source);
            Vector2f.add(result2, add);
            
            result1.x = source.x + add.x;
            result1.y = source.y + add.y;
            
            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void sub() {
        Vector2f source;
        Vector2f sub;
        
        Vector2f result1 = new Vector2f();
        Vector2f result2 = new Vector2f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomVector2f();
            sub    = provideRandomVector2f();
            
            Vector2f.set(result2, source);
            Vector2f.sub(result2, sub);
            
            result1.x = source.x - sub.x;
            result1.y = source.y - sub.y;
            
            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void mul() {
        Vector2f source;
        Vector2f mul;
        
        Vector2f result1 = new Vector2f();
        Vector2f result2 = new Vector2f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomVector2f();
            mul    = provideRandomVector2f();
            
            Vector2f.set(result2, source);
            Vector2f.mul(result2, mul);
            
            result1.x = source.x * mul.x;
            result1.y = source.y * mul.y;
            
            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void mulScalar() {
        Vector2f source;
        float scalar;
        
        Vector2f result1 = new Vector2f();
        Vector2f result2 = new Vector2f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomVector2f();
            scalar = rnd.nextFloat();
            
            Vector2f.set(result2, source);
            Vector2f.mul(result2, scalar);
            
            result1.x = source.x * scalar;
            result1.y = source.y * scalar;
            
            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void divScalar() {
        Vector2f source;
        float scalar;
        
        Vector2f result1 = new Vector2f();
        Vector2f result2 = new Vector2f();
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomVector2f();
            scalar = rnd.nextFloat();
            
            Vector2f.set(result2, source);
            Vector2f.div(result2, scalar);
            
            result1.x = source.x / scalar;
            result1.y = source.y / scalar;
            
            assertEquals(result1, result2);
        }
    }
    
    @Test
    public void dot() {
        Vector2f source;
        Vector2f dot;
        
        float result1;
        float result2;
        
        for(int i = 0; i < 1000; i++) {
            source = provideRandomVector2f();
            dot    = provideRandomVector2f();

            result2 = Vector2f.dot(source, dot);
            result1 = (source.x * dot.x) + (source.y * dot.y);

            assertEquals(result1, result2, 0.0f);
        }
    }
    
    @Test
    public void normalize() {
        Vector2f subject;

        for(int i = 0; i < 1000; i++) {
            subject = provideRandomVector2f();

            Vector2f.normalize(subject);

            assertEquals(1.0f, subject.length(), 0.1f);
        }
    }
    
    @Test
    public void duplicate() {
        Vector2f subject;
        Vector2f expected;
        for(int i = 0; i < 1000; i++) {
            expected = provideRandomVector2f();
            
            subject = Vector2f.duplicate(expected);

            assertEquals(expected, subject);
        }
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Random rnd;
    
    private Vector2f provideRandomVector2f() {
        return new Vector2f( rnd.nextFloat(), rnd.nextFloat() );
    }
        
}
