package com.github.i49.hibiscus.json;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;
import javax.json.JsonValue.ValueType;

import org.junit.Test;

public class JsonLongNumberImplTest {

	public static class GetValueTypeTest {
		
		@Test
		public void testGetValueType() {
			JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
			assertEquals(ValueType.NUMBER, n.getValueType());
		}
	}
	
	public static class IsIntegralTest {
		
		@Test
		public void testIsIntegral() {
			JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
			assertTrue(n.isIntegral());
		}
	}
	
	public static class IntValueTest {
		
		@Test
		public void testZero() {
			JsonNumber n = JsonLongNumberImpl.valueOf(0);
			assertEquals(0, n.intValue());
		}
	
		@Test
		public void testOne() {
			JsonNumber n = JsonLongNumberImpl.valueOf(1);
			assertEquals(1, n.intValue());
		}
	
		@Test
		public void tetInt() {
			JsonNumber n = JsonLongNumberImpl.valueOf(123456789);
			assertEquals(123456789, n.intValue());
		}
		
		@Test
		public void testLong() {
			JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
			assertEquals((int)123456789123456789L, n.intValue());
		}
	}

	public static class IntValueExactTest {
	
		@Test
		public void success() {
			JsonNumber n = JsonLongNumberImpl.valueOf(123456789);
			assertEquals(123456789, n.intValueExact());
		}
		
		@Test(expected = ArithmeticException.class)
		public void failure() {
			JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
			n.intValueExact();
		}
	}

	public static class LongValueTest {
		
		@Test
		public void testLong() {
			JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
			assertEquals(123456789123456789L, n.longValue());
		}
	}
	
	public static class LongValueExactTest {
		
		@Test
		public void success() {
			JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
			assertEquals(123456789123456789L, n.longValueExact());
		}
	}
	
	public static class DoubleValueTest {

		@Test
		public void testDoubleValue() {
			JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
			assertEquals(123456789123456789., n.doubleValue(), 0.0001);
		}
	}
	
	public static class BigDecimalValueTest {

		@Test
		public void testBigDecimalValue() {
			JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
			assertEquals(new BigDecimal("123456789123456789"), n.bigDecimalValue());
		}
	}
	
	public static class BigIntegerValueTest {

		@Test
		public void testBigIntegerValue() {
			JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
			assertEquals(new BigInteger("123456789123456789"), n.bigIntegerValue());
		}
	}

	public static class BigIntegerValueExactTest {

		@Test
		public void success() {
			JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
			assertEquals(new BigInteger("123456789123456789"), n.bigIntegerValueExact());
		}
	}

	public static class EqualsTest {
	
		@Test
		public void equal() {
			JsonNumber n1 = JsonLongNumberImpl.valueOf(123456789123456789L);
			JsonNumber n2 = JsonLongNumberImpl.valueOf(123456789123456789L);
			assertTrue(n1.equals(n2));
		}
	
		@Test
		public void notEqual() {
			JsonNumber n1 = JsonLongNumberImpl.valueOf(123456789123456789L);
			JsonNumber n2 = JsonLongNumberImpl.valueOf(987654321987654321L);
			assertFalse(n1.equals(n2));
		}
		
		@Test
		public void longEqualsInt() {
			JsonNumber n1 = JsonLongNumberImpl.valueOf(123456789L);
			JsonNumber n2 = JsonIntNumberImpl.valueOf(123456789);
			assertTrue(n1.equals(n2));
		}
	}

	public static class ToStringTest {
	
		@Test
		public void positiveValue() {
			JsonNumber n = JsonLongNumberImpl.valueOf(9223372036854775807L);
			assertEquals("9223372036854775807", n.toString());
		}
	
		@Test
		public void negativeValue() {
			JsonNumber n = JsonLongNumberImpl.valueOf(-9223372036854775808L);
			assertEquals("-9223372036854775808", n.toString());
		}
	}
}
