package com.github.i49.hibiscus.json;

import static org.junit.Assert.*;

import javax.json.JsonString;
import javax.json.JsonValue;

import org.junit.Test;

public class WritableJsonStringTest {

	public static class GetValueTypeTest {
		
		@Test
		public void testGetValueType() {
			JsonString s = new WritableJsonString("abc");
			assertEquals(JsonValue.ValueType.STRING, s.getValueType());
		}
	}

	public static class GetCharsTest {
	
		@Test
		public void basicString() {
			JsonString s = new WritableJsonString("abc");
			assertEquals("abc", s.getChars());
		}

		@Test
		public void emptyString() {
			JsonString s = new WritableJsonString("");
			assertEquals("", s.getChars());
		}
	}

	public static class GetStringTest {
	
		@Test
		public void basicString() {
			JsonString s = new WritableJsonString("abc");
			assertEquals("abc", s.getString());
		}
		
		@Test
		public void emptyString() {
			JsonString s = new WritableJsonString("");
			assertEquals("", s.getString());
		}
	}
	
	public static class EqualsTest {
		
		@Test
		public void equal() {
			JsonString s1 = new WritableJsonString("abc123");
			JsonString s2 = new WritableJsonString("abc123");
			assertTrue(s1.equals(s2));
		}
		
		@Test
		public void notEqual() {
			JsonString s1 = new WritableJsonString("abc123");
			JsonString s2 = new WritableJsonString("123abc");
			assertFalse(s1.equals(s2));
		}

		@Test
		public void emptyString() {
			JsonString s1 = new WritableJsonString("");
			JsonString s2 = new WritableJsonString("");
			assertTrue(s1.equals(s2));
		}
	}
	
	public static class ToStringTest {
		
		@Test
		public void basicString() {
			JsonString s = new WritableJsonString("abc");
			assertEquals("\"abc\"", s.toString());
		}
	
		@Test
		public void emptyString() {
			JsonString s = new WritableJsonString("");
			assertEquals("\"\"", s.toString());
		}

		@Test
		public void backslash() {
			JsonString s = new WritableJsonString("1\\2");
			assertEquals("\"1\\\\2\"", s.toString());
		}
	
		@Test
		public void quotation() {
			JsonString s = new WritableJsonString("abc \"123\" xyz");
			assertEquals("\"abc \\\"123\\\" xyz\"", s.toString());
		}
	
		@Test
		public void controlChars() {
			char[] chars = new char[] {
					0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
					16, 17, 18, 19, 20, 21, 22, 23, 24,25, 26, 27, 28, 29, 30, 31
					};
			JsonString s = new WritableJsonString(new String(chars));
			assertEquals(
					"\"\\u0000\\u0001\\u0002\\u0003\\u0004\\u0005\\u0006\\u0007" +
					"\\b\\t\\n\\u000b\\f\\r\\u000e\\u000f" +
					"\\u0010\\u0011\\u0012\\u0013\\u0014\\u0015\\u0016\\u0017" +
					"\\u0018\\u0019\\u001a\\u001b\\u001c\\u001d\\u001e\\u001f\"",
					s.toString());
		}
	}
}
