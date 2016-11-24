package com.github.i49.hibiscus.schema;

import org.junit.Test;

import static com.github.i49.hibiscus.schema.JsonTypes.*;

public class ArrayTypeTest {

	public static class ItemsTest {
		
		@Test(expected = SchemaException.class)
		public void itemIsNull() {
			array().items(string(), getNull(), integer());
		}
	}
	
	public static class MinItemsTest {
	
		@Test(expected = SchemaException.class)
		public void sizeIsNegative() {
			array().minLength(-1);
		}
	}
	
	public static class MaxItemsTest {
		
		@Test(expected = SchemaException.class)
		public void sizeIsNegative() {
			array().maxLength(-1);
		}
	}

	private static JsonType getNull() {
		return null;
	}
}
