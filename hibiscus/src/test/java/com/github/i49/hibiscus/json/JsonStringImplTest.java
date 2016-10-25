package com.github.i49.hibiscus.json;

import static org.junit.Assert.*;

import javax.json.JsonString;
import javax.json.JsonValue;

import org.junit.Test;

public class JsonStringImplTest {

	@Test
	public void testGetValueType() {
		JsonString v = new JsonStringImpl("abc");
		assertEquals(JsonValue.ValueType.STRING, v.getValueType());
	}

	@Test
	public void testGetChars() {
		JsonString v = new JsonStringImpl("abc");
		assertEquals("abc", v.getChars());
	}

	@Test
	public void testGetString() {
		JsonString v = new JsonStringImpl("abc");
		assertEquals("abc", v.getString());
	}
	
	@Test
	public void testToString() {
		JsonString v1 = new JsonStringImpl("abc");
		assertEquals("\"abc\"", v1.toString());
		JsonString v2 = new JsonStringImpl("\"abc\"");
		assertEquals("\"\\\"abc\\\"\"", v2.toString());
	}
}
