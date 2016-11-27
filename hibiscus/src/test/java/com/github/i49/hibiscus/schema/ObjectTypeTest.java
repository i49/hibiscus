package com.github.i49.hibiscus.schema;

import static org.junit.Assert.*;

import org.junit.Test;

import static com.github.i49.hibiscus.schema.JsonTypes.*;

public class ObjectTypeTest {

	public static class PropertiesTest {
		
		@Test(expected = SchemaException.class)
		public void propertyIsNull() {
			try {
				object().properties(required("foo", string()), null, optional("bar", integer()));
			} catch (SchemaException e) {
				throw e;
			}
		}
	}
	
	public static class PatternPropertyTest {
		
		@Test
		public void withPatternProperty() {
			
			ObjectType o = object(
				required("foo", string()),
				optional("bar", bool()),
				pattern("a*b", integer())
			);
			
			assertNotNull(o.getProperty("foo"));
			assertNotNull(o.getProperty("bar"));
			assertNotNull(o.getProperty("aaaaab"));
			assertNull(o.getProperty("ba"));
		}
		
		@Test
		public void withoutPatternProperty() {
			
			ObjectType o = object(
					required("foo", string()),
					optional("bar", bool())
				);
				
			assertNotNull(o.getProperty("foo"));
			assertNotNull(o.getProperty("bar"));
			assertNull(o.getProperty("aaaaab"));
		}
	}
}
