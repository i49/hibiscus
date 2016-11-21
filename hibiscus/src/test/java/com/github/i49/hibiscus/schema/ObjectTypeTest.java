package com.github.i49.hibiscus.schema;

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
}
