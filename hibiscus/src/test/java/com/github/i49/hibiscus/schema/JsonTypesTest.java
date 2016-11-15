package com.github.i49.hibiscus.schema;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.experimental.runners.Enclosed;

import static com.github.i49.hibiscus.schema.JsonTypes.*;

@RunWith(Enclosed.class)
public class JsonTypesTest {
	
	/*
	 * Tests for methods to create array type.
	 */
	public static class ArrayTest {

		@Test
		public void emptyArray() {
			ArrayType array = array();
			assertNotNull(array);
		}

		@Test
		public void validTypes() {
			ArrayType array = array(integer(), string());
			assertNotNull(array);
		}
		
		@Test
		public void firstTypeIsNull() {
			try { 
				array(getNull());
				fail();
			} catch (SchemaException e) {
				assertEquals("Type at the index of 0 is null.", e.getMessage());
			}
		}

		@Test
		public void secondTypeIsNull() {
			try { 
				array(string(), getNull());
				fail();
			} catch (SchemaException e) {
				assertEquals("Type at the index of 1 is null.", e.getMessage());
			}
		}
	}
	
	/*
	 * Tests for methods to create object type.
	 */
	public static class ObjectTest {
		
		@Test
		public void emptyObject() {
			ObjectType object = object();
			assertNotNull(object);
		}
		
		@Test
		public void validProperties() {
			ObjectType object = object(required("foo", string()), optional("bar", integer()));
			assertNotNull(object);
		}
	}
	
	/*
	 * Tests for optional() method.
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
				assertEquals("Type at the index of 0 is null.", e.getMessage());
			}
		}

		@Test
		public void secondTypeIsNull() {
			try {
				optional("foo", integer(), getNull());
				fail();
			} catch (SchemaException e) {
				assertEquals("Type at the index of 1 is null.", e.getMessage());
			}
		}
		
		@Test
		public void typeIsDuplicated() {
			try {
				optional("foo", string(), string());
				fail();
			} catch (SchemaException e) {
				assertEquals("Type \"string\" at the index of 1 is duplicated.", e.getMessage());
			}
		}
	}
	
	/*
	 * Tests for required() method.
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
				assertEquals("Type at the index of 0 is null.", e.getMessage());
			}
		}

		@Test
		public void secondTypeIsNull() {
			try {
				required("foo", integer(), getNull());
				fail();
			} catch (SchemaException e) {
				assertEquals("Type at the index of 1 is null.", e.getMessage());
			}
		}

		@Test
		public void typeIsDuplicated() {
			try {
				required("foo", array(), array());
				fail();
			} catch (SchemaException e) {
				assertEquals("Type \"array\" at the index of 1 is duplicated.", e.getMessage());
			}
		}
	}
	
	private static JsonType getNull() {
		return null;
	}
}
