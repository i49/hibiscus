package com.github.i49.hibiscus.json;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;
import javax.json.JsonValue.ValueType;

import org.junit.Test;

public class WritableJsonIntNumberTest {
	
	public static class GetValueTypeTest {
		
		@Test
		public void testGetValueType() {
			JsonNumber n = new WritableJsonIntNumber(123456789);
			assertEquals(ValueType.NUMBER, n.getValueType());
		}
	}

	public static class IsIntegralTest {
		
		@Test
		public void testIsIntegral() {
			JsonNumber n = new WritableJsonIntNumber(123456789);
			assertTrue(n.isIntegral());
		}
	}
	
	public static class IntValueTest {
		
		@Test
		public void testZero() {
			JsonNumber n = new WritableJsonIntNumber(0);
			assertEquals(0, n.intValue());
		}
	
		@Test
		public void testOne() {
			JsonNumber n = new WritableJsonIntNumber(1);
			assertEquals(1, n.intValue());
		}

		@Test
		public void testInt() {
			JsonNumber n = new WritableJsonIntNumber(123456789);
			assertEquals(123456789, n.intValue());
		}
	}
	
	public static class IntValueExactTest {
		
		@Test
		public void success() {
			JsonNumber n = new WritableJsonIntNumber(123456789);
			assertEquals(123456789, n.intValueExact());
		}
	}
	
	public static class LongValueTest {
	
		@Test
		public void testLongValue() {
			JsonNumber n = new WritableJsonIntNumber(123456789);
			assertEquals(123456789L, n.longValue());
		}
	}
	
	public static class LongValueExactTest {
		
		@Test
		public void testLongValueExact() {
			JsonNumber n = new WritableJsonIntNumber(123456789);
			assertEquals(123456789L, n.longValueExact());
		}
	}

	public static class DoubleValueTest {
	
		@Test
		public void testDoubleValue() {
			JsonNumber n = new WritableJsonIntNumber(123456789);
			assertEquals(123456789., n.doubleValue(), 0.0001);
		}
	}

	public static class BigDecimalValueTest {

		@Test
		public void testBigDecimalValue() {
			JsonNumber n = new WritableJsonIntNumber(123456789);
			assertEquals(new BigDecimal("123456789"), n.bigDecimalValue());
		}
	}

	public static class BigIntegerValueTest {

		@Test
		public void testBigIntegerValue() {
			JsonNumber n = new WritableJsonIntNumber(123456789);
			assertEquals(new BigInteger("123456789"), n.bigIntegerValue());
		}
	}
	
	public static class BigIntegerValueExactTest {
	
		@Test
		public void success() {
			JsonNumber n = new WritableJsonIntNumber(123456789);
			assertEquals(new BigInteger("123456789"), n.bigIntegerValueExact());
		}
	}
	
	public static class EqualsTest {
		
		@Test
		public void equal() {
			JsonNumber n1 = new WritableJsonIntNumber(123456789);
			JsonNumber n2 = new WritableJsonIntNumber(123456789);
			assertTrue(n1.equals(n2));
		}
		
		@Test
		public void notEqual() {
			JsonNumber n1 = new WritableJsonIntNumber(123456789);
			JsonNumber n2 = new WritableJsonIntNumber(987654321);
			assertFalse(n1.equals(n2));
		}
	
		@Test
		public void intEqualsLong() {
			JsonNumber n1 = new WritableJsonIntNumber(123456789);
			JsonNumber n2 = new WritableJsonLongNumber(123456789L);
			assertTrue(n1.equals(n2));
		}
	
		@Test
		public void intNotEqualLong() {
			JsonNumber n1 = new WritableJsonIntNumber(123456789);
			JsonNumber n2 = new WritableJsonLongNumber(987654321L);
			assertFalse(n1.equals(n2));
		}
	}
	
	public static class ToStringTest {
	
		@Test
		public void positiveValue() {
			JsonNumber n = new WritableJsonIntNumber(2147483647);
			assertEquals("2147483647", n.toString());
		}
	
		@Test
		public void negativeValue() {
			JsonNumber n = new WritableJsonIntNumber(-2147483648);
			assertEquals("-2147483648", n.toString());
		}
	}
}
