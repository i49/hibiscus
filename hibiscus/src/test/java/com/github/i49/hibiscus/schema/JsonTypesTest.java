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
		
		@Test(expected = SchemaException.class)
		public void firstTypeIsNull() {
			try { 
				array(getNull());
			} catch (SchemaException e) {
				assertEquals("Type at the index of 0 is null.", e.getMessage());
				throw e;
			}
		}

		@Test(expected = SchemaException.class)
		public void secondTypeIsNull() {
			try { 
				array(string(), getNull());
			} catch (SchemaException e) {
				assertEquals("Type at the index of 1 is null.", e.getMessage());
				throw e;
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
		
		@Test(expected = SchemaException.class)
		public void firstPropertyIsNull() {
			try {
				object(null, optional("bar", integer()));
			} catch (SchemaException e) {
				assertEquals("Object property specified at the index of 0 is null.", e.getMessage());
				throw e;
			}
		}

		@Test(expected = SchemaException.class)
		public void secondPropertyIsNull() {
			try {
				object(required("foo", string()), null);
			} catch (SchemaException e) {
				assertEquals("Object property specified at the index of 1 is null.", e.getMessage());
				throw e;
			}
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
		
		@Test(expected = SchemaException.class)
		public void nameIsNull() {
			try {
				optional(null, string());
			} catch (SchemaException e) {
				assertEquals("Property name is null.", e.getMessage());
				throw e;
			}
		}
		
		@Test(expected = SchemaException.class)
		public void typeIsNull() {
			try {
				optional("foo", null);
			} catch (SchemaException e) {
				assertEquals("Type at the index of 0 is null.", e.getMessage());
				throw e;
			}
		}

		@Test(expected = SchemaException.class)
		public void secondTypeIsNull() {
			try {
				optional("foo", integer(), getNull());
			} catch (SchemaException e) {
				assertEquals("Type at the index of 1 is null.", e.getMessage());
				throw e;
			}
		}
		
		@Test(expected = SchemaException.class)
		public void typeIsDuplicated() {
			try {
				optional("foo", string(), string());
			} catch (SchemaException e) {
				assertEquals("Type \"string\" at the index of 1 is duplicated.", e.getMessage());
				throw e;
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
		
		@Test(expected = SchemaException.class)
		public void nameIsNull() {
			try {
				required(null, string());
			} catch (SchemaException e) {
				assertEquals("Property name is null.", e.getMessage());
				throw e;
			}
		}

		@Test(expected = SchemaException.class)
		public void typeIsNull() {
			try {
				required("foo", null);
			} catch (SchemaException e) {
				assertEquals("Type at the index of 0 is null.", e.getMessage());
				throw e;
			}
		}

		@Test(expected = SchemaException.class)
		public void secondTypeIsNull() {
			try {
				required("foo", integer(), getNull());
			} catch (SchemaException e) {
				assertEquals("Type at the index of 1 is null.", e.getMessage());
				throw e;
			}
		}

		@Test(expected = SchemaException.class)
		public void typeIsDuplicated() {
			try {
				required("foo", array(), array());
			} catch (SchemaException e) {
				assertEquals("Type \"array\" at the index of 1 is duplicated.", e.getMessage());
				throw e;
			}
		}
	}
	
	private static JsonType getNull() {
		return null;
	}
}
