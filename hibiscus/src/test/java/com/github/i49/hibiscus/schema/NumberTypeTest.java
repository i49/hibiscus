package com.github.i49.hibiscus.schema;

import org.junit.Test;

import static com.github.i49.hibiscus.schema.JsonTypes.*;

import java.math.BigDecimal;

public class NumberTypeTest {

	public static class ValuesTest {
		
		@Test(expected = SchemaException.class)
		public void valueIsNull() {
			number().values(new BigDecimal("123.45"), new BigDecimal("678.90"), null);
		}
	}
}