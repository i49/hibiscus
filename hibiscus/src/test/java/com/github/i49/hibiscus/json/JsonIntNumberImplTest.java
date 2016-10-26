package com.github.i49.hibiscus.json;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;
import javax.json.JsonValue.ValueType;

import org.junit.Test;

public class JsonIntNumberImplTest {

	@Test
	public void testGetValueType() {
		JsonNumber n = new JsonIntNumberImpl(123456789);
		assertEquals(ValueType.NUMBER, n.getValueType());
	}
	
	@Test
	public void testIsIntegral() {
		JsonNumber n = new JsonIntNumberImpl(123456789);
		assertTrue(n.isIntegral());
	}
	
	@Test
	public void testIntValue() {
		JsonNumber n = new JsonIntNumberImpl(123456789);
		assertEquals(123456789, n.intValue());
	}
	
	@Test
	public void testIntValueExact() {
		JsonNumber n = new JsonIntNumberImpl(123456789);
		assertEquals(123456789, n.intValueExact());
	}

	@Test
	public void testLongValue() {
		JsonNumber n = new JsonIntNumberImpl(123456789);
		assertEquals(123456789L, n.longValue());
	}
	
	@Test
	public void testLongValueExact() {
		JsonNumber n = new JsonIntNumberImpl(123456789);
		assertEquals(123456789L, n.longValueExact());
	}
	
	@Test
	public void testDoubleValue() {
		JsonNumber n = new JsonIntNumberImpl(123456789);
		assertEquals(123456789., n.doubleValue(), 0.0001);
	}
	
	@Test
	public void testBigDecimalValue() {
		JsonNumber n = new JsonIntNumberImpl(123456789);
		assertEquals(new BigDecimal("123456789"), n.bigDecimalValue());
	}
	
	@Test
	public void testBigIntegerValue() {
		JsonNumber n = new JsonIntNumberImpl(123456789);
		assertEquals(new BigInteger("123456789"), n.bigIntegerValue());
	}

	@Test
	public void testBigIntegerValueExact() {
		JsonNumber n = new JsonIntNumberImpl(123456789);
		assertEquals(new BigInteger("123456789"), n.bigIntegerValueExact());
	}
	
	@Test
	public void testEquals() {
		JsonNumber n1 = new JsonIntNumberImpl(123456789);
		JsonNumber n2 = new JsonIntNumberImpl(123456789);
		assertTrue(n1.equals(n2));
	}
	
	@Test
	public void testNotEquals() {
		JsonNumber n1 = new JsonIntNumberImpl(123456789);
		JsonNumber n2 = new JsonIntNumberImpl(987654321);
		assertFalse(n1.equals(n2));
	}

	@Test
	public void testEqualsToLong() {
		JsonNumber n1 = new JsonIntNumberImpl(123456789);
		JsonNumber n2 = new JsonLongNumberImpl(123456789L);
		assertTrue(n1.equals(n2));
	}

	@Test
	public void testToString() {
		JsonNumber n1 = new JsonIntNumberImpl(2147483647);
		assertEquals("2147483647", n1.toString());
		
		JsonNumber n2 = new JsonIntNumberImpl(-2147483648);
		assertEquals("-2147483648", n2.toString());
	}
}
