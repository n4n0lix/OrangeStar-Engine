package de.orangestar.engine.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class Color4Test {
    
    @Before
    public void setup() {

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
    
}
