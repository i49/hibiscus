package com.github.i49.hibiscus.json;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;
import javax.json.JsonValue.ValueType;

import org.junit.Test;

public class JsonDecimalNumberImplTest {

	@Test
	public void testGetValueType() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123.45"));
		assertEquals(ValueType.NUMBER, n.getValueType());
	}

	@Test
	public void testIsIntegral() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123"));
		assertTrue(n.isIntegral());
	}
	
	@Test
	public void testIsIntegral2() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123.45"));
		assertFalse(n.isIntegral());
	}

	@Test
	public void testIntValue() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123.45"));
		assertEquals(123, n.intValue());
	}
	
	@Test
	public void testIntValueExact() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123"));
		assertEquals(123, n.intValueExact());
	}

	@Test(expected = ArithmeticException.class)
	public void testIntValueExact2() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123.45"));
		n.intValueExact();
	}

	@Test
	public void testLongValue() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123456789123456789.123"));
		assertEquals(123456789123456789L, n.longValue());
	}

	@Test
	public void testLongValueExact() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123456789123456789"));
		assertEquals(123456789123456789L, n.longValueExact());
	}

	@Test(expected = ArithmeticException.class)
	public void testLongValueExact2() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123456789123456789.123"));
		n.longValueExact();
	}

	@Test
	public void testDoubleValue() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123.45"));
		assertEquals(123.45, n.doubleValue(), 0.0001);
	}
	
	@Test
	public void testBigDecimalValue() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123.45"));
		assertEquals(new BigDecimal("123.45"), n.bigDecimalValue());
	}

	@Test
	public void testBigIntegerValue() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123456789.123"));
		assertEquals(new BigInteger("123456789"), n.bigIntegerValue());
	}

	@Test
	public void testBigIntegerValueExact() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123456789"));
		assertEquals(new BigInteger("123456789"), n.bigIntegerValue());
	}

	@Test(expected = ArithmeticException.class)
	public void testBigIntegerValueExact2() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123456789.123"));
		assertEquals(new BigInteger("123456789"), n.bigIntegerValueExact());
	}

	@Test
	public void testEquals() {
		JsonNumber n1 = JsonDecimalNumberImpl.valueOf(new BigDecimal("123.456"));
		JsonNumber n2 = JsonDecimalNumberImpl.valueOf(new BigDecimal("123.456"));
		assertTrue(n1.equals(n2));
	}

	@Test
	public void testEquals2() {
		JsonNumber n1 = JsonDecimalNumberImpl.valueOf(new BigDecimal("123.456"));
		JsonNumber n2 = JsonDecimalNumberImpl.valueOf(new BigDecimal("456.789"));
		assertFalse(n1.equals(n2));
	}
	
	@Test
	public void testEquals3() {
		JsonNumber n1 = JsonDecimalNumberImpl.valueOf(new BigDecimal("123456789"));
		JsonNumber n2 = JsonIntNumberImpl.valueOf(123456789);
		assertTrue(n1.equals(n2));
	}

	@Test
	public void testEquals4() {
		JsonNumber n1 = JsonDecimalNumberImpl.valueOf(new BigDecimal("123456789123456789"));
		JsonNumber n2 = JsonLongNumberImpl.valueOf(123456789123456789L);
		assertTrue(n1.equals(n2));
	}

	@Test
	public void testToString() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("123.456"));
		assertEquals("123.456", n.toString());
	}

	@Test
	public void testToString2() {
		JsonNumber n = JsonDecimalNumberImpl.valueOf(new BigDecimal("-123456.7890"));
		assertEquals("-123456.7890", n.toString());
	}
}