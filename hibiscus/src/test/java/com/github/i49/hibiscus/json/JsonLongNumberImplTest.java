package com.github.i49.hibiscus.json;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;
import javax.json.JsonValue.ValueType;

import org.junit.Test;

public class JsonLongNumberImplTest {

	@Test
	public void testGetValueType() {
		JsonNumber n = new JsonLongNumberImpl(123456789123456789L);
		assertEquals(ValueType.NUMBER, n.getValueType());
	}
	
	@Test
	public void testIsIntegral() {
		JsonNumber n = new JsonLongNumberImpl(123456789123456789L);
		assertTrue(n.isIntegral());
	}
	
	@Test
	public void testIntValue() {
		JsonNumber n1 = new JsonLongNumberImpl(123456789);
		assertEquals(123456789, n1.intValue());

		JsonNumber n2 = new JsonLongNumberImpl(123456789123456789L);
		assertEquals((int)123456789123456789L, n2.intValue());
	}
	
	@Test
	public void testIntValueExact() {
		JsonNumber n = new JsonLongNumberImpl(123456789);
		assertEquals(123456789, n.intValueExact());
	}
	
	@Test(expected = ArithmeticException.class)
	public void testIntValueExactException() {
		JsonNumber n = new JsonLongNumberImpl(123456789123456789L);
		n.intValueExact();
	}

	@Test
	public void testLongValue() {
		JsonNumber n = new JsonLongNumberImpl(123456789123456789L);
		assertEquals(123456789123456789L, n.longValue());
	}
	
	@Test
	public void testLongValueExact() {
		JsonNumber n = new JsonLongNumberImpl(123456789123456789L);
		assertEquals(123456789123456789L, n.longValueExact());
	}
	
	@Test
	public void testDoubleValue() {
		JsonNumber n = new JsonLongNumberImpl(123456789123456789L);
		assertEquals(123456789123456789., n.doubleValue(), 0.0001);
	}
	
	@Test
	public void testBigDecimalValue() {
		JsonNumber n = new JsonLongNumberImpl(123456789123456789L);
		assertEquals(new BigDecimal("123456789123456789"), n.bigDecimalValue());
	}
	
	@Test
	public void testBigIntegerValue() {
		JsonNumber n = new JsonLongNumberImpl(123456789123456789L);
		assertEquals(new BigInteger("123456789123456789"), n.bigIntegerValue());
	}

	@Test
	public void testBigIntegerValueExact() {
		JsonNumber n = new JsonLongNumberImpl(123456789123456789L);
		assertEquals(new BigInteger("123456789123456789"), n.bigIntegerValueExact());
	}

	@Test
	public void testEquals() {
		JsonNumber n1 = new JsonLongNumberImpl(123456789123456789L);
		JsonNumber n2 = new JsonLongNumberImpl(123456789123456789L);
		assertTrue(n1.equals(n2));
	}

	@Test
	public void testNotEquals() {
		JsonNumber n1 = new JsonLongNumberImpl(123456789123456789L);
		JsonNumber n2 = new JsonLongNumberImpl(987654321987654321L);
		assertFalse(n1.equals(n2));
	}
	
	@Test
	public void testEqualsToInt() {
		JsonNumber n1 = new JsonLongNumberImpl(123456789L);
		JsonNumber n2 = new JsonIntNumberImpl(123456789);
		assertTrue(n1.equals(n2));
	}

	@Test
	public void testToString() {
		JsonNumber n1 = new JsonLongNumberImpl(9223372036854775807L);
		assertEquals("9223372036854775807", n1.toString());
		
		JsonNumber n2 = new JsonLongNumberImpl(-9223372036854775808L);
		assertEquals("-9223372036854775808", n2.toString());
	}
}
