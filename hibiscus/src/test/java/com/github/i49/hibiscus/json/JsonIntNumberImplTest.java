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
		JsonNumber n = JsonIntNumberImpl.valueOf(123456789);
		assertEquals(ValueType.NUMBER, n.getValueType());
	}
	
	@Test
	public void testIsIntegral() {
		JsonNumber n = JsonIntNumberImpl.valueOf(123456789);
		assertTrue(n.isIntegral());
	}
	
	@Test
	public void testIntValue() {
		JsonNumber n = JsonIntNumberImpl.valueOf(123456789);
		assertEquals(123456789, n.intValue());
	}
	
	@Test
	public void testIntValue2() {
		JsonNumber n = JsonIntNumberImpl.valueOf(0);
		assertEquals(0, n.intValue());
	}

	@Test
	public void testIntValue3() {
		JsonNumber n = JsonIntNumberImpl.valueOf(1);
		assertEquals(1, n.intValue());
	}

	@Test
	public void testIntValueExact() {
		JsonNumber n = JsonIntNumberImpl.valueOf(123456789);
		assertEquals(123456789, n.intValueExact());
	}

	@Test
	public void testLongValue() {
		JsonNumber n = JsonIntNumberImpl.valueOf(123456789);
		assertEquals(123456789L, n.longValue());
	}
	
	@Test
	public void testLongValueExact() {
		JsonNumber n = JsonIntNumberImpl.valueOf(123456789);
		assertEquals(123456789L, n.longValueExact());
	}
	
	@Test
	public void testDoubleValue() {
		JsonNumber n = JsonIntNumberImpl.valueOf(123456789);
		assertEquals(123456789., n.doubleValue(), 0.0001);
	}
	
	@Test
	public void testBigDecimalValue() {
		JsonNumber n = JsonIntNumberImpl.valueOf(123456789);
		assertEquals(new BigDecimal("123456789"), n.bigDecimalValue());
	}
	
	@Test
	public void testBigIntegerValue() {
		JsonNumber n = JsonIntNumberImpl.valueOf(123456789);
		assertEquals(new BigInteger("123456789"), n.bigIntegerValue());
	}

	@Test
	public void testBigIntegerValueExact() {
		JsonNumber n = JsonIntNumberImpl.valueOf(123456789);
		assertEquals(new BigInteger("123456789"), n.bigIntegerValueExact());
	}
	
	@Test
	public void testEquals() {
		JsonNumber n1 = JsonIntNumberImpl.valueOf(123456789);
		JsonNumber n2 = JsonIntNumberImpl.valueOf(123456789);
		assertTrue(n1.equals(n2));
	}
	
	@Test
	public void testEquals2() {
		JsonNumber n1 = JsonIntNumberImpl.valueOf(123456789);
		JsonNumber n2 = JsonIntNumberImpl.valueOf(987654321);
		assertFalse(n1.equals(n2));
	}

	@Test
	public void testEquals3() {
		JsonNumber n1 = JsonIntNumberImpl.valueOf(123456789);
		JsonNumber n2 = JsonLongNumberImpl.valueOf(123456789L);
		assertTrue(n1.equals(n2));
	}

	@Test
	public void testEquals4() {
		JsonNumber n1 = JsonIntNumberImpl.valueOf(123456789);
		JsonNumber n2 = JsonLongNumberImpl.valueOf(987654321L);
		assertFalse(n1.equals(n2));
	}

	@Test
	public void testToString() {
		JsonNumber n = JsonIntNumberImpl.valueOf(2147483647);
		assertEquals("2147483647", n.toString());
	}

	@Test
	public void testToString2() {
		JsonNumber n = JsonIntNumberImpl.valueOf(-2147483648);
		assertEquals("-2147483648", n.toString());
	}
}
