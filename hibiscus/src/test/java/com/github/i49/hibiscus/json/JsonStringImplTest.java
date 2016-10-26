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

		JsonString v2 = new JsonStringImpl("1\\2");
		assertEquals("\"1\\\\2\"", v2.toString());
		
		JsonString v3 = new JsonStringImpl("abc \"123\" xyz");
		assertEquals("\"abc \\\"123\\\" xyz\"", v3.toString());
		
		char[] chars = new char[] {
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
				16, 17, 18, 19, 20, 21, 22, 23, 24,25, 26, 27, 28, 29, 30, 31
				};
		JsonString v4 = new JsonStringImpl(new String(chars));
		assertEquals(
				"\"\\u0000\\u0001\\u0002\\u0003\\u0004\\u0005\\u0006\\u0007" +
				"\\b\\t\\n\\u000b\\f\\r\\u000e\\u000f" +
				"\\u0010\\u0011\\u0012\\u0013\\u0014\\u0015\\u0016\\u0017" +
				"\\u0018\\u0019\\u001a\\u001b\\u001c\\u001d\\u001e\\u001f\"",
				v4.toString());
	}
}
