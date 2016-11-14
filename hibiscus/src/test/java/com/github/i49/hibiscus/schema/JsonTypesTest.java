package com.github.i49.hibiscus.schema;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.experimental.runners.Enclosed;

import static com.github.i49.hibiscus.schema.JsonTypes.*;

@RunWith(Enclosed.class)
public class JsonTypesTest {
	
	/*
	 * Test for optional() method.
	 */
	public static class OptionalTest {

		@Test
		public void normal() {
			Property p = optional("foo", string());
			assertNotNull(p);
			assertEquals("foo", p.getName());
		}
		
		@Test
		public void nameIsNull() {
			try {
				optional(null, string());
				fail();
			} catch (SchemaException e) {
				assertEquals("Property name is null.", e.getMessage());
			}
		}
		
		@Test
		public void typeIsNull() {
			try {
				optional("foo", null);
				fail();
			} catch (SchemaException e) {
				assertEquals("Property type at the index of 0 is null.", e.getMessage());
			}
		}

		@Test
		public void secondTypeIsNull() {
			try {
				optional("foo", integer(), getNull());
				fail();
			} catch (SchemaException e) {
				assertEquals("Property type at the index of 1 is null.", e.getMessage());
			}
		}
	}
	
	/*
	 * Test for required() method.
	 */
	public static class RequiredTest {

		@Test
		public void normal() {
			Property p = required("foo", string());
			assertNotNull(p);
			assertEquals("foo", p.getName());
		}
		
		@Test
		public void nameIsNull() {
			try {
				required(null, string());
				fail();
			} catch (SchemaException e) {
				assertEquals("Property name is null.", e.getMessage());
			}
		}

		@Test
		public void typeIsNull() {
			try {
				required("foo", null);
				fail();
			} catch (SchemaException e) {
				assertEquals("Property type at the index of 0 is null.", e.getMessage());
			}
		}

		@Test
		public void secondTypeIsNull() {
			try {
				required("foo", integer(), getNull());
				fail();
			} catch (SchemaException e) {
				assertEquals("Property type at the index of 1 is null.", e.getMessage());
			}
		}
	}
	
	private static JsonType getNull() {
		return null;
	}
}
