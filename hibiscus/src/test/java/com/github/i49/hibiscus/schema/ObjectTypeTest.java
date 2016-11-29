package com.github.i49.hibiscus.schema;

import static org.junit.Assert.*;

import org.junit.Test;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;

public class ObjectTypeTest {

	public static class PropertiesTest {
		
		@Test(expected = SchemaException.class)
		public void propertyIsNull() {
			try {
				object().properties(required("name", string()), null, optional("age", integer()));
			} catch (SchemaException e) {
				throw e;
			}
		}
	}
	
	public static class PatternPropertyTest {
		
		@Test
		public void withPatternProperty() {
			
			ObjectType o = object(
				optional("1st", string()),
				optional("2nd", string()),
				optional("3rd", string()),
				pattern("[4-9]th", string())
			);
			
			assertNotNull(o.getProperty("1st"));
			assertNotNull(o.getProperty("2nd"));
			assertNotNull(o.getProperty("5th"));
			assertNull(o.getProperty("10th"));
		}
		
		@Test
		public void withoutPatternProperty() {
			
			ObjectType o = object(
					optional("1st", string()),
					optional("2nd", string()),
					optional("3rd", string())
				);
				
			assertNotNull(o.getProperty("1st"));
			assertNotNull(o.getProperty("2nd"));
			assertNotNull(o.getProperty("3rd"));
			assertNull(o.getProperty("4th"));
		}
	}
}
