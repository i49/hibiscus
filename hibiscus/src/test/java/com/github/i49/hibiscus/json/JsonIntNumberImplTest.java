package com.github.i49.hibiscus.json;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;

import org.junit.Test;

public class JsonIntNumberImplTest {

	@Test
	public void testIsIntegral() {
		JsonNumber n = new JsonIntNumberImpl(123);
		assertTrue(n.isIntegral());
	}
	
	@Test
	public void testIntValue() {
		JsonNumber n = new JsonIntNumberImpl(123);
		assertEquals(123, n.intValue());
	}
	
	@Test
	public void testIntValueExact() {
		JsonNumber n = new JsonIntNumberImpl(123);
		assertEquals(123, n.intValueExact());
	}

	@Test
	public void testLongValue() {
		JsonNumber n = new JsonIntNumberImpl(123);
		assertEquals(123L, n.longValue());
	}
	
	@Test
	public void testLongValueExact() {
		JsonNumber n = new JsonIntNumberImpl(123);
		assertEquals(123L, n.longValueExact());
	}
	
	@Test
	public void testDoubleValue() {
		JsonNumber n = new JsonIntNumberImpl(123);
		assertEquals(123.0, n.doubleValue(), 0.0001);
	}
	
	@Test
	public void testBigDecimalValue() {
		JsonNumber n = new JsonIntNumberImpl(123);
		assertEquals(new BigDecimal("123"), n.bigDecimalValue());
	}
	
	@Test
	public void testBigIntegerValue() {
		JsonNumber n = new JsonIntNumberImpl(123);
		assertEquals(new BigInteger("123"), n.bigIntegerValue());
	}

	@Test
	public void testBigIntegerValueExact() {
		JsonNumber n = new JsonIntNumberImpl(123);
		assertEquals(new BigInteger("123"), n.bigIntegerValueExact());
	}

	@Test
	public void testToString() {
		JsonNumber n1 = new JsonIntNumberImpl(2147483647);
		assertEquals("2147483647", n1.toString());
		
		JsonNumber n2 = new JsonIntNumberImpl(-2147483648);
		assertEquals("-2147483648", n2.toString());
	}
}
