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
		JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
		assertEquals(ValueType.NUMBER, n.getValueType());
	}
	
	@Test
	public void testIsIntegral() {
		JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
		assertTrue(n.isIntegral());
	}
	
	@Test
	public void testIntValue() {
		JsonNumber n = JsonLongNumberImpl.valueOf(123456789);
		assertEquals(123456789, n.intValue());
	}
	
	@Test
	public void testIntValue2() {
		JsonNumber n = JsonLongNumberImpl.valueOf(0);
		assertEquals(0, n.intValue());
	}

	@Test
	public void testIntValue3() {
		JsonNumber n = JsonLongNumberImpl.valueOf(1);
		assertEquals(1, n.intValue());
	}

	@Test
	public void testIntValue4() {
		JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
		assertEquals((int)123456789123456789L, n.intValue());
	}

	@Test
	public void testIntValueExact() {
		JsonNumber n = JsonLongNumberImpl.valueOf(123456789);
		assertEquals(123456789, n.intValueExact());
	}
	
	@Test(expected = ArithmeticException.class)
	public void testIntValueExact2() {
		JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
		n.intValueExact();
	}

	@Test
	public void testLongValue() {
		JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
		assertEquals(123456789123456789L, n.longValue());
	}
	
	@Test
	public void testLongValueExact() {
		JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
		assertEquals(123456789123456789L, n.longValueExact());
	}
	
	@Test
	public void testDoubleValue() {
		JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
		assertEquals(123456789123456789., n.doubleValue(), 0.0001);
	}
	
	@Test
	public void testBigDecimalValue() {
		JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
		assertEquals(new BigDecimal("123456789123456789"), n.bigDecimalValue());
	}
	
	@Test
	public void testBigIntegerValue() {
		JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
		assertEquals(new BigInteger("123456789123456789"), n.bigIntegerValue());
	}

	@Test
	public void testBigIntegerValueExact() {
		JsonNumber n = JsonLongNumberImpl.valueOf(123456789123456789L);
		assertEquals(new BigInteger("123456789123456789"), n.bigIntegerValueExact());
	}

	@Test
	public void testEquals() {
		JsonNumber n1 = JsonLongNumberImpl.valueOf(123456789123456789L);
		JsonNumber n2 = JsonLongNumberImpl.valueOf(123456789123456789L);
		assertTrue(n1.equals(n2));
	}

	@Test
	public void testEquals2() {
		JsonNumber n1 = JsonLongNumberImpl.valueOf(123456789123456789L);
		JsonNumber n2 = JsonLongNumberImpl.valueOf(987654321987654321L);
		assertFalse(n1.equals(n2));
	}
	
	@Test
	public void testEquals3() {
		JsonNumber n1 = JsonLongNumberImpl.valueOf(123456789L);
		JsonNumber n2 = JsonIntNumberImpl.valueOf(123456789);
		assertTrue(n1.equals(n2));
	}

	@Test
	public void testToString() {
		JsonNumber n = JsonLongNumberImpl.valueOf(9223372036854775807L);
		assertEquals("9223372036854775807", n.toString());
	}

	@Test
	public void testToString2() {
		JsonNumber n = JsonLongNumberImpl.valueOf(-9223372036854775808L);
		assertEquals("-9223372036854775808", n.toString());
	}
}
