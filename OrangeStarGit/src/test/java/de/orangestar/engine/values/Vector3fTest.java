package de.orangestar.engine.values;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class Vector3fTest {

	@Before
	public void setup() {
		rnd = new Random();
	}

	@Test
	public void constructors() {
		Vector3f subject;

		subject = new Vector3f();
		assertTrue(Vector3f.isZero(subject));

		subject = new Vector3f(2812f, 12E-8f);
		assertEquals(2812f, subject.x, 0.0f);
		assertEquals(12E-8f, subject.y, 0.0f);
		
		subject = new Vector3f(2812f, 12E-8f, 241f);
		assertEquals(2812f, subject.x, 0.0f);
		assertEquals(12E-8f, subject.y, 0.0f);
		assertEquals(241f, subject.z, 0.0f);
	}

	@Test
	public void isZero() {
		Vector3f subject;

		subject = new Vector3f(0f, 0f);
		assertEquals(subject.x == 0f && subject.y == 0f && subject.z == 0f,
				Vector3f.isZero(subject));
		
		subject = new Vector3f(0f, 0f, 0f);
		assertEquals(subject.x == 0f && subject.y == 0f && subject.z == 0f,
				Vector3f.isZero(subject));

		subject = new Vector3f(57f, 2.42f);
		assertEquals(subject.x == 0f && subject.y == 0f && subject.z == 0f,
				Vector3f.isZero(subject));
		
		subject = new Vector3f(57f, 2.42f, 45.6f);
		assertEquals(subject.x == 0f && subject.y == 0f && subject.z == 0f,
				Vector3f.isZero(subject));

		subject = new Vector3f(3E-5f, 0f);
		assertEquals(subject.x == 0f && subject.y == 0f && subject.z == 0f,
				Vector3f.isZero(subject));

		subject = new Vector3f(0f, -124E12f);
		assertEquals(subject.x == 0f && subject.y == 0f && subject.z == 0f,
				Vector3f.isZero(subject));
		
		subject = new Vector3f(0f, -124E12f, 0f);
		assertEquals(subject.x == 0f && subject.y == 0f && subject.z == 0f,
				Vector3f.isZero(subject));
	}

	@Test
	public void zero() {
		assertTrue(Vector2f.isZero(Vector2f.zero()));
	}

	@Test
	public void zero2() {
		Vector3f subject;

		subject = new Vector3f(0f, 0f);
		Vector3f.zero(subject);
		assertTrue(Vector3f.isZero(subject));

		subject = new Vector3f(-374.12f, 126f);
		Vector3f.zero(subject);
		assertTrue(Vector3f.isZero(subject));

		subject = new Vector3f(651E-2f, 0f);
		Vector3f.zero(subject);
		assertTrue(Vector3f.isZero(subject));

		subject = new Vector3f(0f, -12.823f);
		Vector3f.zero(subject);
		assertTrue(Vector3f.isZero(subject));
	}

	@Test
	public void isUnit() {
		Vector3f subject;

		subject = new Vector3f(1f, 1f, 1f);
		assertEquals(subject.x == 1f && subject.y == 1f && subject.z == 1f,
				Vector3f.isUnit(subject));

		subject = new Vector3f(57f, 2.42f);
		assertEquals(subject.x == 1f && subject.y == 1f && subject.z == 1f,
				Vector3f.isUnit(subject));
		
		subject = new Vector3f(57f, 2.42f, 976.6f);
		assertEquals(subject.x == 1f && subject.y == 1f && subject.z == 1f,
				Vector3f.isUnit(subject));

		subject = new Vector3f(3E-5f, 0f);
		assertEquals(subject.x == 1f && subject.y == 1f && subject.z == 1f,
				Vector3f.isUnit(subject));

		subject = new Vector3f(0f, -124E12f);
		assertEquals(subject.x == 1f && subject.y == 1f && subject.z == 1f,
				Vector3f.isUnit(subject));
		
		subject = new Vector3f(0f, -124E12f, 0f);
		assertEquals(subject.x == 1f && subject.y == 1f && subject.z == 1f,
				Vector3f.isUnit(subject));
	}

	@Test
	public void one() {
		assertTrue(Vector3f.isUnit(Vector3f.one()));
	}

	@Test
	public void one2() {
		Vector3f subject;

		subject = new Vector3f(1f, 1f);
		Vector3f.one(subject);
		assertTrue(Vector3f.isUnit(subject));
		
		subject = new Vector3f(1f, 1f, 1f);
		Vector3f.one(subject);
		assertTrue(Vector3f.isUnit(subject));

		subject = new Vector3f(-374.12f, 126f);
		Vector3f.one(subject);
		assertTrue(Vector3f.isUnit(subject));
		
		subject = new Vector3f(-374.12f, 126f, 0.6f);
		Vector3f.one(subject);
		assertTrue(Vector3f.isUnit(subject));

		subject = new Vector3f(651E-2f, 0f);
		Vector3f.one(subject);
		assertTrue(Vector3f.isUnit(subject));

		subject = new Vector3f(0f, -12.823f);
		Vector3f.one(subject);
		assertTrue(Vector3f.isUnit(subject));
		
		subject = new Vector3f(0f, -12.823f, 0f);
		Vector3f.one(subject);
		assertTrue(Vector3f.isUnit(subject));
	}

	@Test
	public void set() {
		Vector3f subject;
		Vector3f source;

		subject = new Vector3f(412.23f, 12.79f);
		source = new Vector3f(31.7f, 152.4f);

		Vector3f.set(subject, source);
		assertEquals(subject, source);
		
		subject = new Vector3f(412.23f, 12.79f, 68.99f);
		source = new Vector3f(31.7f, 152.4f, 4.35f);

		Vector3f.set(subject, source);
		assertEquals(subject, source);

		subject = new Vector3f(819f, -163E5f);
		source = new Vector3f(84.732f, 0.02f);

		Vector3f.set(subject, source);
		assertEquals(subject, source);

		subject = new Vector3f(-24.72f, -283.3f);
		source = new Vector3f(12.9f, 3E4f, 0.03f);

		Vector3f.set(subject, 12.9f, 3E4f, 0.03f);
		assertEquals(subject, source);

		subject = new Vector3f(82f, 23.5f);
		source = new Vector3f(82f, 23.5f);

		Vector3f.set(subject, source);
		assertEquals(subject, source);
	}

	@Test
	public void neg() {
		float valX;
		float valY;
		float valZ;
		Vector3f subject;

		valX = 84.732f;
		valY = 12.79f;
		subject = new Vector3f(valX, valY);
		Vector3f.neg(subject);
		assertEquals(subject.x, -valX, 0.0f);
		assertEquals(subject.y, -valY, 0.0f);
		
		valX = 804.512f;
		valY = 152.777f;
		valZ = 2.09f;
		subject = new Vector3f(valX, valY, valZ);
		Vector3f.neg(subject);
		assertEquals(subject.x, -valX, 0.0f);
		assertEquals(subject.y, -valY, 0.0f);
		assertEquals(subject.z, -valZ, 0.0f);

		valX = 7E3f;
		valY = -73.5f;
		subject = new Vector3f(valX, valY);
		Vector3f.neg(subject);
		assertEquals(subject.x, -valX, 0.0f);
		assertEquals(subject.y, -valY, 0.0f);

		subject = new Vector3f(0f, 0f);
		Vector3f.neg(subject);
		assertEquals(subject.x, 0f, 0.0f);
		assertEquals(subject.y, 0f, 0.0f);

		valX = -127.92f;
		valY = -32.53f;
		subject = new Vector3f(valX, valY);
		Vector3f.neg(subject);
		assertEquals(subject.x, -valX, 0.0f);
		assertEquals(subject.y, -valY, 0.0f);
		
		valX = -127.92f;
		valY = -32.53f;
		valZ = -27.59f;
		subject = new Vector3f(valX, valY, valZ);
		Vector3f.neg(subject);
		assertEquals(subject.x, -valX, 0.0f);
		assertEquals(subject.y, -valY, 0.0f);
		assertEquals(subject.z, -valZ, 0.0f);
	}

	@Test
	public void add() {
		Vector3f source;
		Vector3f add;

		Vector3f result1 = new Vector3f();
		Vector3f result2 = new Vector3f();

		for (int i = 0; i < 1000; i++) {
			source = provideRandomVector3f();
			add = provideRandomVector3f();

			Vector3f.set(result2, source);
			Vector3f.add(result2, add);

			result1.x = source.x + add.x;
			result1.y = source.y + add.y;
			result1.z = source.z + add.z;

			assertEquals(result1, result2);
		}
	}

	@Test
	public void sub() {
		Vector3f source;
		Vector3f sub;

		Vector3f result1 = new Vector3f();
		Vector3f result2 = new Vector3f();

		for (int i = 0; i < 1000; i++) {
			source = provideRandomVector3f();
			sub = provideRandomVector3f();

			Vector3f.set(result2, source);
			Vector3f.sub(result2, sub);

			result1.x = source.x - sub.x;
			result1.y = source.y - sub.y;
			result1.z = source.z - sub.z;

			assertEquals(result1, result2);
		}
	}

	@Test
	public void mul() {
		Vector3f source;
		Vector3f mul;

		Vector3f result1 = new Vector3f();
		Vector3f result2 = new Vector3f();

		for (int i = 0; i < 1000; i++) {
			source = provideRandomVector3f();
			mul = provideRandomVector3f();

			Vector3f.set(result2, source);
			Vector3f.mul(result2, mul);

			result1.x = source.x * mul.x;
			result1.y = source.y * mul.y;
			result1.z = source.z * mul.z;

			assertEquals(result1, result2);
		}
	}

	@Test
	public void mulScalar() {
		Vector3f source;
		float scalar;

		Vector3f result1 = new Vector3f();
		Vector3f result2 = new Vector3f();

		for (int i = 0; i < 1000; i++) {
			source = provideRandomVector3f();
			scalar = rnd.nextFloat();

			Vector3f.set(result2, source);
			Vector3f.mul(result2, scalar);

			result1.x = source.x * scalar;
			result1.y = source.y * scalar;
			result1.z = source.z * scalar;

			assertEquals(result1, result2);
		}
	}

	@Test
	public void divScalar() {
		Vector3f source;
		float scalar;

		Vector3f result1 = new Vector3f();
		Vector3f result2 = new Vector3f();

		for (int i = 0; i < 1000; i++) {
			source = provideRandomVector3f();
			scalar = rnd.nextFloat();

			Vector3f.set(result2, source);
			Vector3f.div(result2, scalar);

			result1.x = source.x / scalar;
			result1.y = source.y / scalar;
			result1.z = source.z / scalar;

			assertEquals(result1, result2);
		}
	}

	@Test
	public void dot() {
		Vector3f source;
		Vector3f dot;

		float result1;
		float result2;

		for (int i = 0; i < 1000; i++) {
			source = provideRandomVector3f();
			dot = provideRandomVector3f();

			result2 = Vector3f.dot(source, dot);
			result1 = (source.x * dot.x) + (source.y * dot.y) + (source.z * dot.z);

			assertEquals(result1, result2, 0.0f);
		}
	}

	@Test
	public void normalize() {
		Vector3f subject;

		for (int i = 0; i < 1000; i++) {
			subject = provideRandomVector3f();

			Vector3f.normalize(subject);

			assertEquals(1.0f, subject.length(), 0.1f);
		}
	}

	@Test
	public void duplicate() {
		Vector3f subject;
		Vector3f expected;
		for (int i = 0; i < 1000; i++) {
			expected = provideRandomVector3f();

			subject = Vector3f.duplicate(expected);

			assertEquals(expected, subject);
		}
	}

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Random rnd;
    
    private Vector3f provideRandomVector3f() {
        return new Vector3f( rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
    }

}
