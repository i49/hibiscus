package com.github.i49.hibiscus.json;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;
import javax.json.JsonValue.ValueType;

import org.junit.Test;

public class WritableJsonDecimalNumberTest {

	public static class GetValueTypeTest {
		
		@Test
		public void testGetValueType() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123.45"));
			assertEquals(ValueType.NUMBER, n.getValueType());
		}
	}
	
	public static class IsIntegralTest {
		
		@Test
		public void integral() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123"));
			assertTrue(n.isIntegral());
		}
		
		@Test
		public void notIntegral() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123.45"));
			assertFalse(n.isIntegral());
		}
	}

	public static class IntValueTest {
		
		@Test
		public void testIntValue() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123.45"));
			assertEquals(123, n.intValue());
		}
	}

	public static class IntValueExactTest {
		
		@Test
		public void success() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123"));
			assertEquals(123, n.intValueExact());
		}
	
		@Test(expected = ArithmeticException.class)
		public void failure() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123.45"));
			n.intValueExact();
		}
	}

	public static class LongValueTest {
		
		@Test
		public void testLongValue() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123456789123456789.123"));
			assertEquals(123456789123456789L, n.longValue());
		}
	}
	
	public static class LongValueExactTest {
		
		@Test
		public void success() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123456789123456789"));
			assertEquals(123456789123456789L, n.longValueExact());
		}
	
		@Test(expected = ArithmeticException.class)
		public void failure() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123456789123456789.123"));
			n.longValueExact();
		}
	}

	public static class DoubleValueTest {
		
		@Test
		public void testDoubleValue() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123.45"));
			assertEquals(123.45, n.doubleValue(), 0.0001);
		}
	}
	
	public static class BigDecimalValueTest {

		@Test
		public void testBigDecimalValue() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123.45"));
			assertEquals(new BigDecimal("123.45"), n.bigDecimalValue());
		}
	}
	
	public static class BigIntegerValueTest {

		@Test
		public void testBigIntegerValue() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123456789.123"));
			assertEquals(new BigInteger("123456789"), n.bigIntegerValue());
		}
	}
	
	public static class BigIntegerValueExactTest {
		
		@Test
		public void success() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123456789"));
			assertEquals(new BigInteger("123456789"), n.bigIntegerValue());
		}
	
		@Test(expected = ArithmeticException.class)
		public void failure() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123456789.123"));
			assertEquals(new BigInteger("123456789"), n.bigIntegerValueExact());
		}
	}

	public static class EqualsTest {
		
		@Test
		public void decimalEqualsDecimal() {
			JsonNumber n1 = new WritableJsonDecimalNumber(new BigDecimal("123.456"));
			JsonNumber n2 = new WritableJsonDecimalNumber(new BigDecimal("123.456"));
			assertTrue(n1.equals(n2));
		}
	
		@Test
		public void decimalNotEqualsDecimal() {
			JsonNumber n1 = new WritableJsonDecimalNumber(new BigDecimal("123.456"));
			JsonNumber n2 = new WritableJsonDecimalNumber(new BigDecimal("456.789"));
			assertFalse(n1.equals(n2));
		}
		
		@Test
		public void decimalEqualsInt() {
			JsonNumber n1 = new WritableJsonDecimalNumber(new BigDecimal("123456789"));
			JsonNumber n2 = new WritableJsonIntNumber(123456789);
			assertTrue(n1.equals(n2));
		}
	
		@Test
		public void decimalEqualsLong() {
			JsonNumber n1 = new WritableJsonDecimalNumber(new BigDecimal("123456789123456789"));
			JsonNumber n2 = new WritableJsonLongNumber(123456789123456789L);
			assertTrue(n1.equals(n2));
		}
	}
	
	public static class ToStringTest {
		
		@Test
		public void positveValue() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("123.456"));
			assertEquals("123.456", n.toString());
		}
	
		@Test
		public void negativeValue() {
			JsonNumber n = new WritableJsonDecimalNumber(new BigDecimal("-123456.7890"));
			assertEquals("-123456.7890", n.toString());
		}
	}
}